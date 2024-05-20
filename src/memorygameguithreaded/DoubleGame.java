package memorygameguithreaded;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import memorygameguithreaded.IntroSettings.Stats;

/**
 * Αντιπροσωπευει το διπλο παιχνιδι.
 * 
 * @author Steve
 */
public class DoubleGame extends MainGame{
    
    private LogicNormal logic;
    
    /**
     * Κατασκευαστης
     * Δημιουργει την λογικη και το πανελ με τις καρτες.
     * 
     * @param st Αντικειμενο Stats με τις επιλογες του χρηστη.  
     */
    public DoubleGame(Stats st){
        super(st);
        this.logic = (LogicNormal) super.logic;
        
        Thread t = new Thread(){
            
            @Override
            public void run(){
                
                LogicNormal logic = new LogicNormal(st);
            }
        };
        t.start();
        
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
        
        logic.hm = new HashMap<>();
        JLabel la[] = new JLabel[48];
        int j = 0;//Array index
        Card ca[] = new Card[48];
        MouseHandler mh = new MouseHandler();
        
        //Create cards
        for(int k = 0; k <= 1; k++){
            for(int i = 0; i <= 23; i++){
                
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
