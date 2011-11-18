package pl.chyla.watcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;


public final class MainFrame<T> extends JFrame {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private JList<? extends T> list;
  private static final Dimension PREF_SIZE = new Dimension(400, 300);
  public MainFrame(ListModel<? extends T> model) {

    list = new JList<>(model);
    JScrollPane scrollPane = new JScrollPane();
    getContentPane().add(scrollPane, BorderLayout.CENTER);


    list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    list.setBackground(new Color(255, 255, 240));
    scrollPane.setViewportView(list);

    setPreferredSize(PREF_SIZE);
    setMinimumSize(PREF_SIZE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

  }

}
