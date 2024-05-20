package memorygameguithreaded;

import java.util.HashMap;

/**
 * Αντιπροσωπευει τον παικτη με μνημη ελεφαντα(τα θυμαται ολα).
 * Ισχυουν οι μεθοδοι τις κλασης AiPlayer.
 * @author Steve
 */
public class HardPlayer extends AiPlayer{
    
    /**
     * Κατασκευαστης
     * @param s Το ονομα του παικτη.
     * @param i Η ταυτοτητα του(θεση στον πινακα παικτων).
     */
    public HardPlayer(String s, int i){
        super(s, i);
        phm = new HashMap<>();
    }
}
