package org.memory.game.gui;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author EvanStefan
 */
public class Scaler {

  private final ImageIcon scaledImageIcon;

  /**
   * https://stackoverflow.com/questions/3971841/how-to-resize-images-proportionally-keeping-the-aspect-ratio/14731922#14731922
   * @param image
   * @param widthViewport
   * @param heightViewport 
   */
  public Scaler(ImageIcon image, int widthViewport, int heightViewport){

    double widthRatio = (double) widthViewport / image.getIconWidth();
    double heightRatio = (double) heightViewport / image.getIconHeight();
    
    double ratio = Math.min(widthRatio, heightRatio);
    
    int newWidth = (int) Math.min(image.getIconWidth() * ratio, image.getIconWidth());
    int newHeight = (int) Math.min(image.getIconHeight() * ratio, image.getIconHeight());
    
    scaledImageIcon = new ImageIcon(image.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
  }

  public ImageIcon getScaledImageIcon(){
    return scaledImageIcon;
  }

}
