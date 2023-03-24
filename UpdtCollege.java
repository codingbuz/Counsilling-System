import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

class UpdtCollege extends JFrame implements ActionListener, KeyListener, ItemListener
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
	
	private JComboBox id;
	private JTextField nam,cnt;
	private JTextArea add;
	private JButton sub,can;

	UpdtCollege()
	{
		super("Update College Record Form");
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
		
		JLabel lid=new JLabel("College Code");
		lid.setBounds(100,100,100,30);
		JLabel lnm=new JLabel("Name   ");
		lnm.setBounds(100,150,100,30);
		JLabel lad=new JLabel("Address");
		lad.setBounds(100,200,100,30);
		JLabel lcn=new JLabel("Conact No. ");
		lcn.setBounds(100,300,100,30);
		cont.add(lid);cont.add(lnm);
		cont.add(lcn);cont.add(lad);
		cont.add(b1);cont.add(b2);

		Vector vt=new Vector();		
		id=new JComboBox(vt);
		id.addItemListener(this);
		id.setBounds(200,100,200,30);
		
		nam=new JTextField();
		nam.setBounds(200,150,200,30);
		nam.addKeyListener(this);
		cnt=new JTextField();
		cnt.setBounds(200,300,200,30);
		cnt.addKeyListener(this);
		add=new JTextArea();
		add.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		add.setBounds(200,200,200,80);
		add.addKeyListener(this);
		cont.add(id);cont.add(nam);
		cont.add(cnt);cont.add(add);

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

		try
		{
		  Statement st=con.createStatement();
		  ResultSet rs=st.executeQuery("SELECT COLLEGE_CODE FROM COLLEGE");
		  int i=0;
		  while(rs.next())
		  {
		    vt.addElement(new Integer(rs.getInt(1)));
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
				new NewCollege();
			}
		     }
		});

		setVisible(true);
	}

	public void keyPressed(KeyEvent k)
	{
		String st=null;

                if(k.getSource()==nam)
	        {
			int b=k.getKeyCode();

                       	if((b>=0 && b<8) || (b>=11 && b<16) || (b==19) || (b>=21 && b<32) || (b>=33 && b<48) || (b>57 && b<65) || (b>90 && b<97) || b>122)
			{
                       	JOptionPane.showMessageDialog(null,"Enter only alphabets","Invalid Input",JOptionPane.WARNING_MESSAGE);
			st=nam.getText();
			nam.setText(st.substring(0,st.length()-1));                              
			}
                }


		if(k.getSource()==cnt)
		{
			int b=k.getKeyCode();
			if((b<48 || b>57) &&(b!=8))
			{
                       	JOptionPane.showMessageDialog(null,"Enter only numbers","Invalid Input",JOptionPane.WARNING_MESSAGE);
   			st=cnt.getText();
			if(!(b>=16 && b<=20))
			cnt.setText(st.substring(0,st.length()-1));                              
			}
		}
	
		if(k.getSource()==add)
		{
			int b=k.getKeyCode();
						
			if((b>=0 && b<8) || (b>=11 && b<16) || (b==19) || (b>=21 && b<32) || (b>=33 && b<48) || (b>57 && b<65) || (b>90 && b<97) || b>122)
			{
                       	JOptionPane.showMessageDialog(null,"Enter valid characters","Invalid Input",JOptionPane.WARNING_MESSAGE);
                        st=add.getText();
			add.setText(st.substring(0,st.length()-1));                              
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
		  if( !(nam.getText().equals("")) && !(cnt.getText().equals("")) && !(add.getText().equals("")))
		  {
		  try
		  {
		 	 PreparedStatement st=con.prepareStatement("UPDATE COLLEGE SET COLLEGE_NAME=?, ADDRESS=?, TELEPHONE=? WHERE COLLEGE_CODE=?");
			 st.setInt(4,code);
			 st.setString(1,nam.getText());
			 st.setString(2,add.getText());
			 st.setString(3,cnt.getText());
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
		Integer st=(Integer)ie.getItem();
		code=st.intValue();
		
		try
		{
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM COLLEGE where COLLEGE_CODE="+code);
		if(rs.next())
		{
		  nam.setText(rs.getString(2));
		  add.setText(rs.getString(3));
		  cnt.setText(rs.getString(4));
		}
		else
		 JOptionPane.showMessageDialog(null,"ERROR :: Record not found","Error",0); 
		rs.close();
		}catch(SQLException e)
		{
		 JOptionPane.showMessageDialog(null,"ERROR ::"+e,"Error",0); 
		 dispose();
		}

	}

	public static void main(String str[])
	{
		new UpdtCollege();
	}

}			
