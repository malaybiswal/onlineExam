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
ArrayList sno = new ArrayList();
ArrayList ans = new ArrayList();
ArrayList question = new ArrayList();
ArrayList answer = new ArrayList();

ArrayList correctsno = new ArrayList();
ArrayList correctans = new ArrayList();
ArrayList correctquestion = new ArrayList();
ArrayList correctanswer = new ArrayList();

String user = null;int q=0;
String email = (String) session.getAttribute("email");
String testno = (String) session.getAttribute("testno");
String type = (String) session.getAttribute("type");
String qsn="",ansr="",correctqsn="",correctansr="";
email = "'"+email+"'";
testno = "'"+testno+"'";
type = "'"+type+"'";
System.out.println("INSIDE examStatus.jsp###"+email+" "+testno+" "+" "+type);
String query = "select sno,ans from question where sno in (select sno from questioninprogress where score=0 and  email="+email+" and type="+type+" and testno="+testno+")";
String correctquery = "select sno,ans from question where sno in (select sno from questioninprogress where score>0 and  email="+email+" and type="+type+" and testno="+testno+")";
String query1="",query2="",query3="",query4="",correctquery1="";
System.out.println(query);
try{
	dbconn=new mySQLPrepared();
	conn=dbconn.getConn();
	System.out.println("Inside examType DB  connection: "+conn);
	Statement st = conn.createStatement();
	ResultSet rs = st.executeQuery(query);
	Statement correctst = conn.createStatement();
	ResultSet correctrs = correctst.executeQuery(correctquery);
	Statement st1 = conn.createStatement();
	ResultSet rs1 = null;
	Statement correctst1 = conn.createStatement();
	ResultSet correctrs1 = null;
	System.out.println("Before Adding to resultset");
while (rs.next())
{
	
		sno.add(rs.getInt(1));
		ans.add(rs.getString(2));

}
System.out.println("After Adding to resultset");
while (correctrs.next())
{
		System.out.println("INSIDE WHILE of examStatus.jsp");
		correctsno.add(correctrs.getInt(1));
		System.out.println("After getting getInt");
		correctans.add(correctrs.getString(2));
		System.out.println("After getting getString");
}
System.out.println("After Adding to CORRECTresultset");
int size = sno.size();
int correctsize = correctsno.size();
System.out.println("SIZE:"+size);
System.out.println("CORRCT SIZE:"+correctsize);
for(int i=0;i<size;i++){
	System.out.println("sno:"+sno.get(i)+" ans:"+ans.get(i));
	if((ans.get(i)).equals("a1")){
		query1="select question,a1 from question where sno="+sno.get(i);
	}
	else if((ans.get(i)).equals("a2")){
		query1="select question,a2 from question where sno="+sno.get(i);
	}
	else if((ans.get(i)).equals("a3")){
		query1="select question,a3 from question where sno="+sno.get(i);
	}
	else if((ans.get(i)).equals("a4")){
		query1="select question,a4 from question where sno="+sno.get(i);
	}
	System.out.println(query1);
	rs1 = st1.executeQuery(query1);
	//System.out.println("ans:"+ans.get(i));
	while (rs1.next()){
   		question.add(rs1.getString(1));
   		answer.add(rs1.getString(2));
    }
}

for(int j=0;j<correctsize;j++){
	System.out.println("correctsno:"+correctsno.get(j)+" correctans:"+correctans.get(j));
	if((correctans.get(j)).equals("a1")){
		correctquery1="select question,a1 from question where sno="+correctsno.get(j);
	}
	else if((correctans.get(j)).equals("a2")){
		correctquery1="select question,a2 from question where sno="+correctsno.get(j);
	}
	else if((correctans.get(j)).equals("a3")){
		correctquery1="select question,a3 from question where sno="+correctsno.get(j);
	}
	else if((correctans.get(j)).equals("a4")){
		correctquery1="select question,a4 from question where sno="+correctsno.get(j);
	}
	System.out.println(correctquery1);
	correctrs1 = correctst1.executeQuery(correctquery1);
	//System.out.println("ans:"+ans.get(i));
	while (correctrs1.next()){
		correctquestion.add(correctrs1.getString(1));
		correctanswer.add(correctrs1.getString(2));
    }
}


st.close();
conn.close();
st1.close();
correctst1.close();
correctst.close();

}catch(Exception e){e.printStackTrace();}
int size1=answer.size();
int correctsize1 = correctanswer.size();
%>
<h3><font color="#0000FF">These questions you have wrongly answered. Here is questions and correct answers.</font></h3>
<% 
for(int i=0;i<size1;i++){
	//Code resume from here need to print question, answer, question number in this jsp. Check dashboard jsp
	qsn = question.get(i).toString();
	ansr = answer.get(i).toString();
	%>
	
	<h5><%=(i+1)+". " +qsn%></h5>
	<h6><font color="#0000FF"><%="\n Answer: "+ansr%></font></h6>
	<% 
}
%>
<h3><font color="#008000">These were your RIGHT Answers.</font></h3>
<% 
for(int j=0;j<correctsize1;j++){
	correctqsn = correctquestion.get(j).toString();
	correctansr = correctanswer.get(j).toString();
	%>
	
	<h5><%=(j+1)+". " +correctqsn%></h5>
	<h6><font color="#008000"><%="\n Answer: "+correctansr%></font></h6>
	<% 
}
%>

</body>
</html>