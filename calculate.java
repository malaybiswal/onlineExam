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
@WebServlet("/calculate")
@SuppressWarnings("serial")
public class calculate extends HttpServlet{
	String ans, sno, current, email, answer, testno, query, querysno, querynext, type, totalquestion, duration, newquery;
	String question, a1, a2, a3, a4, ansr, qn;
	int score, snum;Integer i=1,qno=0;
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
		 System.out.println(i+"---------"+Integer.parseInt(totalquestion));
		 if((i+1)==Integer.parseInt(totalquestion))
			 hm.clear();
		 System.out.println("IN calculate hashmap hm:"+hm);
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
			 System.out.println("In ELSE I:"+i);
		 }
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
		 System.out.println("IN calculate.java**** "+i+" :"+ans+" "+sno+" "+current+" "+email+" "+testno+" "+answer+" "+score + " "+type+" "+totalquestion+" "+duration);
		 try{
			 dbconn=new mySQLPrepared();
			 conn=dbconn.getConn();
			 PreparedStatement preparedStmt=null;
			 System.out.println("DB  connection: "+conn);
			 int j= (Integer)hm.get(email);
			// if(j==1){
				 query = "update questioninprogress set score = "+score+" where sno="+sno+" and email="+emailtext+" and testno="+testno;
				 System.out.println("IN calculate.java query:"+i+" :"+query);
				  preparedStmt = conn.prepareStatement(query);
				 preparedStmt.executeUpdate();
				 
			 //}
			 
			 
			 querysno= "select sno from question where sno not in (select sno from questioninprogress where email="+emailtext+" and current in('Y','y')) and upper(type)="+types;
			 Statement stsno = conn.createStatement();
				ResultSet rssno = stsno.executeQuery(querysno);
				while (rssno.next())
			      {
			         snum = rssno.getInt("sno");
			         lst.add(snum);
			      }
				System.out.println("LIST:"+lst);
				int index = new Random().nextInt(lst.size());
				Integer randomeValue = lst.get(index);
				
				querynext= "select sno,question,a1,a2,a3,a4,ans from question where sno ="+ randomeValue;
				System.out.println("In calculate.java querynext:"+querynext);
				Statement stnext = conn.createStatement();
				ResultSet rsnext = stnext.executeQuery(querynext);
				while (rsnext.next())
			      {
			         snum = rsnext.getInt("sno");
			         question = rsnext.getString("question");
			         a1 = rsnext.getString("a1");
			         a2 = rsnext.getString("a2");
			         a3 = rsnext.getString("a3");
			         a4 = rsnext.getString("a4");
			         ansr = rsnext.getString("ans");
			         
			        
			      }
				String snums = snum+"";
				System.out.println("$$$$$$$$slno:"+snum+" "+ansr+" j:"+j);
				String insert = "insert into questioninprogress (email,sno,current,answer,testno) values(?,?,?,?,?)";
				PreparedStatement psmt = conn.prepareStatement(insert);
				psmt.setString (1, email);
				psmt.setInt (2, snum);
				psmt.setString (3, "Y");
				psmt.setString (4, ansr);
				psmt.setInt (5, Integer.parseInt(testno));
				psmt.execute();
				/*if(j>1){
					newquery = "update questioninprogress set score = "+score+" where sno="+snum+" and email="+emailtext+" and testno="+testno;
					 System.out.println("In calculate.java newquery+++:"+i+" :"+newquery);
					  preparedStmt = conn.prepareStatement(newquery);
					 preparedStmt.executeUpdate();
				}
				
			 preparedStmt.close();*/
			 psmt.close();
			 stsno.close();
			 stnext.close();
			 conn.close();
			 
			 HttpSession session = request.getSession(false);
			 /*qno=(Integer)hm.get(email);
			 qn = qno+"";
			 if(qn.equals(totalquestion)){
				 qn="1";
			 }*/
				//save message in session
				//session.setAttribute("sno", sno);
			 	session.setAttribute("sno", snums);
				session.setAttribute("question", question);
				session.setAttribute("a1", a1);
				session.setAttribute("a2", a2);
				session.setAttribute("a3", a3);
				session.setAttribute("a4", a4);
				session.setAttribute("user", email);
				session.setAttribute("ans", ansr);
				session.setAttribute("testno", testno);
				session.setAttribute("qno", (Integer)hm.get(email)+"");
				//session.setAttribute("qno", qn);
				session.setAttribute("type", type);
				response.sendRedirect("next.jsp");
				System.out.println("In calculate.java Sending these to next.jsp******** "+ansr+" "+sno+" "+email+" "+testno+" qno:"+i);
				lst.clear();
				
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
