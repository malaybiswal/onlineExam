<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String user = null;int q=0;
//String value = (String)request.getAttribute("myParam");
//out.println(session.getAttribute("myParam"));
String question = (String) session.getAttribute("question");
String a1 = (String) session.getAttribute("a1");
String a2 = (String) session.getAttribute("a2");
String a3 = (String) session.getAttribute("a3");
String a4 = (String) session.getAttribute("a4");
user = (String) session.getAttribute("user");
String ans = (String) session.getAttribute("ans");
String sno =  (String) session.getAttribute("sno");
String testno =  (String) session.getAttribute("testno");
String qno = (String) session.getAttribute("qno");
String type = (String) session.getAttribute("type");
String totalquestion = (String) session.getAttribute("totalquestion");
String duration = (String) session.getAttribute("duration");
System.out.println("INSIDE next.jsp###"+user+" "+ans+" "+" "+sno+" "+testno+" "+qno+" "+type+" "+totalquestion+" "+duration);
if(qno==null){
	qno="1";
}
else{
	 q = Integer.parseInt(qno);
	q=q+1;
	qno=q+"";
	
}
System.out.println("####"+user+" "+ans+" "+sno+" "+testno+" Qno: "+qno);
    session.removeAttribute("question");
    session.removeAttribute("a1");
    session.removeAttribute("a2");
    session.removeAttribute("a3");
    session.removeAttribute("a4");
    session.removeAttribute("user");
    session.removeAttribute("ans");
    session.removeAttribute("sno");
    session.removeAttribute("testno");
    session.removeAttribute("qno");
    session.removeAttribute("type");
    session.removeAttribute("totalquestion");
    session.removeAttribute("duration");
    
    //request.setAttribute("sno", sno);
    //request.setAttribute("current", "Y");
    //request.setAttribute("ans", ans);
	session.setAttribute("ans", ans);
	session.setAttribute("current", "Y");
	session.setAttribute("sno", sno);
	session.setAttribute("email", user);
	session.setAttribute("testno", testno);
	session.setAttribute("type", type);
	session.setAttribute("totalquestion", totalquestion);
	session.setAttribute("duration", duration);
	System.out.println(q+"--------------"+Integer.parseInt(totalquestion));
%>
<h3>Hi <%=user %>, Login successful.</h3>

<br>
<%if(q==Integer.parseInt(totalquestion)){
	
%>
	<form action="finish" method="post">
<%= qno+". "%>
<%= question%>
<p><input type="radio" name="answer" value="a1"> <%=a1 %></input></p>
<p><input type="radio" name="answer" value="a2"> <%=a2 %></input></p>
<p><input type="radio" name="answer" value="a3"> <%=a3 %></input></p>
<p><input type="radio" name="answer" value="a4"> <%=a4 %></input></p>

<input type="submit" value="FINISH" >
</form>
</body>
</html>
<% 
q=0;
qno="1";
}
else{%>
	<form action="calculate" method="post">
<%= qno+". "%>
<%= question%>
<p><input type="radio" name="answer" value="a1"> <%=a1 %></input></p>
<p><input type="radio" name="answer" value="a2"> <%=a2 %></input></p>
<p><input type="radio" name="answer" value="a3"> <%=a3 %></input></p>
<p><input type="radio" name="answer" value="a4"> <%=a4 %></input></p>

<input type="submit" value="NEXT" >
</form>
</body>
</html>
<% 
}
	
	%>
