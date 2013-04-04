package edu.byu.isys413.afreh20.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import edu.byu.isys413.afreh20.mystuff.*;
import edu.byu.isys413.afreh20.web.*;

public class Login implements Action{
	public Login(){
			
	}
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
//		System.out.println("trying");
		if(request.getParameter("ismobile") != null){
			Gson gson = new Gson();
			HashMap<String, String> responseJson = new HashMap<String, String>();
			try{
//				System.out.println("in mobile area");
				
				
				Customer c1 = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("email", request.getParameter("username")));
				if(c1 != null){
					if(c1.getPassword().equals(request.getParameter("password"))){
						
						List<BusinessObject> allPics = BusinessObjectDAO.getInstance().searchForList("Picture", new SearchCriteria("customerid", c1.getId()));
//						HashMap<String, HashMap<String, String>> picMap = new HashMap<String, HashMap<String, String>>();
						ArrayList<HashMap<String, String>> picList = new ArrayList<HashMap<String, String>>();
//						int i = 0;
						for(BusinessObject bo: allPics){
							HashMap<String, String> singlePic = new HashMap<String, String>();
							Picture temppic = (Picture) bo;
							singlePic.put("id", temppic.getId());
							singlePic.put("caption", temppic.getCaption());
							singlePic.put("picname", temppic.getPicname());
							singlePic.put("pic", temppic.getPic());
//							picMap.put(i+"", singlePic);
//							i++;
							picList.add(singlePic);
//							System.out.println(i);
						}
						
//						String picJson = gson.toJson(picMap);
						String picJson = gson.toJson(picList);
						responseJson.put("pics", picJson);
						responseJson.put("custid", c1.getId());
						responseJson.put("username", c1.getEmail());
						responseJson.put("status", "Success");
					} else {
						responseJson.put("status", "Incorrect Password");
					}
				} else {
					responseJson.put("status", "Account not found");
				}
				
//				responseJson.put("testing", "This works suckas");
				String json = gson.toJson(responseJson);
				request.setAttribute("mobiledata", json);			
				return "mobile_return.jsp";
				
			} catch (Exception e){
				e.printStackTrace();
				responseJson.put("status", "horrible failure");
				String json = gson.toJson(responseJson);
				request.setAttribute("mobiledata", json);
				return "mobile_return.jsp";
			}
//			
		} else{
			try{
//				System.out.println("not in mobile area");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				Customer c1 = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("email", email));
				if(c1 != null && c1.getPassword().equals(password)){
					//get the store data for the prod search page.
					List<Store> allStores = new LinkedList<Store>();
					allStores = BusinessObjectDAO.getInstance().searchForAll("Store");
					
					request.setAttribute("stores", allStores);
					session.setAttribute("customer", c1);
					Membership m1 = BusinessObjectDAO.getInstance().searchForBO("Membership", new SearchCriteria("customerid", c1.getId()));
					session.setAttribute("membership", m1);
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
}
