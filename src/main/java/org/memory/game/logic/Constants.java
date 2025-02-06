package org.memory.game.logic;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 * @author EvanStefan
 */
public class Constants {
  private static final String IMAGEPATH = "images/";
  public static String getImagePath(){
    return Constants.IMAGEPATH;
  }
  public static String getCardBackPath(){
    return Constants.getImagePath() + "bg.jpg";
  }
  public static String getCardFrontPath(int id){
    return Constants.getImagePath() + "cardFront" + id + ".jpg";
  }
    /**
   * Create and return an image icon from the specified path.
   * 
   * @param path - The path used, to create the image icon
   * @return ImageIcon - The image icon created from the specified path
   */
  public static ImageIcon createImageIcon(String path) {
    ImageIcon imageIcon = null;
    URL imageUrl = Constants.class.getResource("/org/memory/game/"+path);
    if (imageUrl != null) {
      return new ImageIcon(imageUrl);
    }
    System.out.println("ERROR: Failed to create ImageIcon from " + path + ".");
    return imageIcon;
  }

}
