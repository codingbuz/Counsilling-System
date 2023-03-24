import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

class UpdReg extends JFrame implements ActionListener, KeyListener, ItemListener
{
	public static Connection con;
	private int code;

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

	private JTextField reg,rno,rnk,nam,num,cnm,ch1,ch2,ch3;
	private JTextArea add;
	private JButton sub,can;
	private JComboBox cd;
	private Vector vd;
	private String stcd=null;

	UpdReg(int i)
	{
		super("Update Registration Details");
		setLocation(200,50);
		setSize(550,650);
		setLayout(null);
		setResizable(false);
		Container cont=getContentPane();
		code=i;

		JLabel b1=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b1.setBounds(1,15,550,30);
		JLabel b2=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b2.setBounds(1,580,550,30);
		cont.add(b1);cont.add(b2);

		JLabel lb1=new JLabel("Update Registration Details",JLabel.CENTER);
		lb1.setFont(new Font("Arial",Font.BOLD,20));
		lb1.setBounds(100,50,350,40);
		JLabel lb2=new JLabel("Registration Number");
		lb2.setBounds(100,100,150,30);
		JLabel lb3=new JLabel("Entrance Exam Roll No.");
		lb3.setBounds(100,140,150,30);
		JLabel lb4=new JLabel("Rank in Entrance Exam");
		lb4.setBounds(100,180,150,30);
		JLabel lb5=new JLabel("Student Name");
		lb5.setBounds(100,220,150,30);
		JLabel lb6=new JLabel("Permanent Address");
		lb6.setBounds(100,260,150,30);
		JLabel lb7=new JLabel("Telephone/Mobile No.");
		lb7.setBounds(100,340,150,30);
		JLabel lb8=new JLabel("Course Code");
		lb8.setBounds(100,380,150,30);
		JLabel lb9=new JLabel("Course Name");
		lb9.setBounds(100,420,150,30);
		JLabel lb=new JLabel("Enter College Code of three college's of your choice");
		lb.setBounds(100,460,350,30);
		JLabel l1=new JLabel("1.");
		l1.setBounds(100,490,30,30);
		JLabel l2=new JLabel("2.");
		l2.setBounds(200,490,30,30);
		JLabel l3=new JLabel("3.");
		l3.setBounds(300,490,30,30);
		cont.add(lb1);cont.add(lb2);cont.add(lb3);cont.add(lb4);cont.add(lb5);cont.add(lb6);
		cont.add(lb7);cont.add(lb8);cont.add(lb9);cont.add(l1);cont.add(l2);cont.add(l3);cont.add(lb);

		reg=new JTextField();
		reg.setToolTipText("Student Registeration Number");
		reg.setEnabled(false);
		reg.setBounds(250,100,200,30);
		rno=new JTextField();
		rno.setToolTipText("Enter entrance exam roll number");
		rno.addKeyListener(this);
		rno.setBounds(250,140,200,30);
		rnk=new JTextField();
		rnk.setToolTipText("Enter rank in entrance exam");
		rnk.addKeyListener(this);
		rnk.setBounds(250,180,200,30);
		nam=new JTextField();
		nam.setToolTipText("Enter student name");
		nam.addKeyListener(this);
		nam.setBounds(250,220,200,30);
		add=new JTextArea();
		add.setToolTipText("Enter permanent address");
		add.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		add.addKeyListener(this);
		add.setBounds(250,260,200,70);
		num=new JTextField();
		num.setToolTipText("Enter telephone number");
		num.addKeyListener(this);
		num.setBounds(250,340,200,30);
		cont.add(reg);cont.add(rno);cont.add(rnk);cont.add(nam);cont.add(add);cont.add(num);
		vd=new Vector();

		try
		{
		  Statement st=con.createStatement();
		  ResultSet rs=st.executeQuery("SELECT DISTINCT COURSE_CODE FROM COURSE_SEAT");
		  while(rs.next())
		  {
		    vd.addElement(rs.getString(1));
		  } 
		  rs.close();
		}catch(SQLException e)
		{
		 JOptionPane.showMessageDialog(null,"ERROR :: Unable to load data","Error",0); 
		 System.out.println(e);
		 System.exit(1);
		}

		cd=new JComboBox(vd);
		cd.addItemListener(this);
		cd.setBounds(250,380,200,30);
		cnm=new JTextField();
		cnm.setToolTipText("Course Name");
		cnm.setBounds(250,420,200,30);
		cnm.setEnabled(false);
		ch1=new JTextField();
		ch1.setToolTipText("Enter college code for first choice");
		ch1.addKeyListener(this);
		ch1.setBounds(130,490,50,30);
		ch2=new JTextField();
		ch2.setToolTipText("Enter college code for second choice");
		ch2.addKeyListener(this);
		ch2.setBounds(230,490,50,30);
		ch3=new JTextField();
		ch3.setToolTipText("Enter college code for third choice");
		ch3.addKeyListener(this);
		ch3.setBounds(330,490,50,30);
		cont.add(cd);cont.add(cnm);cont.add(ch1);cont.add(ch2);cont.add(ch3);

		sub=new JButton("Submit");
		sub.setToolTipText(" Save Record");
		sub.setMnemonic('S');
		sub.addActionListener(this);
		sub.setBounds(160,540,100,40);

		can=new JButton("Cancel");
		can.setToolTipText("Back to Main");
		can.setMnemonic('C');
		can.addActionListener(this);
		can.setBounds(260,540,100,40);
		cont.add(can);cont.add(sub);

		try
		{
		  Statement st1=con.createStatement();
		  ResultSet rs1=st1.executeQuery("SELECT * FROM STUD_REG WHERE REG_NO="+code);
		  while(rs1.next())
		  {
		  reg.setText(rs1.getString(1));
		  rno.setText(rs1.getString(2));
		  rnk.setText(rs1.getString(3));
		  nam.setText(rs1.getString(4));
		  add.setText(rs1.getString(5));
		  num.setText(rs1.getString(6));
		  cd.setSelectedItem(rs1.getString(7));
		  cnm.setText(rs1.getString(8));
		  ch1.setText(rs1.getString(9));
		  ch2.setText(rs1.getString(10));
		  ch3.setText(rs1.getString(11));
		  }
		  rs1.close();
		}catch(SQLException e)
		{
		 JOptionPane.showMessageDialog(null,"ERROR :: Unable to load form","Error",0); 
		 System.out.println(e);
		 System.exit(1);
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
				new UpdReg(ii);
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

                       	if((b>=0 && b<8) || (b>=11 && b<16) || (b==19) || (b>=21 && b<32) || (b>=33 && b<65) || (b>90 && b<97) || b>122)
			{
                       	JOptionPane.showMessageDialog(null,"Enter only alphabets","Invalid Input",JOptionPane.WARNING_MESSAGE);
			st=nam.getText();
			nam.setText(st.substring(0,st.length()-1));                              
			}
                }


		if(k.getSource()==rnk)
		{
			int b=k.getKeyCode();
			if((b<48 || b>57) &&(b!=8))
			{
                       	JOptionPane.showMessageDialog(null,"Enter only numbers","Invalid Input",JOptionPane.WARNING_MESSAGE);
   			st=rnk.getText();
			if(!(b>=16 && b<=20))
			rnk.setText(st.substring(0,st.length()-1));                              
			}
		}

		if(k.getSource()==ch1)
		{
			int b=k.getKeyCode();
			if((b<48 || b>57) &&(b!=8))
			{
                       	JOptionPane.showMessageDialog(null,"Enter only numbers","Invalid Input",JOptionPane.WARNING_MESSAGE);
   			st=ch1.getText();
			if(!(b>=16 && b<=20))
			ch1.setText(st.substring(0,st.length()-1));                              
			}
		}

		if(k.getSource()==ch2)
		{
			int b=k.getKeyCode();
			if((b<48 || b>57) &&(b!=8))
			{
                       	JOptionPane.showMessageDialog(null,"Enter only numbers","Invalid Input",JOptionPane.WARNING_MESSAGE);
   			st=ch2.getText();
			if(!(b>=16 && b<=20))
			ch2.setText(st.substring(0,st.length()-1));                              
			}
		}

		if(k.getSource()==ch3)
		{
			int b=k.getKeyCode();
			if((b<48 || b>57) &&(b!=8))
			{
                       	JOptionPane.showMessageDialog(null,"Enter only numbers","Invalid Input",JOptionPane.WARNING_MESSAGE);
   			st=ch3.getText();
			if(!(b>=16 && b<=20))
			ch3.setText(st.substring(0,st.length()-1));                              
			}
		}

		if(k.getSource()==num)
		{
			int b=k.getKeyCode();
			if((b<48 || b>57) &&(b!=8))
			{
                       	JOptionPane.showMessageDialog(null,"Enter only numbers","Invalid Input",JOptionPane.WARNING_MESSAGE);
   			st=num.getText();
			if(!(b>=16 && b<=20))
			num.setText(st.substring(0,st.length()-1));                              
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

		if(k.getSource()==rno)
		{
			int b=k.getKeyCode();
						
			if((b>=0 && b<8) || (b>=11 && b<16) || (b==19) || (b>=21 && b<32) || (b>=33 && b<48) || (b>57 && b<65) || (b>90 && b<97) || b>122)
			{
                       	JOptionPane.showMessageDialog(null,"Enter valid characters","Invalid Input",JOptionPane.WARNING_MESSAGE);
                        st=rno.getText();
			rno.setText(st.substring(0,st.length()-1));                              
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
			if(!((rno.getText()).equals("")) && !((nam.getText()).equals("")) && !((rnk.getText()).equals("")) && !((add.getText()).equals("")) && !((num.getText()).equals("")) && !((ch1.getText()).equals("")) && !((ch2.getText()).equals("")) && !((ch3.getText()).equals("")) && !((cnm.getText()).equals("")))
			{
			try
			{
			  Statement p=con.createStatement();
			  ResultSet r=p.executeQuery("SELECT * FROM STUD_REG WHERE RANK="+Integer.parseInt(rnk.getText())+" AND COURSE_CODE='"+stcd+"'");
			  if(r.next())
			  {
				JOptionPane.showMessageDialog(null,"Duplicate Rank.....!!\nEnter correct Rank","Message",0);
				rnk.setText("");
			  }
			  else
			  {
 			  int k=1;
			  ResultSet rs=p.executeQuery("SELECT * FROM COLLEGE WHERE COLLEGE_CODE="+Integer.parseInt(ch1.getText()));
			  if(!rs.next()) k=0;

			  ResultSet rs1=p.executeQuery("SELECT * FROM COLLEGE WHERE COLLEGE_CODE="+Integer.parseInt(ch2.getText()));
			  if(!rs1.next()) k=0;

			  ResultSet rs2=p.executeQuery("SELECT * FROM COLLEGE WHERE COLLEGE_CODE="+Integer.parseInt(ch3.getText()));
			  if(!rs2.next()) k=0;

			  if(k==0)
			  {
				JOptionPane.showMessageDialog(null,"College Code does not exist","Message",0);
				ch1.setText("");ch2.setText("");ch3.setText("");
			  }
			  else
			  {
			  PreparedStatement ps=con.prepareStatement("UPDATE STUD_REG SET ROLL_NO=?, RANK=?, NAME=?, ADDRESS=?, CONTACT_NO=?, COURSE_CODE=?, COURSE_NAME=?, COLLEGE_CH1=?, COLLEGE_CH2=?, COLLEGE_CH3=? WHERE REG_NO=?");
			  ps.setString(1,rno.getText());
			  ps.setInt(2,Integer.parseInt(rnk.getText()));
			  ps.setString(3,nam.getText());
			  ps.setString(4,add.getText());
			  ps.setString(5,num.getText());
			  ps.setString(6,stcd);
			  ps.setString(7,cnm.getText());
			  ps.setInt(8,Integer.parseInt(ch1.getText()));
			  ps.setInt(9,Integer.parseInt(ch2.getText()));
			  ps.setInt(10,Integer.parseInt(ch3.getText()));
			  ps.setInt(11,Integer.parseInt(reg.getText()));
			  int i=ps.executeUpdate();
			  ps.close();
			  if(i==0)
			  JOptionPane.showMessageDialog(null,"Updation Error","Message",0);
			  else
			  JOptionPane.showMessageDialog(null,"One Record updated successfully","Message",1);
			  dispose();
			  }
			  }
			}
			catch(Exception e)
			{
			  JOptionPane.showMessageDialog(null,"Error::"+e,"Message",0);
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
		if(ie.getSource()==cd)
		{
		stcd=(String)ie.getItem();
		
		if(stcd.equals("M.C.A"))
		cnm.setText("Master of Computer Applications");
		else if(stcd.equals("B.C.A"))
		cnm.setText("Bachelor of Computer Applications");
		else if(stcd.equals("M.B.A"))
		cnm.setText("Master of Buisness Administration");
		else if(stcd.equals("B.B.A"))
		cnm.setText("Bachelor of Buisness Administration");
		else if(stcd.equals("B.Tech.(E)") || stcd.equals("B.Tech.(M)") || stcd.equals("B.Tech.(CS)"))
		cnm.setText("Bachelor of Technology");
		}
	}
}			

		