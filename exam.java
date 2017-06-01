package onlineExam;




import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
import java.util.List;
import java.util.Random;

@WebServlet("/exam")
@SuppressWarnings("serial")
public class exam  extends HttpServlet{
	String question, a1, a2, a3, a4, user, ans, snos, duration, totalquestion;
	int sno, index, testno=1;
	String query,querysno;
	Connection conn;
	Statement stmt;
	ResultSet res;
	mySQLPrepared dbconn;
	List<Integer> lst = new ArrayList<Integer>();
	Integer randomeValue;
	HttpSession session;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			try {
			 
			dbconn=new mySQLPrepared();
			 
			
			//user = request.getParameter("email");
			//String usr = "'"+user+"'";
			conn=dbconn.getConn();
			    System.out.println("DB  connection: "+conn);
			//stmt=conn.createStatement();
			    //stmt = conn.prepareStatement();
			    session = request.getSession(false);
			 //String   type = (String) session.getAttribute("type");
			 //duration = (String) session.getAttribute("duration");
			 //totalquestion = (String) session.getAttribute("totalquestion");
			    String tp = request.getParameter("type");
			    String d = request.getParameter("duration");
			    String tq = request.getParameter("totalquestion");
			    String type = tp+"";
			    duration=d+"";
			    totalquestion = tq+"";
			    user = (String) session.getAttribute("user");
			    String usr = "'"+user+"'";
			 type = type.toUpperCase();
			 String types = "'"+type+"'";
			 querysno= "select sno from question where sno not in (select sno from questioninprogress where email="+usr+" and current in('Y','y')) and upper(type)="+types;
			//query= "select sno,question,a1,a2,a3,a4,ans from question where sno not in (select sno from questioninprogress where email="+usr+" and current in('Y','y')) limit 1";
			
			//" values('"+type+"','"+name+"','"+policy+"',"+account+",'"+ issue+"','"+server+"','"+login+"','"+pwd+"','"+port+"','"+location+"')";
			//" values(?,?,?,?,?,?,?,?)";
			//int i=stmt.executeUpdate(query);
			 System.out.println("In exam.Java****querysno"+querysno);
			Statement stsno = conn.createStatement();
			ResultSet rssno = stsno.executeQuery(querysno);
			while (rssno.next())
		      {
		         sno = rssno.getInt("sno");
		         lst.add(sno);
		      }
			System.out.println("In exam.Java****"+lst);
			int index = new Random().nextInt(lst.size());
			Integer randomeValue = lst.get(index);
			lst.clear();
			query= "select sno,question,a1,a2,a3,a4,ans from question where sno ="+ randomeValue;
			System.out.println(query);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
		      {
		         sno = rs.getInt("sno");
		         question = rs.getString("question");
		         a1 = rs.getString("a1");
		         a2 = rs.getString("a2");
		         a3 = rs.getString("a3");
		         a4 = rs.getString("a4");
		         ans = rs.getString("ans");
		         
		        
		      }
			
			String querytest = "select testno from result where email="+usr;
			System.out.println("In exam.Java****"+querytest);
			Statement sttest = conn.createStatement();
			ResultSet rstest = sttest.executeQuery(querytest);
			while (rstest.next())
		      {
		         testno = rstest.getInt("testno");
		         System.out.println("----IN SIDE WHILE MALAY ADDING IN exam for testno:"+testno);
		         testno = testno+1;
		      }
			System.out.println("-----MALAY ADDING IN exam for testno:"+testno);
			String insert = "insert into questioninprogress (email,sno,current,answer,testno) values(?,?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(insert);
			preparedStmt.setString (1, user);
			preparedStmt.setInt (2, sno);
			preparedStmt.setString (3, "Y");
			preparedStmt.setString (4, ans);
			preparedStmt.setInt (5, testno);
			preparedStmt.execute();
			preparedStmt.close();
		      st.close();
		      stsno.close();
		      sttest.close();
		      preparedStmt.close();
		      conn.close();
		      snos=sno+"";
		      String testnum = testno+"";
		     // HttpSession session = request.getSession(false);
				//save message in session
				session.setAttribute("sno", snos);
				session.setAttribute("question", question);
				session.setAttribute("a1", a1);
				session.setAttribute("a2", a2);
				session.setAttribute("a3", a3);
				session.setAttribute("a4", a4);
				session.setAttribute("user", user);
				session.setAttribute("ans", ans);
				session.setAttribute("testno", testnum);
				session.setAttribute("type", type);
				session.setAttribute("totalquestion", totalquestion);
				session.setAttribute("duration", duration);
				response.sendRedirect("next.jsp");
				System.out.println("INSIDE exam.java###"+user+" "+ans+" "+" "+sno+" "+testno+" "+type+" "+totalquestion+" "+duration);
				testno = 1;
			 
			} catch(Exception e){
			     
			    System.out.println("ERROR:"+e.getMessage());
			request.setAttribute("Error",e);
			RequestDispatcher rd= request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
			}finally {
			// RequestDispatcher rd= request.getRequestDispatcher("index.jsp");
			// rd.forward(request, response);
			out.close();
			}
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
