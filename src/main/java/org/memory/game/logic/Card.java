package org.memory.game.logic;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * This class represents a card to be matched.
 * 
 * @author Steve
 */
public class Card {
  private String cardFrontPath;
  private String cardBackPath;
  private ImageIcon cardFrontImageIcon;
  private ImageIcon cardBackImageIcon;
  private int order; // The order that defines the sequence that each card must be matched in

  /**
   * Construct a card.
   * 
   * @param id    - The identifier of the card
   * @param order - The order of the card in the sequence
   */
  public Card(int id, int order) {
    this.cardFrontPath = Constants.getCardFrontPath(id);
    this.cardBackPath = Constants.getCardBackPath();
    this.cardFrontImageIcon = Constants.createImageIcon(this.cardFrontPath);
    this.cardBackImageIcon = Constants.createImageIcon(this.cardBackPath);
    this.order = order;
  }


  /**
   * Get the order in the sequence that this card must be matched in
   * 
   * @return int - The order in the sequence
   */
  public int getOrder() {
    return order;
  }

  /**
   * Return the image used as this card's back
   * 
   * @return ImageIcon - The image used as this card's back
   */
  public ImageIcon getCardBackImageIcon() {
    return cardBackImageIcon;
  }

  /**
   * Open a card such that its front side will be displayed
   * 
   * @param label - A Jlabel that will be set to display the card's front
   */
  public void openCard(JLabel label) {
    label.setIcon(cardFrontImageIcon);
  }

  /**
   * Close a card such that its back side will be displayed
   * 
   * @param label - A Jlabel that will be set to display the card's back
   */
  public void closeCard(JLabel label) {
    label.setIcon(cardBackImageIcon);
  }

  /**
   * Compare a card to another object
   * 
   * @param o The other object to compare against
   * @return boolean - True if they are equal otherwise false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Card))
      return false;

    Card c = (Card) o;
    return cardFrontPath.equals(c.cardFrontPath) && order == c.getOrder();
  }

  /**
   * Returns a hash code value for the object
   * 
   * @return int - The hashcode assigned to the object
   */
  @Override
  public int hashCode() {
    int hash = 4;
    hash = 4 * hash + order;
    hash = 4 * hash + cardFrontPath.hashCode();
    return hash;
  }
}