import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class StudentSystem {
	public void setManager(){
		String sql="select permitted from manager where mgNo='"+mgNo+"'";
	DataBase db=new DataBase();
	db.selectDB(sql);
	try{
		db.rs.next();
		String str=db.rs.getString(1).trim();
		if(str.equals("0")){mg.setFlag(false);}
	}
	catch(Exception e){e.printStackTrace();}
	}
	public void initJp(){
		jp.setLayout(cl);
		jp.add(jlRoot,"root");
		jp.add(new Student(),"stu");
		jp.add(new BookManage(),"bm");
		jp.add(new SearchBook(),"sb");
		jp.add(new BorrowBook(),"bb");
		jp.add(new ReturnBook(),"rb");
		jp.add(this.mg,"Manager");
		jp.add(new ExceedTime(),"et");
	}
	public void addTreeListener(){
		jt.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e){
				DefaultMutableTreeNode cdmtn=(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
				NodeValue cnv=(NodeValue)cdmtn.getUserObject();
				if(cnv.value.equals("Library Management System")){
					cl.show(jp,"root");
				}
				if(cnv.value.equals("student user management")){
					cl.show(jp,"stu");
				}
				else if(cnv.value.equals("books management")){
					cl.show(jp,"bm");
				}
				if(cnv.value.equals("search for books")){
					cl.show(jp,"sb");
				}
				else if(cnv.value.equals("borrow books")){
					cl.show(jp,"bb");
				}
				else if(cnv.value.equals("return books")){
					cl.show(jp,"rb");
				}
				else if(cnv.value.equals("exceed time")){
					cl.show(jp,"et");
				}
				else if(cnv.value.equals("manager management")){
					cl.show(jp,"manager");
				}
				else if(cnv.value.equals("exit")){
					int i=JOptionPane.showConfirmDialog(Root.this, "want to exit the system?","information",JOptionPane.YES_NO_CANCEL_OPTION);
					if(i==JOptionPane.YES_OPTION){System.exit(0);}
				}
				
			}
		});
	}	
}
