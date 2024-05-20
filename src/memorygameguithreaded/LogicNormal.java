package memorygameguithreaded;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.SwingUtilities;
import memorygameguithreaded.IntroSettings.Stats;

/**
 * Λογικη απλου παιχνιδιου. Οριζει τους κανονες και τον τροπο παιχνιδιου του
 * απλου παιχνιδιου.
 * 
 * @author Steve
 */
public class LogicNormal extends Logic{
    
    /**
     * Κατασκευαστης
     * @param st Αντικειμενο με τις επιλογες του χρηστη
     */
    public LogicNormal(Stats st){
        super(st);
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
        JLabel l1 = ol.get(0);
        JLabel l2 = ol.get(1);
        Card x = hm.get(l1);
        Card y = hm.get(l2);
        
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
                unequallity(l1,l2,x,y);
            }
        }
        else{
            if(x.equals(y) && openSeq == x.getSequence()){
                equallity(k,l1,l2,x,y);
            }
            else{
                unequallity(l1,l2,x,y);
            }
        }
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                x.closeCard(l1);
                y.closeCard(l2);
            }
        });
        ol.clear();
    }
    
    /**
     * Καλειται σε περιπτωση ισοτητας καρτων, απενεργοποιει τις καρτες,
     * αυξανει το σκορ του παικτη και τις αφαιρει απ'τo HashMap hm
     * αλλα και απ'τα HashMap των αντιπαλων παικτων.
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
        noreplay = true;//Player will play again
        scores[k] = scores[k]+2;
        forgetAll();
        hm.remove(l1, x);
        hm.remove(l2, y);
    }
    
    /**
     * Καλειται σε περιπτωση ανισοτητας καρτων και οριζει οτι θα παιξει ο επομενος
     * παικτης. Αν η εναλλαγη καρτων ειναι ενεργοποιημενη εναλλασει τις καρτες.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα
     * @param l1 JLabel της πρωτης καρτας
     * @param l2 JLabel της δευτερης καρτας
     * @param x Η πρωτη καρτα
     * @param y Η δευτερη καρτα
     */
    private void unequallity(JLabel l1, JLabel l2, Card x, Card y){
        //System.out.println("Unfortunately you didn't find a pair.");
        if(csp){
            hm.replace(l1, y);
            hm.replace(l2, x);
        }
        noreplay = false;//Player will not play again
    }
}
