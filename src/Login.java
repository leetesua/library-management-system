import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		Login L =new Login();
		System.out.println(L);
	}
	
	private JPanel jp=new JPanel();
	private JLabel[] jlArray={
			new JLabel("user IP"),new JLabel("ports number"),new JLabel("user name"),
			new JLabel("password"),new JLabel("")
	};
	private JButton[] jbArray={
			new JButton("student login"),new JButton("clear"),new JButton("Administrator login")
	};
	private JTextField[] jtxtArray={
			new JTextField("127.0.0.1"),new JTextField("3306"),new JTextField("1001")
	};
	private JPasswordField jpassword=new JPasswordField("1001");
	String sql;
	DataBase db;
	
	public Login(){
		jp.setLayout(null);
		for(int i=0;i<4;i++){
			jlArray[i].setBounds(30,20+i*50,80,25);
			jp.add(jlArray[i]);
		}
		for(int i=0;i<3;i++){
			jbArray[i].setBounds(10+i*155, 230, 150, 25);
			jp.add(jbArray[i]);
			jbArray[i].addActionListener(this);
		}
		for(int i=0;i<3;i++){
			jtxtArray[i].setBounds(150,20+50*i,180,25);
			jp.add(jtxtArray[i]);
			jtxtArray[i].addActionListener(this);
		}
		jpassword.setBounds(150,170,180,25);
		jp.add(jpassword);
		jpassword.setEchoChar('$');
		jpassword.addActionListener(this);
		jlArray[4].setBounds(10,280,300,25);
		jp.add(jlArray[4]);
		this.add(jp);
		Image image=new ImageIcon().getImage();
		this.setIconImage(image);
		this.setTitle("Library Management System - login");
		this.setResizable(true);
		this.setBounds(100,100,400,350);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String mgno=jtxtArray[2].getText().trim();
		String mgIP=jtxtArray[0].getText().trim();
		String port=jtxtArray[1].getText().trim();
		String message=mgIP+":"+port;
		DataBase.message=message;
//		DataBase.log=this;
		if(e.getSource()==jtxtArray[0]){
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jtxtArray[1]){
			jtxtArray[2].requestFocus();
		}
		if(e.getSource()==jtxtArray[2]){
			jpassword.requestFocus();
		}
		else if(e.getSource()==jbArray[1]){
			jlArray[4].setText("");  
			jtxtArray[2].setText("");
			jpassword.setText("");
			jtxtArray[2].requestFocus();
		}
		else if(e.getSource()==jbArray[2]){
			if(!mgno.matches("\\d+")){
				JOptionPane.showMessageDialog(this,"wrong user formation","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(jtxtArray[1].getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "port number can't be empty","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			sql="select mgNo,password from manager where mgNo="+Integer.parseInt(mgno);
			db=new DataBase();
			db.selectDB(sql);
			try{
				String mgNo="";
				String password="";
				while(db.rs.next()){
					mgNo=db.rs.getString(1).trim();
					password=db.rs.getString(2).trim();
				}
				if(jtxtArray[2].getText().trim().equals(mgNo)&& String.valueOf(jpassword.getPassword()).equals(password)){
					jlArray[4].setText("Congratulation! login successfully");
					new Root(mgNo);
				this.dispose();
				}	
				else{
					jlArray[4].setText("sorry! login failed");
				}
			}
			catch(Exception e1){
				e1.printStackTrace();
			}
			db.dbClose();
					
		}
		else if(e.getSource()==jbArray[0]){
			if(!jtxtArray[2].getText().trim().matches("\\d+")){
				JOptionPane.showMessageDialog(this, "wrong input,only numbers allowed!!","information",JOptionPane.INFORMATION_MESSAGE);
				return;
				
			}
			if(jtxtArray[0].getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "IP address can't be empty!","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(jtxtArray[1].getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "port number can't be empty!","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			sql="select StuNO,Password from STUDENT where StuNO="+Integer.parseInt(jtxtArray[2].getText().trim());
			db=new DataBase();
			db.selectDB(sql);
			try{
				if(!(db.rs.next())){
					JOptionPane.showMessageDialog(this, "wrong student ID input","information",JOptionPane.INFORMATION_MESSAGE);
				}
				else{String stuNO=db.rs.getString(1).trim();
					String password = db.rs.getString(2).trim();
					if(jtxtArray[2].getText().trim().equals(stuNO)&&String.valueOf(jpassword.getPassword()).equals(password)){
						jlArray[4].setText("Congratulation, login successfully");
						new StudentSystem();
						this.dispose();
					}
					else{
						jlArray[4].setText("sorry, login failed");
					}
				}
				
			}
			catch(Exception ex){ex.printStackTrace();}
			db.dbClose();
			
		}
	}	
}
