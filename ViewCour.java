import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

class ViewCour extends JFrame implements ItemListener
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
	

	ViewCour()
	{
		super("View Course & Seat Details");
		setLocation(200,100);
		setSize(550,550);
		setLayout(null);
		setResizable(false);
		code=0;i=1;
		cont=getContentPane();

		JLabel b1=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b1.setBounds(1,15,550,30);
		JLabel b2=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b2.setBounds(1,480,550,30);

		JLabel nf=new JLabel("View Seat Details !!",JLabel.CENTER);
		nf.setBounds(200,60,200,50);
		nf.setFont(new Font("Arial",Font.BOLD,20));
		cont.add(nf);
		JLabel lb=new JLabel("Select College Code");
		vd=new Vector();
		ccd=new JComboBox(vd);
		ccd.addItemListener(this);
		lb.setBounds(100,130,180,30);
		ccd.setBounds(220,130,100,30);
		cont.add(b1);cont.add(b2);
		cont.add(lb);cont.add(ccd);

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
		if(i==0) dispose();
		try
		{
		  j=180;
		  Statement st2=con.createStatement();
		  ResultSet rs=st2.executeQuery("SELECT * FROM COURSE_SEAT WHERE COLLEGE_CODE="+code);
		  while(rs.next())
		  {
			if(i==1)
			{
			  //JLabel lb1=new JLabel("Course Code");
			  JLabel lb2=new JLabel("Course Name");
			  JLabel lb3=new JLabel("Branch");
			  JLabel lb4=new JLabel("Total Seats");
	  		  JLabel lb5=new JLabel("Seats Available");
			 // lb1.setBounds(30,j,100,30);
			  lb2.setBounds(50,j,100,30);
			  lb3.setBounds(280,j,100,30);
			  lb4.setBounds(350,j,100,30);
			  lb5.setBounds(430,j,100,30);
			  //cont.add(lb1);
			  cont.add(lb2);cont.add(lb3);
			  cont.add(lb4);cont.add(lb5);
			  j+=30; i=0; continue;
		  	}


			JTextField tf1=new JTextField();
			JTextField tf2=new JTextField();
			JTextField tf3=new JTextField();
			JTextField tf4=new JTextField();
			JTextField tf5=new JTextField();

			//tf1.setBounds(30,j,100,30);
			tf2.setBounds(25,j,205,30);
			tf3.setBounds(230,j,120,30);
			tf4.setBounds(350,j,80,30);
			tf5.setBounds(430,j,80,30);

			//tf1.setText(rs.getString("COURSE_CODE"));
			tf2.setText(rs.getString("COURSE_NAME"));
			tf3.setText(rs.getString("BRANCH"));
			int ts=rs.getInt("TOTAL_SEATS");
			int as=ts-rs.getInt("ALLOCATED_SEATS");
			tf4.setText(Integer.toString(ts));
			tf5.setText(Integer.toString(as));
			j+=35;
			//cont.add(tf1);
			cont.add(tf2);cont.add(tf3);
			cont.add(tf4);cont.add(tf5);
		  } 
		  if(i==1)
		  {
			JLabel nf=new JLabel("RECORD NOT FOUND !!",JLabel.CENTER);
			nf.setBounds(150,200,200,50);
			nf.setFont(new Font("Arial",Font.BOLD,40));
			cont.add(nf);
		  }
		  rs.close();
		}catch(SQLException e)
		{
		 JOptionPane.showMessageDialog(null,"ERROR :: Unable to load Course Record","Error",0); 
		 System.out.println(e);
		 System.exit(1);
		}
	}

	

	public static void main(String str[])
	{
		new ViewCour();
	}

}			
