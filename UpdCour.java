import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

class UpdCour extends JFrame implements ActionListener, KeyListener, ItemListener
{
	public static Connection con;
	private int code;

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
	
	private JComboBox ccd,cnm;
	private JTextField cnam,brnc,seat;
	private JButton sub,can;
	private Vector vd,vc;
	private String stcd=null;

	UpdCour()
	{
		super("Update Course Details Form");
		setLocation(200,100);
		setSize(550,550);
		setLayout(null);
		setResizable(false);
		code=0;
		Container cont=getContentPane();

		JLabel b1=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b1.setBounds(1,15,550,30);
		JLabel b2=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b2.setBounds(1,480,550,30);

		JLabel lb1=new JLabel("College Code");
		JLabel lb2=new JLabel("Course Code");
		JLabel lb3=new JLabel("Course Name");
		JLabel lb4=new JLabel("Branch");
		JLabel lb5=new JLabel("Number of Seats");

		vd=new Vector();
		ccd=new JComboBox(vd);
		vc=new Vector();
		vc.addElement("M.C.A");
		vc.addElement("M.B.A");
		vc.addElement("B.C.A");
		vc.addElement("B.B.A");
		vc.addElement("B.Tech.(E)");
		vc.addElement("B.Tech.(M)");
		vc.addElement("B.Tech.(CS)");
		cnm=new JComboBox(vc);
		cnam=new JTextField();
		brnc=new JTextField();
		seat=new JTextField();

		lb1.setBounds(100,80,130,30);
		ccd.setBounds(220,80,200,30);
		lb2.setBounds(100,130,130,30);
		cnm.setBounds(220,130,200,30);
		lb3.setBounds(100,180,130,30);
		cnam.setBounds(220,180,200,30);
		lb4.setBounds(100,230,130,30);
		brnc.setBounds(220,230,200,30);
		lb5.setBounds(100,280,130,30);
		seat.setBounds(220,280,200,30);

		ccd.addItemListener(this);
		cnm.addItemListener(this);
		cnam.addKeyListener(this);
		brnc.addKeyListener(this);
		seat.addKeyListener(this);
		
		sub=new JButton("Submit");
		sub.setToolTipText(" Save Record");
		sub.setMnemonic('S');
		sub.addActionListener(this);
		sub.setBounds(160,400,100,40);

		can=new JButton("Cancel");
		can.setToolTipText("Back to Main");
		can.setMnemonic('C');
		can.addActionListener(this);
		can.setBounds(260,400,100,40);
		cont.add(can);cont.add(sub);
		cont.add(lb1);cont.add(ccd);
		cont.add(lb2);cont.add(cnm);
		cont.add(lb3);cont.add(cnam);
		cont.add(lb4);cont.add(brnc);
		cont.add(lb5);cont.add(seat);
		cont.add(b1);cont.add(b2);

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
				dispose();;
			}
			else if(le==JOptionPane.NO_OPTION)
			{
				new InsrCour();
			}
		     }
		});

		setVisible(true);
	}

	public void keyPressed(KeyEvent k)
	{
		String st=null;

                if(k.getSource()==brnc)
	        {
			int b=k.getKeyCode();

                       	if((b>=0 && b<8) || (b>=11 && b<16) || (b==19) || (b>=21 && b<32) || (b>=33 && b<65) || (b>90 && b<97) || b>122)
			{
                       	JOptionPane.showMessageDialog(null,"Enter only alphabets","Invalid Input",JOptionPane.WARNING_MESSAGE);
			st=brnc.getText();
			brnc.setText(st.substring(0,st.length()-1));                              
			}
                }


		if(k.getSource()==seat)
		{
			int b=k.getKeyCode();
			if((b<48 || b>57) &&(b!=8))
			{
                       	JOptionPane.showMessageDialog(null,"Enter only numbers","Invalid Input",JOptionPane.WARNING_MESSAGE);
   			st=seat.getText();
			if(!(b>=16 && b<=20))
			seat.setText(st.substring(0,st.length()-1));                              
			}
		}
	
		if(k.getSource()==cnam)
		{
			int b=k.getKeyCode();
						
			if((b>=0 && b<8) || (b>=11 && b<16) || (b==19) || (b>=21 && b<32) || (b>=33 && b<48) || (b>57 && b<65) || (b>90 && b<97) || b>122)
			{
                       	JOptionPane.showMessageDialog(null,"Enter valid characters","Invalid Input",JOptionPane.WARNING_MESSAGE);
                        st=cnam.getText();
			cnam.setText(st.substring(0,st.length()-1));                              
			}
		}
	}

	public void  keyTyped(KeyEvent k)
	{ }
       
	public void keyReleased(KeyEvent k)		
	{}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==sub)
		{
		  if( !(cnam.getText().equals("")) && !(seat.getText().equals("")) && !(brnc.getText().equals("")))
		  {
		  try
		  {
		 	 PreparedStatement st=con.prepareStatement("UPDATE COURSE_SEAT SET COURSE_NAME=?, BRANCH=?, TOTAL_sEATS=? WHERE COLLEGE_CODE=? AND COURSE_CODE=?");
			 st.setString(1,cnam.getText());
			 st.setString(2,brnc.getText());
			 st.setInt(3,Integer.parseInt(seat.getText()));
			 st.setInt(4,code);
			 st.setString(5,stcd);
			 st.executeUpdate();
			
			 JOptionPane.showMessageDialog(null,"Record Updated successfully","Message",1);
			 dispose();
		  }catch(Exception e)
		  {
		 	JOptionPane.showMessageDialog(null,"ERROR ::"+e,"Error",0);
		  }
		  }
		  else
			JOptionPane.showMessageDialog(null,"ERROR :: One of the data field is left blank","Error",0);	
		}
		if(ae.getSource()==can)
		{
			setVisible(false);
			dispose();
		}
	}

	public void itemStateChanged(ItemEvent ie)
	{
		if(ie.getSource()==ccd)
		{
		Integer st=(Integer)ie.getItem();
		code=st.intValue();
		}
		if(ie.getSource()==cnm)
		{
		stcd=(String)ie.getItem();
		try
		{
		  Statement st2=con.createStatement();
		  ResultSet rs=st2.executeQuery("SELECT COURSE_NAME, BRANCH, TOTAL_SEATS FROM COURSE_SEAT WHERE COLLEGE_CODE="+code+" and COURSE_CODE='"+stcd+"'");
		  if(rs.next())
		  {
		    cnam.setText(rs.getString(1));
		    brnc.setText(rs.getString(2));
		    seat.setText(rs.getString(3));
		  } 
		  else
		  {
		   JOptionPane.showMessageDialog(null,"ERROR :: Record not found","Error",2);  
		   cnam.setText("");
		   brnc.setText("");
		   seat.setText("");
		  }
		  rs.close();
		}catch(SQLException e)
		{
		 JOptionPane.showMessageDialog(null,"ERROR :: Unable to load Course Record","Error",0); 
		 System.out.println(e);
		 System.exit(1);
		}
		}

	}

	public static void main(String str[])
	{
		new UpdCour();
	}

}			
