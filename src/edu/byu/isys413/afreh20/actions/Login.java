package edu.byu.isys413.afreh20.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.byu.isys413.afreh20.mystuff.*;
import edu.byu.isys413.afreh20.web.*;

public class Login implements Action{
	public Login(){
			
	}
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try{
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			Customer c1 = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("email", email));
			if(c1 != null && c1.getPassword().equals(password)){
				//get the store data for the prod search page.
				List<Store> allStores = new LinkedList<Store>();
				allStores = BusinessObjectDAO.getInstance().searchForAll("Store");
				
				request.setAttribute("stores", allStores);
				session.setAttribute("customer", c1);
				session.setAttribute("loggedin", true);
				return "productsearch.jsp";
			}else {
				request.setAttribute("message", "Incorrect username and/or password.");
				return "login.jsp";
			}
		}catch(Exception e){
			throw new WebException("<b>An error occurred while logging in: " + e.getMessage()+"</b>");
		}
		
	}
}
