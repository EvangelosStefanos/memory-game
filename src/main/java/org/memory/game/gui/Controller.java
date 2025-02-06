package org.memory.game.gui;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Displays the settings the player must set at the start of a new game.
 * @author EvanStefan
 */
public class Controller implements ControllerInterface, Runnable {

  private JFrame f;

  private int currentPanelIndex;
  private ArrayList<JPanel> panels;

  /**
   * Creates and displays a window and two panels. The first contains the
   * New Game and Exit buttons and is displayed initially. The second contains
   * the settings the players needs to set and is displayed after the player
   * presses the New Game button.
   */
  public Controller() { }
  
  private void advance(){
    f.remove(panels.get(currentPanelIndex));
    currentPanelIndex += 1;
    if(currentPanelIndex == panels.size()){
      // handle case
      currentPanelIndex = 0;
    }
    f.add(panels.get(currentPanelIndex));
    Utils.update(f);
  }

  @Override
  public void newGame() {
    advance();
  }
  
  @Override
  public void exitGame() {
    System.exit(0);
  }

  @Override
  public void run() {
    f = Utils.createFrame("Intro", 640, 480);    
    currentPanelIndex = 0;
    panels = new ArrayList<>();
    panels.add(new IntroPanel(this));
    panels.add(new JPanel());
    f.add(panels.get(currentPanelIndex));
    Utils.update(f);
  }

}
