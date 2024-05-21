package org.memory.game.logic;

/**
 * @author EvanStefan
 */
public class Constants {
  private static final String IMAGEPATH = "images/";
  public static String getImagePath(){
    return Constants.IMAGEPATH;
  }
  public static String getCardBackPath(){
    return Constants.getImagePath() + "cardBack.jpg";
  }
  public static String getCardFrontPath(int id){
    return Constants.getImagePath() + "cardFront" + id + ".jpg";
  }
}
