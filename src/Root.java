import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Root extends JFrame{
	
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DefaultMutableTreeNode[] dmtn={
			new DefaultMutableTreeNode(new NodeValue("Library Management System")),
			new DefaultMutableTreeNode(new NodeValue("student user management")),
			new DefaultMutableTreeNode(new NodeValue("books management")),
			new DefaultMutableTreeNode(new NodeValue("search for books")),
			new DefaultMutableTreeNode(new NodeValue("borrow books")),
			new DefaultMutableTreeNode(new NodeValue("return books")),
			new DefaultMutableTreeNode(new NodeValue("exceed time")),
			new DefaultMutableTreeNode(new NodeValue("manager management")),
			new DefaultMutableTreeNode(new NodeValue("exit"))
	};
	DefaultTreeModel dtm=new DefaultTreeModel(dmtn[0]);	
	JTree jt=new JTree(dtm);
	JScrollPane jsp=new JScrollPane(jt);
	private JSplitPane jsplr = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);
	private JPanel jp=new JPanel();
	Image image=new ImageIcon(this.getClass().getResource("/f.jpg")).getImage(); 
	ImageIcon ii=new ImageIcon(image);
	private JLabel jlRoot=new JLabel(ii);
	private Manager mg;
	int mgNo;
	CardLayout cl= new CardLayout();
	public Root(int mgNo){
		this.mgNo=mgNo;
		mg=new Manager(mgNo);
		this.setManager();
		this.initJp();
		this.addTreeListener();
		for(int i=1;i<9;i++){
			dtm.insertNodeInto(dmtn[i], dmtn[0], i-1);
		}
		jt.setEditable(false);
		this.add(jsplr);
		jsplr.setLeftComponent(jt);
		jp.setBounds(200,50,600,500);
		jsplr.setRightComponent(jp);
		jsplr.setDividerLocation(200);
		jsplr.setDividerSize(4);
		jlRoot.setFont(new Font("courier",Font.PLAIN,30));
		jlRoot.setHorizontalAlignment(JLabel.CENTER);
		jlRoot.setVerticalAlignment(JLabel.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		"ico.png"
		Image image =new ImageIcon(this.getClass().getResource("/foreface.jpg")).getImage();
		this.setIconImage(image);
		this.setTitle("Library Management System Designed by @Dexuan Li");
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=500;
		int h=400;
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		jt.setShowsRootHandles(true);		
	}
	public static void main(String[] args){
		@SuppressWarnings("unused")
		Root r=new Root(1);
	}
	public void setManager() {
		String sql = "select permitted from manager where mgNo='"+mgNo+"';";
		DataBase db = new DataBase();
		db.selectDB(sql);
		try {
			db.rs.next();
			String str = db.rs.getString(1).trim();
			if (str.equals("0")) {
				mg.setFlag(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void initJp(){
		jp.setLayout(cl);
		jp.add(jlRoot,"root");
		jp.add(new SearchBook(),"sb");
		jp.add(new BorrowBook(),"bb");
		jp.add(new Student(),"stu");
		jp.add(new BookManage(),"bm");
		jp.add(new ReturnBook(),"rb");
		jp.add(this.mg,"Manager");
//		jp.add(new ExceedTime(),"et");
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

class NodeValue{
	String value;
	public NodeValue(String value){
		this.value=value;
		
	}
	public String getValue(){
		return this.value;
	}
	public String toString(){
		return value;
	}	
}