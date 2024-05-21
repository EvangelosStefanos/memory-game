package org.memory.game;

import org.memory.game.gui.IntroSettings;
import javax.swing.SwingUtilities;

public class MemoryGame {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        IntroSettings i = new IntroSettings(); 
      }
    });
  }    
}
