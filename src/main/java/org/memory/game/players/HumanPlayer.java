package org.memory.game.players;

import org.memory.game.players.Player;

/**
 * Αντιπροσωπευει τον χρηστη.
 * 
 * @author Steve
 */
public class HumanPlayer extends Player {

  /**
   * Κατασκευαστης
   * 
   * @param s Το ονομα του παικτη.
   * @param i Η ταυτοτητα του(θεση στον πινακα παικτων).
   */
  public HumanPlayer(String s, int i) {
    super(s, i);
  }
}
