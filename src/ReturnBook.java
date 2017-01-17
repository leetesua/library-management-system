import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ReturnBook extends JPanel implements ActionListener{
	
	
	
	
	int flag;
	String sql;
	DataBase db;
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jpt=new JPanel();
	private JPanel jpb=new JPanel();
	private String[] str={"book name","publishing company","author","purchase time"};
	private JComboBox jcb=new JComboBox(str);
	private JButton jb=new JButton("submit");
	private JLabel[] jlArray=new JLabel[]{
			new JLabel("book name"),new JLabel("author"),new JLabel("publishing company")
	};
	private JTextField[] jtxtArray=new JTextField[]{
			new JTextField(),new JTextField(),new JTextField(),new JTextField()
	};
	private JRadioButton[] jrbArray={
			new JRadioButton("simple search",true), new JRadioButton("advanced search")
	};
	private ButtonGroup bg=new ButtonGroup();
	Vector<String> head=new Vector<String>();
	{
		head.add("book number");
		head.add("book name");
		head.add("author");
		head.add("purchase time");
		head.add("borrow or not");
		head.add("reserve or not");
	}
	Vector<Vector> data=new Vector<Vector>();
	DefaultTableModel dtm=new DefaultTableModel(data,head);
	JTable jt=new JTable(dtm);
	JScrollPane jspn=new JScrollPane(jt);
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void loseBook(int row){
		String bname ="";
		int lbno=0;
		int bno=Integer.parseInt((String)jt.getValueAt(row,0));
		String sno=(String)jt.getValueAt(row,1);
		sql="select BookName from BOOK where BookNO="+bno;
		db=new DataBase();
		db.selectDB(sql);
		try{
			if(db.rs.next()){
				bname=db.rs.getString(1).trim();
				
			}
		}
		catch(Exception e){e.printStackTrace();}
		
		sql="select MAX(LbNO) from LoseBook";
		db.selectDB(sql);
		
		try{
			if(db.rs.next()){
				lbno=db.rs.getInt(1);
				lbno++;
			}
		}
		catch(Exception ea){ea.printStackTrace();
		}
		
		sql="insert into LOSEBOOK values("+lbno+","+sno+","+bno+",'"+bname+"')";
		db.updateDB(sql);
		try{
			while(db.rs.next()){
				sql="delect from ORDERREPORT where BookNO="+bno;
				db.updateDB(sql);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		sql="select BookNo from EXCEEDTIME where BookNO="+bno;
		db.selectDB(sql);
		try{
			while(db.rs.next()){
				sql="delect from EXCEEDTIME where BookNO="+bno;
				db.updateDB(sql);
			}
		}
		catch(Exception e){e.printStackTrace();}
		sql="delete from RECORD where BookNO="+bno;
		db.updateDB(sql);
		sql="delete from BOOK where BookNO="+bno;
		int i=db.updateDB(sql);
		db.dbClose();
		if(i>0){
			JOptionPane.showMessageDialog(this, "congratulations, suspend successfully","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else{
			JOptionPane.showMessageDialog(this, "SORRY, SUSPEND FAILED","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
	
	public void updateTable(){
		sql="select * from RECORD where StuNO="+jtxt.getText().trim();
		db=new DataBase();
		db.selectDB(sql);
		Vector<Vector> vtemp=new Vector<Vector>();
		try{
			while(db.rs.next()){
				Vector<String> v=new Vector<String>();
				for(int i=1;i<7;i++){
					String str=db.rs.getString(i);
//					str=new String(str.getBytes("ISO-8859-1"),"gb2312");
					v.add(str);
				}
				vtemp.add(v);
			}
			db.dbClose();
		}
		catch(Exception ea){ea.printStackTrace();}
		jt.clearSelection();
		dtm.setDataVector(vtemp,head);
		jt.updateUI();jt.repaint();
	}
	
	public int checkTime(int sno,int bno){
		int day=0;
		int flag=0;
		String bname="";
		Date now = new Date();
		String returntime="";
		sql="select ReturnTime from RECORD where StuNO="+sno+"and BookNO="+bno;
		
		db=new DataBase();
		db.selectDB(sql);
		try{
			if(db.rs.next()){
				returntime=db.rs.getString(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		String[] strday=returntime.split("\\.");
		int ryear=Integer.parseInt(strday[0].trim());
		int rmonth=Integer.parseInt(strday[1].trim());
		int rday=Integer.parseInt(strday[2].trim());
		day=(now.getYear()+1900-ryear)*365+(now.getMonth()+1-rmonth)*30+(now.getDate()-rday);
		if(day==-30){
			JOptionPane.showMessageDialog(this, "can't return book borrowed today!","information",JOptionPane.INFORMATION_MESSAGE);
			flag=0;
		}
		else if(day>0){
			int i=JOptionPane.showConfirmDialog(this, "this book is over due, should pay bill"+day*0.1+"dollar, want to pay bill","information",JOptionPane.YES_NO_OPTION);
			if(i==JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(this, "you already paid "+day*0.1+"dollar","information",JOptionPane.INFORMATION_MESSAGE);
				flag=-2;
			}
			else{
				flag=-1;
				sql="select BookName from BOOK where BookNO="+bno;
				
				db.selectDB(sql);
				try{
					if(db.rs.next()){
						bname=db.rs.getString(1).trim();
					}
				}
				catch(Exception e){e.printStackTrace();
				}
				sql="insert into EXCEEDTIME(StuNO,BookNO,BookName,DelayTime)values("+sno+","+bno+",'"+bname+"',"+day+")";
				db.updateDB(sql);
			}
		}
		else{
			flag=1;
		}
		db.dbClose();
		return flag;
		
	}
}
