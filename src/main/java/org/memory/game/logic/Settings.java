package org.memory.game.logic;

/**
 * A collection of setting values.
 * @author EvanStefan
 */
public class Settings {
  
  public int playerN;
  public int dif2;
  public int dif3;
  public int dif4;

  public boolean pass;
  public boolean nr;
  public boolean cos;
  public boolean csp;
  public int gt;

  public Settings() {
    playerN = 0;
    dif2 = 0;
    dif3 = 0;
    dif4 = 0;

    pass = false;
    nr = false;
    cos = false;
    csp = false;
    gt = 1;
  }
}
