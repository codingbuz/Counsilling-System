import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Login implements KeyListener 
{	
	final JTextField acn;
	public Login()
	{
		final JFrame f=new JFrame("Login Panel");
		f.getContentPane().setLayout(null);
		f.setSize(600,500);
		f.setLocation(10,10);

		JLabel lognm=new JLabel("Login Name ");
		acn=new JTextField(10);
		acn.setToolTipText("Enter Login Name of User !!");
		acn.addKeyListener(this);
		JLabel pwd=new JLabel("Password ");
		final JPasswordField pin=new JPasswordField(5);
		pin.setEchoChar('*');
		pin.setToolTipText("Enter valid password !!");

		JButton loginb=new JButton("Login");
		loginb.setToolTipText("Login to Counselling System");
		JButton backb=new JButton("Cancel");
		backb.setToolTipText("Close the Software");		
		JLabel lb1 =new JLabel(new ImageIcon("starln_e0.gif"));
		JLabel lb2 =new JLabel(new ImageIcon("starln_e0.gif"));

		lognm.setBounds(150,100,120,25);
		pwd.setBounds(150,150,120,25);		
		acn.setBounds(300,100,150,25);
		pin.setBounds(300,150,150,25);
		loginb.setBounds(150,250,100,50);
		backb.setBounds(350,250,100,50);
		lb1.setBounds(1,280,304,200);
		lb2.setBounds(305,280,290,200);

		f.getContentPane().add(lb1);
		f.getContentPane().add(lb2);
		f.getContentPane().add(lognm);
		f.getContentPane().add(acn);
		f.getContentPane().add(pwd);
		f.getContentPane().add(pin);
		f.getContentPane().add(loginb);
		//f.getContentPane().add(loginb);	
		f.getContentPane().add(backb);
		
		acn.requestFocus();
		f.setVisible(true);		
		f.setResizable(false);
		
		ActionListener al=new ActionListener()
		{  
		  public void actionPerformed(ActionEvent e)
	  	  {
			String command=new String(((JButton)e.getSource()).getText());
			if(command.equals("Login"))
                        {      			
			try
			{
			ResultSet r;
                        Class.forName("oracle.jdbc.driver.OracleDriver");
//System.out.println("@@");
                        Connection c=DriverManager.getConnection("jdbc:ucanaccess://e://Java//JavaSE//MsAccess//Contacts.accdb");
//con=DriverManager.getConnection("jdbc:ucanaccess://e://Java//JavaSE//MsAccess//Contacts.accdb");
//System.out.println("!!!");
                        Statement st=c.createStatement();
                        r=st.executeQuery("SELECT * FROM LOGIN");
			String pinv=pin.getText();
			String acnv=acn.getText();
			int flg=0;
				
			while(r.next())
		    	{
			String log=r.getString(1);
                        if( r.getString(2).equals(pinv) && log.equalsIgnoreCase(acnv))
                        {
			flg=1;
			if(log.equals("admin"))
				new MainPage(log);
			else
				new StudPage();
                        f.dispose();
			break; 
                        }
			}
                        if(flg==0)
                        {
                        JOptionPane.showMessageDialog(null,"Login name or password is incorrect ...!!","Login Error",0);
                        pin.setText("");
			acn.setText("");
                        }
		    	
                      	}
                        catch(SQLException eSQL)
                        {
			JOptionPane.showMessageDialog(null,"SQL ERROR::"+eSQL,"Database Error",2);
                        }
                        catch(Exception ex)
                        {
			JOptionPane.showMessageDialog(null,"ERROR::"+ex,"Error",2);
                        }
		        }

			if(command.equals("Cancel"))
                        {      
			int le=JOptionPane.showConfirmDialog(f,"Are you sure"+"\n"+"you want to exit","Confirmation.......",JOptionPane.YES_NO_OPTION);
			if(le==JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
			}
		}		
	     };
		
	
		loginb.addActionListener(al);
		backb.addActionListener(al);

		f.addWindowListener(new WindowAdapter()
		{
		     public void windowClosing(WindowEvent we)
		     {
			int le=JOptionPane.showConfirmDialog(f,"Are you sure"+"\n"+"you want to exit","Confirmation.......",JOptionPane.YES_NO_OPTION);
			if(le==JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
			else if(le==JOptionPane.NO_OPTION)
			{
				new Login();
			}
		     }
		});
	}

	public void keyPressed(KeyEvent k)
	{
                if(k.getSource()==acn)
	        {
			int b=k.getKeyCode();
			
                       	if((b>=0 && b<16) || (b>=17 && b<20) || (b>=21 && b<65) || (b>90 && b<97))
			{
                       	JOptionPane.showMessageDialog(null,"Enter only alphabets","Invalid Input",JOptionPane.WARNING_MESSAGE);
                        acn.setText("");                              
			}
                }
	}

	public void  keyTyped(KeyEvent k)
	{ }
       
	public void keyReleased(KeyEvent k)		
	{}			

	public static void main(String str[])
	{
	new Login();
	}
}
