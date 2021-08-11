package ers_controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ers_model.User;


public class UserController {
	public static void userFinder(HttpServletRequest req, HttpServletResponse res)
			throws  IOException, JsonProcessingException {
		
		
		HttpSession session = req.getSession();
		User user =(User) session.getAttribute("currentUser");
		if(user!=null) {
			
			
			PrintWriter printer = res.getWriter();
			printer.write(new ObjectMapper().writeValueAsString(user));
		}
		else
		{user=null;}
		
	}
	
	


}
