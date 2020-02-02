package com.mrhenry.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.service.IBuildingService;
import com.mrhenry.utils.DataUtil;
import com.mrhenry.utils.FormUtil;

@WebServlet(urlPatterns = "/admin-building")
public class BuildingController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(BuildingController.class);

	@Inject
	private IBuildingService buildingService;
	
	/*public BuildingController() {
		buildingService = new BuildingService();
	}*/
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BuildingDTO model = FormUtil.toModel(BuildingDTO.class, request);
		String url = "";
		if(model.getAction().equals("list")) {
			url="/views/building/list.jsp";
			BuildingSearchBuilder builder = initBuilder(model);
			String finAllAPIStr="http://localhost:8087/api/building?page="+model.getPage()+"&maxPageItem="+ model.getMaxPageItem();
			String countAllAPIStr="http://localhost:8087/api/building/count";
			StringBuilder findAllAPI = initBuildingParams(finAllAPIStr, builder);
			
			if(StringUtils.isNotBlank(model.getSortName())) {
				findAllAPI.append("&sortName="+model.getSortName()+"&sortBy="+model.getSortBy());
			}
			
			StringBuilder countAllAPI = initBuildingParams(countAllAPIStr, builder);
			/*old code*/
			/*Pageable pageable = new PageRequest(model.getPage(), model.getMaxPageItem(), new Sorter(model.getSortName(), model.getSortBy()));
			model.setTotalItem(buildingService.countAll(builder));
			model.setTotalPage((int) Math.ceil((double) model.getTotalItem()/model.getMaxPageItem()));
			model.setResults(buildingService.findAll(builder, pageable));*/

			/*new code*/
			model.setTotalItem(buildingService.countAll(countAllAPI.toString()));
			model.setTotalPage((int) Math.ceil((double) model.getTotalItem()/model.getMaxPageItem()));
			model.setResults(buildingService.findAll(findAllAPI.toString()));
			
		} else if(model.getAction().equals("edit")) {
			if(model.getId() != null) {
//				model = buildingService.findById(model.getId());
				
				String findByIdAPI = "http://localhost:8087/api/building/"+model.getId();
				model = buildingService.findById(findByIdAPI);
			}
			url="/views/building/edit.jsp";
		}
		request.setAttribute("model", model);
		request.setAttribute("districts", DataUtil.getDistricts());
		request.setAttribute("buildingTypes", DataUtil.getBuildingTypes());
		request.setAttribute("models", model.getResults());
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	private StringBuilder initBuildingParams(String url, BuildingSearchBuilder builder) {

		StringBuilder findAllAPI = new StringBuilder(url);

		//log
		logger.info("begin add parameter to URL");

		Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
		for (Field field: fields) {
			field.setAccessible(true);
			try {
				if(field.get(builder) != null) {
					if(field.getName().equals("buildingTypes")) {
						String[] buildingTypes = (String[]) field.get(builder);
						if(buildingTypes.length > 0) {
							findAllAPI.append("&buildingTypes="+buildingTypes[0]);
							Arrays.stream(buildingTypes).filter(item -> !item.equals(buildingTypes[0]))
									.forEach(item -> findAllAPI.append(","+item));
						}
					} else {
						findAllAPI.append("&"+field.getName()+"="+field.get(builder));
					}
				}
			}catch (IllegalAccessException e) {
				logger.error("ERROR when adding parameter to URL: "+ e.getMessage());
			}
		}
		logger.info("URL: "+findAllAPI);
		return findAllAPI;
	}

	private BuildingSearchBuilder initBuilder(BuildingDTO model) {
		BuildingSearchBuilder builder = new BuildingSearchBuilder.Builder()
				.setName(model.getName()).setWard(model.getWard()).setStreet(model.getStreet())
				.setAreaRentFrom(model.getAreaRentFrom()).setAreaRentTo(model.getAreaRentTo())
				.setCostRentFrom(model.getCostRentFrom()).setCostRentTo(model.getCostRentTo())
				.setNumberOfBasement(model.getNumberOfBasement()).setBuildingTypes(model.getBuildingTypes())
				.setDistrict(model.getDistrict()).setBuildingArea(model.getBuildingArea()).setDirection(model.getDirection())
				.build();
		return builder;
	}
}
