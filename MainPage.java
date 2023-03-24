import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.*;
import java.text.*;

class MainPage extends JFrame implements ActionListener, Runnable
{
	public final Toolkit tk=Toolkit.getDefaultToolkit();
	public final Dimension d=tk.getScreenSize();
	public JMenuItem coln,colu,cold,cols,progn,progu,progd,progv,lgn,pwd,lgt,abt,ext,gn;

	JLabel logo,time;
	Thread t1,t2,t3;
	boolean blue=true;
	int pos=0,cnt=0;
	String msg,un;	

	MainPage(String u)
	{
		super("Counseling System...Administrator Page !!");
		setLocation(0,0);
                setSize(d.width,d.height-22);
		setLayout(null);
		un=u;
		final String u1=u;

		Container con=getContentPane();
		JMenuBar mb=new JMenuBar();
		setJMenuBar(mb);

		JMenu col=new JMenu("College");
		col.setMnemonic('C');
		coln=new JMenuItem("New Record");
		coln.addActionListener(this);
		coln.setToolTipText("insert new record for college");
		coln.setMnemonic('N');
		colu=new JMenuItem("Update ");
		colu.addActionListener(this);
		colu.setToolTipText("update record for college");
		colu.setMnemonic('U');
		cold=new JMenuItem("Delete ");
		cold.addActionListener(this);
		cold.setToolTipText("delete record for college");
		cold.setMnemonic('l');
		cols=new JMenuItem("View");
		cols.addActionListener(this);
		cols.setToolTipText("display all college's");
		cols.setMnemonic('S');
		col.add(coln);
		col.addSeparator();
		col.add(colu);
		col.add(cold);
		col.addSeparator();
		col.add(cols);
		
		JMenu prog=new JMenu("Course");
		prog.setMnemonic('o');
		progn=new JMenuItem("New Record");
		progn.addActionListener(this);
		progn.setToolTipText("add new course for college");
		progn.setMnemonic('w');
		progu=new JMenuItem("Update ");
		progu.addActionListener(this);
		progu.setToolTipText("update record for course");
		progu.setMnemonic('p');
		progd=new JMenuItem("Delete ");
		progd.addActionListener(this);
		progd.setToolTipText("delete record for course");
		progd.setMnemonic('t');
		progv=new JMenuItem("View Seat Details");
		progv.addActionListener(this);
		progv.setToolTipText("View Seat record for course");
		progv.setMnemonic('V');
		prog.add(progn);
		prog.add(progu);
		prog.addSeparator();
		prog.add(progd);
		prog.add(progv);

		JMenu dis=new JMenu("Login");
		dis.setMnemonic('L');
		lgn=new JMenuItem("New Account");
		lgn.addActionListener(this);
		lgn.setToolTipText("Create new User Account");
		lgn.setMnemonic('A');
		pwd=new JMenuItem("Change Password ");
		pwd.addActionListener(this);
		pwd.setToolTipText("Change password for Admin");
		pwd.setMnemonic('n');
		abt=new JMenuItem("About Us ");
		abt.addActionListener(this);
		abt.setToolTipText("Software Information");
		abt.setMnemonic('b');
		lgt=new JMenuItem("Log Out");
		lgt.addActionListener(this);
		lgt.setToolTipText("Switch User");
		lgt.setMnemonic('O');
		ext=new JMenuItem("Exit");
		ext.setMnemonic('x');
		ext.addActionListener(this);
		ext.setToolTipText("Close the counselling system");
		dis.add(abt);
		dis.addSeparator();
		dis.add(lgn);
		dis.add(pwd);
		dis.addSeparator();
		dis.add(lgt);
		dis.addSeparator();
		dis.add(ext);

		JMenu alt=new JMenu("Allocation");
		alt.setMnemonic('A');
		gn=new JMenuItem("Get Seat Allotment...");
		gn.addActionListener(this);
		gn.setToolTipText("Allot Seat to Student");
		gn.setMnemonic('G');
		alt.add(gn);
					
		mb.add(col);
		mb.add(prog);
		mb.add(dis);
		mb.add(alt);

		t1=new Thread(this);
		t2=new Thread(this);
		t3=new Thread(this);
		
		logo=new JLabel("Counseling System....",JLabel.CENTER);
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
				new MainPage(u1);
			}
		     }
		});
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==coln)
		  new NewCollege();
		if(ae.getSource()==colu)
		  new UpdtCollege();
		if(ae.getSource()==cold)
		  new DelCollege();
		if(ae.getSource()==cols)
		  new ViewCollege();
		if(ae.getSource()==progn)
		  new InsrCour();
		if(ae.getSource()==progu)
		  new UpdCour();
		if(ae.getSource()==progd)
		  new DelCour();
		if(ae.getSource()==progv)
		  
		 {
		  try{
		  	int id=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter College Code ::"));
		  	new ViewC(id);
		  }catch(NumberFormatException e)
		  {
			JOptionPane.showMessageDialog(null,"Enter only numbers","Error",0);
		  }
		}
		if(ae.getSource()==lgn)
		  new NewAcc();
		if(ae.getSource()==pwd)
		  new ChgPass(un);
		if(ae.getSource()==abt)
		  new About();
		if(ae.getSource()==ext)
		  System.exit(0);
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
		new MainPage(str[0]);
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
		//cal=;
		time.setBackground(Color.cyan);
		Date dt=Calendar.getInstance().getTime();
		DateFormat df=DateFormat.getTimeInstance();
		time.setText(df.format(dt));
	}	

}		