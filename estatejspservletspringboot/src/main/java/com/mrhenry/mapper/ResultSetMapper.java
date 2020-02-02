package com.mrhenry.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.mrhenry.annotation.Column;
import com.mrhenry.annotation.Entity;

public class ResultSetMapper<T> {
	public List<T> mapRow(ResultSet rs, Class<T> zClass) {
		List<T> resutls = new ArrayList<>();
		try {
			if(zClass.isAnnotationPresent(Entity.class)) {
				ResultSetMetaData rsMetaData = rs.getMetaData();
				Field[] fields = zClass.getDeclaredFields();
				
				//row
				while(rs.next()) {
					T object = (T) zClass.newInstance();
					//get metadata in row
					for(int i = 0; i < rsMetaData.getColumnCount(); i++) {
						String columnName = rsMetaData.getColumnName(i + 1);
						Object columnValue = rs.getObject(i+1);
						
						//current class
						for(Field field: fields) {
							if(convertResultSetToEntity(field, columnName, columnValue, object)) break;
						}
						
						//parent class
						Class<? super T> parentClass = zClass.getSuperclass();
						while(parentClass != null) {
							Field[] parentFields = parentClass.getDeclaredFields();
							for(Field field: parentFields) {
								if(convertResultSetToEntity(field, columnName, columnValue, object)) break;
							}
							parentClass = parentClass.getSuperclass();
						}
					}
					resutls.add(object);
				}
			}
		} catch (Exception e) {
			resutls = null;
			e.printStackTrace();
		}
		
		return resutls;
	}

	private Boolean convertResultSetToEntity(Field field, String columnName, Object columnValue, T object) {
		Boolean flag = false;	
		if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				if(column.name().equals(columnName) && columnValue != null){
					try {
						BeanUtils.setProperty(object, field.getName(), columnValue);
						flag = true;
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
				}
			}
		}
		return flag;
	}
}
