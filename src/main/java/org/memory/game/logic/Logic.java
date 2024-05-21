package org.memory.game.logic;

import org.memory.game.players.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Abstract κλαση που κληροδοτει κωδικα. Δημιουργει τους παικτες και οριζει τι
 * θα κανει ο καθε παικτης οταν ερθει η σειρα του.
 * 
 * @author Steve
 */
public abstract class Logic {

  public Player pa[]; // Πινακας με ολους τους παικτες
  public int scores[]; // Πινακας με το σκορ των παικτων
  public HashMap<JLabel, Card> map; // HashMap με ολα τα ζευγαρια jlabel-card
  public ArrayList<JLabel> openLabels;// ArrayList με jlabels των οποιων οι καρτες ειναι ανοιχτες
  public int limit; // Σχετιζεται με το τριο και το κουαρτετο και μια προσπαθεια για γενικευση του
                    // κωδικα που δεν πετυχε
  public boolean noreplay; // Κατασταση επαναληψης σειρας μετα απο επιτυχη συγκριση (True = με, false =
                           // χωρις)
  public boolean csp; // Κατασταση της εναλλαγης καρτων(True = με, false = χωρις)
  public boolean cos; // Κατασταση ανοιγματος καρτων σε σειρα(True = με, false = χωρις)
  public int openSeq; // Ποιο ζευγαρι ειναι το επομενο που πρεπει να ανοιχτει
  public int steps;

  /**
   * Κατασκευαστης
   * Αρχικοποιει τα πεδια τις κλασης και δημιουργει τους παικτες.
   * 
   * @param st Αντικειμενο με τις επιλογες του χρηστη
   */
  public Logic(Settings settings) {
    createPlayers(settings);

    map = new HashMap<>();
    openLabels = new ArrayList<>();
    noreplay = settings.nr;
    csp = settings.csp;
    cos = settings.cos;
    if (settings.cos) {
      openSeq = 0;
    }

    switch (settings.gt) {
      case 1:
      case 2:
      case 5:
        limit = 2;
        break;
      case 3:
        limit = 3;
        break;
      case 4:
        limit = 4;
        break;
      default:
        System.out.println("Error in switch->limit");
        break;
    }
  }

  /**
   * Δημιουργει τους παικτες και τα σκορ τους.
   * 
   * @param settings Αντικειμενο με τις επιλογες του χρηστη
   */
  private void createPlayers(Settings settings) {

    pa = new Player[settings.playerN + 1];
    scores = new int[settings.playerN + 1];

    for (int i = 0; i <= settings.playerN; i++) {
      if (i == 0) {
        pa[i] = new HumanPlayer("Player 1", i);
        scores[i] = 0;
        steps = 0;
      }
      else {
        int dif = 0;
        // Get current players difficulty
        switch (i) {
          case 1:
            dif = settings.dif2;
            break;
          case 2:
            dif = settings.dif3;
            break;
          case 3:
            dif = settings.dif4;
            break;
          default:
            System.out.println("Error in createScores");
            break;
        }

        // Init a new player based on his difficulty
        int j = i + 1;
        switch (dif) {
          case 0:
            pa[i] = new EasyPlayer("Player " + j, i);
            scores[i] = 0;
            break;
          case 1:
            pa[i] = new NormalPlayer("Player " + j, i);
            scores[i] = 0;
            break;
          case 2:
            pa[i] = new HardPlayer("Player " + j, i);
            scores[i] = 0;
            break;
          default:
            System.out.println("Error in createScores.");
            break;
        }
      }
    }
  }

  public abstract void compareCards(int k, ArrayList<JLabel> ol);

  /**
   * Ανοιγει τυχαια μια καρτα και το ζευγαρι της αν εχει αποθηκευτει ή δυο
   * τυχαιες καρτες αν δεν εχει. Τις αποθηκευει για τον καθε παικτη και τις
   * συγκρινει.
   * 
   * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα
   */
  public synchronized void nextTurn(int k) {
    if (k == 0) {
      return;
    }
    try {
      System.out.println("Sleeping in next turn.");
      Thread.sleep(2000);
    } catch (Exception e) {
      System.out.println("Exception in nextTurn");
    }
    Random rng = new Random();
    ArrayList<JLabel> list = new ArrayList<>(map.keySet());
    AiPlayer aip = (AiPlayer) pa[k];

    for (int i = 0; i < limit; i++) {
      JLabel l = list.get(rng.nextInt(list.size()));
      while (openLabels.contains(l)) {
        l = list.get(rng.nextInt(list.size()));
      }

      openSesami(l);
      if (openLabels.size() >= limit) {
        break;
      }
      JLabel l2 = aip.rememberCard(l, map);

      if (l2 != null) {

        openSesami(l2);
        if (openLabels.size() >= limit) {
          break;
        }
      }
    }
    saveAll();
    compareCards(k, openLabels);
  }

  /**
   * Ανοιγει μια καρτα και αποθηκευει την αντιστοιχη της jlabel στην openLabels.
   * 
   * @param l Η αντιστοιχη jlabel της καρτας που θα ανοιχτει
   */
  public synchronized void openSesami(JLabel l) {
    Card c = map.get(l);
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        c.openCard(l);
      }
    });
    if (!openLabels.contains(l)) {
      openLabels.add(l);
      if (pa.length == 1) {
        steps++;
      }
    }
  }

  /**
   * Αποθηκευει τις εκαστοτε ανοιχτες καρτες για ολους τους παικτες.
   */
  public synchronized void saveAll() {
    for (Player p : pa) {

      if (p instanceof AiPlayer) {
        AiPlayer aip = (AiPlayer) p;
        aip.saveCard(openLabels, map, csp);
      }
    }
  }

  /**
   * Ξεχναει τις καρτες των οποιων τα ζευγαρια βρεθηκαν.
   */
  public synchronized void forgetAll() {
    for (Player p : pa) {

      if (p instanceof AiPlayer) {
        AiPlayer aip = (AiPlayer) p;
        aip.forgetCards(openLabels, map);
      }
    }
  }

}
