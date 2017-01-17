import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DataBase {
	Connection con=null;
	Statement stat;
	ResultSet rs;
	int count;
	public static String message;
//	jdbc:mysql://localhost:3306/?user=root   jdbc:mysql://"+message+"/mydb
	public DataBase(){
		try{
			System.out.println("-----0-------");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("-----1-------");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","249131");
				System.out.println("-----2-------");
				stat=con.createStatement();
				System.out.println("-----3-------");
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,"wrong IP number or ports number","message",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public void selectDB(String sql){
		System.out.println("-----a-------");
		try{rs=stat.executeQuery(sql);
		System.out.println("-----b-------");}
		catch(Exception ie){ie.printStackTrace();}
	}
	public int updateDB(String sql){
		try{
//			sql=new String(sql.getBytes(),"ISO-8859-1");
			count=stat.executeUpdate(sql);
		}
		catch(Exception ie){ie.printStackTrace();}
		return count;
	}
	public void dbClose(){
		try{con.close();}
		catch(Exception e){e.printStackTrace();}
	}
}
 