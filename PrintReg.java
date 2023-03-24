import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

class PrintReg extends JFrame implements ActionListener
{
	public static Connection con;
	private int code;
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

	PrintReg(int i)
	{
		super("Student Registration Slip");
		setLocation(200,50);
		setSize(550,650);
		setLayout(null);
		setResizable(false);
		Container cont=getContentPane();

		JTextArea ta=new JTextArea();
		ta.setBounds(1,1,548,550);
		sub=new JButton("Print");
		sub.setToolTipText(" Print Slip");
		sub.setMnemonic('P');
		sub.addActionListener(this);
		sub.setBounds(160,580,100,40);
		can=new JButton("Close");
		can.setToolTipText("Back to Main");
		can.setMnemonic('C');
		can.addActionListener(this);
		can.setBounds(260,580,100,40);
		cont.add(can);cont.add(sub);
		cont.add(ta);
		code=i;

		ta.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		ta.append("\n\n\t\t COUNSELLING SYSTEM");
		ta.append("\n\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		java.util.Date dt=new java.util.Date();
		ta.append("\n\n\t\tDate ::"+dt);

		try
		{
		  Statement st1=con.createStatement();
		  ResultSet rs1=st1.executeQuery("SELECT * FROM STUD_REG WHERE REG_NO="+code);
		  if(rs1.next())
		  {
		  ta.append("\n\n\nRegistration Number\t\t::\t "+ rs1.getString(1));
		  ta.append("\nEntrance Exam Roll Number\t\t::\t "+rs1.getString(2));
		  ta.append("\nEntrance Exam Rank\t\t::\t "+rs1.getString(3));
		  ta.append("\nCandidate Name\t\t::\t "+rs1.getString(4));
		  ta.append("\nCourse Code\t\t\t::\t "+rs1.getString(7));
		  ta.append("\nCourse Name\t\t\t::\t "+rs1.getString(8));
		  ta.append("\n\n\tList of Colleges Selected");
		  int j= rs1.getInt(9);  
		  ta.append("\n1. College Code :: "+j+"\tCollege Name :: "+ getCollege(j));
		  j=rs1.getInt(10);
		  ta.append("\n2. College Code :: "+j+"\tCollege Name :: "+ getCollege(j));
		  j=rs1.getInt(11);
		  ta.append("\n3. College Code :: "+j+"\tCollege Name :: "+ getCollege(j));
		  ta.append("\n\n\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=");
		  }
		  else
		  { 
		  JOptionPane.showMessageDialog(null,"Record not found !!","Error Message",0);
		  dispose();
		  }
		  rs1.close();
		}catch(SQLException e)
		{
		 JOptionPane.showMessageDialog(null,"ERROR :: Unable to load form","Error",0); 
		 System.out.println(e);
		 dispose();
		}
		final int ii=i;

		addWindowListener(new WindowAdapter()
		{
		     public void windowClosing(WindowEvent we)
		     {
			int le=JOptionPane.showConfirmDialog(null,"Are you sure"+"\n"+"you want to close window","Confirmation.......",JOptionPane.YES_NO_OPTION);
			if(le==JOptionPane.YES_OPTION)
			{
				dispose();;
			}
			else if(le==JOptionPane.NO_OPTION)
			{
				new PrintReg(ii);
			}
		     }
		});

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==sub)
		{
		  new PrintWork(this);
		}
		if(ae.getSource()==can)
		  dispose();
	}

	private String getCollege(int choice) throws SQLException
	{
		Statement stm=con.createStatement();
		ResultSet rs1=stm.executeQuery("SELECT * FROM COLLEGE WHERE COLLEGE_CODE="+choice);
		if(rs1.next())
		 return rs1.getString("COLLEGE_NAME");
		rs1.close();
		return "";
	}
}