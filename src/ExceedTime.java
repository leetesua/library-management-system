import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ExceedTime extends JPanel implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int day=0;
		DataBase db=new DataBase();
		String sno=(String)jtf.getText().trim();
		if(sno.equals("")){
			JOptionPane.showMessageDialog(this, "student number can't be empty!","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(sno.matches("\\D")){
			JOptionPane.showMessageDialog(this, "student number can only be numbers!","information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String sql="select DelayTime from EXCEEDTIME where StuNO="+Integer.parseInt(sno);
		db.selectDB(sql);
		try{
			int flag=0; 
			while(db.rs.next()){
				flag++;
				day+=db.rs.getInt(1);
						
			}
			if(flag==0){
				JOptionPane.showMessageDialog(this, "your book isn't over due so you don't need to pay bill for it.","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}			
		}
		catch(Exception ex){ex.printStackTrace();}
		
		if(e.getSource()==jb1){
			if(day>0){
				JOptionPane.showMessageDialog(this, "you owe "+day*0.1+" dollar! ","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else{
				JOptionPane.showMessageDialog(this, "your borrowing isn't over due yet, you don't need to pay!","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==jb){
			if(jtf1.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "please input the amont of money you want to pay: ","information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int k=JOptionPane.showConfirmDialog(this, "confirm to pay","information",JOptionPane.YES_NO_OPTION);
			if(k==JOptionPane.YES_OPTION){
				int ii=Integer.parseInt(jtf1.getText().trim());
				if(ii<(day*0.1)){
					sql="update exceedtime set delaytime=delaytime-"+ii/0.1+"where StuNO="+Integer.parseInt(sno);
					db=new DataBase();
					int i=db.updateDB(sql);
					if(i==1){
						JOptionPane.showMessageDialog(this, "you already paid "+ii+" dollar, you still need to pay "+(day*0.1-ii)+" dollar.","information",JOptionPane.INFORMATION_MESSAGE);
					}
					return;
				}
				else{JOptionPane.showMessageDialog(this, "sorry, failed to pay money!","informaiton",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			}
			else{
				JOptionPane.showMessageDialog(this, "you already pay "+day*0.1+" dollar","informaiton",JOptionPane.INFORMATION_MESSAGE);
				jtf.setText("");
				sql="delete from EXCEEDTIME where StuNO="+Integer.parseInt(sno);
				db.updateDB(sql);
			}
		}
		db.dbClose();
	}
}
