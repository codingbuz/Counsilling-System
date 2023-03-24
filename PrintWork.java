import java.awt.*;
import javax.swing.*;
import java.awt.print.*;
import javax.swing.UIManager.*;
import javax.print.attribute.*;

class PrintWork implements Printable
{
        static JFrame f1;
   
        PrintWork(JFrame f)
        {
                try
                {
                String  s=UIManager.getSystemLookAndFeelClassName();
                UIManager.setLookAndFeel(s);
                
                PrinterJob p=PrinterJob.getPrinterJob();
                PrintRequestAttributeSet as=new HashPrintRequestAttributeSet();
                PageFormat pf=p.pageDialog(as);

                p.setPrintable(this,pf);

                f1=f;
                if(p.printDialog(as))
                {
                       p.print(as);
                }


                }
                catch(Exception ex)
                {
                }
         }


     public int print(Graphics g,PageFormat pf,int pg) throws PrinterException
        {
                if(pg>0)
                {
                     return NO_SUCH_PAGE;
                }

                Graphics2D gd=(Graphics2D)g;
                gd.translate(pf.getImageableX(),pf.getImageableY());
                f1.printAll(g);
                return PAGE_EXISTS;
       }

}
