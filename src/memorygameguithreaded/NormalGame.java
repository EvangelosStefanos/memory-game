package memorygameguithreaded;

import memorygameguithreaded.IntroSettings.Stats;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.*;

/**
 * Αντιπροσωπευει το απλο παιχνιδι.
 * 
 * @author Steve
 */
public class NormalGame extends MainGame{
    
    private LogicNormal logic;
    
    /**
     * Κατασκευαστης
     * 
     * @param st Αντικειμενο με τις επιλογες του χρηστη
     */
    public NormalGame(Stats st){
        super(st);
        this.logic = (LogicNormal) super.logic;
        
        createCards();
    }
    
    /**
     * Δημιουργει το πανελ με τις 24 καρτες σε πλεγμα 4 x 6. Δημιουργει τις καρτες
     * και τα jlabels και τα τοποθετει τυχαια στο πανελ. Τελος εμφανιζει το
     * παραθυρο.
     */
    private void createCards(){
        
        GridLayout gl = new GridLayout(4,6);
        p3 = new JPanel(gl);
        p.add(p3, BorderLayout.CENTER);
        
        logic.hm = new HashMap<>();
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
                logic.hm.put(la[j], ca[j]);
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
            p3.add(la[i]);
        }
        f.pack();
        update(f);
    }
}
