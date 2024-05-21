package org.memory.game.logic;

import javax.swing.*;
import java.net.URL;

/**
 * This class represents a card to be matched.
 * 
 * @author Steve
 */
public class Card {
  private String name;
  private int order; // The order that defines the sequence that each card must be matched in
  private ImageIcon cardFront;
  private ImageIcon cardBack;

  /**
   * Construct a card.
   * 
   * @param id    - The identifier of the card
   * @param order - The order of the card in the sequence
   */
  public Card(int id, int order) {
    this.name = Constants.getCardFrontPath(id);
    this.order = order;
    this.cardFront = this.getImage(this.name);
    this.cardBack = this.getImage(Constants.getCardBackPath());
  }

  /**
   * Return the image with the specified name.
   * 
   * @param name - The name of the image to return
   * @return ImageIcon - The image with the specified name
   */
  private ImageIcon getImage(String name) {
    ImageIcon image = null;
    URL imageUrl = this.getClass().getResource(name);
    if (imageUrl != null) {
      image = new ImageIcon(imageUrl);
    }
    return image;
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
  public ImageIcon getCardBack() {
    return cardBack;
  }

  /**
   * Open a card such that its front side will be displayed
   * 
   * @param label - A Jlabel that will be set to display the card's front
   */
  public void openCard(JLabel label) {
    label.setIcon(cardFront);
  }

  /**
   * Close a card such that its back side will be displayed
   * 
   * @param label - A Jlabel that will be set to display the card's back
   */
  public void closeCard(JLabel label) {
    label.setIcon(cardBack);
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
    return name.equals(c.name) && order == c.getOrder();
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
    hash = 4 * hash + name.hashCode();
    return hash;
  }
}