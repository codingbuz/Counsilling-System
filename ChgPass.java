import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

class ChgPass extends JFrame implements ActionListener, KeyListener
{
	private static Connection con;
	
	static
	{
        	try
        	{
		  Class.forName("oracle.jdbc.driver.OracleDriver");                 
		  con=DriverManager.getConnection("jdbc:ucanaccess://e://Java//JavaSE//MsAccess//Contacts.accdb");                 
        	}catch(Exception e)
		{
                 System.out.println(e);
        	}
        }

	private JPasswordField lgn,pwd1,pwd2;
	private JButton sub,can;
	String un=null;

	ChgPass(String un)
	{
		super("Change Password");
		setLocation(200,100);
		setSize(400,400);
		setLayout(null);
		setResizable(false);
		Container cont=getContentPane();
		this.un=un;
		final String u=un;

		JLabel b1=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b1.setBounds(1,15,400,30);
		JLabel b2=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b2.setBounds(1,330,400,30);

		JLabel lh=new JLabel("Change Your Password",JLabel.CENTER);
		lh.setFont(new Font("Arial",Font.BOLD,20));
		JLabel lcd=new JLabel("        Old Password    ::");
		JLabel lp1=new JLabel("       New Password    ::");
		JLabel lp2=new JLabel("Retype New Password ::");
		lgn=new JPasswordField();
		lgn.addKeyListener(this);
		lgn.setToolTipText("Enter Old password");
		lgn.setEchoChar('*');
		pwd1=new JPasswordField();
		pwd1.setEchoChar('*');
		pwd1.addKeyListener(this);
		pwd1.setToolTipText("Enter new password");
		pwd2=new JPasswordField();
		pwd2.setEchoChar('*');
		pwd2.addKeyListener(this);
		pwd2.setToolTipText("Retype new password..");

		lh.setBounds(50,40,300,50);
		lcd.setBounds(50,100,150,30);
		lgn.setBounds(200,100,150,30);
		lp1.setBounds(50,150,150,30);
		pwd1.setBounds(200,150,150,30);
		lp2.setBounds(50,200,150,30);
		pwd2.setBounds(200,200,150,30);

		sub=new JButton("Change");
		sub.setToolTipText("New User Account");
		sub.setMnemonic('h');
		sub.addActionListener(this);
		sub.setBounds(100,250,100,40);

		can=new JButton("Cancel");
		can.setToolTipText("Back to Main");
		can.setMnemonic('C');
		can.addActionListener(this);
		can.setBounds(200,250,100,40);
		cont.add(can);cont.add(sub);
		cont.add(lcd);cont.add(lgn);
		cont.add(b1);cont.add(b2);
		cont.add(lp1);cont.add(pwd1);
		cont.add(lp2);cont.add(pwd2);
		cont.add(lh);

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
				new ChgPass(u);
			}
		     }
		});

		setVisible(true);
	}

	public void keyPressed(KeyEvent k)
	{
		String st=null;

                if(k.getSource()==lgn)
	        {
			int b=k.getKeyCode();

                       	if((b>=0 && b<8) || (b>=11 && b<16) || (b==19) || (b>=21 && b<32) || (b>=33 && b<48) || (b>57 && b<65) || (b>90 && b<97) || b>122)
			{
                       	JOptionPane.showMessageDialog(null,"Password contains only number and alphabets","Invalid Input",JOptionPane.WARNING_MESSAGE);
			lgn.setText("");                              
			}
                }
		if(k.getSource()==pwd1)
		{
			int b=k.getKeyCode();
						
			if((b>=0 && b<8) || (b>=11 && b<16) || (b==19) || (b>=21 && b<32) || (b>=33 && b<48) || (b>57 && b<65) || (b>90 && b<97) || b>122)
			{
                       	JOptionPane.showMessageDialog(null,"Password should contain numbers and alphabets only","Invalid Input",JOptionPane.WARNING_MESSAGE);
                        pwd1.setText("");                              
			}
		}
		if(k.getSource()==pwd2)
		{
			int b=k.getKeyCode();
						
			if((b>=0 && b<8) || (b>=11 && b<16) || (b==19) || (b>=21 && b<32) || (b>=33 && b<48) || (b>57 && b<65) || (b>90 && b<97) || b>122)
			{
                       	JOptionPane.showMessageDialog(null,"Password should contain numbers and alphabets only","Invalid Input",JOptionPane.WARNING_MESSAGE);
                        pwd2.setText("");                              
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
		  if(!(lgn.getText().equals("")) && !(pwd1.getText().equals("")) && !(pwd2.getText().equals("")))
		  {
			try
			{
			  Statement stmt=con.createStatement();
			  ResultSet rs=stmt.executeQuery("SELECT PASSWORD FROM LOGIN WHERE LOGIN_ID='"+un+"'");
			  String sp=null;
			  if(rs.next())
			  sp=rs.getString("PASSWORD");
			  
			  if(sp.equals(lgn.getText()))
			  {
				if((pwd1.getText()).equals(pwd2.getText()))
				{
		  	 	try
		  	 	{
		 	 	PreparedStatement st=con.prepareStatement("UPDATE LOGIN SET PASSWORD=? WHERE LOGIN_ID=?");
			 	st.setString(1,pwd1.getText());
			 	st.setString(2,un);
				st.executeUpdate();
			 	JOptionPane.showMessageDialog(null,"Password Changed Successfully","Message",1);
			 	dispose();
			 	}catch(Exception e)
		  	 	{
		 	 	JOptionPane.showMessageDialog(null,"ERROR ::"+e,"Error",0);
		  	 	}
		  		}
				else
				{
				JOptionPane.showMessageDialog(null,"New password does not matches","Error",0);
				pwd1.setText("");pwd2.setText("");lgn.setText("");
				}
			}
			else
			{
			  JOptionPane.showMessageDialog(null,"Incorrect Old Password","Error",0);  
			  pwd1.setText("");pwd2.setText("");lgn.setText("");
			}
		     }catch(Exception e)
		     {
		 	JOptionPane.showMessageDialog(null,"ERROR ::"+e,"Error",0);
		     }
		  }	 
		  else
		  {
			JOptionPane.showMessageDialog(null,"ERROR :: One of the field is left blank","Error",0);	
			pwd1.setText("");pwd2.setText("");lgn.setText("");
		  }
		}
		if(ae.getSource()==can)
		{
			dispose();
		}
	}

	public static void main(String str[])
	{
		new ChgPass(str[0]);
	}
}
