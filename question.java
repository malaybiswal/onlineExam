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

import java.sql.*;

@WebServlet("/question")
@SuppressWarnings("serial")
public class question  extends HttpServlet{
	String q, opt1, opt2, opt3, opt4, answer, type, subtype;
	String query;
	Connection conn;
	Statement stmt;
	ResultSet res;
	mySQLPrepared dbconn;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			try {
			 
			dbconn=new mySQLPrepared();
			 
			//Af_Scheme_Number=request.getParameter("Af_Scheme_Number");
			q=request.getParameter("q");
			opt1=request.getParameter("opt1");
			opt2=request.getParameter("opt2");
			opt3=request.getParameter("opt3").trim();
			opt4=request.getParameter("opt4");
			answer=request.getParameter("answer");
			type = request.getParameter("type");
			subtype=request.getParameter("subtype");
			
			    System.out.println("GOT THESE FROM HTML: "+q+" "+opt1+" "+opt2+" "+opt3+" "+opt4+" "+type+" "+subtype+" "+answer);
			conn=dbconn.getConn();
			    System.out.println("DB  connection: "+conn);
			//stmt=conn.createStatement();
			    //stmt = conn.prepareStatement();
			query= "insert into question(question,a1,a2,a3,a4,ans,type,subtype)"+
			//" values('"+type+"','"+name+"','"+policy+"',"+account+",'"+ issue+"','"+server+"','"+login+"','"+pwd+"','"+port+"','"+location+"')";
			" values(?,?,?,?,?,?,?,?)";
			//int i=stmt.executeUpdate(query);
			PreparedStatement pst =(PreparedStatement) conn.prepareStatement(query);
			  pst.setString(1,q);  
	          pst.setString(2,opt1);        
	          pst.setString(3,opt2);
	          pst.setString(4,opt3);
	          pst.setString(5,opt4);
	          pst.setString(6,answer);
	          pst.setString(7,type);
	          pst.setString(8,subtype);
	          
	          int i = pst.executeUpdate();
	          //conn.commit();
	          
	          String msg=" ";
	          if(i!=0){  
	            msg="Record has been inserted";
	            out.println("<font size='6' color=blue>" + msg + "</font>");  


	          }  
	          else{  
	            msg="failed to insert the data";
	            out.println("<font size='6' color=blue>" + msg + "</font>");
	           }  
	          pst.close();
	          
			 
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
