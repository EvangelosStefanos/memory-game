package memorygameguithreaded;

/**
 * Abstract κλαση με μονο σκοπο την κρηροδοτηση κωδικα. Αναθετει σε καθε παικτη
 * ενα ονομα και εναν ακεραιο.
 * @author Steve
 */
public abstract class Player {
    
    private String name;
    private int id;
    
    /**
     * Κατασκευαστης
     * @param n Το ονομα του παικτη.
     * @param i Η σειρα με την οποια δημιουργηθηκε ο παικτης ή αλλιως η θεση του
     * στον πινακα παικτων(0-3).
     */
    public Player(String n, int i){
        name = n;
        id = i;
    }
    
    /**
     * Επιστρεφει το id του παικτη.
     * @return Int - Την ταυτοτητα του παικτη.
     */
    public int getID(){
        return id;
    }
    
    /**
     * Επιστρεφει το ονομα του παικτη.
     * @return String - Το ονομα του παικτη.
     */
    public String getName(){
        return name;
    }
}
