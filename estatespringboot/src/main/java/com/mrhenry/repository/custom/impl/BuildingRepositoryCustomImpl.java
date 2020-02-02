package com.mrhenry.repository.custom.impl;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.entity.BuildingEntity;
import com.mrhenry.repository.custom.BuildingRepositoryCustom;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;

@Repository
public class BuildingRepositoryCustomImpl implements BuildingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BuildingEntity> findAll(BuildingSearchBuilder builder, Pageable pageable) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM building AS dao WHERE 1=1");

            Map<String, Object> properties = buildingMapSearch(builder);
            StringBuilder whereClause = createWhereClause(builder);
            sql = createSQLFindAll(sql, properties);
            sql.append(whereClause);
            Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
            if (pageable != null) {
                query.setFirstResult((int) pageable.getOffset());
                query.setMaxResults(pageable.getPageSize());
            }
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public Long countAll(BuildingSearchBuilder builder) {

        try {
            StringBuilder sql = new StringBuilder("SELECT COUNT(dao.id) FROM building AS dao WHERE 1=1");

            Map<String, Object> properties = buildingMapSearch(builder);
            StringBuilder whereClause = createWhereClause(builder);
            sql = createSQLFindAll(sql, properties);
            sql.append(whereClause.toString());
            Query query = entityManager.createNativeQuery(sql.toString());

            List<BigInteger> resultList = query.getResultList();

            return Long.parseLong(resultList.get(0).toString(), 10);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0L;
    }

    private Map<String, Object> buildingMapSearch(BuildingSearchBuilder builder) {
        Map<String, Object> properties = new HashMap<>();
        Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
        for(Field field : fields) {
            if(!field.getName().equals("buildingTypes") && !field.getName().startsWith("areaRent")
                    && !field.getName().startsWith("costRent")) {
                field.setAccessible(true);
                try {
                    Object value = field.get(builder);
                    if(value != null) {
                        if(field.getName().equals("numberOfBasement") || field.getName().equals("buildingArea")) {
                            if(!((String) value).equals("")) {
                                properties.put(field.getName().toLowerCase(), Integer.valueOf((String)value));
                            }
                        } else {
                            properties.put(field.getName().toLowerCase(), value);
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    private StringBuilder createWhereClause(BuildingSearchBuilder builder) {
        StringBuilder whereClause = new StringBuilder();
        if(StringUtils.isNotBlank(builder.getCostRentFrom())) {
            whereClause.append("AND costrent >= "+builder.getCostRentFrom()+"");
        }
        if(StringUtils.isNotBlank(builder.getCostRentTo())) {
            whereClause.append("AND costrent <= "+builder.getCostRentTo()+"");
        }

        if(StringUtils.isNotBlank(builder.getAreaRentFrom()) || StringUtils.isNotBlank(builder.getAreaRentTo())) {
            whereClause.append(" AND EXISTS (SELECT * FROM rentarea ra WHERE (ra.buildingid = dao.id ");
            if(StringUtils.isNotBlank(builder.getAreaRentFrom())) {
                whereClause.append(" AND ra.value >= '"+builder.getAreaRentFrom()+"'");
            }
            if(StringUtils.isNotBlank(builder.getAreaRentTo())) {
                whereClause.append(" AND ra.value <= '"+builder.getAreaRentTo()+"'");
            }
            whereClause.append(" ))");
        }
        String[] buildingTypes = builder.getBuildingTypes();
        //jva 8
        if(buildingTypes.length > 0) {
            whereClause.append(" AND (dao.type LIKE '%"+buildingTypes[0]+"%'");
            Arrays.stream(buildingTypes).filter(item -> !item.equals(buildingTypes[0]))
                    .forEach(item -> whereClause.append(" OR dao.type LIKE '%"+item+"%'"));
            whereClause.append(")");
        }
        return whereClause;
    }

    protected StringBuilder createSQLFindAll(StringBuilder sql, Map<String, Object> properties) {
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
        return sql;
    }
}
