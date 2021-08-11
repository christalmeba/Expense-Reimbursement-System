package ers_controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import ers_dao.UserDAOImpl;
import ers_model.User;


public class LoginController {
	
	public static void login(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException{
		
		String myPath=null;
		
		if(!req.getMethod().equals("POST")) {
			myPath="/index.html";
			req.getRequestDispatcher(myPath).forward(req, res);
			return;
		}
		
		//get information from user
		
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		
		//create current user if information provided match with database information
		
		User currentUser=new UserDAOImpl().signIn(username, password);
		
		
		if(currentUser==null) {
			myPath="/forwarding/incorrectcredentials";
			req.getRequestDispatcher(myPath).forward(req, res);
			return;
		}else {
			req.getSession().setAttribute("currentUser", currentUser);
			
			if(currentUser.getRole_id()!=2) // 2 is role_id for manager
			{
			myPath="/forwarding/home";
			req.getRequestDispatcher(myPath).forward(req, res);
			}
			else {
				myPath="/forwarding/home/Manager";
				req.getRequestDispatcher(myPath).forward(req, res);
			}
			
			 
			return;
		}
		
	}
}
