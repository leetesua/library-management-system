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

public class Student extends JPanel implements ActionListener{
	
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
	private String[] str1={"YES","NO"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox jcb=new JComboBox(str1);
//	private JButton jb=new JButton("submit");
	
	
	private JLabel[] jlArray=new JLabel[]{
			new JLabel("UserNO"),new JLabel("UserName"),new JLabel("Gender")
	};
	private JLabel[] jlArray1=new JLabel[]{
			new JLabel("Location"),new JLabel("Tel"),new JLabel("Password")
	};
	private JLabel jlArray2=new JLabel("borrow authority");
	private JTextField[] jtxtArray=new JTextField[]{
			new JTextField(),new JTextField(),new JTextField(),new JTextField()
	};
	private JTextField[] jtxtArray1=new JTextField[]{
			new JTextField(),new JTextField(),new JTextField(),new JTextField()
	};
	
	
	private JButton[] jbArray={
			new JButton("insert user"), new JButton("delete user"), new JButton("update user"), new JButton("search user")
	};
	
	
	private ButtonGroup bg=new ButtonGroup();
	Vector<String> head=new Vector<String>();
	{
		head.add("UserNO");
		head.add("UserName");
		head.add("Gender");
		head.add("Location");
		head.add("Tel");
		head.add("Password");
		head.add("borrowTime");
	}
	@SuppressWarnings("rawtypes")
	Vector<Vector> data=new Vector<Vector>();
	DefaultTableModel dtm=new DefaultTableModel(data,head);
	JTable jt=new JTable(dtm);
	JScrollPane jspn=new JScrollPane(jt);
	
	public Student(){
		this.setLayout(new GridLayout(1,1));
		jpt.setLayout(null);
		jpb.setLayout(null);
		jpt.add(jcb);
		jcb.setBounds(780, 20, 120, 20);
		jcb.addActionListener(this);
//		jpt.add(jb);
//		jb.setBounds(580, 20, 120, 20);
//		jb.addActionListener(this);
		for(int i=0;i<4;i++){
			jbArray[i].setBounds(60+i*160,100, 120, 20);
			jpt.add(jbArray[i]);
			jbArray[i].addActionListener(this);
			bg.add(jbArray[i]);
		}
		for(int i=0;i<3;i++){
			jlArray[i].setBounds(60+i*200,20, 80, 20);
			jtxtArray[i].setBounds(130+i*200, 20, 120, 20);
			
			jpt.add(jtxtArray[i]);
			jpt.add(jlArray[i]);
			
			jlArray1[i].setBounds(60+i*200,60, 80, 20);
			jtxtArray1[i].setBounds(130+i*200, 60, 120, 20);
			
			jpt.add(jtxtArray1[i]);
			jpt.add(jlArray1[i]);
		}
		jlArray2.setBounds(660,20, 120, 20);
		jpt.add(jlArray2);
		for(int i=0;i<3;i++){
			jtxtArray[i].setEditable(true);
		}
//		jtxtArray[3].setBounds(350, 20, 120, 20);
//		jpt.add(jtxtArray[3]);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		jsp.setDividerSize(4);
		this.add(jsp);
		jsp.setDividerLocation(100);
		this.setBounds(3, 10, 600, 400);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jtxtArray[0]){jtxtArray[1].requestFocus();}
		if(e.getSource()==jtxtArray[1]){jtxtArray[2].requestFocus();}
		if(e.getSource()==jtxtArray[2]){jtxtArray1[0].requestFocus();}
		if(e.getSource()==jtxtArray1[0]){jtxtArray1[1].requestFocus();}
		if(e.getSource()==jtxtArray1[1]){jtxtArray1[2].requestFocus();}
		
		if(e.getSource()==jbArray[0]){this.insertStudent();}
		if(e.getSource()==jbArray[1]){this.deleteStudent();}
		if(e.getSource()==jbArray[2]){this.updateStudent();}
		if(e.getSource()==jbArray[3]){this.searchStudent();}
	}
	
	public void insertStudent (){
//		for(int i=0;i<6;i++){
//			str1[i]=jtxtArray[i].getText().trim();
//		}
//		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")&&str1[3].equals("")&&str1[4].equals("")&&str1[5].equals("")){
//			JOptionPane.showMessageDialog(this, "student information can't be empty!","information",JOptionPane.INFORMATION_MESSAGE);
//			return;
//		}
//		!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")&&!str1[4].equals("")&&!str1[5].equals("")
		if(true){
//			str1[6]=jcp.getSelectedItem().toString();
			sql="insert into STUDENT(StuNO,StuName,StuSex,Class,Department,"+"Password) values('"+jtxtArray[0]+"','"+jtxtArray[1]+"','"+
					jtxtArray[2]+"','"+jtxtArray1[0]+"','"+jtxtArray1[1]+"','"+jtxtArray1[2]+"')";
			db=new DataBase();
			db.updateDB(sql);
			Vector<String> v=new Vector<String>();
//			for(int i=0;i<=6;i++){
//				v.add(str1[1]);
//				if(i<6){
//					jtxtArray[i].setText("");
//				}
//			}
			v.add(jtxtArray[0].getText().trim());
			v.add(jtxtArray[1].getText().trim());
			v.add(jtxtArray[2].getText().trim());
			v.add(jtxtArray1[0].getText().trim());
			v.add(jtxtArray1[1].getText().trim());
			v.add(jtxtArray1[2].getText().trim());
			data.add(v);
			dtm.setDataVector(data,head);
			jt.updateUI();
			jt.repaint();
			return;
		}
	}

	public  void deleteStudent(){
		String stuno=jtxtArray[0].getText().trim();
		if(stuno.equals("")){
			JOptionPane.showMessageDialog(this, "student ID can't be empty!","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select * from STUDENT where StuNO="+Integer.parseInt(stuno);
		db=new DataBase();
		db.selectDB(sql);
		try{
			if(db.rs.next()){
				sql="delete from STUDENT where StuNO="+Integer.parseInt(stuno);
				db=new DataBase();
				db.updateDB(sql);
				JOptionPane.showMessageDialog(this, "student information deleted successfully!","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else{
				JOptionPane.showMessageDialog(this, "can't delete student information because this student hasn't return yet");			
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		sql="delete from STUDENT where StuNO="+Integer.parseInt(stuno);
		db.updateDB(sql);
	}
	public void updateStudent(){
		String str[]=new String[7];
		int row=jt.getSelectedRow();
		if(row>=0){
			for(int i=0;i<7;i++){
				str[i]=jt.getValueAt(row,i).toString();
			}
			sql="update STUDENT set StuName='"+str[1]+"',StuSex='"+str[2]+"',Class='"+str[3]+"',Department='"+str[4]+"',Permitted='"+str[5]+"',Password='"+str[6]+"'where StuNO="+Integer.parseInt(str[0].trim());
			
			db=new DataBase();
			db.updateDB(sql);
			JOptionPane.showMessageDialog(this, "successfully Changed!","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(row==-1){
			JOptionPane.showMessageDialog(this, "please click search botton and then choose the line to change click change information botton.","Warning!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
	public void searchStudent(){
		if(jtxtArray[0].getText().equals("")){
			JOptionPane.showMessageDialog(this, "input can't be empty,please re-enter!","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select * from STUDENT where StuNO="+Integer.parseInt(jtxtArray[0].getText().trim());
		db=new DataBase();
		db.selectDB(sql);
		try{
			int k=0;
			Vector<Vector> vtemp=new Vector<Vector>();
			while(db.rs.next()){
				k++;
				Vector<String> v=new Vector<String>();
				for(int i=1;i<=7;i++){
					String str=db.rs.getString(i).trim();
					str=new String(str.getBytes("ISO-8859-1"),"gb2312");
					v.add(str);
				}
				vtemp.add(v);
			}
			if(k==0){
				JOptionPane.showMessageDialog(this, "nothing found regrading to your research","information",JOptionPane.INFORMATION_MESSAGE);
			}
			dtm.setDataVector(vtemp,head);
			jt.updateUI();
			jt.repaint();
		}
		catch(Exception e){e.printStackTrace();}
	}
}
