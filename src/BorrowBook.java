import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class BorrowBook extends JPanel implements ActionListener {
	
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
		if(e.getSource()==jb2){
			if(jtxt4.getText().equals(""))
				JOptionPane.showInternalMessageDialog(this, "input can't be empty","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select * from STUDENT where StuNO="+Integer.parseInt(jtxt4.getText().trim());
		db=new DataBase();
		db.selectDB(sql);
		Vector<Vector> vtemp=new Vector<Vector>();
		try{
			if(!(db.rs.next())){
				JOptionPane.showInternalMessageDialog(this, "wrong student ID number","information",JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				String stuName=db.rs.getString(2).trim();
				String classes=db.rs.getString(5).trim();
//				stuName=new String(stuName.getBytes("ISO-8859-1"),"gb2312");
//				classes=new String(classes.getBytes("ISO-8859-1"),"gb2312");
				if(db.rs.getString(8).trim().equals("no")){
					JOptionPane.showInternalMessageDialog(this, "you don't have the access to it!","information",JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					db.selectDB(sql);
					do{
						String str6=null;
						String str7=null;
						String bookName=null;
						String author=null;
						if(!(db.rs.next()))
						{JOptionPane.showInternalMessageDialog(this, "no result found regarding to your search","information",JOptionPane.INFORMATION_MESSAGE);}
						Vector<String> v =new Vector<String>();
						for(int i=1;i<=7;i++){
							if(i==5){
								str6=db.rs.getString(i+1);
								str6=new String(str6.getBytes("ISO-8859-1"),"gb2312");
								v.add(str6);
							}
							if(i==6){
								str7=db.rs.getString(i+1);
								str7=new String(str6.getBytes("ISO-8859-1"),"gb2312");
								v.add(str7);
							}
							if(i==2){
								bookName=db.rs.getString(i+1);
								bookName=new String(str6.getBytes("ISO-8859-1"),"gb2312");
								v.add(bookName);
							}
							if(i==3){
								author=db.rs.getString(i+1);
								author=new String(str6.getBytes("ISO-8859-1"),"gb2312");
								v.add(author);
							}
							if(i==1){
								String str=db.rs.getString(i).trim();
								str=new String(str6.getBytes("ISO-8859-1"),"gb2312");
								v.add(str);
							}
							if(i==4){
								String str=db.rs.getString(i).trim();
								str=new String(str6.getBytes("ISO-8859-1"),"gb2312");
								v.add(str); 
							}
						}
						vtemp.add(v);
						dtm.setDataVector(vtemp,head);
						jt.updateUI();
						jt.repaint();
					}
				}
			}
		}
	}
}
