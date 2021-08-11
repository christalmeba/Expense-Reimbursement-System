package ers_controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ers_dao.ReimbursementDAOImpl;
import ers_dao.UserDAOImpl;
import ers_model.Reimbursement;
import ers_model.User;



public class ExpenseRequestController {
	
	

	public static void ExpenseHomeView(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException, JsonProcessingException{
		
		
				String myPath="/resources/htmlpages/ReimbursementResquestPage.html";
				req.getRequestDispatcher(myPath).forward(req,res);
	}
	
	public static void reimbursementManagerView(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException, JsonProcessingException{
				
				
						String myPath="/resources/htmlpages/ReimbursementsView.html";
						req.getRequestDispatcher(myPath).forward(req,res);
			}
	
	public static void ExpenseRequest(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException{
		
		// receive ajax object as JSON and convert it to java object as Reimbursement object
		ObjectMapper mapper = new ObjectMapper();
		Reimbursement request = mapper.readValue(req.getInputStream(), Reimbursement.class);
		
		ReimbursementDAOImpl reimbursement=new ReimbursementDAOImpl();
		if (request.getReimbResolver()==2) // 2 represent the type of resolver id which is a Finance manager
		{
			//make an update of request by using approveOrDenyReimbursement method 
			reimbursement.approveOrDenyReimbursement(request);
		}
		else
			
		// make new request by using requestReimbursement method that insert a request as new record in Reimbursement table in my database
			reimbursement.requestReimbursement(request);
		
		System.out.println(request);
		
	}
	
	public static void userReimbursementRequestList(HttpServletRequest req, HttpServletResponse res)
			throws  IOException, JsonProcessingException {
		
		/*
		 * THIS IS WHERE YOU'D GO TO THE DATABASE TO GET all user request to send to home page
		 */
		ReimbursementDAOImpl reimbursement=new ReimbursementDAOImpl();
		HttpSession session = req.getSession();
		User user =(User) session.getAttribute("currentUser");
		//int user_id=Integer.parseInt(req.getParameter("employeeId"));
		
			PrintWriter printer = res.getWriter();
			printer.write(new ObjectMapper().writeValueAsString(reimbursement.viewPastTickets(user.getUsers_id())));
			//printer.write(new ObjectMapper().writeValueAsString(reimbursement.viewAllReimbursements()));
			
			
	}
	
	
	public static void ReimbursementRequestList(HttpServletRequest req, HttpServletResponse res)
			throws  IOException, JsonProcessingException {
		List<Object> myList=new ArrayList<>();
		ReimbursementDAOImpl reimbursement=new ReimbursementDAOImpl();
		UserDAOImpl users=new UserDAOImpl();
		myList.add(reimbursement.viewAllReimbursements());
		myList.add(users.getAllUsers());
			PrintWriter printer = res.getWriter();
			printer.write(new ObjectMapper().writeValueAsString(myList));
			
			
	}
	
	
	
}
