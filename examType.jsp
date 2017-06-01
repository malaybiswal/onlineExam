<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ page import="java.sql.*" %>
<%@ page import="onlineExam.*" %>
<%@ page import="java.util.*" %>
<%
Connection conn;
Statement stmt;
ResultSet res;
mySQLPrepared dbconn;

String user = request.getParameter("email");
System.out.println("--------------------STARTING .... BEFORE IF:"+user+":---------------------------");
if(user==""){
	System.out.println("INSIDE IF");
	%>
	<h3>Don't get lazy. UserName Can Not left out</h3>
	<%
	
}
else{
	String usr = "'"+user+"'";
	String query = "select type,subtype,totalquestion,duration from testdata";
	//String type,subtype;
	ArrayList type = new ArrayList();
	ArrayList subtype = new ArrayList();
	ArrayList totalquestion = new ArrayList();
	ArrayList duration = new ArrayList();
	try{
		dbconn=new mySQLPrepared();
		conn=dbconn.getConn();
		System.out.println("Inside examType DB  connection: "+conn);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next())
	      {
	         type.add(rs.getString(1));
	         subtype.add(rs.getString(2));
	         totalquestion.add(rs.getInt(3));
	         duration.add(rs.getInt(4));
	      }
		int size = type.size();
		%>
		<h3>Hello <%=user %>, Login successful.</h3><br>
		<% 
		for(int i=0;i<size;i++){
			System.out.println("Type:"+type.get(i));
			String tp = type.get(i)+"";
			//session.setAttribute("type", type.get(i));
			String tq = totalquestion.get(i)+"";
			String d = duration.get(i)+"";
			//session.setAttribute("totalquestion", tq);
			//session.setAttribute("duration", d);
			%>
			
			<tr><td><a href="exam?type=<%=tp%>&totalquestion=<%=tq%>&duration=<%=d%>"><%=type.get(i)%></td></tr><br>
			<% 
			
		}
		session.setAttribute("user", user);
		
		st.close();
		conn.close();
	}catch(Exception e){e.printStackTrace();}

	
}

%>



</body>
</html>