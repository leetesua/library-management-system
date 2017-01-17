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

public class Manager extends JPanel implements ActionListener{
	
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
	private JRadioButton[] jbArray={
			new JRadioButton("simple search",true), new JRadioButton("advanced search")
	};
	private ButtonGroup bg=new ButtonGroup();
	Vector<String> head=new Vector<String>();
	{head.add("book number");
		head.add("book name");
		head.add("author");
		head.add("purchase time");
		head.add("borrow or not");
		head.add("reserve or not");	}
	Vector<Vector> data=new Vector<Vector>();
	DefaultTableModel dtm=new DefaultTableModel(data,head);
	JTable jt=new JTable(dtm);
	JScrollPane jspn=new JScrollPane(jt);
	
	public static void main(String[] args){
		Manager m=new Manager("");
	}	
	public Manager(int mgNo){
		
	}
	public void setFlag(boolean b){
		jbArray[0].setEnabled(b);
		jbArray[1].setEnabled(b);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jtxtArray[0]) {
			jtxtArray[1].requestFocus();
		}
		if (e.getSource() == jtxtArray[1]) {
			jtxtArray[2].requestFocus();
		}
		sql = "select permitted from manager where mgNo='" + mgNo + "'";
		db = new DataBase();
		db.selectDB(sql);
		String string = "";
		try {
			while (db.rs.next()) {
				string = db.rs.getString(1).trim();
			}
			int p = 0;
			if (string.equals("1")) {
				p++;
				String jtxt = jtxtArray[0].getText().trim();
				if (jtxt.equals("")) {
					JOptionPane.showMessageDialog(this, "please input manager name:", "information",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (e.getSource() == jbArray[0]) {
					this.insertManager();
				}
				if (e.getSource() == jbArray[1]) {
					this.deleteManager();
				}
				if (e.getSource() == jbArray[2]) {
					this.updateManager();
				}
				if (e.getSource() == jbArray[3]) {
					this.selectManager();
				}
			}
			if (p == 0) {
				jtxtArray[0].requestDefaultFocus();
				String jtxt = jtxtArray[0].getText().trim();
				if (jtxt.equals("")) {
					JOptionPane.showMessageDialog(this, "please input manager name:", "information",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				} else if (jtxt.equals(mgNo)) {
					if (e.getSource() == jbArray[3]) {
						this.manager1();
					}
					if (e.getSource() == jbArray[2]) {
						String str[] = new String[3];
						int row = jt.getSelectedRow();
						if (row >= 0) {
							for (int i = 0; i < 3; i++) {
								str[i] = jt.getValueAt(row, i).toString();
							}
							sql = "update manager set password='" + str[2] + "' where mgNo="
									+ Integer.parseInt(str[0].trim());
							db = new DataBase();
							int i = db.updateDB(sql);
							if (i == 1) {
								JOptionPane.showMessageDialog(this, "changed successfully", "information",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							} else {
								JOptionPane.showMessageDialog(this, "please re-operate again!", "warning!",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						} else {
							JOptionPane.showMessageDialog(this, "please re-operate again!", "warning!",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}

				} else {
					JOptionPane.showMessageDialog(this,
							"you can't check other people's information, please input again!", "information",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		db.dbClose();
	}

	public void manager1(){
		sql="select * from manager where mgNo="+Integer.parseInt(jtxtArray[0].getText().trim());
		db=new DataBase();
		db.selectDB(sql);
		try{
			int k=0;
			Vector<Vector> vtemp=new Vector<Vector>();
			while(db.rs.next()){
				k++;
				Vector<String> v =new Vector<String>();
				for(int i=1;i<=3;i++){
						String str9=db.rs.getString(i).trim();
//						str=new String(str.getBytes("ISO-8859-1"),"gb2312");
						v.add(str);
				}
				vtemp.add(v);
			}
			if(k==0){
				JOptionPane.showMessageDialog(this, "nothing regarding to what you are looking for.","information",JOptionPane.INFORMATION_MESSAGE);
			}
			dtm.setDataVector(vtemp,head);
			jt.updateUI();
			jt.repaint();
		}
		catch(Exception e){e.printStackTrace();}
	}
	public void insertManager(){
		for(int i=0;i<3;i++){
			str1[i]=jtxtArray[i].getText().trim();
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&){
			if(!str1[0].matches("^\\d+$")){
				JOptionPane.showMessageDialog(this, "manager name can only be number!","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int temp=Integer.parseInt(str1[0]);
			sql="insert into manager(mgNo,Permitted,password) values("+temp+",'"+str1[1]+"','"+str1[2]+"')";
			db=new DataBase();
			int j=db.updateDB(sql);
			if(j==0){
				JOptionPane.showMessageDialog(this, "insert failed","inforamtion",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Vector<String> v=new Vector<String>();
			for(int i=0;i<=2;i++){
				v.add(str1[1]);
				if(i<3){
					jtxtArray[i].setText("");
				}
			}
			data.add(v);
			dtm.setDataVector(data,head);
			jt.updateUI();
			jt.repaint();
			return;
		}
		else{
			JOptionPane.showMessageDialog(this, "manager information can't be empty!","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
	public void deleteManager(){
		String mgNo=jtxtArray[0].getText().trim();
		if(mgNo.equals("")){
			JOptionPane.showMessageDialog(this, "student number can't be empty!","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select permitted from manager where mgNo="+Integer.parseInt(mgNo);
		db=new DataBase();
		db.selectDB(sql);
		String str="";
		try{
			while(db.rs.next()){
				str=db.rs.getString(1).trim();
			}
			if(str.equals("1")){
				JOptionPane.showMessageDialog(this, "can't delete super manager information record!!!","warning",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else{
				sql="delete from manager where mgNo="+Integer.parseInt(mgNo);
				
				db=new DataBase();
				db.updateDB(sql);
				JOptionPane.showMessageDialog(this, "delete manager successfully!","information",JOptionPane.INFORMATION_MESSAGE);
			}
		}catch(Exception e){e.printStackTrace();}
	}
	public void updateManager(){
		String str[]=new String[3];
		int row=jt.getSelectedRow();
		if(row>=0){
			for(int i=0;i<3;i++){
				str[i]=jt.getValueAt(row,i).toString();
			}
			sql="update manager set mgNo='"+str[0]+"',permitted='"+str[1]+"',password='"+str[2]+"' where mgNo="+Integer.parseInt(str[0].trim());
			db=new DataBase();
			db.updateDB(sql);
			JOptionPane.showMessageDialog(this, "modified successfully","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(row==-1){
			JOptionPane.showMessageDialog(this, "please search button and change in the excel on the botton and then choose the line and finally click modify information button","warning!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
	public void selectManager(){
		if(jtxtArray[0].getText().equals("")){
			JOptionPane.showMessageDialog(this, "input can't be empty! please re enter again!","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int q=Integer.parseInt(jtxtArray[0].getText().trim());
		sql="select * from manager where mgNo="+q;
		db.selectDB(sql);
		try{
			int k=0;
			Vector<Vector> vtemp=new Vector<Vector>();
			while(db.rs.next()){
				k++;
				Vector<String> v=new Vector<String>();
				for(int i=1;i<=3;i++){
					String str=db.rs.getString(i).trim();
					v.add(str);
				}
				vtemp.add(v);
			}
			if(k==0){
				JOptionPane.showMessageDialog(this, "nothing found regarding to your searching!","information",JOptionPane.INFORMATION_MESSAGE);
			}
			dtm.setDataVector(vtemp,head);
			jt.updateUI();
			jt.repaint();
		}catch(Exception e){e.printStackTrace();}
	}
}
