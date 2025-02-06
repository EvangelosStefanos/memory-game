package org.memory.game;

import org.memory.game.gui.IntroGui;
import org.memory.game.gui.Controller;
import javax.swing.SwingUtilities;

public class MemoryGame {
  public static void main(String[] args) {
    /*
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        IntroGui i = new IntroGui(); 
      }
    });
    */
    SwingUtilities.invokeLater(new Controller());
  }    
}
