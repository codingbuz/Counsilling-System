import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

class ViewCollege extends JFrame implements ActionListener
{
	private static Connection con;
	private JButton sub,can;
	
	static
	{
        	try
        	{
		  Class.forName("oracle.jdbc.driver.OracleDriver");                 
con=DriverManager.getConnection("jdbc:ucanaccess://e://Java//JavaSE//MsAccess//Contacts.accdb");		
//  con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};");                 
        	}catch(Exception e)
		{
                 System.out.println(e);
        	}
        }

	ViewCollege()
	{
		super("List of College's");
		setLocation(200,100);
		setSize(700,600);
		setLayout(new BorderLayout());
		setResizable(false);
		Container cont=getContentPane();

		JPanel up=new JPanel();
		up.setLayout(new BorderLayout());
		JLabel lid=new JLabel("List of College's",JLabel.CENTER);
		//lid.setBounds(100,20,500,50);
		lid.setFont(new Font("Arial",Font.BOLD | Font.ITALIC,30));
		up.add(lid,BorderLayout.CENTER);

		JPanel lp=new JPanel();
		lp.setLayout(new BorderLayout());
		sub=new JButton("Print");
		sub.setToolTipText(" Print List");
		sub.setMnemonic('P');
		sub.addActionListener(this);
		//sub.setBounds(230,70,100,40);

		can=new JButton("Cancel");
		can.setToolTipText("Back to Main");
		can.setMnemonic('C');
		can.addActionListener(this);
		//can.setBounds(350,70,100,40);
		//cont.add(can);cont.add(sub);
		//cont.add(lid);
		lp.add(new JLabel("",new ImageIcon("image003.gif"),JLabel.CENTER),BorderLayout.CENTER);
		lp.add(sub,BorderLayout.WEST);
		lp.add(can,BorderLayout.EAST);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
  		String col[] = {"College Code","Name","Address","Phone"};
		String dt[][]={{"","","",""}};
		DefaultTableModel model = new DefaultTableModel(dt,col);
		JTable table = new JTable(model);
  		JTableHeader header = table.getTableHeader();
  		header.setBackground(Color.yellow);
  		JScrollPane pane = new JScrollPane(table);
  		panel.add(pane,BorderLayout.CENTER);
  		
		setUndecorated(true);
  		getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		
		//panel.setBounds(20,120,660,300);
		cont.add(panel,BorderLayout.CENTER);
		cont.add(up,BorderLayout.NORTH);
		cont.add(lp,BorderLayout.SOUTH);
		try
		{
		  Statement stmt=con.createStatement();
		  ResultSet rs=stmt.executeQuery("SELECT * FROM COLLEGE");
		  while(rs.next())
		  {
			String st1=rs.getString(1);		
			String st2=rs.getString(2);
			String st3=rs.getString(3);
			String st4=rs.getString(4);
			model.addRow(new Object[]{st1, st2, st3, st4});
		  }
		  rs.close();
		}
		catch(Exception e)
		{
		 	JOptionPane.showMessageDialog(null,"ERROR ::"+e,"Error",0);
		}
		
		addWindowListener(new WindowAdapter()
		{
		     public void windowClosing(WindowEvent we)
		     {
			int le=JOptionPane.showConfirmDialog(null,"Are you sure"+"\n"+"you want to close window","Confirmation.......",JOptionPane.YES_NO_OPTION);
			if(le==JOptionPane.YES_OPTION)
			{
				dispose();
			}
			else if(le==JOptionPane.NO_OPTION)
			{
				new ViewCollege();
			}
		     }
		});
		//pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==sub)
		{
			new PrintWork(this);
		}
		if(ae.getSource()==can)
		{
			setVisible(false);
			dispose();
		}
	}

	public static void main(String str[])
	{
		new ViewCollege();
	}
}




		
