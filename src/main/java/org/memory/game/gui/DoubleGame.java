package org.memory.game.gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import org.memory.game.logic.Settings;
import org.memory.game.logic.LogicNormal;
import org.memory.game.logic.Card;

/**
 * A Game mode where the number of cards to match is doubled.
 * 
 * @author EvanStefan
 */
public class DoubleGame extends MainGame {

  private LogicNormal logic;

  /**
   * Create the DoubleGame game mode
   * 
   * @param settings - The settings that the user selected
   */
  public DoubleGame(Settings settings) {
    super(settings);
    this.logic = (LogicNormal) super.logic;

    Thread t = new Thread() {

      @Override
      public void run() {
        LogicNormal logic = new LogicNormal(settings);
      }
    };
    t.start();

    createCards();
  }

  /**
   * Create a panel that contains 48 cards in a 6 x 8 grid.
   */
  private void createCards() {

    GridLayout gl = new GridLayout(6, 8);
    p3 = new JPanel(gl);
    p.add(p3, BorderLayout.CENTER);

    logic.map = new HashMap<>();
    JLabel la[] = new JLabel[48];
    int j = 0;// Array index
    Card ca[] = new Card[48];
    MouseHandler mh = new MouseHandler();

    // Create cards
    for (int k = 0; k <= 1; k++) {
      for (int i = 0; i <= 23; i++) {
        ca[j] = new Card(i, i);
        la[j] = new JLabel(ca[i].getCardBack());
        la[j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
        la[j].addMouseListener(mh);
        logic.map.put(la[j], ca[j]);
        j++;
      }
    }

    // Randomize cards
    for (int i = 0; i < la.length; i++) {
      Random rng = new Random();
      int k = rng.nextInt(48);
      JLabel l = la[i];
      la[i] = la[k];
      la[k] = l;
    }

    // Add cards on the panel
    for (int i = 0; i < la.length; i++) {
      p3.add(la[i]);
    }
    f.pack();
    update(f);
  }
}
