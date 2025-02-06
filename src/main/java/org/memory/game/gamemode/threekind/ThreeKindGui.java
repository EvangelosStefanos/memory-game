package org.memory.game.gamemode.threekind;

import org.memory.game.gamemode.AbstractGameModeGui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.memory.game.gamemode.AbstractGameModeLogic;
import org.memory.game.gui.Utils;
import org.memory.game.logic.Card;
import org.memory.game.logic.Settings;

/**
 * Αντιπροσωπευει το τριο.
 * 
 * @author Steve
 */
public class ThreeKindGui extends AbstractGameModeGui {

  private ThreeKindLogic logic;

  /**
   * Κατασκευαστης.
   * 
   * @param settings
   */
  public ThreeKindGui(Settings settings, ThreeKindLogic logic) {
    super(settings, logic);
    this.logic = (ThreeKindLogic) super.logic;

    System.out.println(SwingUtilities.isEventDispatchThread());
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        createCards();
      }
    });
  }

  /**
   * Διμιουργει το πανελ με τις 36 καρτες σε πλεγμα 6 x 6. Δημιουργει τις καρτες
   * και τα jlabels και τα τοποθετει τυχαια στο πανελ. Τελος εμφανιζει το
   * παραθυρο.
   */
  private void createCards() {

    GridLayout gl = new GridLayout(6, 6);
    p3 = new JPanel(gl);
    p.add(p3, BorderLayout.CENTER);

    logic.map = new HashMap<>();
    JLabel la[] = new JLabel[36];
    int j = 0;// Array index
    Card ca[] = new Card[36];
    AbstractGameModeGui.MouseHandler mh = new AbstractGameModeGui.MouseHandler();

    // Create cards
    for (int k = 0; k <= 2; k++) {
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
      int k = rng.nextInt(36);
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
