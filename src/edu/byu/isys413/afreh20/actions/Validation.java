package edu.byu.isys413.afreh20.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.byu.isys413.afreh20.mystuff.*;
import edu.byu.isys413.afreh20.web.*;

public class Validation implements Action{
	public Validation(){
		
	}
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try{
			Customer c1 = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("email", request.getParameter("email")));
			if(c1 != null && c1.getConfirmation().equals(request.getParameter("validation"))){
				c1.setIsconfirmed(true);
				c1.save();
				request.setAttribute("customer", c1);
				request.setAttribute("message", "Your account was validated! Please login to browse products.");
				return "login.jsp";
			}else{
				request.setAttribute("email", request.getParameter("email"));
				request.setAttribute("message", "Failed to validate your account. Please try again.");
				return "validation.jsp";
			}
			
		}catch (Exception e){
			throw new WebException("<b>Could not process validation: " + e.getMessage()+"</b>");
		}
	}
}