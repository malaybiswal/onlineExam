<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login Success Page</title>
</head>
<body>
<%
//String userName = null;
//String ans = (String)request.getAttribute("ans");
String ans = (String) session.getAttribute("ans");
String sno = (String) session.getAttribute("sno");
String current = (String) session.getAttribute("current");
String email = (String) session.getAttribute("email");
String answer=request.getParameter("answer");
out.println("Your anwer is "+answer);
if(answer.equals(ans)){
	out.println("***RIGHT ANSWER****");
}
else{
	out.println("***WRONG ANSWER****");
}

%>

<%= ans%>
<%= sno%>
<%= current%>
<%= email%>
<br>
<form action="LogoutServlet" method="post">
<input type="submit" value="Logout" >
</form>
</body>
</html>