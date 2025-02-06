package org.memory.game.gui;

/**
 *
 * @author EvanStefan
 */
public class Centerer {
  private final int X;
  private final int Y;
  public Centerer(int width, int height, int widthViewport, int heightViewport){
    X = (widthViewport - width) / 2;
    Y = (heightViewport - height) / 2;
  }
  public int getX(){
    return X;
  }
  public int getY(){
    return Y;
  }
}
