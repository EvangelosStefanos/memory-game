package org.memory.game.gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A collection of utility methods
 * @author EvanStefan
 */
public abstract class Utils{
    
  private Utils(){}

  public static JFrame createFrame(String s, int w, int h){
    JFrame f = new JFrame(s);
    f.setSize(w,h);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    return f;
  }

  public static JFrame createFrame(String s){
    JFrame f = new JFrame(s);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    return f;
  }

  public static void update(JFrame f){
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }

  public static JPanel createPanel(){
    JPanel p = new JPanel();
    return p;
  }

  public static JPanel createPanel(LayoutManager m){
    JPanel p = new JPanel(m);
    return p;
  }

  public static JLabel createLabel(){
    JLabel l = new JLabel();
    return l;
  }

  public static JLabel createLabel(String s){
    JLabel l = new JLabel(s);
    return l;
  }

  public static JButton createButton(String s){
    JButton b = new JButton(s);
    return b;
  }

  public static JButton createButton(String s, ActionListener h){
    JButton b = new JButton(s);
    b.addActionListener(h);
    return b;
  }

  public static JComboBox createBox(String[] s, ActionListener h){
    JComboBox box = new JComboBox(s);
    box.addActionListener(h);
    return box;
  }

  public static void addtoPanel(Component c, JPanel p){
    p.add(c);
  }

  public static void addtoFrame(Component c, JFrame f){
    f.add(c);
  }
    
}