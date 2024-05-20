package memorygameguithreaded;

import javax.swing.*;
import java.net.URL;

/**
 * Καθε αντικειμενο της κλασης αντιπροσωπευει μια καρτα.
 * 
 * @author Steve
 */
public class Card {
    private String name;
    private int openSequence;
    //private int openSequence;
    private ImageIcon img;
    private ImageIcon Bg;
    
    /**
     * Κατασκευαστης
     * 
     * @param n Το ονομα της καρτας.
     * @param os Ακεραιος που δηλωνει την σειρα ανοιγματος.
     */
    public Card(String n, int os){
        name = n;
        openSequence = os;
        img = getImage(n);
        Bg = getImage("images/bg.jpg");
    }
    
    /**
     * Επιστρεφει την εικονα με το συγκεκριμενο ονομα.
     * 
     * @param name Το ονομα της εικονας.
     * @return ImageIcon - Την εικονα με ονομα name.
     */
    private ImageIcon getImage(String name){
        ImageIcon image = null;
        String imgName = name;
        URL imageUrl = getClass().getResource(imgName);
        if(imageUrl != null){
            image = new ImageIcon(imageUrl);
        }
        return image;
    }
    
    /**
     * Επιστρεφει τον ακεραιο που δηλωνει την σειρα ανοιγματος.
     * 
     * @return int - Την σειρα ανοιγματος.
     */
    protected int getSequence(){
        return openSequence;
    }
    
    /**
     * Επιστρεφει την εικονα που χρησιμοποιειται στην πισω πλευρα των καρτων.
     * 
     * @return ImageIcon - Την εικονα στην πισω πλευρα των καρτων.
     */
    protected ImageIcon getBg(){
        return Bg;
    }
    
    /**
     * Ανοιγει μια καρτα.
     * 
     * @param l Η αντιστοιχη JLabel της καρτας.
     */
    protected void openCard(JLabel l){
        l.setIcon(img);
    }
    
    /**
     * Κλεινει μια καρτα.
     * 
     * @param l Η αντιστοιχη JLabel της καρτας.
     */
    protected void closeCard(JLabel l){
        l.setIcon(Bg);
    }
    
    /**
     * Συγρινει δυο καρτες.
     * Επιστρεφει true αν ειναι το ιδιο αντικειμενο καθως και οταν εχουν ιδιο
     * ονομα και σειρα ανοιγματος.
     * 
     * @param o Η δευτερη καρτα που συκρινεται.
     * @return boolean - True αν ειναι ισες, false αν δεν ειναι.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Card))
            return false;
        
        Card c = (Card) o;
        return name.equals(c.name) && openSequence == c.getSequence();
    }
    
    /**
     * Υπολογιζει το hashcode του αντικειμενου.
     * 
     * @return int - Το hashcode του αντικειμενου. 
     */
    @Override
    public int hashCode(){
        int hash = 4;
        hash = 4 * hash + openSequence;
        hash = 4 * hash + name.hashCode();
        return hash;
    }
}