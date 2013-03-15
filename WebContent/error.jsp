<jsp:directive.page import="edu.byu.isys413.afreh20.web.*"/> 

<%
   // if we get to this page, we have a web exception in the request
   // (Tomcat puts it there for us per web.xml settings)
   // let's just make sure it is there
   WebException exc = (WebException)request.getAttribute("javax.servlet.error.exception");
   if (exc == null) {
     throw new JspException("error.jsp cannot be called directly!");
   }
%>

<jsp:include page="/header.jsp">
  <jsp:param name="title" value="Error" />
</jsp:include>
<jsp:include page="/navigation.jsp" />

<div class="wrapper">
	We regret to inform you that an error occurred.  A team of flying ninjas has been dispatched to solve the problem.
	<br>
	<br>
	In the unlikely event that you see them, please give them the following information:
	<br>
	<br>
	<%=exc.getMessage()%>
</div>  

<jsp:include page="/footer.jsp"/>
