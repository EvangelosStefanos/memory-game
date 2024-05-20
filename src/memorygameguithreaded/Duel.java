package memorygameguithreaded;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import memorygameguithreaded.IntroSettings.Stats;

/**
 * Αντιπροσωπευει την μονομαχια.
 * 
 * @author Steve
 */
public class Duel extends MainGame{
    
    private LogicDuel logic;
    private JTabbedPane tp;
    
    /**
     * Κατασκευαστης
     * Δημιουργει τα δυο πανελ και τα προσθετει σε ενα tabbedpane.
     * 
     * @param st Αντικειμενο Stats με τις επιλογες του χρηστη. 
     */
    public Duel(Stats st){
        super(st);
        this.logic = (LogicDuel)super.logic;
        
        JPanel p4 = createPlayerPanel();
        JPanel p5 = createAiPanel();
        tp = createCards(p4, p5);
    }
    
    /**
     * Δημιουργει και επισρτεφει το πανελ του χρηστη μαζι με τις καρτες 
     * σε τυχαια σειρα.
     * 
     * @return JPanel - Το πανελ στο οποιο παιζει ο χρηστης.
     */
    private JPanel createPlayerPanel(){
        GridLayout gl = new GridLayout(4,6);
        JPanel p = new JPanel(gl);
        
        JLabel la[] = new JLabel[24];
        int j = 0;//Array index
        Card ca[] = new Card[24];
        MouseHandler mh = new MouseHandler();
        
        //Create cards
        for(int k = 0; k <= 1; k++){
            for(int i = 0; i <= 11; i++){
                
                ca[j] = new Card("images/" + i + ".jpg", i);
                la[j] = new JLabel(ca[i].getBg());
                la[j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
                la[j].addMouseListener(mh);
                logic.hmp.put(la[j], ca[j]);
                j++;
            }
        }
        
        //Randomize cards
        for(int i = 0; i < la.length; i++){
            Random rng = new Random();
            int k = rng.nextInt(24 - 1);
            JLabel l = la[i];
            la[i] = la[k];
            la[k] = l;
        }
        
        //Add cards on the panel
        for(int i = 0; i < la.length; i++){
            p.add(la[i]);
        }
        return p;
    }
    
    /**
     * Δημιουργει και επιστρεφει το πανελ του αντιπαλου μαζι με τις καρτες
     * σε τυχαια σειρα.
     * 
     * @return JPanel - Το πανελ στο οποιο παιζει ο αντιπαλος.
     */
    private JPanel createAiPanel(){
        GridLayout gl = new GridLayout(4,6);
        JPanel p = new JPanel(gl);
        
        JLabel la[] = new JLabel[24];
        int j = 0;//Array index
        Card ca[] = new Card[24];
        MouseHandler mh = new MouseHandler();
        
        //Create cards
        for(int k = 0; k <= 1; k++){
            for(int i = 0; i <= 11; i++){
                
                ca[j] = new Card("images/" + i + ".jpg", i);
                la[j] = new JLabel(ca[i].getBg());
                la[j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
                la[j].addMouseListener(mh);
                logic.hmo.put(la[j], ca[j]);
                j++;
            }
        }
        
        //Randomize cards
        for(int i = 0; i < la.length; i++){
            Random rng = new Random();
            int k = rng.nextInt(24);
            JLabel l = la[i];
            la[i] = la[k];
            la[k] = l;
        }
        
        //Add cards on the panel
        for(int i = 0; i < la.length; i++){
            p.add(la[i]);
        }
        return p;
    }
    
    /**
     * Δημιουργει και επιστραφει το tabbedpane στο οποιο προσθετει το πανελ του 
     * χρηστη και του αντιπαλου.
     * 
     * @param p1 Το πανελ του χρηστη.
     * @param p2 Το πανελ του αντιπαλου.
     * @return JTabbedPane - Το tabbedpane με τα δυο πανελ.
     */
    private JTabbedPane createCards(JPanel p1, JPanel p2){
        
        JTabbedPane tp = new JTabbedPane();
        tp.addTab("Player's Panel", p1);
        tp.addTab("Opponent's Panel", p2);
        tp.setEnabled(false);
        JPanel p3 = new JPanel();
        p3.add(tp);
        p.add(p3, BorderLayout.CENTER);
        
        f.pack();
        f.setLocationRelativeTo(null);
        update(f);
        
        return tp;
    }
    
    /**
     * Οριζει την σειρα του αντιπαλου παικτη.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα.
     */
    @Override
    protected synchronized void turnOrder(int k){
        if(k != 0){
            tp.setSelectedIndex(1);
        }
        
        updateTitle(1);
        logic.nextTurn(1);
        logic.compareCards(k, logic.openLabels);
        updateScores(1);
        logic.nextTurn(1);
                
        updateTitle(0);
        tp.setSelectedIndex(0);
        input = true;
    }
    
    /**
     * Ελεγχει αν ειναι αδειος τουλαχιστον ενας απο τους HashMap hmp, hmo. 
     * Αν ειναι εμφανιζει το παραθυρο τελους, καταστρεφει το τωρινο παραθυρο
     * και ξαναρχιζει το παιχνιδι μολις πατησει το ok ο χρηστης.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα.
     */
    @Override
    protected void terminator(int k){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                if(logic.hmp.isEmpty() || logic.hmo.isEmpty()){

                    updateScores(k);
                    JOptionPane.showOptionDialog(f, "Game Over. You scored :\n"+logic.scores[0]+" points.\n "
                            + "Player 2 scored : "+logic.scores[1]+" points.",
                            "Party Time", DEFAULT_OPTION, INFORMATION_MESSAGE, null, null, null);
                    IntroSettings is = new IntroSettings();
                    f.setVisible(false);
                    f.dispose();
                }
            }
        });
    }
    
    /**
     * Διαχειριζεται τα κλικς στα jlabels. Οταν κανει κλικ ο χρηστης ανοιγει
     * μια καρτα και παιζει ο αντιπαλος. Αν υπαρχει ηδη μια καρτα ανοιχτη
     * τοτε γινεται συγκριση και μετα ξαναπαιζει ο χρηστης. Αν αδειασει ενας
     * απο τους δυο HashMap hmp ή hmo εμφανιζει το παραθυρο τελους, καταστρεφει
     * το τωρινο παραθυρο και ξαναρχιζει το παιχνιδι μολις πατησει το ok ο χρηστης.
     */
    protected class MouseHandler implements MouseListener{
        @Override
        public void mouseEntered(MouseEvent e){
            
        }
        @Override
        public void mouseExited(MouseEvent e){
            
        }
        @Override
        public synchronized void mousePressed(MouseEvent e){
            //Not in mouseClicked because mousePressed offers unparalleled clicking speed.
            JLabel l =(JLabel) e.getSource();
            if(input == true && l.isEnabled()){
                
                Thread t = new Thread(){
                    @Override
                    public void run(){
                        
                        input = false;
                        logic.openSesami(0, l);
                        logic.saveAll();
                        if(logic.openLabels.size() == 2){
                            tp.setSelectedIndex(1);
                            logic.compareCards(0, logic.openLabels);
                            terminator(0);
                            updateScores(0);
                            tp.setSelectedIndex(0);
                            input = true;
                        }
                        else{
                            turnOrder(1);
                        }
                    }
                };
                t.start();
                
            }
        }
        @Override
        public void mouseReleased(MouseEvent e){
            
        }
        @Override
        public void mouseClicked(MouseEvent e){   
            
        }
    }
    
}
