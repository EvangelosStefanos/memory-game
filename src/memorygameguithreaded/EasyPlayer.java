package memorygameguithreaded;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;

/**
 * Αντιπροσωπεύει τον παικτη με μνημη χρυσοψαρου.
 * @author Steve
 */
public class EasyPlayer extends AiPlayer{
    
    /**
     * Κατασκευαστης
     * @param s Το ονομα του παικτη.
     * @param i Η ταυτοτητα του παικτη(θεση στον πινακα παικτων).
     */
    public EasyPlayer(String s, int i){
        super(s, i);
    }
    
    /**
     * Δεν κανει τιποτα.
     * @param ol
     * @param hm
     * @param csp 
     */
    @Override
    protected void saveCard(ArrayList<JLabel> ol, HashMap<JLabel, Card> hm, boolean csp){
        
    }
    
    /**
     * Επιστρεφει παντα null.
     * @param l
     * @param hm
     * @return null - Επιστρεφει παντα null.
     */
    @Override
    protected JLabel rememberCard(JLabel l, HashMap<JLabel, Card> hm){
        return null;
    }
    
    /**
     * Επιστρεφει παντα null.
     * @param ol
     * @param hmp
     * @return null - Επιστρεφει παντα null.
     */
    @Override
    protected JLabel rememberDuel(ArrayList<JLabel> ol, HashMap<JLabel, Card> hmp){
        return null;
    }
    
    /**
     * Επιστρεφει παντα null.
     * @param l
     * @param hm
     * @param limit
     * @return null - Επιστρεγει παντα null;
     */
    @Override
    protected ArrayList<JLabel> rememberAll(JLabel l, HashMap<JLabel, Card> hm, int limit){
        return null;
    }
    
    /**
     * Δεν κανει τιποτα.
     * @param ol
     * @param hm 
     */
    @Override
    protected void forgetCards(ArrayList<JLabel> ol, HashMap<JLabel, Card> hm){
        
    }
    
}