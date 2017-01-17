import java.awt.GridLayout;
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

public class SearchBook extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int flag;
	String sql;
	DataBase db;
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jpt=new JPanel();
	private JPanel jpb=new JPanel();
	private String[] str={"book name","publishing company","author","purchase time"};
	private JComboBox<?> jcb=new JComboBox<Object>(str);
	private JButton jb=new JButton("submit");
	private JLabel[] jlArray=new JLabel[]{
			new JLabel("book name"),new JLabel("author"),new JLabel("publisher")	};
	private JTextField[] jtxtArray=new JTextField[]{
			new JTextField(),new JTextField(),new JTextField(),new JTextField()	};
	private JRadioButton[] jrbArray={
			new JRadioButton("simple search",true), new JRadioButton("advanced search")	};
	private ButtonGroup bg=new ButtonGroup();
	Vector<String> head=new Vector<String>();
	{
		head.add("book number");
		head.add("book name");
		head.add("author");
		head.add("Originally published");
		head.add("borrow or not");
		head.add("reserve or not");
	}
	Vector<Vector> data=new Vector<Vector>();
	DefaultTableModel dtm=new DefaultTableModel(data,head);
	JTable jt=new JTable(dtm);
	JScrollPane jspn=new JScrollPane(jt);	
	
	
	public void actionPerformed(ActionEvent e){
		if(jcb.getSelectedIndex()>=0&&jcb.getSelectedIndex()<4){
			jtxtArray[3].requestFocus();
			if(e.getSource()==jb){
				String str=jtxtArray[3].getText().trim();
				if(str.equals("")){
					JOptionPane.showMessageDialog(this, "please input necessary information!","information",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(jcb.getSelectedIndex()==0){
					sql="select * from BOOK where BookName='"+str+"'";
					jtxtArray[3].setText("");
				}
				else if(jcb.getSelectedIndex()==1){
					sql="select * from BOOK where Publishment='"+str+"'";
					jtxtArray[3].setText("");
				}
				else if(jcb.getSelectedIndex()==2){
					sql="select * from BOOK where Author='"+str+"'";
					jtxtArray[3].setText("");
				}
				else{
					sql="select * from BOOK where BuyTime='"+str+"'";
					jtxtArray[3].setText("");
				}
				db=new DataBase();
				db.selectDB(sql);
			}
		}
		Vector<Vector> vtemp=new Vector<Vector>();
		try{
			int flag=0;
			while(db.rs.next()){
				flag++;
				Vector<String> v=new Vector<String>();				
				for(int i=1;i<=7;i++){
					String str1	=db.rs.getString(i);
					v.add(str1);
				}
				vtemp.add(v);
			}
			if(flag==0){
				JOptionPane.showMessageDialog(this, "nothing found","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}catch(Exception ea){ea.printStackTrace();}
		dtm.setDataVector(vtemp, head);
		jt.updateUI();
		jt.repaint();
		db.dbClose();
	}
	
	
	public SearchBook(){
		this.setLayout(new GridLayout(1,1));
		jpt.setLayout(null);
		jpb.setLayout(null);
		jpt.add(jcb);
		jcb.setBounds(160, 20, 150, 20);
		jcb.addActionListener(this);
		jpt.add(jb);
		jb.setBounds(580, 20, 160, 20);
		jb.addActionListener(this);
		for(int i=0;i<2;i++){
			jrbArray[i].setBounds(20,20+i*40,130,20);
			jpt.add(jrbArray[i]);
			jrbArray[i].addActionListener(this);
			bg.add(jrbArray[i]);
		}
		for(int i=0;i<3;i++){
			jlArray[i].setBounds(160+i*200,60, 80, 20);
			jtxtArray[i].setBounds(230+i*200, 60, 120, 20);
			
			jpt.add(jtxtArray[i]);
			jpt.add(jlArray[i]);
		}
		for(int i=0;i<3;i++){
			jtxtArray[i].setEditable(false);
		}
		jtxtArray[3].setBounds(350, 20, 120, 20);
		jpt.add(jtxtArray[3]);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		jsp.setDividerSize(4);
		this.add(jsp);
		jsp.setDividerLocation(100);
		this.setBounds(3, 10, 600, 400);
		this.setVisible(true);
	}
	public int seniorSearch(){
		return flag;	
	}
	
//	public static void main(String[] args){
//		SearchBook sb=new SearchBook();
//	}
}





























