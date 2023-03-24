import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class About extends JFrame
{
	About()
	{
		super("About Us");
		setLocation(200,100);
		setSize(400,400);
		setLayout(null);
		setResizable(false);
		Container cont=getContentPane();
		
		JLabel lb1=new JLabel("Counselling System",JLabel.CENTER);
		lb1.setFont(new Font("Arial",Font.BOLD,30));
		JLabel lb2=new JLabel("",new ImageIcon("ln3_e0.gif"),JLabel.LEFT);
		JLabel lb3=new JLabel("Developed in partial fullfillment of");
		lb3.setFont(new Font("Arial",Font.ITALIC,20));
		JLabel lb4=new JLabel("Master of Computer Applications");
		lb4.setFont(new Font("Arial",Font.BOLD | Font.ITALIC,20));
		JLabel lb5=new JLabel ("By- Upendra Kumar");
		lb5.setFont(new Font("Arial",Font.PLAIN,20));

		lb1.setBounds(10,50,380,50);
		lb2.setBounds(1,100,400,30);
		lb3.setBounds(50,150,350,40);
		lb4.setBounds(50,200,350,40);
		lb5.setBounds(50,250,350,40);
		cont.add(lb1);cont.add(lb2);
		cont.add(lb3);cont.add(lb4);
		cont.add(lb5);

		addWindowListener(new WindowAdapter()
		{
		     public void windowClosing(WindowEvent we)
		     {
			int le=JOptionPane.showConfirmDialog(null,"Are you sure"+"\n"+"you want to close the window","Confirmation.......",JOptionPane.YES_NO_OPTION);
			if(le==JOptionPane.YES_OPTION)
			{
				dispose();
			}
			else if(le==JOptionPane.NO_OPTION)
			{
				new About();
			}
		     }
		});

		setVisible(true);
	}

	public static void main(String str[])
	{
	new About();
	}
}
