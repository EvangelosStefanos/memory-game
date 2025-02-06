package org.memory.game.gamemode.duel;

import org.memory.game.gamemode.AbstractGameModeGui;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.memory.game.gui.IntroGui;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import org.memory.game.gamemode.AbstractGameModeLogic;
import org.memory.game.gui.Utils;
import org.memory.game.logic.Settings;
import org.memory.game.logic.Card;

/**
 * The graphical user interface for the Duel game mode. Each player has his own
 * deck of cards in a separate tab. Uses the Duel game mode logic.
 * 
 * @author EvanStefan
 */
public class DuelGui extends AbstractGameModeGui {

  private JTabbedPane tp;

  /**
   * Create the DuelLogic game mode
   * 
   * @param settings - The settings that the user selected
   */
  public DuelGui(Settings settings, DuelLogic logic) {
    super(settings, logic);

    JPanel p4 = createPlayerPanel();
    JPanel p5 = createAiPanel();
    tp = createCards(p4, p5);
  }
  
  private DuelLogic getLogic(){
    return (DuelLogic) super.logic;
  }

  /**
   * Create and return a panel with randomized cards for the human player
   * 
   * @return JPanel - A panel with the human player's cards
   */
  private JPanel createPlayerPanel() {
    GridLayout gl = new GridLayout(4, 6);
    JPanel p = new JPanel(gl);

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
        this.getLogic().hmp.put(la[j], ca[j]);
        j++;
      }
    }

    // Randomize cards
    for (int i = 0; i < la.length; i++) {
      Random rng = new Random();
      int k = rng.nextInt(24 - 1);
      JLabel l = la[i];
      la[i] = la[k];
      la[k] = l;
    }

    // Add cards on the panel
    for (int i = 0; i < la.length; i++) {
      p.add(la[i]);
    }
    return p;
  }

  /**
   * Create and return a panel with randomized cards for the ai player
   * 
   * @return JPanel - A panel with the ai player's cards
   */
  private JPanel createAiPanel() {
    GridLayout gl = new GridLayout(4, 6);
    JPanel p = new JPanel(gl);

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
        this.getLogic().hmo.put(la[j], ca[j]);
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
      p.add(la[i]);
    }
    return p;
  }

  /**
   * Creates and returns the tabbedpane with the two tabs.
   * 
   * @param p1 - The human player's JPanel
   * @param p2 - The ai player's JPanel
   * @return JTabbedPane - The JTabbedPane with the human and the ai players'
   *         JPanels
   */
  private JTabbedPane createCards(JPanel p1, JPanel p2) {

    JTabbedPane tp = new JTabbedPane();
    tp.addTab("Player's Panel", p1);
    tp.addTab("Opponent's Panel", p2);
    tp.setEnabled(false);
    JPanel p3 = new JPanel();
    p3.add(tp);
    p.add(p3, BorderLayout.CENTER);

    f.pack();
    f.setLocationRelativeTo(null);
    Utils.update(f);

    return tp;
  }

  /**
   * The ai player makes one move and after that he makes another
   * @param k - The id of the player who is currently playing
   */
  @Override
  public synchronized void turnOrder(int k) {
    if (k != 0) {
      tp.setSelectedIndex(1);
    }

    updateTitle(1);
    logic.nextTurn(1);
    logic.compareCards(k, logic.openLabels);
    updateScores(1);
    logic.nextTurn(1);

    updateTitle(0);
    tp.setSelectedIndex(0);
    input = true;
  }

  /**
   * Checks the game termination condition. If true, ends the game and 
   * resets the state
   * @param k - The id of the player who is currently playing
   */
  @Override
  public void terminator(int k) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        if (getLogic().hmp.isEmpty() || getLogic().hmo.isEmpty()) {

          updateScores(k);
          JOptionPane.showOptionDialog(f, "Game Over. You scored :\n" + logic.scores[0] + " points.\n "
              + "Player 2 scored : " + logic.scores[1] + " points.",
              "Party Time", DEFAULT_OPTION, INFORMATION_MESSAGE, null, null, null);
          new IntroGui();
          f.setVisible(false);
          f.dispose();
        }
      }
    });
  }
  
  /**
   * 
   */
  public class MouseHandler implements MouseListener {
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Open the clicked card. If able make another move otherwise the ai player
     * will make two moves
     * @param e - The mouse event
     */
    @Override
    public synchronized void mousePressed(MouseEvent e) {
      // Not in mouseClicked because mousePressed offers unparalleled clicking speed.
      JLabel l = (JLabel) e.getSource();
      if (input == true && l.isEnabled()) {

        Thread t = new Thread() {
          @Override
          public void run() {

            input = false;
            getLogic().openSesami(0, l);
            logic.saveAll();
            if (logic.openLabels.size() == 2) {
              tp.setSelectedIndex(1);
              logic.compareCards(0, logic.openLabels);
              terminator(0);
              updateScores(0);
              tp.setSelectedIndex(0);
              input = true;
            } else {
              turnOrder(1);
            }
          }
        };
        t.start();

      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}
  }

}
