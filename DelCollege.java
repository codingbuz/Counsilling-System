import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

class DelCollege extends JFrame implements ActionListener, KeyListener
{
	private static Connection con;
	
	static
	{
        	try
        	{
		  Class.forName("oracle.jdbc.driver.OracleDriver");                 
		//  con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};");                 
con=DriverManager.getConnection("jdbc:ucanaccess://e://Java//JavaSE//MsAccess//Contacts.accdb");        	
}catch(Exception e)
		{
                 System.out.println(e);
        	}
        }

	private JTextField tf;
	private JButton sub,can;

	DelCollege()
	{
		super("Delete College Record Form");
		setLocation(200,100);
		setSize(400,400);
		setLayout(null);
		setResizable(false);
		Container cont=getContentPane();

		JLabel b1=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b1.setBounds(1,15,400,30);
		JLabel b2=new JLabel("",new ImageIcon("bar_eleg_e0.gif"),JLabel.LEFT);
		b2.setBounds(1,330,400,30);

		JLabel lcd=new JLabel("Enter College code ::");
		tf=new JTextField();
		tf.addKeyListener(this);
		lcd.setBounds(50,120,150,30);
		tf.setBounds(200,120,150,30);
		sub=new JButton("Delete");
		sub.setToolTipText(" Delete Record");
		sub.setMnemonic('D');
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
				new DelCollege();
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
		  int le=JOptionPane.showConfirmDialog(null,"Are you sure .........."+"\n"+"you want to delete record","Confirmation.......",JOptionPane.YES_NO_OPTION);
			
		  if(le==JOptionPane.YES_OPTION)
		  {
		  try
		  {
		 	 PreparedStatement st=con.prepareStatement("DELETE FROM COLLEGE WHERE COLLEGE_CODE=?");
			 st.setInt(1,Integer.parseInt(tf.getText()));
			 int i=st.executeUpdate();
			 if(i==0)
			 {
			 JOptionPane.showMessageDialog(null,"Record does not exist","Message",1);
			 tf.setText("");
			 }
			 else
			 {
			 JOptionPane.showMessageDialog(null,"Record Deleted successfully","Message",1);
			 dispose();
			 }
		  }catch(Exception e)
		  {
		 	JOptionPane.showMessageDialog(null,"ERROR ::"+e,"Error",0);
		  }
		  }
		  else if(le==JOptionPane.NO_OPTION)
		  {
				tf.setText("");
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
}
