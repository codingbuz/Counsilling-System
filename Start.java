import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Start extends JWindow 
{
JProgressBar pb;
int sum;
int count;

public Start()
{
        setSize(500,300);
        setLocation(200,200);
        

        Container con = getContentPane();
        con.setLayout(new BorderLayout());
        con.setBackground(Color.gray);

        sum=count=0;
        pb=new JProgressBar();
        pb.setStringPainted(true);
        pb.setBackground(Color.gray);
        pb.setForeground(Color.blue);
        pb.setMinimum(0);
        pb.setMaximum(100);

        JLabel lb=new JLabel(" ");
        lb.setFont(new Font("Sans",Font.BOLD,20));
        lb.setForeground(Color.blue);
        
        JLabel lb1=new JLabel("",new ImageIcon("wel-19.gif"),JLabel.CENTER);
        

        con.add(lb,BorderLayout.CENTER);
        con.add(lb1,BorderLayout.NORTH);
        con.add(pb,BorderLayout.SOUTH);
        setVisible(true);
}

public static void main(String str[])
{
new Start().action();
}

private void action()
{
        for(;count<=100;count=count+1)
        {
        try
        {
         Thread.sleep(100);
        }
        catch(Exception e)
        { }
          
        sum=sum+count;
        pb.setValue(count);
        }

        new Login();
	dispose();
        setVisible(false);
}
}