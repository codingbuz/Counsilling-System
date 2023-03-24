import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

class ViewCr extends JFrame implements ItemListener
{
	private static Connection con;
	
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

	private JComboBox ccd;
	private JTextField cnam,brnc,seat;
	private JButton sub,can;
	private Vector vd;
	private int code,i,j;
	private Container cont;
	private DefaultTableModel df;
	

	ViewCr()
	{
		super("View Course & Seat Details");
		setLocation(200,100);
		setSize(550,550);
		setLayout(new GridLayout(4,1));
		setResizable(false);
		code=0;i=1;
		cont=getContentPane();

		JPanel up=new JPanel();
		up.setLayout(new BorderLayout());
		//up.setSize(550,300);
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(1,4));

		JPanel cp=new JPanel();
		JPanel lp=new JPanel();
		lp.setLayout(new BorderLayout());
		JLabel b1=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b1.setBounds(1,15,550,30);
		JLabel b2=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		//b2.setBounds(1,480,550,30);

		JLabel nf=new JLabel("View Seat Details !!",JLabel.CENTER);
		nf.setBounds(200,60,200,50);
		nf.setFont(new Font("Arial",Font.BOLD,20));
		up.add(nf);
		JLabel lb=new JLabel("Select College Code");
		vd=new Vector();
		ccd=new JComboBox(vd);
		ccd.addItemListener(this);

		ccd=new JComboBox(vd);
		ccd.addItemListener(this);
		lb.setBounds(100,130,180,30);
		ccd.setBounds(220,130,100,30);
		up.add( b1,BorderLayout.NORTH);
		lp.add(b2,BorderLayout.SOUTH);
		p1.add(new JLabel(""));
		p1.add(lb);
		p1.add(ccd);
		p1.add(new JLabel(""));
		cont.add(up);
		cont.add(p1);
		cont.add(cp);
		cont.add(lp);

		try
		{
		  Statement st=con.createStatement();
		  ResultSet rs=st.executeQuery("SELECT DISTINCT COLLEGE_CODE FROM COURSE_SEAT");
		  while(rs.next())
		  {
		    vd.addElement(new Integer(rs.getInt(1)));
		  } 
		  rs.close();
		}catch(SQLException e)
		{
		 JOptionPane.showMessageDialog(null,"ERROR :: Unable to load form","Error",0); 
		 System.out.println(e);
		 System.exit(1);
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
				new DelCour();
			}
		     }
		});

		setVisible(true);
	}

	public void itemStateChanged(ItemEvent ie)
	{
		Integer st=(Integer)ie.getItem();
		code=st.intValue();
	}

	public static void main(String str[])
	{
		new ViewCr();
	}
}