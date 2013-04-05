package edu.byu.isys413.afreh20.actions;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import edu.byu.isys413.afreh20.mystuff.*;
import edu.byu.isys413.afreh20.web.*;

public class SavePicture implements Action{
	public SavePicture(){
		
	}
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Gson gson = new Gson();
		HashMap<String, String> responseJson = new HashMap<String, String>();
		try{
			Picture p1 = BusinessObjectDAO.getInstance().create("Picture");
			p1.setCaption(request.getParameter("caption"));
			p1.setCustomerid(request.getParameter("custId"));
			p1.setPic(request.getParameter("pic"));
			p1.setPicname(request.getParameter("picname"));
			p1.save();
			
			responseJson.put("newId", p1.getId());
			responseJson.put("status", "Success");
			String json = gson.toJson(responseJson);
			request.setAttribute("mobiledata", json);
			
			return "mobile_return.jsp";
		} catch (Exception e){
			e.printStackTrace();
			responseJson.put("status", "failure");
			String json = gson.toJson(responseJson);
			request.setAttribute("mobiledata", json);
			return "mobile_return.jsp";
		}
		
		
	}
	
}
