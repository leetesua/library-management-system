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

public class BookManage extends JPanel implements ActionListener{
	
	
	int flag;
	String sql;
	DataBase db;
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jpt=new JPanel();
	private JPanel jpb=new JPanel();
	private String[] str1={"book name","publishing company","author","purchase time"};
	private JComboBox jcb=new JComboBox(str1);
	private JButton jb=new JButton("submit");
	private JLabel[] jlArray=new JLabel[]{
			new JLabel("book name"),new JLabel("author"),new JLabel("publishing company")
	};
	private JTextField[] jtxtArray=new JTextField[]{
			new JTextField(),new JTextField(),new JTextField(),new JTextField()
	};
	private JRadioButton[] jbArray={
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
	
	
	
	
	
	
	public BookManage(){
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jtxtArray[0]){jtxtArray[1].requestFocus();}
		if(e.getSource()==jtxtArray[1]){jtxtArray[2].requestFocus();}
		if(e.getSource()==jtxtArray[2]){jtxtArray[3].requestFocus();}
		if(e.getSource()==jtxtArray[3]){jtxtArray[4].requestFocus();}
		
		if(e.getSource()==jbArray[0]){this.insertBook();}
		if(e.getSource()==jbArray[1]){this.deleteBook();}
		if(e.getSource()==jbArray[2]){this.updateBook();}
		if(e.getSource()==jbArray[3]){this.searchBook();}
	}
	public void insertBook(){
		for(int i=0;i<5;i++){
			str1[1]=jtxtArray[i].getText().trim();
			
		}
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")&&str1[3].equals("")&&str1[4].equals("")){
			JOptionPane.showMessageDialog(this, "Book information can't be empty","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")&&!str1[4].equals("")){
			str1[5]=jcp1.getSelectedItem().toString();
			str1[6]=jcp2.getSelectedItem().toString();
			sql="insert into BOOK values('"+str1[0]+"'+'"+str1[1]+"'+'"+str1[2]+"'+'"+str1[3]+"'+'"+str1[4]+"'+'"+str1[5]+"'+'"+str1[6]+"'+)";
			db=new DataBase();
			db.updateDB(sql);
			Vector<String> v=new Vector<String>();
			for(int i=1;i<=7;i++){
				v.add(str1[i-1]);
			}
			data.add(v);
			dtm.setDataVector(data,head);
			jt.updateUI();jt.repaint();
			return;
		}
	}
	public void deleteBook(){
		String bookno=jtxtArray[0].getArray[0].getText().trim();
		if(bookno.equals("")){
			JOptionPane.showMessageDialog(this, "book number can't be empty!","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select * from RECORD where BookNO="+Integer.parseInt(bookno);
		
		db=new DataBase();
		db.selectDB(sql);
		try{
			if(db.rs.next()){
				JOptionPane.showMessageDialog(this, "can't delete this book","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		catch(Exception e){e.printStackTrace();}
		sql="delete from book where BookNO="+Integer.parseInt(bookno);
		
		db.updateDB(sql);
	}
	public void searchBook(){
		JOptionPane.showMessageDialog(this, "please click 'search book' button on the left","strong recommandation",JOptionPane.INFORMATION_MESSAGE);
		return;
	}
	public void updateBook(){
		String bookno=jtxtArray[0].getText().trim();
		if(bookno.equals("")){
			JOptionPane.showMessageDialog(this, "please input the book number you need to change information of","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else{
			for(int i=0;i<5;i++){
				str1[1]=jtxtArray[1].getText().trim();
			}
			db=new DataBase();
			int i=0;
			int flag=0;
			int b=Integer.parseInt(bookno);
			if(!str1[1].equals("")){i=i+1;}
			if(!str1[2].equals("")){i=i+2;}
			if(!str1[3].equals("")){i=i+4;}
			if(!str1[4].equals("")){i=i+8;}
			switch(i){
				case 0:
					JOptionPane.showMessageDialog(this, "infomation can't be empty!","information",JOptionPane.INFORMATION_MESSAGE);
					break;
				case 15:
					sql="update BOOK set BookName='"+str1[1]+"',"+"Author='"+str1[2]+"',"+"Publishment='"+str1[3]+"',"+"BuyTime='"+str1[2]+"' where BookNO="+b;
					flag=db.updateDB(sql);
					if(flag>0){
						JOptionPane.showMessageDialog(this, "congratulations! changed successfully","information",JOptionPane.INFORMATION_MESSAGE);
					}
					break;
				
			}
		}
	}
	
}
