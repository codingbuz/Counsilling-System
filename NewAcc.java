import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

class NewAcc extends JFrame implements ActionListener, KeyListener
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

	private JTextField lgn;
	private JPasswordField pwd1,pwd2;
	private JButton sub,can;

	NewAcc()
	{
		super("New User Account Form");
		setLocation(200,100);
		setSize(400,400);
		setLayout(null);
		setResizable(false);
		Container cont=getContentPane();

		JLabel b1=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b1.setBounds(1,15,400,30);
		JLabel b2=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b2.setBounds(1,330,400,30);

		JLabel lcd=new JLabel("    Login Name      ::");
		JLabel lp1=new JLabel("    Password        ::");
		JLabel lp2=new JLabel("Retype Password ::");
		lgn=new JTextField();
		lgn.addKeyListener(this);
		lgn.setToolTipText("Enter user name");
		pwd1=new JPasswordField();
		pwd1.setEchoChar('*');
		pwd1.addKeyListener(this);
		pwd1.setToolTipText("Enter password here..");
		pwd2=new JPasswordField();
		pwd2.setEchoChar('*');
		pwd2.addKeyListener(this);
		pwd2.setToolTipText("Retype the password..");

		lcd.setBounds(50,80,150,30);
		lgn.setBounds(200,80,150,30);
		lp1.setBounds(50,130,150,30);
		pwd1.setBounds(200,130,150,30);
		lp2.setBounds(50,180,150,30);
		pwd2.setBounds(200,180,150,30);

		sub=new JButton("Create");
		sub.setToolTipText("New User Account");
		sub.setMnemonic('C');
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
				new NewAcc();
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

                       	if((b>=0 && b<8) || (b>=11 && b<16) || (b==19) || (b>=21 && b<32) || (b>=33 && b<65) || (b>90 && b<97) || b>122)
			{
                       	JOptionPane.showMessageDialog(null,"Login name can have only alphabets","Invalid Input",JOptionPane.WARNING_MESSAGE);
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
			if((pwd1.getText()).equals(pwd2.getText()))
			{
		  	 try
		  	 {
		 	 PreparedStatement st=con.prepareStatement("INSERT INTO LOGIN VALUES(?,?)");
			 st.setString(1,lgn.getText());
			 st.setString(2,pwd1.getText());
			 st.executeUpdate();
			 JOptionPane.showMessageDialog(null,"New User Created","Message",1);
			 dispose();
			 }catch(Exception e)
		  	 {
		 	 JOptionPane.showMessageDialog(null,"ERROR ::"+e,"Error",0);
		  	 }
		  	}
			else
			{
			JOptionPane.showMessageDialog(null,"Both password does not matches","Error",0);
			pwd1.setText("");pwd2.setText("");
			}
		  }	 
		  else
		  {
			JOptionPane.showMessageDialog(null,"ERROR :: One of the data field is left blank","Error",0);	
			pwd1.setText("");pwd2.setText("");
		  }
		}
		if(ae.getSource()==can)
		{
			dispose();
		}
	}

	public static void main(String str[])
	{
		new NewAcc();
	}
}
