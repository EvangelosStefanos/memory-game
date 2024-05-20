package memorygameguithreaded;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import memorygameguithreaded.IntroSettings.Stats;


/**
 * Αντιπροσωπευει το κουαρτετο.
 * 
 * @author Steve
 */
public class Quartet extends MainGame{
    
    private LogicQuartet logic;
    
    /**
     * Κατασκευαστης
     * 
     * @param st Αντικειμενο με τις επιλογες του χρηστη
     */
    public Quartet(Stats st){
        super(st);
        this.logic = (LogicQuartet) super.logic;
        
        createCards(); 
    }
    
    /**
     * Διμιουργει το πανελ με τις 48 καρτες σε πλεγμα 6 x 8. Δημιουργει τις καρτες
     * και τα jlabels και τα τοποθετει τυχαια στο πανελ. Τελος εμφανιζει το
     * παραθυρο.
     */
    private void createCards(){
        
        GridLayout gl = new GridLayout(6,8);
        p3 = new JPanel(gl);
        p.add(p3, BorderLayout.CENTER);
        
        JLabel la[] = new JLabel[48];
        int j = 0;//Array index
        Card ca[] = new Card[48];
        MainGame.MouseHandler mh = new MainGame.MouseHandler();
        
        //Create cards
        for(int k = 0; k <= 3; k++){
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
            int k = rng.nextInt(48);
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
