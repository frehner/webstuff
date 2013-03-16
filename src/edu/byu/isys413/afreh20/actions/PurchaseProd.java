package edu.byu.isys413.afreh20.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.byu.isys413.afreh20.mystuff.*;
import edu.byu.isys413.afreh20.web.*;

public class PurchaseProd implements Action{
	public PurchaseProd(){
		
	}
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Customer c1 = (Customer)session.getAttribute("customer");
		Membership m1 = BusinessObjectDAO.getInstance().searchForBO("Membership", new SearchCriteria("customerid", c1.getId()));
		request.setAttribute("creditcard", m1.getCreditcard());
//		System.out.println(request.getParameter("prodid"));
		Store s1 = BusinessObjectDAO.getInstance().read(request.getParameter("storeid"));
		request.setAttribute("storelocation", s1.getLocation());
		request.setAttribute("storeid", s1.getId());
		request.setAttribute("taxrate", s1.getSalestaxrate());
		try{
			Product p1 = BusinessObjectDAO.getInstance().read(request.getParameter("prodid"));
			if(p1.getType().equals("PhysicalProd")){
				PhysicalProd pp1 = BusinessObjectDAO.getInstance().read(p1.getId());
				request.setAttribute("product", pp1);
				request.setAttribute("quantity", 1);
				request.setAttribute("location", pp1.getLocation());
				request.setAttribute("producttype", pp1.getType());
				double tax = pp1.getPrice()*s1.getSalestaxrate();
				request.setAttribute("tax", tax);
				request.setAttribute("total", pp1.getPrice()+tax);
				return "buyprod.jsp";
			}else if(p1.getType().equals("ConceptualProd")){
				ConceptualProd cp1 = BusinessObjectDAO.getInstance().read(p1.getId());
				request.setAttribute("product", cp1);
				StoreProd sp1 = BusinessObjectDAO.getInstance().searchForBO("StoreProd", new SearchCriteria("cprod_id", cp1.getId()), new SearchCriteria("store_id", s1.getId()));
				request.setAttribute("quantity", sp1.getQuantity());
				request.setAttribute("location", sp1.getLocation());
				request.setAttribute("producttype", cp1.getType());
				double tax = cp1.getPrice()*s1.getSalestaxrate();
				request.setAttribute("tax", tax);
				request.setAttribute("total", cp1.getPrice()+tax);
				return "buyprod.jsp";
			}else{
				throw new WebException("<b>Could not process purchase: Not products with id found.");
			}
		}catch(Exception e){
			throw new WebException("<b>Could not process purchase: " + e.getMessage()+"</b>");
		}
	}
}