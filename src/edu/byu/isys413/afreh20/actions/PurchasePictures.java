package edu.byu.isys413.afreh20.actions;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import edu.byu.isys413.afreh20.mystuff.*;
import edu.byu.isys413.afreh20.web.*;

public class PurchasePictures implements Action{
	public PurchasePictures(){
		
	}
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Gson gson = new Gson();
		HashMap<String, String> responseJson = new HashMap<String, String>();
		try{
//			Customer c1 = BusinessObjectDAO.getInstance().read(request.getParameter("custid"));
			Double price = Double.parseDouble(request.getParameter("price"));
			int styleInt = Integer.parseInt(request.getParameter("style"));
			int sizeInt = Integer.parseInt(request.getParameter("size"));
			int quanInt = Integer.parseInt(request.getParameter("quantity"));
			
			JSONParser parser = new JSONParser();
			String picjson = request.getParameter("pics");
			JsonArray jArray = new JsonParser().parse(picjson).getAsJsonArray();
			Double singlePrice = price/jArray.size();
			
			Transaction t1 = BusinessObjectDAO.getInstance().create("Transaction");
			
			for(JsonElement temptemp: jArray){
//				System.out.println(temptemp.toString());
				PrintOrder tempPO = BusinessObjectDAO.getInstance().create("PrintOrder");
				tempPO.setChargeamt(singlePrice);
				tempPO.setQuantity(quanInt);
				tempPO.setTransaction_id(t1.getId());
				tempPO.setType("PrintOrder");
				tempPO.setPictureId(temptemp.toString());
				if(styleInt == 0 && sizeInt == 0){
					tempPO.setPrintId("print2");
				}else if(styleInt == 0 && sizeInt == 1){
					tempPO.setPrintId("print4");
				}else if(styleInt == 1 && sizeInt ==0){
					tempPO.setPrintId("print1");
				}else {
					tempPO.setPrintId("print3");
				}
				tempPO.save();
			}
			
			Payment p1 = BusinessObjectDAO.getInstance().create("Payment");
			p1.setAmount(price);
			p1.setPay_change(0);
			p1.setTransaction_id(t1.getId());
			p1.setType("Credit Card");
			p1.save();
			
			t1.setCustomer_id(request.getParameter("custid"));
			t1.setTotal(price);
			t1.save();
			
			responseJson.put("status", "Success");
			String json = gson.toJson(responseJson);
			request.setAttribute("mobiledata", json);
			return "mobile_return.jsp";
		}catch (Exception e){
			e.printStackTrace();
			responseJson.put("status", "failure");
			String json = gson.toJson(responseJson);
			request.setAttribute("mobiledata", json);
			return "mobile_return.jsp";
		}
		
	}
}