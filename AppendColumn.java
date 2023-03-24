import java.awt.BorderLayout;
import java.awt.Point;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class AppendColumn{
  public static void main(String[] args) {
  new AppendColumn();
  }

  public AppendColumn(){
  Frame frame = new JFrame("Sparse Test");
    String headers[] = { "English", "Japanese" };
    TableModel model = new SparseTableModel(10, headers);
    JTable table = new JTable(model);

    model.setValueAt("one", 0, 0);
    model.setValueAt("ten", 9, 0);
    model.setValueAt("roku - \u516D", 5, 1);
    model.setValueAt("hachi - \u516B", 8, 1);

    JScrollPane scrollPane = new JScrollPane(table);
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    frame.setSize(300, 150);
    frame.setVisible(true);

  }
}

