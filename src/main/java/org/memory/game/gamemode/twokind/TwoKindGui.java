package org.memory.game.gamemode.twokind;

import org.memory.game.logic.Settings;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import org.memory.game.gamemode.AbstractGameModeGui;
import org.memory.game.gamemode.AbstractGameModeLogic;
import org.memory.game.gui.Utils;
import org.memory.game.logic.Card;

/**
 * A game mode where there is a deck of cards that players must open and match.
 * There are 24 cards in a 4 x 6 grid and each player can open 2 cards per turn.
 * 
 * @author EvanStefan
 */
public class TwoKindGui extends AbstractGameModeGui {

  private TwoKindLogic logic;

  /**
   * Κατασκευαστης
   * 
   * @param settings Αντικειμενο με τις επιλογες του χρηστη
   */
  public TwoKindGui(Settings settings, TwoKindLogic logic) {
    super(settings, logic);
    this.logic = (TwoKindLogic) super.logic;

    createCards();
  }

  /**
   * Δημιουργει το πανελ με τις 24 καρτες σε πλεγμα 4 x 6. Δημιουργει τις καρτες
   * και τα jlabels και τα τοποθετει τυχαια στο πανελ. Τελος εμφανιζει το
   * παραθυρο.
   */
  private void createCards() {

    GridLayout gl = new GridLayout(4, 6);
    p3 = new JPanel(gl);
    p.add(p3, BorderLayout.CENTER);

    logic.map = new HashMap<>();
    JLabel la[] = new JLabel[24];
    int j = 0;// Array index
    Card ca[] = new Card[24];
    MouseHandler mh = new MouseHandler();

    // Create cards
    for (int k = 0; k <= 1; k++) {
      for (int i = 0; i <= 11; i++) {

        ca[j] = new Card(i, i);
        la[j] = new JLabel(ca[i].getCardBackImageIcon());
        la[j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
        la[j].addMouseListener(mh);
        logic.map.put(la[j], ca[j]);
        j++;
      }
    }

    // Randomize cards
    for (int i = 0; i < la.length; i++) {
      Random rng = new Random();
      int k = rng.nextInt(24);
      JLabel l = la[i];
      la[i] = la[k];
      la[k] = l;
    }

    // Add cards on the panel
    for (int i = 0; i < la.length; i++) {
      p3.add(la[i]);
    }
    f.pack();
    Utils.update(f);
  }
}
