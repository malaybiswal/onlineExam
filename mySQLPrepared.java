package onlineExam;


import java.sql.*;
import java.util.Calendar;

public class mySQLPrepared
{
	//static String  myDriver = "org.gjt.mm.mysql.Driver";
	static String myDriver = "com.mysql.jdbc.Driver";
    static String myUrl = "jdbc:mysql://localhost/exam";
  public static void main(String[] args)
  {
	  
	  
    try
    {
      // create a mysql database connection
      
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "malay", "malay123");
     
      // create a sql date object so we can use it in our INSERT statement
      Calendar calendar = Calendar.getInstance();
      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
 
      // the mysql insert statement
      String query = " insert into users (first_name, last_name, date_created, is_admin, num_points)"
        + " values (?, ?, ?, ?, ?)";
 
      // create the mysql insert preparedstatement
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setString (1, "Barney");
      preparedStmt.setString (2, "Rubble");
      preparedStmt.setDate   (3, startDate);
      preparedStmt.setBoolean(4, false);
      preparedStmt.setInt    (5, 5000);
 
      // execute the preparedstatement
      preparedStmt.execute();
       
      conn.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
  }
  
  public Connection getConn() throws SQLException{
	  
	  
	  Connection conn = null;
	  try {
		Class.forName(myDriver);
		 conn = DriverManager.getConnection(myUrl, "malay", "malay123");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
	  return conn;
  }
  
  
}

