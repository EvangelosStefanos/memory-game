package memorygameguithreaded;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Η κλαση δημιουργει ενα παραθυρο στο οποιο τοποθετειται ενα πανελ με δυο κουμπια.
 * Το πρωτο με όνομα start game αλλαζει το πανελ του παραθυρου με ενα αλλο. 
 * Σε αυτο περιεχεται μια σειρα απο επιλογες τις οποιες ο χρηστης καλειται να 
 * τροποποιηση πριν ξεκινησει το παιχνιδι ενω το δευτερο κουμπι με ονομα exit 
 * τερματιζει την εφαρμογη.
 * 
 * @author Steve
 */
public class IntroSettings extends BaseGui{
    
    private JFrame f;
    //Intro
    private JPanel p;
    private JPanel p1;
    
    private JButton b1;
    private JButton b2;
    
    //Settings
    private JPanel p2;
    
    private JComboBox cbxgm;
    private JComboBox cbxplayerN;
    private JComboBox cbxdif2;
    private JComboBox cbxdif3;
    private JComboBox cbxdif4;
    
    private JCheckBox chbP;
    private JCheckBox chbNR;
    private JCheckBox chbCOS;
    private JCheckBox chbCSP;
    
    private JRadioButton rbNrml;
    private JRadioButton rbDbl;
    private JRadioButton rbTr;
    private JRadioButton rbQrt;
    private JRadioButton rbDuel;

    private JButton sg;
    
    
    /**
     * Η κλαση αποθηκευει πληροφοριες σε μορφη πεδιων int και boolean.
     * Οι τιμες τους αναθετονται στους handlers του δευτερου πανελ.
     */
    public class Stats{
        
        protected int playerN;
        protected int dif2;
        protected int dif3;
        protected int dif4;
        
        protected boolean pass;
        protected boolean nr;
        protected boolean cos;
        protected boolean csp;
        protected int gt;
        
        /**
         * Κατασκευαστης
         */
        public Stats(){
            playerN = 0;
            dif2 = 0;
            dif3 = 0;
            dif4 = 0;
        
            pass = false;
            nr = false;
            cos = false;
            csp = false;
            gt = 1;
        }
    }
    
    Stats st;
    
    /**
     * Κατασκευαστης
     * Δημιουργει και εμφανιζει το κυριως παραθυρο με το πρωτο πανελ.
     */
    public IntroSettings(){
        
        st = new Stats();
        ButtonHandler h = new ButtonHandler();
        
        f = createFrame("Intro",450,450);
        p1 = createIntro(h);
        p2 = createSettings(h);
        
        update(f);
    }
    
    /**
     * Δημιουργει το πρωτο πανελ.
     * 
     * @param h Ο actionlistener των κουμπιων
     * @return JPanel - Το πρωτο πανελ με τα δυο κουμπια.
     */
    private JPanel createIntro(ActionListener h){
        
        GridLayout gl = new GridLayout(5,3);
        BorderLayout bl = new BorderLayout();
        
        p = createPanel(gl);
        p1 = createPanel(bl);
        
        addtoPanel(new JLabel("Start a new game :",JLabel.CENTER),p);
        
        b1 = createButton("New Game",h);
        addtoPanel(b1,p);
        
        addtoPanel(new JLabel("Close Application :",JLabel.CENTER),p);
        
        b2 = createButton("Exit",h);
        addtoPanel(b2,p);
        
        addtoPanel(new JLabel(),p);
        
        p1.add(p,BorderLayout.CENTER);
        p1.add(new JLabel("             "),BorderLayout.EAST);
        p1.add(new JLabel("             "),BorderLayout.WEST);
        
        addtoFrame(p1,f);
        
        return p1;
    }
    
    /**
     * Δημιουργει το δευτερο πανελ με τις επιλογες.
     * 
     * @param h Ο actionlistener για τα κουμπια του πανελ.
     * @return JPanel - Το δευτερο πανελ με τις επιλογες.
     */
    private JPanel createSettings(ActionListener h){
        // 2nd Window
        
        //Set Layout
        p2 = createPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.weighty = 0.1;
        gbc.weightx = 0.5;
        
        //Set Labels Left
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,40,0,0);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        p2.add(new JLabel("Select Gamemode :"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        p2.add(new JLabel("Select number of players :"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        p2.add(new JLabel("Player 2 difficulty :"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        p2.add(new JLabel("Player 3 difficulty :"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        p2.add(new JLabel("Player 4 difficulty :"), gbc);
        
        //Separator
        
        gbc.insets = new Insets(20,40,20,40);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 5;
        p2.add(new JLabel("Select Features :"),gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0,40,0,0);
        
        //Set CheckBoxes Left
        CheckBoxHandler ch = new CheckBoxHandler();
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        chbP = new JCheckBox("Pass");
        chbP.addItemListener(ch);
        p2.add(chbP, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        chbNR = new JCheckBox("No Replay");
        chbNR.addItemListener(ch);
        p2.add(chbNR, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        chbCOS = new JCheckBox("Card Opening Sequence");
        chbCOS.addItemListener(ch);
        p2.add(chbCOS, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        chbCSP = new JCheckBox("Card Switch Position");
        chbCSP.addItemListener(ch);
        p2.add(chbCSP, gbc);
        
        
        //Set ComboBoxes Right
        ComboBoxHandler c = new ComboBoxHandler();
        String[] gm = {"Singleplayer", "Multiplayer"};
        String[] playerN = {"2", "3", "4"};
        String[] dif = {"Easy", "Normal", "Hard"};
        
        gbc.insets = new Insets(0,0,0,40);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        cbxgm = createBox(gm,c);
        p2.add(cbxgm,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        cbxplayerN = createBox(playerN,c);
        p2.add(cbxplayerN,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        cbxdif2 = createBox(dif,c);
        p2.add(cbxdif2,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        cbxdif3 = createBox(dif,c);
        p2.add(cbxdif3,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        cbxdif4 = createBox(dif,c);
        p2.add(cbxdif4,gbc);
        
        
        //Set RadioButtons Right
        RadioHandler rh = new RadioHandler();
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        rbNrml = new JRadioButton("Normal Game");
        rbNrml.addActionListener(rh);
        rbNrml.setSelected(true);
        p2.add(rbNrml, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 7;
        rbDbl = new JRadioButton("Double Game");
        rbDbl.addActionListener(rh);
        p2.add(rbDbl, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 8;
        rbTr = new JRadioButton("Trio");
        rbTr.addActionListener(rh);
        p2.add(rbTr, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 9;
        rbQrt = new JRadioButton("Quartet");
        rbQrt.addActionListener(rh);
        p2.add(rbQrt, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 10;
        rbDuel = new JRadioButton("Duel");
        rbDuel.addActionListener(rh);
        p2.add(rbDuel, gbc);
        
        //Add to ButtonGroup
        ButtonGroup bg = new ButtonGroup();
        
        bg.add(rbNrml);
        bg.add(rbDbl);
        bg.add(rbTr);
        bg.add(rbQrt);
        bg.add(rbDuel);
        
        
        //StartGame Button
        gbc.gridx = 1;
        gbc.gridy = 11;
        sg = createButton("Start Game",h);
        p2.add(sg,gbc);
        
        //Initialize Settings Panel
        cbxgm.setSelectedIndex(0);
        
        return p2;
    }
    
    
    /**
     * Handler για τα κουμπια του πρωτου και του δευτερου πανελ.
     */
    private class ButtonHandler implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            if(e.getSource() == b1){//new game button
                f.remove(p1);
                f.add(p2);
                update(f);
                
            }
            else if(e.getSource() == b2){//exit button
                System.exit(0);
                
            }
            else if(e.getSource() == sg){//start game button
                
                if(!chbP.isEnabled()){
                    st.pass = false;
                }
                if(!chbNR.isEnabled()){
                    st.nr = false;
                }
                if(!chbCSP.isEnabled()){
                    st.csp = false;
                }
                if(!chbCOS.isEnabled()){
                    st.csp = false;
                }
                
                switch(st.gt){
                    case 1 :
                        NormalGame ng = new NormalGame(st);
                        break;
                    case 2 :
                        DoubleGame dg = new DoubleGame(st);
                        break;
                    case 3 :
                        Trio tr = new Trio(st);
                        break;
                    case 4 :
                        Quartet qt = new Quartet(st);
                        break;
                    case 5 :
                        Duel dl = new Duel(st);
                        break;
                    default :
                        System.out.println("Error in StartGame button.");
                        break;
                }
                
                f.setVisible(false);
                f.dispose();
            }
        }
    }
    
    /**
     * Handler για τα radiobuttons του δευτερου πανελ.
     * Aπενεργοποιει/ενεργοποιει τα checkboxes αναλογα με τους κανονες 
     * συμβατοτητας και τις επιλογες του χρηστη.
     */
    private class RadioHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == rbNrml){
                st.gt = 1;
                chbP.setEnabled(true);
                chbNR.setEnabled(true);
                chbCSP.setEnabled(true);
                chbCOS.setEnabled(true);
            }
            else if(e.getSource() == rbDbl){
                st.gt = 2;
                chbP.setEnabled(true);
                chbNR.setEnabled(true);
                chbCSP.setEnabled(true);
                chbCOS.setEnabled(true);
            }
            else if(e.getSource() == rbTr){
                st.gt = 3;
                chbP.setEnabled(true);
                chbNR.setEnabled(true);
                chbCSP.setEnabled(false);
                chbCOS.setEnabled(true);
            }
            else if(e.getSource() == rbQrt){
                st.gt = 4;
                chbP.setEnabled(true);
                chbNR.setEnabled(true);
                chbCSP.setEnabled(false);
                chbCOS.setEnabled(true);
            }
            else if(e.getSource() == rbDuel){
                st.gt = 5;
                chbP.setEnabled(false);
                chbNR.setEnabled(false);
                chbCSP.setEnabled(false);
                chbCOS.setEnabled(true);
            }
        }
    }
    
    /**
     * Handler για τα comboboxes του δευτερου πανελ.
     * Αναλογα με τις επιλογες ενεργοποιει/απενεργοποιει τα αλλα components.
     */
    private class ComboBoxHandler implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            if(e.getSource() == cbxgm){    
                JComboBox cb = (JComboBox)e.getSource();
                String gm = (String)cb.getSelectedItem();
                switch(gm){
                        case "Singleplayer" :
                            cbxplayerN.setEnabled(false);
                            cbxdif2.setEnabled(false);
                            cbxdif3.setEnabled(false);
                            cbxdif4.setEnabled(false);
                            chbP.setEnabled(false);
                            chbNR.setEnabled(false);
                            if(rbDuel.isEnabled()){
                            rbNrml.setSelected(true);
                            }
                            rbDuel.setEnabled(false);
                            st.playerN = 0;
                            break;
                        case "Multiplayer" :
                            cbxplayerN.setEnabled(true);
                            rbDuel.setEnabled(true);
                            cbxplayerN.setSelectedIndex(0);
                            st.playerN = 1;
                            break;
                        default :
                            System.out.println("Error at cbxgm");
                            break;
                }
            }
            else if(e.getSource() == cbxplayerN){
                JComboBox cb = (JComboBox)e.getSource();
                String gm = (String)cb.getSelectedItem();
                switch(gm){
                    case "2" :
                        cbxdif2.setEnabled(true);
                        cbxdif3.setEnabled(false);
                        cbxdif4.setEnabled(false);
                        chbP.setEnabled(true);
                        chbNR.setEnabled(true);
                        rbDuel.setEnabled(true);
                        st.playerN = 1;
                        break;
                    case "3" :
                        cbxdif2.setEnabled(true);
                        cbxdif3.setEnabled(true);
                        cbxdif4.setEnabled(false);
                        chbP.setEnabled(true);
                        chbNR.setEnabled(true);
                        if(rbDuel.isEnabled()){
                            rbNrml.setSelected(true);
                        }
                        rbDuel.setEnabled(false);
                        st.playerN = 2;
                        break;
                    case "4" :
                        cbxdif2.setEnabled(true);
                        cbxdif3.setEnabled(true);
                        cbxdif4.setEnabled(true);
                        chbP.setEnabled(true);
                        chbNR.setEnabled(true);
                        if(rbDuel.isEnabled()){
                            rbNrml.setSelected(true);
                        }
                        rbDuel.setEnabled(false);
                        st.playerN = 3;
                        break;
                    default :
                        System.out.println("Error at cbxplayerN");
                        break;
                }
            }
            else if(e.getSource() == cbxdif2){
                JComboBox cb = (JComboBox)e.getSource();
                String dift2 = (String)cb.getSelectedItem();
                switch(dift2){
                    case "Easy" :
                        st.dif2 = 0;
                        break;
                    case "Normal" :
                        st.dif2 = 1;
                        break;
                    case "Hard" :
                        st.dif2 = 2;
                        break;
                    default :
                        System.out.println("Error at dif2");
                        break;
                }
            }
            else if(e.getSource() == cbxdif3){
                JComboBox cb = (JComboBox)e.getSource();
                String dift3 = (String)cb.getSelectedItem();
                switch(dift3){
                    case "Easy" :
                        st.dif3 = 0;
                        break;
                    case "Normal" :
                        st.dif3 = 1;
                        break;
                    case "Hard" :
                        st.dif3 = 2;
                        break;
                    default :
                        System.out.println("Error at dif3");
                        break;
                }
            }
            else if(e.getSource() == cbxdif4){
                JComboBox cb = (JComboBox)e.getSource();
                String dift4 = (String)cb.getSelectedItem();
                switch(dift4){
                    case "Easy" :
                        st.dif4 = 0;
                        break;
                    case "Normal" :
                        st.dif4 = 1;
                        break;
                    case "Hard" :
                        st.dif4 = 2;
                        break;
                    default :
                        System.out.println("Error at dif4");
                        break;
                }
            }
        }
    }
    
    /**
     * Handler για τα checkboxes του δευτερου πανελ.
     * Αναθεση τιμων αντικειμενο της Stats.
     */
    private class CheckBoxHandler implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e){
            if(e.getItemSelectable() == chbP){
                if(st.pass == false){
                    st.pass = true;
                }
                else{
                    st.pass = false;
                }
            }
            else if(e.getItemSelectable() == chbNR){
                if(st.nr == false){
                    st.nr = true;
                }
                else{
                    st.nr = false;
                }
            }
            else if(e.getItemSelectable() == chbCOS){
                if(st.cos == false){
                    st.cos = true;
                }
                else{
                    st.cos = false;
                }
            }
            else if(e.getItemSelectable() == chbCSP){
                if(st.csp == false){
                    st.csp = true;
                }
                else{
                    st.csp = false;
                }
            } 
        }
    }
    
}
