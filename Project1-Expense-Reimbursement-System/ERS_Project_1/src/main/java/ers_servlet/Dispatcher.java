package ers_servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ers_controller.ExpenseRequestController;
import ers_controller.LoginController;
import ers_controller.UserController;

public class Dispatcher {
	
	public static void myVirtualRouter(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("\t\tIn My dispatcher(myvirtualRouter())");
		System.out.println("Current URL :"+req.getRequestURL());
		System.out.println("Current URI :"+req.getRequestURI());
		
		
		
		switch(req.getRequestURI()) {
		case "/ERS_Project_1/forwarding/login":
			System.out.println("Case 1");
			LoginController.login(req, res);
			break;
		case "/ERS_Project_1/forwarding/home":
			System.out.println("Case 2");
			ExpenseRequestController.ExpenseHomeView(req, res);
			
			break;
		case "/ERS_Project_1/forwarding/home/Manager":
			System.out.println("Case 21");
			ExpenseRequestController.reimbursementManagerView(req, res);
			
			break;
		case "/ERS_Project_1/forwarding/request-reimbursement":
			System.out.println("Case 3");
			ExpenseRequestController.ExpenseRequest(req, res);
			break;
		case "/ERS_Project_1/forwarding/update-request":
			System.out.println("Case 4");
			//ExpenseRequestController.ExpenseRequest(req, res);
			break;
		case "/ERS_Project_1/forwarding/json/user":
			System.out.println("case 5");
			UserController.userFinder(req, res);
			break;
		case "/ERS_Project_1/forwarding/json/user-request":
			ExpenseRequestController.userReimbursementRequestList(req, res);
			break;
		case "/ERS_Project_1/forwarding/json/allReimburmentsView":
			ExpenseRequestController.ReimbursementRequestList(req, res);
			break;
		default :
			System.out.println("wrong uri provided");
			req.getRequestDispatcher("/resources/htmlpages/badlogin.html").forward(req, res);
		
		}
	}

}
