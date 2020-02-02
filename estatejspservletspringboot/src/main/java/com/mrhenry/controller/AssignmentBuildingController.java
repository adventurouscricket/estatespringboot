package com.mrhenry.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mrhenry.dto.AssignmentBuildingDTO;
import com.mrhenry.service.IAssignmentBuildingService;
import com.mrhenry.utils.FormUtil;

@WebServlet(urlPatterns = "/admin-building-assignment")
public class AssignmentBuildingController extends HttpServlet{
	
	private static final long serialVersionUID = 4135544763297945489L;

	final static Logger logger = Logger.getLogger(BuildingController.class);
	
	@Inject
	private IAssignmentBuildingService assignmentBuildingService;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AssignmentBuildingDTO model = FormUtil.toModel(AssignmentBuildingDTO.class, request);
		String url = "/views/assignment/assignmentbuilding.jsp";
		String findAllAssignmentAPIStr="http://localhost:8087/api/building/assignment/"+model.getId()+"?page="+model.getPage()+"&maxPageItem="+ model.getMaxPageItem();
		String countAllAssignmentAPIStr="http://localhost:8087/api/building/assignment/"+model.getId()+"/count";
		
		model.setTotalItem(assignmentBuildingService.countAllByBuildingId(countAllAssignmentAPIStr.toString()));
		model.setTotalPage((int) Math.ceil((double) model.getTotalItem()/model.getMaxPageItem()));
		model.setResults(assignmentBuildingService.findAllByBuildingId(findAllAssignmentAPIStr.toString()));
		
		request.setAttribute("assignments", model.getResults());
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}
