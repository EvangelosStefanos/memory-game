package memorygameguithreaded;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Αντιπροσωπευει το τριο.
 * 
 * @author Steve
 */
public class Trio extends MainGame{
    
    private LogicTrio logic;
    
    /**
     * Κατασκευαστης.
     * @param st 
     */
    public Trio(IntroSettings.Stats st){
        super(st);
        this.logic = (LogicTrio) super.logic;
        
        System.out.println(SwingUtilities.isEventDispatchThread());
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                createCards();
            }
        });   
    }
    
    /**
     * Διμιουργει το πανελ με τις 36 καρτες σε πλεγμα 6 x 6. Δημιουργει τις καρτες
     * και τα jlabels και τα τοποθετει τυχαια στο πανελ. Τελος εμφανιζει το
     * παραθυρο.
     */
    private void createCards(){
        
        GridLayout gl = new GridLayout(6,6);
        p3 = new JPanel(gl);
        p.add(p3, BorderLayout.CENTER);
        
        logic.hm = new HashMap<>();
        JLabel la[] = new JLabel[36];
        int j = 0;//Array index
        Card ca[] = new Card[36];
        MainGame.MouseHandler mh = new MainGame.MouseHandler();
        
        //Create cards
        for(int k = 0; k <= 2; k++){
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
            int k = rng.nextInt(36);
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
