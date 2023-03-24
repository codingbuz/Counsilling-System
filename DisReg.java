import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

class DisReg extends JFrame implements ActionListener, KeyListener
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

	private JTextField tf;
	private JButton sub,can;

	DisReg()
	{
		super("Display Registration");
		setLocation(200,100);
		setSize(400,400);
		setLayout(null);
		setResizable(false);
		Container cont=getContentPane();

		JLabel b1=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b1.setBounds(1,15,400,30);
		JLabel b2=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b2.setBounds(1,330,400,30);

		JLabel lcd=new JLabel("Enter Registration Number ::");
		tf=new JTextField();
		tf.addKeyListener(this);
		lcd.setBounds(40,120,180,30);
		tf.setBounds(200,120,150,30);
		sub=new JButton("Update");
		sub.setToolTipText(" Update Record");
		sub.setMnemonic('U');
		sub.addActionListener(this);
		sub.setBounds(100,220,100,40);

		can=new JButton("Cancel");
		can.setToolTipText("Back to Main");
		can.setMnemonic('C');
		can.addActionListener(this);
		can.setBounds(200,220,100,40);
		cont.add(can);cont.add(sub);
		cont.add(lcd);cont.add(tf);
		cont.add(b1);cont.add(b2);

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
				new DisReg();
			}
		     }
		});

		setVisible(true);
	}

	public void keyPressed(KeyEvent k)
	{
		String st=null;

                if(k.getSource()==tf)
		{
			int b=k.getKeyCode();
			if((b<48 || b>57) &&(b!=8))
			{
                       	JOptionPane.showMessageDialog(null,"Enter only numbers","Invalid Input",JOptionPane.WARNING_MESSAGE);
   			st=tf.getText();
			if(!(b>=16 && b<=20))
			tf.setText(st.substring(0,st.length()-1));                              
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
		  if(!(tf.getText().equals("")))
		  {
		  try
		  {
		 	Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM STUD_REG WHERE REG_NO="+Integer.parseInt(tf.getText()));
			if(rs.next())
			 {
				new UpdReg(rs.getInt(1));
				dispose();
			 }
			 else
			 {
				JOptionPane.showMessageDialog(null,"Record does not exist","Message",1);
				tf.setText("");
			 }
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
			dispose();
		}
	}

	public static void main(String str[])
	{
		new DisReg();
	}
}
