package com.mrhenry.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.mrhenry.annotation.Column;
import com.mrhenry.annotation.Entity;
import com.mrhenry.mapper.ResultSetMapper;
import com.mrhenry.paging.Pageable;
import com.mrhenry.paging.Sorter;
import com.mrhenry.repository.GenericJDBC;

public class AbstractJDBC<T> implements GenericJDBC<T>{

	private Class<T> zClass;
	
	@SuppressWarnings("unchecked")
	AbstractJDBC(){
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType paramerterizedType = (ParameterizedType) type;
		zClass = (Class<T>) paramerterizedType.getActualTypeArguments()[0];
	}
	
	private Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String databaseURL = "jdbc:mysql://localhost:3306/estatejspservlet";
			String user = "root";
			String password = "vuphuong2811";
			return DriverManager.getConnection(databaseURL, user, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*@Override
	public List<T> query(Object... parameters) {
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		String tableName = zClass.getAnnotation(Entity.class).name();
		String sql = "";
		if(tableName != null) {
			sql = "SELECT * FROM "+tableName;
		}
		
		try(Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery()){
			
			if(conn != null) {
				return resultSetMapper.mapRow(resultSet, zClass);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}*/

	@Override
	public Long insert(Object entity) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			String sql = createSQLInsert();
			
			statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			if(conn != null) {
				//set parameter to statement
				Class<?> entityClass = entity.getClass();
				Field[] fields = entityClass.getDeclaredFields();
				for(int i=0; i < fields.length; i++) {
					int index = i + 1;
					Field field = fields[i];
					field.setAccessible(true);
					statement.setObject(index, field.get(entity));
				}
				
				int parentIndex = fields.length + 1;
				Class<?> parentClass = entityClass.getSuperclass();
				while(parentClass != null) {
					Field[] parentFields = parentClass.getDeclaredFields();
					for(int i=0; i < parentFields.length; i++) {
						Field field = parentFields[i];
						field.setAccessible(true);
						statement.setObject(parentIndex, field.get(entity));
						parentIndex++;
					}
					parentClass = parentClass.getSuperclass();
				}
				
				int rowsInserted = statement.executeUpdate();
				conn.commit();
				resultSet = statement.getGeneratedKeys();
				if(rowsInserted > 0) {
					while(resultSet.next()) {
						Long id = resultSet.getLong(1);
						return id;
					}
				}
			}
			
		} catch(SQLException | IllegalAccessException e) {
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(statement != null) {
					statement.close();
				}
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	private String createSQLInsert() {
		
		String tableName = "";
		
		if(zClass.isAnnotationPresent(Entity.class)) {
			tableName = zClass.getAnnotation(Entity.class).name();
		}
		
		StringBuilder fields = new StringBuilder("");
		StringBuilder params = new StringBuilder("");
		
		for(Field field : zClass.getDeclaredFields()) {
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				
				if(fields.length() > 0) {
					fields.append(",");
					params.append(",");
				}
				
				fields.append(column.name());
				params.append("?");
			}
		}
		
		//fields in parent class
		Class<?> parentClass = zClass.getSuperclass();
		while(parentClass != null) {
			for(Field field: parentClass.getDeclaredFields()) {
				if(field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					
					if(fields.length() > 0) {
						fields.append(",");
						params.append(",");
					}
					
					fields.append(column.name());
					params.append("?");
				}
			}
			parentClass = parentClass.getSuperclass();
		}
		
		
		String sql = "INSERT INTO "+tableName+"("+fields.toString()+")"+"VALUES("+params.toString()+")";
		
		return sql;
	}

	@Override
	public void update(Object entity, Long id) {
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			String sql = createSQLUpdate();
			
			statement = conn.prepareStatement(sql);
			
			if(conn != null) {
				//set parameter to statement
				Class<?> entityClass = entity.getClass();
				Field[] fields = entityClass.getDeclaredFields();
				for(int i=0; i < fields.length; i++) {
					int index = i + 1;
					Field field = fields[i];
					field.setAccessible(true);
					statement.setObject(index, field.get(entity));
				}
				
				int parentIndex = fields.length + 1;
				Class<?> parentClass = entityClass.getSuperclass();
				while(parentClass != null) {
					Field[] parentFields = parentClass.getDeclaredFields();
					for(int i=0; i < parentFields.length; i++) {
						Field field = parentFields[i];
						field.setAccessible(true);
						if(!field.getName().equals("id")) {
							statement.setObject(parentIndex, field.get(entity));
							parentIndex++;
						} 
					}
					parentClass = parentClass.getSuperclass();
				}
				
				statement.setObject(parentIndex, id);
				statement.executeUpdate();
				conn.commit();
			}
			
		} catch(SQLException | IllegalAccessException e) {
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(statement != null) {
					statement.close();
				}
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	private String createSQLUpdate() {
		
		String tableName = "";
		
		if(zClass.isAnnotationPresent(Entity.class)) {
			tableName = zClass.getAnnotation(Entity.class).name();
		}
		
		StringBuilder sets = new StringBuilder("");
		String where = " WHERE ";
		
		for(Field field : zClass.getDeclaredFields()) {
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				String columnName = column.name();
				String setValue = columnName +"= ?"; 
				if(!columnName.equals("id")){
					if(sets.length() > 0) {
						sets.append(",");
					}
					sets.append(setValue);
				} else {
					where += setValue;
				}
			}
		}
		
		//fields in parent class
		Class<?> parentClass = zClass.getSuperclass();
		while(parentClass != null) {
			for(Field field: parentClass.getDeclaredFields()) {
				if(field.isAnnotationPresent(Column.class)) {
					
					Column column = field.getAnnotation(Column.class);
					String columnName = column.name();
					String setValue = columnName +"= ?"; 
					if(!columnName.equals("id")){
						if(sets.length() > 0) {
							sets.append(",");
						}
						sets.append(setValue);
					} else {
						where += setValue;
					}
				}
			}
			parentClass = parentClass.getSuperclass();
		}
		
		
		String sql = "UPDATE "+tableName+" SET "+sets.toString()+" "+ where;
		
		return sql;
	}

	@Override
	public T findById(Long id) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		
		String tableName = zClass.getAnnotation(Entity.class).name();
		String sql = "";
		if(tableName != null) {
			sql = "SELECT * FROM "+tableName+" WHERE id=?";
		}
		
		try{
			conn = getConnection();
			statement = conn.prepareStatement(sql);
			statement.setObject(1, id);
			ResultSet resultSet = statement.executeQuery();
			if(conn != null) {
				return resultSetMapper.mapRow(resultSet, zClass).get(0);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(statement != null) {
					statement.close();
				}
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void delete(Long id, String sql) {
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);


			String tableName = zClass.getAnnotation(Entity.class).name();
			String sqlQuery = "";
			if(sql != null) {
				sqlQuery = sql;
			} else {
				if(tableName != null) {
					sqlQuery = "DELETE FROM "+tableName+" WHERE id=?";
				}
			}
			
			statement = conn.prepareStatement(sqlQuery);
			
			if(conn != null) {
				statement.setObject(1, id);
				statement.executeUpdate();
				conn.commit();
			}
			
		} catch(SQLException e) {
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(statement != null) {
					statement.close();
				}
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public List<T> findAll(Map<String, Object> properties, Pageable pageable, Object ... where) {
		Connection conn = null;
		Statement statement = null;
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		StringBuilder sql = createSQLFindAll(properties);
		if(where != null && where.length >0) {
			sql.append(where[0]);
		}
		
		if(pageable != null) {
			if(pageable.getSorter() != null && StringUtils.isNotBlank(pageable.getSorter().getSortName())) {
				Sorter sorter = pageable.getSorter();
				sql.append(" ORDER BY "+sorter.getSortName()+" "+sorter.getSortBy());
			}
			if(pageable.getLimit() != null && pageable.getOffset() != null) {
				sql.append(" LIMIT "+pageable.getOffset()+" , "+pageable.getLimit());
			}
		}
		
		try{
			conn = getConnection();
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql.toString());
			if(conn != null) {
				return resultSetMapper.mapRow(resultSet, zClass);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(statement != null) {
					statement.close();
				}
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	protected StringBuilder createSQLFindAll(Map<String, Object> properties) {
		String tableName = zClass.getAnnotation(Entity.class).name();
		StringBuilder sql = new StringBuilder();
		if(tableName != null) {
			sql.append("SELECT * FROM "+tableName+" dao WHERE 1 = 1 ");
			if(properties != null && properties.size() > 0) {
				String[] columns = new String[properties.size()];
				Object[] values = new Object[properties.size()];
				int index = 0;
				for(Map.Entry<?, ?> item: properties.entrySet()) {
					columns[index] = (String) item.getKey();
					values[index] = item.getValue();
					index++;
				}
				
				for(int i = 0; i < columns.length; i++) {
					if(values[i] instanceof String) {
						sql.append("AND LOWER("+columns[i]+") LIKE '%"+values[i]+"%'");
					} else if(values[i] instanceof Integer || values[i] instanceof Long) {
						sql.append("AND LOWER("+columns[i]+") = "+values[i]+" ");
					}
				}
			}
		}
		return sql;
	}

	@Override
	public Integer countAll(Map<String, Object> properties, Object... where) {
		Connection conn = null;
		Statement statement = null;
		StringBuilder sql = createSQLCountAll(properties);
		if(where != null && where.length >0) {
			sql.append(where[0]);
		}
		
		try{
			conn = getConnection();
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql.toString());
			if(conn != null) {
				while(resultSet.next()) {
					return resultSet.getInt(1);
				}
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(statement != null) {
					statement.close();
				}
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		return 0;
	}
	
	protected StringBuilder createSQLCountAll(Map<String, Object> properties) {
		String tableName = zClass.getAnnotation(Entity.class).name();
		StringBuilder sql = new StringBuilder();
		if(tableName != null) {
			sql.append("SELECT COUNT(dao.id) FROM "+tableName+" dao WHERE 1 = 1 ");
			if(properties != null && properties.size() > 0) {
				String[] columns = new String[properties.size()];
				Object[] values = new Object[properties.size()];
				int index = 0;
				for(Map.Entry<?, ?> item: properties.entrySet()) {
					columns[index] = (String) item.getKey();
					values[index] = item.getValue();
					index++;
				}
				
				for(int i = 0; i < columns.length; i++) {
					if(values[i] instanceof String) {
						sql.append("AND LOWER("+columns[i]+") LIKE '%"+values[i]+"%'");
					} else if(values[i] instanceof Integer || values[i] instanceof Long) {
						sql.append("AND LOWER("+columns[i]+") = "+values[i]+" ");
					}
				}
			}
		}
		return sql;
	}
}
