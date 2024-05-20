package memorygameguithreaded;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;

/**
 * Abstract κλαση με σκοπο την κληροδοτηση κωδικα. Αναθετει σε καθε παικτη 
 * ελεγχομενο απ'τον υπολογιστη εναν HashMap με ονομα phm.
 * 
 * @author Steve
 */
public abstract class AiPlayer extends Player{
    
    protected HashMap<JLabel, Card> phm;
    
   
    /**
     * Κατασκευαστης
     * @param s Το ονομα του παικτη.
     * @param i Η ταυτοτητα του(θεση στον πινακα παικτων).
     */
    public AiPlayer(String s, int i){
        super(s, i);
        phm = new HashMap<>();
    }
    
    /**
     * Η μεθοδος αποθηκευει τις εκαστοτε ανοιχτες καρτες και τα jlabels τους.
     * 
     * @param ol ArrayList με τις jlabels που αντιστοιχουν στις εκαστοτε ανοιχτες cards.
     * @param hm HashMap που περιεχει ολα τα ζευγαρια jlabel-card.
     * @param csp Κατασταση εναλλαγης καρτων(True = με εναλλαγη, false = χωρις).
     */
    protected void saveCard(ArrayList<JLabel> ol, HashMap<JLabel, Card> hm, boolean csp){
        if(!csp){
            for(JLabel l : ol){
                Card x = hm.get(l);
                phm.put(l, x);
            }
        }
        else{
            if(ol.size()!= 2){
                System.out.println("Error in saveCard");
            }
            JLabel l1 = ol.get(0);
            JLabel l2 = ol.get(1);
            Card x = hm.get(l1);
            Card y = hm.get(l2);
            phm.put(l1, y);
            phm.put(l2, x);
        }
    }
    
    /**
     * Βρισκει το ζευγαρι της εκαστοτε ανοιχτης καρτας αλλιως επιστρεφει null.
     * 
     * @param l Η jlabel για της οποιας την καρτα αναζηταται το ζευγαρι.
     * @param hm HashMap με ολα τα ζευγαρια jlabel-card.
     * @return JLabel - Την jlabel που αντιστοιχει στο ζευγαρι της εκαστοτε ανοιχτης καρτας αν εχει αποθηκευτει ή null αν δεν εχει.
     */
    protected JLabel rememberCard(JLabel l, HashMap<JLabel, Card> hm){
        Card x = hm.get(l);
        for(JLabel j : phm.keySet()){
            Card y = phm.get(j);
            if(x != y && x.equals(y)){
                return j;
            }
        }
        return null;
    }
    
    /**
     * Παιρνει την αντιστοιχη καρτα της jlabel που βρισκεται στην ArrayList ol 
     * απο τον HashMap hmp του χρηστη και αναζητα το ζευγαρι της στον HashMap
     * της κλασης(phm).
     * 
     * @param ol ArrayList με jlabels οι καρτες των οποιων ειναι ανοιχτες.
     * @param hmp HashMap με ολα τα ζευγαρια jlabel-card του χρηστη.
     * @return Jlabel - Την jlabel που αντιστοιχει στο ζευγαρι της εκαστοτε ανοιχτης καρτας αν εχει αποθηκευτει ή null αν δεν εχει.
     */
    protected JLabel rememberDuel(ArrayList<JLabel> ol, HashMap<JLabel, Card> hmp){
        Card x = hmp.get(ol.get(0));
        for(JLabel j : phm.keySet()){
            Card y = phm.get(j);
            if(x != y && x.equals(y)){
                return j;
            }
        }
        return null;
    }
    
    /**
     * Βρισκει ολες τις καρτες που ειναι ισες με την αντιστοιχη καρτα της jlabel l.
     * Επιστρεφει ArrayList με καρτες αν βρεθουν ολες ή null αν δεν βρεθει εστω και μια.
     * 
     * @param l JLabel για την καρτα της οποιας αναζητουνται ισες.
     * @param hm HashMap με ολα τα ζευγαρια jlabel-card.
     * @param limit Ακεραιος που αν ειναι ισος με 3 δηλωνει τριο ή αν ειναι 4 κουαρτετο.
     * @return ArrayList<JLabel> - Την ArrayList με 2 για τριο ή 3 για κουαρτετο ισες καρτες με αυτην της jlabel l, αλλιως null.
     */
    protected ArrayList<JLabel> rememberAll(JLabel l, HashMap<JLabel, Card> hm, int limit){
        Card x = hm.get(l);
        ArrayList<JLabel> ra = new ArrayList<>();
        for(JLabel j : phm.keySet()){
            Card y = phm.get(j);
            if(x != y && x.equals(y)){
                ra.add(j);
            }
        }
        if(ra.size() == limit-1){
            return ra;
        }
        return null;
    }
    
    /**
     * Αφαιρει απ'τον HashMap της κλασης(phm) τις καρτες των οποιων τα ζευγαρια 
     * βρεθηκαν και τις jlabels τους
     * 
     * @param ol ArrayList με jlabels οι καρτες των οποιων ειναι ανοιχτες.
     * @param hm HashMap με ολα τα ζευγαρια jlabel-card.
     */
    protected void forgetCards(ArrayList<JLabel> ol, HashMap<JLabel, Card> hm){
        for(JLabel l : ol){
           Card x = hm.get(ol);
           phm.remove(l, x);
        }
    }
    
}
