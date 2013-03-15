<jsp:include page="/header.jsp">
  <jsp:param name="title" value="Purchase Product" />
</jsp:include>
<jsp:include page="/navigation.jsp" />
<%@ page import="edu.byu.isys413.afreh20.mystuff.*" %>
<div class="wrapper">
<%Product p1 = (Product)request.getAttribute("product");%>
	<form method="POST" action="">
		<table>
			<tr>
				<td>
					Name:
				</td>
				<td> <%
					out.println(p1.getName());
					%>
				</td>
			</tr>
			<tr>
				<td>
					Quantity:
				</td>
				<td><%
					out.println(request.getAttribute("quantity"));
					%>
				</td>
			</tr>
			<tr>
				<td>
					Price:
				</td>
				<td>$<%
					out.println(p1.getPrice()+"");
					%>
				</td>
			</tr>
			<tr>
				<td>
					Credit Card:
				</td> 
				<td>
					<input class="forminput" type="text" value="
					<%Customer c1 = (Customer)session.getAttribute("customer");
					//TODO print out the customer cc info.%>
					">
				</td>
			</tr>
			<tr>
				<td>
					Shipping Address:
				</td>
				<td>
					<input class="forminput" type="text" value="<% 
						out.println(c1.getAddress());
					%>">
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<button style="float:right;" type="button" class="button">Purchase!</button>
				</td>
			</tr>
		</table>
	</form>
</div>
<jsp:include page="/footer.jsp"/>