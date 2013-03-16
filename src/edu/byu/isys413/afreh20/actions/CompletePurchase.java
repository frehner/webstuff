package edu.byu.isys413.afreh20.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.byu.isys413.afreh20.mystuff.*;
import edu.byu.isys413.afreh20.web.*;

public class CompletePurchase implements Action {
	public CompletePurchase() {

	}

	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			Transaction trans = BusinessObjectDAO.getInstance().create("Transaction");
			Customer cust = (Customer) session.getAttribute("customer");
			String storeid = request.getParameter("store_id");
//			System.out.println(storeid);
			Store store = BusinessObjectDAO.getInstance().read(request.getParameter("store_id"));
			Employee emp = BusinessObjectDAO.getInstance().read(store.getManagerid());
			
			if(request.getParameter("product_type").equals("Physical")){
				ForSale fs1 = BusinessObjectDAO.getInstance().read(request.getParameter("prodid"));
				fs1.setType("sold");
				fs1.save();
			}else{
				ConceptualProd cp1 = BusinessObjectDAO.getInstance().read(request.getParameter("prodid"));
				StoreProd tempsp = BusinessObjectDAO.getInstance().searchForBO("StoreProd", new SearchCriteria("cprod_id", cp1.getId()), new SearchCriteria("store_id", store.getId()));
				tempsp.setQuantity(tempsp.getQuantity()-Double.parseDouble(request.getParameter("buying_quant")));
				tempsp.save();
			}
			
			Sale sale = BusinessObjectDAO.getInstance().create("Sale");
			sale.setQuantity(Double.parseDouble(request.getParameter("buying_quant")));
			sale.setType("sale");
			sale.setChargeamt(Double.parseDouble(request.getParameter("price")));
			sale.setTransaction_id(trans.getId());
			sale.save();
			
			//TODO get commission total?
//			trans.setCommissionTotal(sale.getChargeamt() * prod.getCommissionRate());
			
			trans.setSubtotal(Double.parseDouble(request.getParameter("price")));
			trans.setTax(Double.parseDouble(request.getParameter("tax")));
			trans.setTotal(Double.parseDouble(request.getParameter("total")));
			
			trans.setCustomer_id(cust.getId());
			trans.setStore_id(store.getId());
			trans.setEmployee_id(emp.getId());
			trans.setDate(new Date());
			trans.save();

			Payment pay1 = BusinessObjectDAO.getInstance().create("Payment");
			pay1.setAmount(Double.parseDouble(request.getParameter("total")));
			pay1.setPay_change(0);
			pay1.setTransaction_id(trans.getId());
			pay1.setType("card");
			pay1.save();

			JournalEntry je = BusinessObjectDAO.getInstance().create("JournalEntry");
			je.setTransaction_id(trans.getId());
			je.setDate(new Date());
			je.save();

			for (int i = 0; i < 5; i++) {
				DebitCredit dc = BusinessObjectDAO.getInstance().create("DebitCredit");
				if (i == 0) {
					dc.setAmount(trans.getTotal());
					dc.setGenledger_name("Cash");
					dc.setIsdebit(true);
					dc.setJournalentry_id(je.getId());
					dc.save();
				} else if (i == 1) {
					dc.setAmount(trans.getSubtotal());
					dc.setGenledger_name("Sales Revenue");
					dc.setIsdebit(false);
					dc.setJournalentry_id(je.getId());
					dc.save();
				} else if (i == 2) {
					dc.setAmount(trans.getTax());
					dc.setGenledger_name("Sales Tax");
					dc.setIsdebit(false);
					dc.setJournalentry_id(je.getId());
					dc.save();
				} else if (i == 3) {
					dc.setAmount(trans.getCommissionTotal());
					dc.setGenledger_name("Commission Expense");
					dc.setIsdebit(true);
					dc.setJournalentry_id(je.getId());
					dc.save();
				} else if (i == 4) {
					dc.setAmount(trans.getCommissionTotal());
					dc.setGenledger_name("Commission Payable");
					dc.setIsdebit(false);
					dc.setJournalentry_id(je.getId());
					dc.save();
				}

				Commission comm = BusinessObjectDAO.getInstance().create("Commission");
				comm.setAmount(trans.getCommissionTotal());
				comm.setDate(new Date());
				comm.setEmployee_id(emp.getId());
				comm.setTransaction_id(trans.getId());
				comm.save();
			}
		} catch (Exception e) {
			throw new WebException("<b>Could not process purchase: " + e.getMessage()+"</b>");
		}

		return "yay";
	}
}