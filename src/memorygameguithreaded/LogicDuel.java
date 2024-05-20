package memorygameguithreaded;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.SwingUtilities;
import memorygameguithreaded.IntroSettings.Stats;

/**
 * Λογικη μονομαχιας. Οριζει τους κανονες και τον τροπο παιχνιδιου της μονομαχιας.
 * 
 * @author Steve
 */
public class LogicDuel extends Logic{
    
    protected HashMap<JLabel, Card> hmp;
    protected HashMap<JLabel, Card> hmo;
    
    /**
     * Κατασκευαστης
     * @param st Αντικειμενο με τις επιλογες του χρηστη
     */
    public LogicDuel(Stats st){
        super(st);
        hmp = new HashMap<>();
        hmo = new HashMap<>();
    }
    
    /**
     * Ανοιγει μια τυχαια καρτα και την αποθηκευει. Αν προηγουμενως εχει ανοιξει
     * καρτα ο χρηστης τοτε ανοιγει το ζευγαρι της αν ειναι αποθηκευμενο.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα.
     */
    @Override
    protected synchronized void nextTurn(int k){
        if(k == 0){
            return;
        }
        try{
            System.out.println("Sleeping in next turn.");
            Thread.sleep(1000);
        }
        catch(Exception e){
            System.out.println("Exception in nextTurn");
        }
        Random rng = new Random();
        ArrayList<JLabel> list = new ArrayList<>(hmo.keySet());
        AiPlayer aip = (AiPlayer)pa[k];
        
        JLabel l;
        if(openLabels.size() == 1 && (l = aip.rememberDuel(openLabels, hmp)) != null){
                openSesami(k, l);
                saveAll();
        }
        else{
            l = list.get(rng.nextInt(list.size()));
            while(openLabels.contains(l)){
                l = list.get(rng.nextInt(list.size()));
            }
            openSesami(k, l);
            saveAll();
        }
    }
    
    /**
     * Ανοιγει μια καρτα και αποθηκευει την αντιστοιχη της jlabel στην openLabels.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα
     * @param l Η αντιστοιχη jlabel της καρτας που θα ανοιχτει
     */
    protected synchronized void openSesami(int k, JLabel l){
        Card c;
        if(k == 0){
            c = hmp.get(l);
        }
        else{
            c = hmo.get(l);
        }
        c.openCard(l);
        
        if(!openLabels.contains(l)){
            openLabels.add(l);
        }
        try{
            System.out.println("Sleeping in next turn.");
            Thread.sleep(1000);
        }
        catch(Exception e){
            System.out.println("Exception in nextTurn");
        }
    }
    
    /**
     * Αποθηκευει τις καρτες για τον αντιπαλο παικτη(ο γενικευμενος χαρακτηρας
     * αν και περιττος διατηρηται).
     */
    @Override
    protected synchronized void saveAll(){
        for (Player p : pa){
                                
            if(p instanceof AiPlayer){
                AiPlayer aip = (AiPlayer) p;
                aip.saveCard(openLabels, hmo, csp);
            }
        }
    }
    
    /**
     * Συγκρινει δυο καρτες. Μετα τη συγκριση κλεινει τις καρτες και
     * αδειαζει την openLabels.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα
     * @param ol ArrayList με τις εκαστοτε ανοιχτες καρτες
     */
    @Override
    protected synchronized void compareCards(int k, ArrayList<JLabel> ol){
        if(openLabels.size() != limit){
            System.out.println("Not enought cards to compare");
            return;
        }
        if(k == 0){
            hm = hmp;
            hmp = hmo;
            hmo = hm;
        }
        JLabel l1 = ol.get(0);
        JLabel l2 = ol.get(1);
        Card x = hmp.get(l1);
        Card y = hmo.get(l2);
        
        try{
            System.out.println("Now Sleeping");
            Thread.sleep(1500);
        }
        catch(Exception e){
            
        }
        if(!cos){
            if(x.equals(y)){
                equallity(k,l1,l2,x,y);
            }
            else{
                unequallity();
            }
        }
        else{
            if(x.equals(y) && openSeq == x.getSequence()){
                equallity(k,l1,l2,x,y);
            }
            else{
                unequallity();
            }
        }
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                x.closeCard(l1);
                y.closeCard(l2);
            }
        });
        if(k == 0){
            hm = hmp;
            hmp = hmo;
            hmo = hm;
        }
        ol.clear();
    }
    
    /**
     * Καλειται σε περιπτωση ισοτητας καρτων, απενεργοποιει τις καρτες,
     * αυξανει το σκορ του παικτη και τις αφαιρει απ'τα HashMap hmp, hmo
     * αλλα και απ'το HashMap του αντιπαλου παικτη.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα
     * @param l1 JLabel της πρωτης καρτας
     * @param l2 JLabel της δευτερης καρτας
     * @param x Η πρωτη καρτα
     * @param y Η δευτερη καρτα
     */
    private void equallity(int k, JLabel l1, JLabel l2, Card x, Card y){
        System.out.println("Congratulations "+ pa[k].getName() +" found a pair!");
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                l1.setEnabled(false);
                l2.setEnabled(false);
            }
        });
        openSeq = openSeq+1;
        scores[k] = scores[k]+2;
        forgetAll();
        hmp.remove(l1, x);
        hmo.remove(l2, y);
    }
    
    /**
     * Καλειται σε περιπτωση ανισοτητας καρτων. Δεν κανει τιποτα.
     * 
     */
    private void unequallity(){
        //System.out.println("Unfortunately you didn't find a pair.");
    }
    
}
