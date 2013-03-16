<%@ page import="edu.byu.isys413.afreh20.mystuff.Customer" %>
<div class="navigation"  style="font-size:150%">
			<a href='http://localhost:8080/WebCode/index.jsp'>Home</a> &nbsp;|&nbsp; <a href='http://localhost:8080/WebCode/login.jsp'>Login</a> &nbsp;|&nbsp; <a href='http://localhost:8080/WebCode/accountcreate.jsp'>Create Account </a>
			<% if(session.getAttribute("customer") != null){%>
				<div style="float:right;font-size:100%; margin-right:.3em;"> Welcome&nbsp;
				<%Customer c1 = (Customer) session.getAttribute("customer"); 
					out.println(c1.getFirstname());%>
				</div>
				<%} %>
</div>