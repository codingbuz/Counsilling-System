import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

class GetAlloc extends JFrame implements ActionListener
{
	public static Connection con;
	private int code,rnk=0;
	private JButton sub,can;
	private String msg=null;
	private JTextArea ta;

	static
	{
        	try
        	{
		  Class.forName("oracle.jdbc.driver.OracleDriver");                 
con=DriverManager.getConnection("jdbc:ucanaccess://e://Java//JavaSE//MsAccess//Contacts.accdb");		 
// con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};");                 
        	}catch(Exception e)
		{
                 System.out.println(e);
        	}
        }

	GetAlloc(int i)
	{
		super("Seat Allocation Slip");
		setLocation(200,50);
		setSize(550,650);
		setLayout(null);
		setResizable(false);
		Container cont=getContentPane();

		ta=new JTextArea();
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
		int ch[]={0,0,0};
		boolean flag=true;
		String nm="start";

		try
		{
		  Statement st=con.createStatement();
		  ResultSet rs=st.executeQuery("SELECT * FROM STUD_REG WHERE REG_NO="+code);
		  if(rs.next())
		  {
		  ch[0]=rs.getInt("COLLEGE_CH1");
		  ch[1]=rs.getInt("COLLEGE_CH2");
		  ch[2]=rs.getInt("COLLEGE_CH3");
		  rnk=rs.getInt("RANK");
		  msg=rs.getString("COURSE_CODE");
		  nm=rs.getString("name");
		  }
		  else
		  { 
		  ta.setText("\t\tRecord not found !!");
		  nm="stop";
		  }
		  rs.close();

		  if(rnk<=2000 && !nm.equals("stop"))
		  {
		  for(int j=0;flag;j++)
		  {
			if(j==3)
			{
			msg="All the seats in selected colleges are full...select another list of colleges";
			ta.append(msg);
			break;
			}

			flag=getAllot(ch[j],msg);
			
			if(flag==false)
			{
			String cnm=getCollege(ch[j]);

			PreparedStatement ps=con.prepareStatement("INSERT INTO ALLOC_DETAIL VALUES(?,?,?,?,?,?)");
			ps.setInt(1,code);
			ps.setString(2,nm);
			ps.setInt(3,ch[j]);
			ps.setString(4,cnm);
			ps.setString(5,msg);
			java.util.Date dt=new java.util.Date();
			String ds=""+dt;
			ps.setString(6, ds);
			ps.executeUpdate();
			print(code,nm,ch[j],cnm,msg,dt);
			break;
			}
		  }
		  }
		  else if(!nm.equals("stop"))
			ta.setText(" Wait for next round of counselling");
		}catch(SQLException e)
		{
		 if((e.getMessage()).equals("General error"))
		 ta.append("ERROR :: Seat already alloted");
		 else
		 ta.append("ERROR ::\n"+e.getMessage());
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
				new GetAlloc(ii);
			}
		     }
		});

		setVisible(true);
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

	private boolean getAllot(int choice, String cd) throws SQLException
	{
		Statement st1=con.createStatement();
		ResultSet rs=st1.executeQuery("SELECT total_seats, allocated_seats FROM COURSE_SEAT WHERE college_code="+choice+" AND course_code='"+msg+"'");
		int as=0,ct=0;
		if(rs.next())
		{
		ct=(rs.getInt(2));
		as=(rs.getInt(1)) - ct;
		if(as>0)
		{
		  st1.executeUpdate("UPDATE COURSE_SEAT SET allocated_seats="+(++ct)+" WHERE college_code="+choice+" AND course_code='"+msg+"'");
		  return false;
		}
		}
		return true;
	}

	private void print(int rg, String nm, int cd, String cnm, String crs, java.util.Date dt)
	{

		ta.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		ta.append("\n\n\t\t COUNSELLING SYSTEM");
		ta.append("\n\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		ta.append("\n\n\t\tDate :: "+dt);
  	 	ta.append("\n\n\nRegistration Number\t::\t "+ rg);
		ta.append("\n\nCandidate Name\t\t::\t "+ nm);
		ta.append("\n\nCollege Code\t\t::\t "+ cd);
		ta.append("\n\nCollege Name\t\t::\t "+ cnm);
		ta.append("\n\nCourse Code\t\t::\t "+ crs);
		ta.append("\n\n\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=");
		ta.append("\n\n\t\t****Best Wishes For Your Future****");
		ta.append("\n\n\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=");
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
}