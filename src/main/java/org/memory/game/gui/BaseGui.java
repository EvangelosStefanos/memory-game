package org.memory.game.gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A collection of utility methods
 * @author EvanStefan
 */
public abstract class BaseGui{
    
  public BaseGui(){}

  public JFrame createFrame(String s, int w, int h){
    JFrame f = new JFrame(s);
    f.setSize(w,h);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    return f;
  }

  public JFrame createFrame(String s){
    JFrame f = new JFrame(s);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    return f;
  }

  public void update(JFrame f){
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }

  public JPanel createPanel(){
    JPanel p = new JPanel();
    return p;
  }

  public JPanel createPanel(LayoutManager m){
    JPanel p = new JPanel(m);
    return p;
  }

  public JLabel createLabel(){
    JLabel l = new JLabel();
    return l;
  }

  public JLabel createLabel(String s){
    JLabel l = new JLabel(s);
    return l;
  }

  public JButton createButton(String s){
    JButton b = new JButton(s);
    return b;
  }

  public JButton createButton(String s, ActionListener h){
    JButton b = new JButton(s);
    b.addActionListener(h);
    return b;
  }

  public JComboBox createBox(String[] s, ActionListener h){
    JComboBox box = new JComboBox(s);
    box.addActionListener(h);
    return box;
  }

  public void addtoPanel(Component c, JPanel p){
    p.add(c);
  }

  public void addtoFrame(Component c, JFrame f){
    f.add(c);
  }
    
}