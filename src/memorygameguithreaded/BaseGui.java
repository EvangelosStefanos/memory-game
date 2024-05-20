package memorygameguithreaded;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Εντελως προαιρετικη κλαση η οποια δεν σχετιζεται με το προτζεκτ αλλα στην
 * αρχη με βοηθησε επειδη εκανε τον κωδικα πιο ευαναγνωστο και συμπηκνωμενο.
 * 
 * Περιεχει μεθοδους που κανουν new διαφορα components(frames, panels, buttons κτλ),
 * τους αναθετουν listeners και μετα τα επιστρεφουν. Ακομα εχει δυο μεθοδους για
 * προσθεση component σε jpanel και jframe.
 * 
 * Η μεθοδος update(frame f) χρησημοποιηται αρκετα.
 * 
 * @author Steve
 */
public abstract class BaseGui{
    
    public BaseGui(){
        
    }
    
    protected JFrame createFrame(String s, int w, int h){
        JFrame f = new JFrame(s);
        f.setSize(w,h);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return f;
    }
    
    protected JFrame createFrame(String s){
        JFrame f = new JFrame(s);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return f;
    }
    
    protected void update(JFrame f){
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    protected JPanel createPanel(){
        JPanel p = new JPanel();
        return p;
    }
    
    protected JPanel createPanel(LayoutManager m){
        JPanel p = new JPanel(m);
        return p;
    }
    
    protected JLabel createLabel(){
        JLabel l = new JLabel();
        return l;
    }
    
    protected JLabel createLabel(String s){
        JLabel l = new JLabel(s);
        return l;
    }
    
    protected JButton createButton(String s){
        JButton b = new JButton(s);
        return b;
    }
    
    protected JButton createButton(String s, ActionListener h){
        JButton b = new JButton(s);
        b.addActionListener(h);
        return b;
    }
    
    protected JComboBox createBox(String[] s, ActionListener h){
        JComboBox box = new JComboBox(s);
        box.addActionListener(h);
        return box;
    }
    
    protected void addtoPanel(Component c, JPanel p){
        p.add(c);
    }
    
    protected void addtoFrame(Component c, JFrame f){
        f.add(c);
    }
    

}