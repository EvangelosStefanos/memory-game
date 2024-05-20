package memorygameguithreaded;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;

/**
 * Αντιπροσωπευει τον παικτη που θυμαται μονο τα μισα.
 * @author Steve
 */
public class NormalPlayer extends AiPlayer{
    
    private boolean save;
    
    /**
     * Κατασκευαστης
     * @param s Το ονομα του παικτη.
     * @param i Η ταυτοτητα του(θεση στον πινακα παικτων).
     */
    public NormalPlayer(String s, int i){
        super(s, i);
        phm = new HashMap<>();
        save = true;
    }
    
    /**
     * Υποσκελισμενη εκδοση της saveCard της AiPlayer. Εξαιτιας της μεταβλητης
     * save αποθηκευει μονο τις μισες φορες απο αυτες που καλειται. Κατα τα αλλα
     * ειναι ιδια. Η μεθοδος αποθηκευει τις εκαστοτε ανοιχτες καρτες και τα jlabels τους.
     * 
     * @param ol ArrayList με τις jlabels που αντιστοιχουν στις εκαστοτε ανοιχτες cards.
     * @param hm HashMap που περιεχει ολα τα ζευγαρια jlabel-card.
     * @param csp Κατασταση εναλλαγης καρτων(True = με εναλλαγη, false = χωρις).
     */
    @Override
    protected void saveCard(ArrayList<JLabel> ol, HashMap<JLabel, Card> hm, boolean csp){
        if(save){
            if(!csp){
                for(JLabel l : ol){
                    Card x = hm.get(l);
                    phm.put(l, x);
                }
            }
            else{
                if(ol.size()!=2){
                    System.out.println("Error in saveCard");
                }
                JLabel l1 = ol.get(0);
                JLabel l2 = ol.get(1);
                Card x = hm.get(l1);
                Card y = hm.get(l2);
                phm.put(l1, y);
                phm.put(l2, x);
            }
            save = false;
        }
        else{
            save = true;
        }
    }
}
