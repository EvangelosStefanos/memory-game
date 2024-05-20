package memorygameguithreaded;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import memorygameguithreaded.IntroSettings.Stats;

/**
 * Abstract κλαση που κληροδοτει κωδικα. Δημιουργει τους παικτες και οριζει τι
 * θα κανει ο καθε παικτης οταν ερθει η σειρα του.
 * @author Steve
 */
public abstract class Logic {
    
    protected Player pa[];                 //Πινακας με ολους τους παικτες
    protected int scores[];                //Πινακας με το σκορ των παικτων
    protected HashMap<JLabel, Card> hm;    //HashMap με ολα τα ζευγαρια jlabel-card
    protected ArrayList<JLabel> openLabels;//ArrayList με jlabels των οποιων οι καρτες ειναι ανοιχτες
    protected int limit;                   //Σχετιζεται με το τριο και το κουαρτετο και μια προσπαθεια για γενικευση του κωδικα που δεν πετυχε
    protected boolean noreplay;            //Κατασταση επαναληψης σειρας μετα απο επιτυχη συγκριση (True = με, false = χωρις)
    protected boolean csp;                 //Κατασταση της εναλλαγης καρτων(True = με, false = χωρις)
    protected boolean cos;                 //Κατασταση ανοιγματος καρτων σε σειρα(True = με, false = χωρις)
    protected int openSeq;                 //Ποιο ζευγαρι ειναι το επομενο που πρεπει να ανοιχτει
    protected int steps;
    
    /**
     * Κατασκευαστης
     * Αρχικοποιει τα πεδια τις κλασης και δημιουργει τους παικτες.
     * 
     * @param st Αντικειμενο με τις επιλογες του χρηστη
     */
    public Logic(Stats st){
        createPlayers(st);
        
        hm = new HashMap<>();
        openLabels = new ArrayList<>();
        noreplay = st.nr;
        csp = st.csp;
        cos = st.cos;
        if(st.cos){
            openSeq = 0;
        }
        
        switch(st.gt){
            case 1 :
            case 2 :
            case 5 :
                limit = 2;
                break;
            case 3 :
                limit = 3;
                break;
            case 4 :
                limit = 4;
                break;
            default :
                System.out.println("Error in switch->limit");
                break;
        }
    }
    
    /**
     * Δημιουργει τους παικτες και τα σκορ τους.
     * 
     * @param st Αντικειμενο με τις επιλογες του χρηστη
     */
    private void createPlayers(Stats st){
        
        pa = new Player[st.playerN+1];
        scores = new int[st.playerN+1];
        
        for(int i = 0; i <= st.playerN; i++){
            if(i == 0){
                pa[i] = new HumanPlayer("Player 1", i);
                scores[i] = 0;
                steps = 0;
            }
            else{
                
                int dif = 0;
                
                //Get current players difficulty
                switch(i){
                    case 1 :
                        dif = st.dif2;
                        break;
                    case 2 :
                        dif = st.dif3;
                        break;
                    case 3 :
                        dif = st.dif4;
                        break;
                    default : 
                        System.out.println("Error in createScores");
                        break;
                }
                
                //Init a new player based on his difficulty
                int j = i+1;
                switch(dif){
                case 0 :
                    pa[i] = new EasyPlayer("Player " + j, i);
                    scores[i] = 0;
                    break;
                case 1 :
                    pa[i] = new NormalPlayer("Player " + j, i);
                    scores[i] = 0;
                    break;
                case 2 :
                    pa[i] = new HardPlayer("Player " + j, i);
                    scores[i] = 0;
                    break;
                default :
                    System.out.println("Error in createScores.");
                    break;
                }
            }
        }
    }
    
    protected abstract void compareCards(int k, ArrayList<JLabel> ol);
    
    /**
     * Ανοιγει τυχαια μια καρτα και το ζευγαρι της αν εχει αποθηκευτει ή δυο
     * τυχαιες καρτες αν δεν εχει. Τις αποθηκευει για τον καθε παικτη και τις συγκρινει.
     * 
     * @param k Η ταυτοτητα του παικτη του οποιου ειναι η σειρα
     */
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
        
        for(int i = 0; i < limit; i++){
            JLabel l = list.get(rng.nextInt(list.size()));
            while(openLabels.contains(l)){
                l = list.get(rng.nextInt(list.size()));
            }
            
            openSesami(l);
            if(openLabels.size() >= limit){
                break;
            }
            JLabel l2 = aip.rememberCard(l, hm);

            if(l2 != null){
                
                openSesami(l2);
                if(openLabels.size() >= limit){
                    break;
                }
            }
        }
        saveAll();
        compareCards(k, openLabels);
    }
    
    /**
     * Ανοιγει μια καρτα και αποθηκευει την αντιστοιχη της jlabel στην openLabels.
     * 
     * @param l Η αντιστοιχη jlabel της καρτας που θα ανοιχτει
     */
    protected synchronized void openSesami(JLabel l){
        Card c = hm.get(l);
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                c.openCard(l);
            }
        });
        if(!openLabels.contains(l)){
            openLabels.add(l);
            if(pa.length == 1){
                steps++;
            }
        }
    }
    
    /**
     * Αποθηκευει τις εκαστοτε ανοιχτες καρτες για ολους τους παικτες.
     */
    protected synchronized void saveAll(){
        for (Player p : pa){
                                
            if(p instanceof AiPlayer){
                AiPlayer aip = (AiPlayer) p;
                aip.saveCard(openLabels, hm, csp);
            }
        }
    }
    
    /**
     * Ξεχναει τις καρτες των οποιων τα ζευγαρια βρεθηκαν.
     */
    protected synchronized void forgetAll(){
        for (Player p : pa){
                                
            if(p instanceof AiPlayer){
                AiPlayer aip = (AiPlayer) p;
                aip.forgetCards(openLabels, hm);
            }
        }
    }
    
}
