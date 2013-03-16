package edu.byu.isys413.afreh20.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import edu.byu.isys413.afreh20.mystuff.*;
import edu.byu.isys413.afreh20.web.*;

public class ProdAjax implements Action{
	public ProdAjax(){
		
	}
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try{
			Store s1 = BusinessObjectDAO.getInstance().read(request.getParameter("store"));
			String searchtext = request.getParameter("text");
			
//			List<StoreProd> spList = BusinessObjectDAO.getInstance().searchForList("StoreProd", new SearchCriteria("store_id", s1.getId()));
//			List<PhysicalProd> ppList = BusinessObjectDAO.getInstance().searchForList("PhysicalProd", new SearchCriteria("store_id", s1.getId()));
			
			List<StoreProd> spList = new LinkedList<StoreProd>();
//			List<PhysicalProd> ppList = new LinkedList<PhysicalProd>();
			List<ForSale> fsList = new LinkedList<ForSale>();
			List<Product> prodList = BusinessObjectDAO.getInstance().searchForList("Product", new SearchCriteria("name", searchtext+"%", 6));
			for(Product prod : prodList){
				if(prod.getType().equals("PhysicalProd")){
					//see if there is a physical prod in this store with that text.
					PhysicalProd temppprod = BusinessObjectDAO.getInstance().searchForBO("PhysicalProd", new SearchCriteria("id", prod.getId()), new SearchCriteria("store_id", s1.getId()));
//					PhysicalProd temppprod = BusinessObjectDAO.getInstance().read(prod.getId());
					if (temppprod != null){
//						System.out.println(temppprod.getType());
						//if there is a physical prod in the store, then check to see if it is for sale
						if(temppprod.getPhystype().equals("ForSale") && temppprod.getStatus().equals("available")){
							ForSale tempforsale = BusinessObjectDAO.getInstance().read(temppprod.getId());
							if(tempforsale != null){
								fsList.add(tempforsale);
							}
						}
						
					}
		
				}else if(prod.getType().equals("ConceptualProd")){
					StoreProd tempsprod = BusinessObjectDAO.getInstance().searchForBO("StoreProd", new SearchCriteria("cprod_id", prod.getId()), new SearchCriteria("store_id", s1.getId()), new SearchCriteria("quantity", 0, 2));
					if (tempsprod != null){
						spList.add(tempsprod);
					}
				}
			}
			
			
			
//			List<ConceptualProd> cpList = new LinkedList<ConceptualProd>();
//			HashMap<String, HashMap<String, String>> prodMap = new HashMap<String, HashMap<String, String>>();
			ArrayList<HashMap<String, String>> jsonthingy = new ArrayList<HashMap<String, String>>();
			//to put the storeprods into the list
			for(StoreProd sp : spList){
				ConceptualProd tempcp = (ConceptualProd) BusinessObjectDAO.getInstance().read(sp.getCprod_id());
				HashMap<String, String> tempHash = new HashMap<String, String>();
				tempHash.put("price", tempcp.getPrice()+"");
				tempHash.put("name", tempcp.getName());
				tempHash.put("id", tempcp.getId());
//				prodMap.put(tempcp.getId(), tempHash);
				jsonthingy.add(tempHash);
			}
			
			//to put the physcial prods in the list
//			for(PhysicalProd pp : ppList){
//				HashMap<String, String> tempHash = new HashMap<String, String>();
//				tempHash.put("price", pp.getPrice()+"");
//				tempHash.put("name", pp.getName());
//				tempHash.put("id", pp.getId());
////				prodMap.put(pp.getId(), tempHash);
//				jsonthingy.add(tempHash);
//			}
			
			for(ForSale fs : fsList){
				HashMap<String, String> tempHash = new HashMap<String, String>();
				tempHash.put("price", fs.getPrice()+"");
				tempHash.put("name", fs.getName());
				tempHash.put("id", fs.getId());
//				prodMap.put(pp.getId(), tempHash);
				jsonthingy.add(tempHash);
			}
			
			Gson gson = new Gson();
			String json1 = gson.toJson(jsonthingy);
			request.setAttribute("prodList", json1);
//			System.out.println(json1.toString());
			return "product_ajax.jsp";
		}catch(Exception e){
			
		}
		return null;

	}
}
