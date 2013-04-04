package edu.byu.isys413.afreh20.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.byu.isys413.afreh20.mystuff.*;
import edu.byu.isys413.afreh20.web.*;

public class CreateAccount implements Action{
	public CreateAccount(){
		
	}
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try{
			Customer c1 = BusinessObjectDAO.getInstance().create("Customer");
			c1.setAddress(request.getParameter("address"));
			c1.setEmail(request.getParameter("email1"));
			c1.setFirstname(request.getParameter("firstname"));
			c1.setLastname(request.getParameter("lastname"));
			c1.setConfirmation(GUID.generate());
			c1.setIsconfirmed(false);
			c1.setPassword(request.getParameter("password1"));
			c1.save();
			
			Membership m1 = BusinessObjectDAO.getInstance().create("Membership");
			m1.setCreditcard(request.getParameter("cc1"));
			m1.setCustomerid(c1.getId());
			m1.save();
			
			//request is temporary
			request.setAttribute("customer", c1);
			
			//session is more permanent
//			session.setAttribute("customerid", c1);
//			session.setAttribute("isloggedin", true);
			String tempEmail = c1.getEmail().replaceAll("@", "%40");
			
			String messageBody = "Thank you for registering on <a href='intexmystuff.com'>IntexMyStuff.com</a>.  Please click on the following link to validate your account:" +
					"<br><br><a href='http://localhost:8080/WebCode/edu.byu.isys413.afreh20.actions.Validation.action?email="+tempEmail+"&validation="+c1.getConfirmation()+"'>Validate Account</a>" +
					"<br><br>Alternativly, you can go to the following webpage and enter your email and validation code manually.  Your code is <b>"+c1.getConfirmation()+"</b>"+
					"<br><a href='http://localhost:8080/WebCode/validation.jsp'>http://localhost:8080/WebCode/validation.jsp</a>";
			
			BatchEmail be = new BatchEmail();
			//TODO the next line should be uncommented for live stuff.  I just don't wanna spam people.
			be.send("noreply@intexmystuff.com", "Do not reply", c1.getEmail(), "Validate Account", messageBody);
			
			request.setAttribute("message", "An email was sent to your account. You can click on the link there or get the validation code and enter it here.");
			
			return "validation.jsp";
			
		}catch (Exception e){
			throw new WebException("<b>An error occurred while creating and saving your account in the CreateAccount class: " + e.getMessage()+"</b>");
		}		
	}
	
}
