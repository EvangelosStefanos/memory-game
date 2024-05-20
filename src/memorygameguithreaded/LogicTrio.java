package memorygameguithreaded;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.SwingUtilities;
import memorygameguithreaded.IntroSettings.Stats;

/**
 * Λογικη τριου. Οριζει τους κανονες και τον τροπο παιχνιδιου του τριου.
 * 
 * @author Steve
 */
public class LogicTrio extends Logic{
    
    /**
     * Κατασκευαστης
     * 
     * @param st Αντικειμενο με τις επιλογες του χρηστη
     */
    public LogicTrio(Stats st){
        super(st);
    }
    
    /**
     * Ανοιγει τυχαια μια καρτα και αν ολα τα ζευγαρια τις ειναι στη μνημη του παικτη
     * τα ανοιγει αλλιως ανοιγει τυχαιες καρτες, τις αποθηκευουν ολοι οι παικτες
     * και τις συγκρινει.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα
     */
    @Override
    protected synchronized void nextTurn(int k){
        if(k == 0){
            return;
        }
        try{
            System.out.println("Sleeping in next turn.");
            Thread.sleep(2000);
        }
        catch(Exception e){
            System.out.println("Exception in nextTurn");
        }
        Random rng = new Random();
        ArrayList<JLabel> list = new ArrayList<>(hm.keySet());
        AiPlayer aip = (AiPlayer)pa[k];
        
        JLabel l1 = list.get(rng.nextInt(list.size()));
        while(openLabels.contains(l1)){
            l1 = list.get(rng.nextInt(list.size()));
        }
        //System.out.println("Handle 1");
        openSesami(l1);
        
        ArrayList<JLabel> rall = aip.rememberAll(l1, hm, limit);
        if(rall != null){
            for(JLabel rl : rall){
                openSesami(rl);
            }
        }
        else{
            for(int i = 1; i < limit; i++){
                JLabel l2 = list.get(rng.nextInt(list.size()));
                while(openLabels.contains(l2)){
                    l2 = list.get(rng.nextInt(list.size()));
                }
                openSesami(l2);
            }
        }
        saveAll();
        compareCards(k, openLabels);
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
        JLabel l3 = ol.get(2);
        Card x = hm.get(l1);
        Card y = hm.get(l2);
        Card z = hm.get(l3);
        
        try{
            System.out.println("Now Sleeping");
            Thread.sleep(1500);
        }
        catch(Exception e){
            
        }
        if(!cos){
            if(x.equals(y) && x.equals(z)){
                equallity(k,l1,l2,l3,x,y,z);
            }
            else{
                unequallity();
            }
        }
        else{
            if(x.equals(y) && x.equals(z) && openSeq == x.getSequence()){
                equallity(k,l1,l2,l3,x,y,z);
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
                z.closeCard(l3);
            }
        });
        ol.clear();
    }
    
    /**
     * Καλειται σε περιπτωση ισοτητας καρτων, απενεργοποιει τις καρτες,
     * αυξανει το σκορ του παικτη και τις αφαιρει απ'το HashMap hm
     * αλλα και απ'τα HashMap των αντιπαλων παικτων.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα
     * @param l1 JLabel της πρωτης καρτας
     * @param l2 JLabel της δευτερης καρτας
     * @param l3 JLabel της τριτης καρτας
     * @param x Η πρωτη καρτα
     * @param y Η δευτερη καρτα
     * @param z Η τριτη καρτα
     */
    private void equallity(int k, JLabel l1, JLabel l2, JLabel l3, Card x, Card y, Card z){
        System.out.println("Congratulations "+ pa[k].getName() +" found a trio!");
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                l1.setEnabled(false);
                l2.setEnabled(false);
                l3.setEnabled(false);
            }
        });
        openSeq = openSeq+1;
        noreplay = true;//Player will play again
        scores[k] = scores[k]+3;
        forgetAll();
        hm.remove(l1, x);
        hm.remove(l2, y);
        hm.remove(l3, z);
    }
    
    /**
     * Οριζει οτι θα παιξει ο επομενος παικτης.
     */
    private void unequallity(){
        noreplay = false;//Player will not play again
    }
    
}
