package memorygameguithreaded;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import memorygameguithreaded.IntroSettings.Stats;

/**
 * Abstract κλαση που κληροδοτει κωδικα. Δημιουργει το κυριος παραθυρο και φορτωνει
 * σε αυτο 4 πανελ και 1 κουμπι. Ακομα οριζει και δημιουργει την λογικη του καθε
 * παιχνιδιου.
 * 
 * @author Steve
 */
public abstract class MainGame extends BaseGui{
    
    protected JFrame f;//Frame
    
    protected JPanel p;//Base Panel
    protected JPanel p1;//Scores
    protected JPanel p2;//CardSequence
    protected JPanel p3;//CardDisplay
    
    protected JButton b;//Pass
    
    protected Logic logic;
    protected boolean input;
    
    
    /**
     * Κατασκευαστης
     * Δημιουργει και εμφανιζει το κυριος παραθυρο, το κυριος πανελ και την λογικη
     * 
     * @param st Αντικειμενο της Stats με τις επιλογες του χρηστη.
     */
    public MainGame(Stats st){
        
        BorderLayout bl = new BorderLayout();
        
        f = createFrame("Main Game");
        p = createPanel(bl);
        addtoFrame(p,f);
        
        Thread t5 = new Thread(){
            @Override
            public void run(){
                switch(st.gt){
                    case 1 :
                        logic = new LogicNormal(st);
                        break;
                    case 2 :
                        logic = new LogicNormal(st);
                        break;
                    case 3 :
                        logic = new LogicTrio(st);
                        break;
                    case 4 :
                        logic = new LogicQuartet(st);
                        break;
                    case 5 :
                        logic = new LogicDuel(st);
                        break;
                    default :
                        System.out.println("Error in logic.");
                }
            }
        };
        t5.start();
        try{
            t5.join();
        }
        catch(Exception e){
            
        }
        
        createScores(st.playerN);
        createTitle();
        createPass(st.pass);
        createCardSequence(st.cos, st.gt);
        
        input = true;
        f.pack();
        update(f);
    }
    
    
    /**
     * Δημιουργει ενα πανελ με jlabels στις οποιες αναγραφεται το σκορ του καθε
     * παικτη.
     * 
     * @param numofplayers 
     */
    private void createScores(int numofplayers){
        GridLayout gl = new GridLayout(1,0);
        p1 = createPanel(gl);
        p.add(p1,BorderLayout.SOUTH);
        
        for(int i = 0; i <= numofplayers; i++){
            int j = i + 1;
            p1.add(new JLabel(" Player " + j + " :  0 ", JLabel.CENTER));
        }
    }
    
    /**
     * Ενημερωνει το πανελ με τα σκορ των παικτων.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιυ ειναι η σειρα.
     */
    protected void updateScores(int k){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                p1.remove(k);
                p1.add(new JLabel(logic.pa[k].getName() + " :  " + logic.scores[k] + " ", JLabel.CENTER), k);
                p1.revalidate();
                p1.repaint();
            }
        });
    }
    
    /**
     * Δημιουργει ενα πανελ στο οποιο αναφραφεται το ονομα του παικτη του οποιου ειναι η σειρα.
     */
    private synchronized void createTitle(){
        if(p == null){
            System.out.println("p null");
        }
        p.add(new JLabel("Now Playing : Player 1",JLabel.CENTER),BorderLayout.NORTH);
    }
    
    /**
     * Ενημερωνει το πανελ στο οποιο αναφραφεται το ονομα του παικτη του οποιου ειναι η σειρα.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα.
     */
    protected synchronized void updateTitle(int k){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                BorderLayout bl =(BorderLayout) p.getLayout();
                if((bl.getLayoutComponent(BorderLayout.NORTH) != null)){
                    p.remove(bl.getLayoutComponent(BorderLayout.NORTH));
                }
                p.add(new JLabel("Now Playing : "+logic.pa[k].getName(),JLabel.CENTER),BorderLayout.NORTH); // Incomplete Function
                p.revalidate();
                p.repaint();
            }
        });
    }
    
    /**
     * Δημιυργει το κουμπι για πασο αν εχει ενεργοποιηθει απο τον χρηστη.
     * 
     * @param pass Η κατασταση του πασου(True = με πασο, false = χωρις).
     */
    private void createPass(boolean pass){
        if(pass == true){
            b = new JButton("Pass");
            PassHandler bh = new PassHandler();
            b.addActionListener(bh);
            p.add(b,BorderLayout.WEST);
        }
    }
    
    /**
     * Δημιυργει ενα πανελ στο οποιο αναγραφεται η σειρα με την οποια πρεπει να
     * ανοιχτουν οι καρτες.
     * 
     * @param cardSequence Κατασταση της σειρας ανοιγματος(True = με συγκεκριμενη σειρα, false = χωρις).
     * @param gametype Το ειδος του παιχνιδιου.
     */
    private void createCardSequence(boolean cardSequence, int gametype){
        if(cardSequence == true){
            GridLayout gl = new GridLayout(0,1);
            p2 = createPanel(gl);
            p.add(p2,BorderLayout.EAST);
            String[] s = {"Βίκος","Αύρα","Nestea","Amita Motion","Λούξ","Λεμονίτα","Πορτοκαλάδα","Sprite","Pepsi","Coca Cola",
                "Fix Hellas","Άλφα","Mythos","Amstel","Μαλαματίνα","Red Wine","White Wine","Smirnoff","Bacardi","Absolut Vodka",
                "Cutty Shark","Famous Grouse","Jack Daniels","Malibu"};
            int a = 1;
            if(gametype == 2){//Double Game
                a = 2;
            }
            for(int i = 1; i <= 12*a; i++){
                p2.add(new JLabel("  " + i + ". " + s[i-1]));
            }
        }
    }
    
    /**
     * Εκτελει την σειρα του καθε ελεγχομενου απ'τον υπολογιστη παικτη και
     * ενημερωνει σκορ και τιτλο.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιυ ειναι η σειρα.
     */
    protected synchronized void turnOrder(int k){
        
        if(logic.noreplay == false){
            
            for(Player p : logic.pa){
                
                if(logic.hm.isEmpty()){
                    
                    break;
                }
                do{
                    if(logic.hm.isEmpty()){
                        
                        break;
                    }
                    updateTitle(p.getID());
                    logic.nextTurn(p.getID());
                    updateScores(p.getID());
                }while(logic.noreplay);
            }
            logic.noreplay = false;//Restoring noreplay to its initial value. Each loop will have the same initial value.
        }
        else{
            for(Player p : logic.pa){
                
                if(logic.hm.isEmpty()){
                    
                    break;
                }
                updateTitle(p.getID());
                logic.nextTurn(p.getID());
                updateScores(p.getID());
            }
        }
        updateTitle(0);
        input = true;
        terminator(k);
    }
    
    /**
     * Αν υπαρχει εστω και μια καρτα ανοιχτη, την κλεινει και μεταφερει τον 
     * ελεγχο στον επομενο παικτη αλλιως δεν κανει τιποτα.
     */
    protected synchronized void pass(){
        if(logic.openLabels.isEmpty()){
                return;
            }
            for(JLabel l : logic.openLabels){
                Card x = logic.hm.get(l);
                x.closeCard(l);
                System.out.println("in for");
            }
            
            System.out.println("for done");
            logic.openLabels.clear();
            Thread t1 = new Thread(){
                @Override
                public void run(){
                    turnOrder(1);
                }
            };
            t1.start();
    }
    
    /**
     * Ελεγχει αν υπαρχουν αλλες jlabels στον HashMap hm. Αν δεν υπαρχουν εμφανιζει
     * το παραθυρο τελους, καταστρεφει το τωρινο παραθυρο και ξαναρχιζει το
     * παιχνιδι μολις πατησει το ok ο χρηστης.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα.
     */
    protected void terminator(int k){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                if(logic.hm.isEmpty()){

                    updateScores(k);
                    switch(logic.pa.length){
                        case 1 :
                            JOptionPane.showOptionDialog(f, "Game Over. You scored :\n"+logic.scores[0]+" points.\n"
                                    + "Steps taken : " +logic.steps ,
                            "Party Time", DEFAULT_OPTION, INFORMATION_MESSAGE, null, null, null);
                            break;
                        case 2 : 
                            JOptionPane.showOptionDialog(f, "Game Over. You scored :\n"+logic.scores[0]+" points.\n "
                            + "Player 2 scored : "+logic.scores[1]+" points.",
                            "Party Time", DEFAULT_OPTION, INFORMATION_MESSAGE, null, null, null);
                            break;
                        case 3 :
                            JOptionPane.showOptionDialog(f, "Game Over. You scored :\n"+logic.scores[0]+" points.\n "
                            + "Player 2 scored : "+logic.scores[1]+" points.\n Player 3 scored : "+logic.scores[2]+" points.\n ",
                            "Party Time", DEFAULT_OPTION, INFORMATION_MESSAGE, null, null, null);
                            break;
                        case 4 :
                            JOptionPane.showOptionDialog(f, "Game Over. You scored :\n"+logic.scores[0]+" points.\n "
                            + "Player 2 scored : "+logic.scores[1]+" points.\n Player 3 scored : "+logic.scores[2]+" points.\n "
                            + "Player 4 scored : "+logic.scores[3]+" points.", "Party Time", DEFAULT_OPTION, INFORMATION_MESSAGE, null, null, null);
                            break;
                        default : 
                            System.out.println("Error in terminator.");
                    }
                    IntroSettings is = new IntroSettings();
                    f.setVisible(false);
                    f.dispose();
                }
            }
        });
    }
    
    /**
     * Διαχειριζεται τα κλικς στα jlabels. Ανοιγει την καρτα, αν υπηρχε αλλη 
     * ανοιχτη τις συγκρινει και μεταφερει τον ελεγχο στον επομενο παικτη.
     * Αν η καρτες που ανοιχτηκαν ηταν οι τελευταιες τερματιζεται το τωρινο
     * παιχνιδι.
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
                        logic.openSesami(l);
                        if(logic.openLabels.size() == logic.limit){
                            input = false;
                            logic.saveAll();
                            logic.compareCards(0, logic.openLabels);
                            updateScores(0);
                            terminator(0);
                            if(!logic.noreplay){
                                turnOrder(1);
                            }
                            else{
                                input = true;
                            }
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
    
    /**
     * Καλει την pass().
     */
    private class PassHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            pass();
        }
    }
}
