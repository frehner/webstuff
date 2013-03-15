<%@page import="edu.byu.isys413.afreh20.mystuff.Customer" %>
<jsp:include page="/header.jsp">
  <jsp:param name="title" value="Validate Account" />
</jsp:include>
<jsp:include page="/navigation.jsp" />
<div class="wrapper">
You should have received an email containing your confirmation code.<br>
Please check your spam folder if it is not in your inbox.
<p>
Once your have your confirmation code, please enter you email and confirmation code to verify your account.
	<form method="POST" action="edu.byu.isys413.afreh20.actions.Validation.action">
		<table id="creationtable">
			<tr>
				<td>
					Email:
				</td>
				<td>
					<input class="forminput" type="text" name="email" value="<%
						if(request.getAttribute("customer") != null){
							Customer c1 = (Customer)request.getAttribute("customer");
							out.print(c1.getEmail());
							//this makes things easier for the user
						}
					%>">
				</td>
			</tr>
			<tr>
				<td>
					Validation Code:
				</td>
				<td>
					<input class="forminput" type="text" name="validation">
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<button class="button" type="submit" style="float:right;" >Validate Account</button>
				</td>
			</tr>
		</table>
		
	</form>
	<% 
		if(request.getAttribute("message") != null){
			out.println("<div style=color:red;>");
			out.println(request.getAttribute("message"));
			out.println("</div>");
		}
	%>
</div>
<jsp:include page="/footer.jsp"/>