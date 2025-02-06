package org.memory.game.gamemode;

import org.memory.game.logic.*;
import org.memory.game.players.Player;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.memory.game.gui.Utils;
import org.memory.game.gui.IntroGui;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

/**
 * Creates the base window layout and the logic.
 * 
 * @author EvanStefan
 */
public abstract class AbstractGameModeGui {

  public JFrame f; // Frame

  public JPanel p; // Base Panel
  public JPanel p1; // Scores
  public JPanel p2; // CardSequence
  public JPanel p3; // CardDisplay

  public JButton b; // Pass

  public AbstractGameModeLogic logic;
  public boolean input;

  /**
   * Create a window with a panel and the game mode specific logic.
   * 
   * @param settings - The settings defined by the player
   */
  public AbstractGameModeGui(Settings settings, AbstractGameModeLogic logic) {

    this.logic = logic;

    BorderLayout bl = new BorderLayout();

    f = Utils.createFrame("Main Game");
    p = Utils.createPanel(bl);
    Utils.addtoFrame(p, f);

    createScores(settings.playerN);
    createTitle();
    createPass(settings.pass);
    createCardSequence(settings.cos, settings.gt);

    input = true;
    f.pack();
    Utils.update(f);
  }

  /**
   * Create a panel with labels that display the score for each player.
   * 
   * @param numofplayers - The total number of players
   */
  private void createScores(int numofplayers) {
    GridLayout gl = new GridLayout(1, 0);
    p1 = Utils.createPanel(gl);
    p.add(p1, BorderLayout.SOUTH);

    for (int i = 0; i <= numofplayers; i++) {
      int j = i + 1;
      p1.add(new JLabel(" Player " + j + " :  0 ", JLabel.CENTER));
    }
  }

  /**
   * Update the score display for the specified player.
   * 
   * @param k - The identifier of the player whose score display will be updated
   */
  public void updateScores(int k) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        p1.remove(k);
        p1.add(new JLabel(logic.pa[k].getName() + " :  " + logic.scores[k] + " ", JLabel.CENTER), k);
        p1.revalidate();
        p1.repaint();
      }
    });
  }

  /**
   * Create a panel that displays the name of the current player
   */
  private synchronized void createTitle() {
    if (p == null) {
      System.out.println("p null");
    }
    p.add(new JLabel("Now Playing : Player 1", JLabel.CENTER), BorderLayout.NORTH);
  }

  /**
   * Update the panel that displays the name of the current player
   * 
   * @param k - The identifier of the current player
   */
  public synchronized void updateTitle(int k) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        BorderLayout bl = (BorderLayout) p.getLayout();
        if ((bl.getLayoutComponent(BorderLayout.NORTH) != null)) {
          p.remove(bl.getLayoutComponent(BorderLayout.NORTH));
        }
        p.add(new JLabel("Now Playing : " + logic.pa[k].getName(), JLabel.CENTER), BorderLayout.NORTH); // Incomplete
                                                                                                        // Function
        p.revalidate();
        p.repaint();
      }
    });
  }

  /**
   * Create a pass button if enabled in the settings
   * 
   * @param pass - true if pass is enabled
   */
  private void createPass(boolean pass) {
    if (pass == true) {
      b = new JButton("Pass");
      PassHandler bh = new PassHandler();
      b.addActionListener(bh);
      p.add(b, BorderLayout.WEST);
    }
  }

  /**
   * Create a panel with the order that the cards must be matched in.
   * 
   * @param cardSequence - true if cards must be matched in order
   * @param gametype     - the identifier of the game mode
   */
  private void createCardSequence(boolean cardSequence, int gametype) {
    if (cardSequence == true) {
      GridLayout gl = new GridLayout(0, 1);
      p2 = Utils.createPanel(gl);
      p.add(p2, BorderLayout.EAST);
      String[] s = { "Βίκος", "Αύρα", "Nestea", "Amita Motion", "Λούξ", "Λεμονίτα", "Πορτοκαλάδα", "Sprite", "Pepsi",
          "Coca Cola",
          "Fix Hellas", "Άλφα", "Mythos", "Amstel", "Μαλαματίνα", "Red Wine", "White Wine", "Smirnoff", "Bacardi",
          "Absolut Vodka",
          "Cutty Shark", "Famous Grouse", "Jack Daniels", "Malibu" };
      int a = 1;
      if (gametype == 2) { // Double Game
        a = 2;
      }
      for (int i = 1; i <= 12 * a; i++) {
        p2.add(new JLabel("  " + i + ". " + s[i - 1]));
      }
    }
  }

  /**
   * For each ai player, execute their turn and update the score and the title.
   * 
   * @param k - The identifier of the current player
   */
  public synchronized void turnOrder(int k) {

    if (logic.noreplay == false) {
      for (Player p : logic.pa) {
        if (logic.map.isEmpty()) {
          break;
        }
        do {
          if (logic.map.isEmpty()) {
            break;
          }
          updateTitle(p.getID());
          logic.nextTurn(p.getID());
          updateScores(p.getID());
        } while (logic.noreplay);
      }
      logic.noreplay = false;// Restoring noreplay to its initial value. Each loop will have the same initial
                             // value.
    } else {
      for (Player p : logic.pa) {
        if (logic.map.isEmpty()) {
          break;
        }
        updateTitle(p.getID());
        logic.nextTurn(p.getID());
        updateScores(p.getID());
      }
    }
    updateTitle(0);
    input = true;
    terminator(k);
  }

  /**
   * Close any open cards and if at least one was closed, end current player's
   * turn and begin next player's turn
   */
  public synchronized void pass() {
    if (logic.openLabels.isEmpty()) {
      return;
    }
    for (JLabel l : logic.openLabels) {
      Card x = logic.map.get(l);
      x.closeCard(l);
      System.out.println("in for");
    }

    System.out.println("for done");
    logic.openLabels.clear();
    Thread t1 = new Thread() {
      @Override
      public void run() {
        turnOrder(1);
      }
    };
    t1.start();
  }

  /**
   * When the game is over, display the ending window and reset
   * 
   * @param k - The identifier of the current player
   */
  public void terminator(int k) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        if (logic.map.isEmpty()) {

          updateScores(k);
          switch (logic.pa.length) {
            case 1:
              JOptionPane.showOptionDialog(f, "Game Over. You scored :\n" + logic.scores[0] + " points.\n"
                  + "Steps taken : " + logic.steps,
                  "Party Time", DEFAULT_OPTION, INFORMATION_MESSAGE, null, null, null);
              break;
            case 2:
              JOptionPane.showOptionDialog(f, "Game Over. You scored :\n" + logic.scores[0] + " points.\n "
                  + "Player 2 scored : " + logic.scores[1] + " points.",
                  "Party Time", DEFAULT_OPTION, INFORMATION_MESSAGE, null, null, null);
              break;
            case 3:
              JOptionPane.showOptionDialog(f, "Game Over. You scored :\n" + logic.scores[0] + " points.\n "
                  + "Player 2 scored : " + logic.scores[1] + " points.\n Player 3 scored : " + logic.scores[2]
                  + " points.\n ",
                  "Party Time", DEFAULT_OPTION, INFORMATION_MESSAGE, null, null, null);
              break;
            case 4:
              JOptionPane.showOptionDialog(f, "Game Over. You scored :\n" + logic.scores[0] + " points.\n "
                  + "Player 2 scored : " + logic.scores[1] + " points.\n Player 3 scored : " + logic.scores[2]
                  + " points.\n "
                  + "Player 4 scored : " + logic.scores[3] + " points.", "Party Time", DEFAULT_OPTION,
                  INFORMATION_MESSAGE, null, null, null);
              break;
            default:
              System.out.println("Error in terminator.");
          }
          new IntroGui();
          f.setVisible(false);
          f.dispose();
        }
      }
    });
  }

  /**
   * Handles clicks on labels associated with cards. Open the clicked card,
   * test for a match, test for game end and end the player's turn.
   */
  public class MouseHandler implements MouseListener {
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public synchronized void mousePressed(MouseEvent e) {
      // Not in mouseClicked because mousePressed offers unparalleled clicking speed.
      JLabel l = (JLabel) e.getSource();
      if (input == true && l.isEnabled()) {

        Thread t = new Thread() {
          @Override
          public void run() {
            logic.openSesami(l);
            if (logic.openLabels.size() == logic.limit) {
              input = false;
              logic.saveAll();
              logic.compareCards(0, logic.openLabels);
              updateScores(0);
              terminator(0);
              if (!logic.noreplay) {
                turnOrder(1);
              } else {
                input = true;
              }
            }
          }
        };
        t.start();
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
  }

  /**
   * Handler for the pass button.
   */
  private class PassHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      pass();
    }
  }
}
