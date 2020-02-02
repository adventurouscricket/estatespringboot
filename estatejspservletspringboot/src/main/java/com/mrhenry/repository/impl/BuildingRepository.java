package com.mrhenry.repository.impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.entity.BuildingEntity;
import com.mrhenry.paging.Pageable;
import com.mrhenry.repository.IBuildingRepository;

public class BuildingRepository extends AbstractJDBC<BuildingEntity> implements IBuildingRepository{
	
	public List<BuildingEntity> findAll(BuildingSearchBuilder builder, Pageable pageable) {
		Map<String, Object> properties = buildingMapSearch(builder);
		StringBuilder whereClause = createWhereClause(builder);
		return super.findAll(properties, pageable, whereClause);
	}
	
	private Map<String, Object> buildingMapSearch(BuildingSearchBuilder builder) {
		Map<String, Object> properties = new HashMap<>();
		Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
		for(Field field : fields) {
			if(!field.getName().equals("buildingTypes") && !field.getName().startsWith("areaRent") 
			&& !field.getName().startsWith("costRent")) {
//				result.put(field.getName().toLowerCase(), getValue(field, builder));
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

	@Override
	public Integer countAll(BuildingSearchBuilder builder) {
		Map<String, Object> properties = buildingMapSearch(builder);
		StringBuilder whereClause = createWhereClause(builder);
		return super.countAll(properties, whereClause);
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
}
