import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.*;
import java.text.*;

class StudPage extends JFrame implements ActionListener, Runnable
{
	public final Toolkit tk=Toolkit.getDefaultToolkit();
	public final Dimension d=tk.getScreenSize();
	public JMenuItem lst,sv,abt,lgt,ext,rgu,rgn,prn,gn;

	JLabel logo,time;
	Thread t1,t2,t3;
	boolean blue=true;
	int pos=0,cnt=0;
	String msg;	

	StudPage()
	{
		super("Welcome to Counseling System... !!");
		setLocation(0,0);
                setSize(d.width,d.height-22);
		setLayout(null);
		Container con=getContentPane();
		JMenuBar mb=new JMenuBar();
		setJMenuBar(mb);

		JMenu vw=new JMenu("View");
		vw.setMnemonic('V');
		lst=new JMenuItem("List College...");
		lst.addActionListener(this);
		lst.setToolTipText("Display list of college's");
		lst.setMnemonic('L');
		sv=new JMenuItem("Seat Availability ");
		sv.addActionListener(this);
		sv.setToolTipText("Display seat status in college");
		sv.setMnemonic('S');
		abt=new JMenuItem("About Us ");
		abt.addActionListener(this);
		abt.setToolTipText("Display information");
		abt.setMnemonic('U');
		lgt=new JMenuItem("Log Out");
		lgt.addActionListener(this);
		lgt.setToolTipText("Switch User");
		lgt.setMnemonic('O');
		ext=new JMenuItem("Exit");
		ext.setMnemonic('x');
		ext.addActionListener(this);
		ext.setToolTipText("Close the counseling system");
		vw.add(lst);
		vw.addSeparator();
		vw.add(sv);
		vw.addSeparator();
		vw.add(abt);
		vw.addSeparator();
		vw.add(lgt);
		vw.addSeparator();
		vw.add(ext);

		JMenu rg=new JMenu("Registeration");
		rg.setMnemonic('R');
		rgn=new JMenuItem("New Registeration");
		rgn.addActionListener(this);
		rgn.setToolTipText("Displays registeration form");
		rgn.setMnemonic('N');
		rgu=new JMenuItem("Update... ");
		rgu.addActionListener(this);
		rgu.setToolTipText("update registeration details");
		rgu.setMnemonic('p');
		prn=new JMenuItem("Print reg. slip");
		prn.addActionListener(this);
		prn.setToolTipText("Print registeration slip");
		prn.setMnemonic('t');
		rg.add(rgn);
		rg.add(rgu);
		rg.addSeparator();
		rg.add(prn);
		
		JMenu alt=new JMenu("Allocation");
		alt.setMnemonic('A');
		gn=new JMenuItem("Get Seat Allotment...");
		gn.addActionListener(this);
		gn.setToolTipText("Allot Seat to Student");
		gn.setMnemonic('G');
		alt.add(gn);
				
		mb.add(vw);
		mb.add(rg);
		mb.add(alt);

		t1=new Thread(this);
		t2=new Thread(this);
		t3=new Thread(this);
		
		logo=new JLabel("Counseling System...welcome's all candidates !!",JLabel.CENTER);
		logo.setFont(new Font("Arial",Font.BOLD,60));
		logo.setBounds(10,100,d.width,100);
		time=new JLabel("**",JLabel.CENTER);
		time.setFont(new Font("Arial",Font.BOLD,40));
		time.setBounds(d.width/2-150,300,300,100);
		JLabel lb=new JLabel("",new ImageIcon("rfheartani_e0.gif"),JLabel.LEFT);        
		lb.setBounds(1,d.height-200,550,50);
		JLabel lb1=new JLabel("",new ImageIcon("rfheartani_e0.gif"),JLabel.LEFT);        
		lb1.setBounds(550,d.height-200,550,50);
		JLabel lb2=new JLabel("",new ImageIcon("rfheartani_e0.gif"),JLabel.LEFT);        
		lb2.setBounds(1100,d.height-200,d.width,50);
		con.add(logo);
		con.add(time);
		con.add(lb);
		con.add(lb1);
		con.add(lb2);
		msg=logo.getText();
		t1.start();
		t2.start();
		t3.start();

		addWindowListener(new WindowAdapter()
		{
		     public void windowClosing(WindowEvent we)
		     {
			int le=JOptionPane.showConfirmDialog(null,"Are you sure"+"\n"+"you want to exit","Confirmation.......",JOptionPane.YES_NO_OPTION);
			if(le==JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
			else if(le==JOptionPane.NO_OPTION)
			{
				new StudPage();
			}
		     }
		});
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==lst)
		  new ViewCollege();
		if(ae.getSource()==sv)
		 {
		  try{
		  	int id=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter College Code ::"));
		  	new ViewC(id);
		  }catch(NumberFormatException e)
		  {
			JOptionPane.showMessageDialog(null,"Enter only numbers","Error",0);
		  }
		}
		if(ae.getSource()==abt)
		  new About();
		if(ae.getSource()==ext)
		  System.exit(0);
		if(ae.getSource()==rgn)
		  new StudReg();
		if(ae.getSource()==rgu)
		  new DisReg();
		if(ae.getSource()==prn)
		{
		  try{
		  	int id=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter registration number ::"));
		  	new PrintReg(id);
		  }catch(NumberFormatException e)
		  {
			JOptionPane.showMessageDialog(null,"Enter only numbers","Error",0);
		  }
		}
		if(ae.getSource()==gn)
		{
		  try{
		  	int id=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter registration number ::"));
		  	new GetAlloc(id);
		  }catch(NumberFormatException e)
		  {
			JOptionPane.showMessageDialog(null,"Enter only numbers","Error",0);
		  }
		}
		if(ae.getSource()==lgt)
		{
			new Login();
			dispose();	
		}
	}

	public static void main(String str[])
	{
		new StudPage();
	}

	public void run()
	{
		while(true)
		{
		try
		{
		if(cnt==0)
		{
		  cnt=1;
		  scroll();
		  Thread.sleep(500);
		}
		if(cnt==1)
	  	{
		  cnt=2;
		  changeColor();
		  Thread.sleep(1200);
		}
		if(cnt==2)
		{
		  cnt=0;
		  dispDate();
		  Thread.sleep(1000);
		}
		}
		catch(InterruptedException ie){ }
		}
	}

	private void scroll()
	{
		String s1;
		s1=logo.getText();
		int l=msg.length();
		s1=msg.substring(pos,l)+". . ."+msg.substring(0,pos);
		pos++;
		if(pos>l)
		pos=0;
		logo.setText(s1);
		cnt=1;
	}

	private void changeColor()
	{
		if(blue)
		{
	  	logo.setForeground(Color.blue);
		time.setForeground(Color.magenta);
	  	blue=false;
		}
		else
		{
	  	logo.setForeground(Color.pink);
		time.setForeground(Color.darkGray);
	  	blue=true;
		}
	}

	private void dispDate()
	{
		time.setBackground(Color.cyan);
		Date dt=Calendar.getInstance().getTime();
		DateFormat df=DateFormat.getTimeInstance();
		time.setText(df.format(dt));
	}	

}		