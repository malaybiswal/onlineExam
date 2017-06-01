package onlineExam;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
@WebServlet("/finish")
@SuppressWarnings("serial")
public class finish extends HttpServlet{
	String ans, sno, current, email, answer, testno, query, querysno, querynext, type, totalquestion, duration;
	String question, a1, a2, a3, a4, ansr;
	int score, snum,tscore;
	Integer i=1;
	Connection conn;
	Statement stmt;
	ResultSet res;
	mySQLPrepared dbconn;
	List<Integer> lst = new ArrayList<Integer>();
	Integer randomeValue;
	HttpSession session;
	HashMap hm = new HashMap();
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		 session = request.getSession(false);
		 ans = (String) session.getAttribute("ans");
		 sno = (String) session.getAttribute("sno");
		 current = (String) session.getAttribute("current");
		 email = (String) session.getAttribute("email");
		 testno = (String) session.getAttribute("testno");
		 //answer= (String) session.getAttribute("answer");
		 String answer=request.getParameter("answer");
		 type= (String) session.getAttribute("type");
		 totalquestion= (String) session.getAttribute("totalquestion");
		 duration= (String) session.getAttribute("duration");
		 String types = "'"+type+"'";
		 String emailtext = "'"+email+"'";
		 /*i++;
		 hm.put(email, i);*/
		 /*if(answer.equals(ans)){
				score=1;
			}
		 else{
			 score=0;
		 }*/
		 
		 if(answer==null){
			 score=0;
			 System.out.println(email+" You did not answer this question"+sno);
		 }
		 else if(answer.equals(ans)){
				score=1;
			}
		 else{
			 score=0;
		 }
		 
		 i=(Integer)hm.get(email);
		 if(i==null){
			 i=0;
			 i++;
			 System.out.println("IN IF I:"+i);
			 hm.put(email, i);
			 i=0;
		 }
		 else{
			 i++;
			 hm.put(email, i);
			 System.out.println("In FINISH.java ELSE I:"+i);
		 }
		 System.out.println("%%%%% IN finish.java **** "+ans+" "+sno+" "+current+" "+email+" "+testno+" "+answer+" "+score + " "+type+" "+totalquestion+" "+duration);
		 try{
			 dbconn=new mySQLPrepared();
			 conn=dbconn.getConn();
			 PreparedStatement preparedStmt=null;
			 System.out.println("DB  connection: "+conn);
			 int j= (Integer)hm.get(email);
			 //if(j==1){
				 query = "update questioninprogress set score = "+score+" where sno="+sno+" and email="+emailtext+" and testno="+testno;
				 System.out.println("IN finish.java query:"+i+" :"+query);
				  preparedStmt = conn.prepareStatement(query);
				 preparedStmt.executeUpdate();
				 
			 //}
			 
			 
			 querysno= "select sum(score) from questioninprogress where email="+emailtext+" and testno="+testno;
			 Statement stsno = conn.createStatement();
				ResultSet rssno = stsno.executeQuery(querysno);
				while (rssno.next())
			      {
			         tscore = rssno.getInt(1);
			         
			      }
				System.out.println("tscore:"+tscore);
				
				
				
				String insert = "insert into result (email,score,type,testno) values(?,?,?,?)";
				PreparedStatement psmt = conn.prepareStatement(insert);
				psmt.setString (1, email);
				psmt.setInt (2, tscore);
				psmt.setString (3, type);
				
				psmt.setInt (4, Integer.parseInt(testno));
				psmt.execute();
				
				PrintWriter out = response.getWriter(  ); 
				 out.print("<h1>You scored </h1> "+tscore);
				 float pcg = (((float)tscore/(float)Integer.parseInt(totalquestion))*100);
				 System.out.println("tscore "+tscore+"totalquestion: "+totalquestion+" "+pcg);
				 out.println("<h1>Your %ge is </h1> "+(int)pcg);
				 out.println("<h3><a href=\"examStatus.jsp\">Click to Check wrong Answers.</a></h3>");
			 preparedStmt.close();
			 psmt.close();
			 stsno.close();
			 
			 conn.close();
			 session.setAttribute("email", email);
			 session.setAttribute("testno", testno);
			 session.setAttribute("type", type);
			 System.out.println("INSIDE finish.java sending to examStatus.jsp###"+email+" "+testno+" "+type);
			
		 }catch(Exception e){e.printStackTrace();}
		    
		 
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
			processRequest(request, response);
			}
			 
			 
			@Override
			protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			processRequest(request, response);
			}
			 
			 
			@Override
			public String getServletInfo() {
			return "Short description";
			}

}
