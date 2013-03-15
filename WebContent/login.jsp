<jsp:include page="/header.jsp">
  <jsp:param name="title" value="MyStuff Login" />
</jsp:include>
<jsp:include page="/navigation.jsp" />
<div class="wrapper">
	Please login to your MyStuff account.
	<p>
	<form method="POST" action="edu.byu.isys413.afreh20.actions.Login.action">
		<table id="creationtable">
			<tr>
				<td>
					Email:
				</td>
				<td>
					<input class="forminput" type="text" name="email">
				</td>
			</tr>
			<tr>
				<td>
					Password:
				</td>
				<td>
					<input class="forminput" type="password" name="password">
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<button style="float:right;min-width:75px;" class="button" type="submit">Login</button>
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