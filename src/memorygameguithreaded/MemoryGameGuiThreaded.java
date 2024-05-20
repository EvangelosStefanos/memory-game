package memorygameguithreaded;

import javax.swing.SwingUtilities;

public class MemoryGameGuiThreaded {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
               IntroSettings i = new IntroSettings(); 
            }
        });
        
        
    }
    
}
