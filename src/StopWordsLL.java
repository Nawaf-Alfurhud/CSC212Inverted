import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StopWordsLL {
    private StopWordNode Head;
    /*
     * a linked list representing stop words
     */

    public StopWordsLL() {
        Head = null;
    }
    

    /*
     * loads the stop words and adds them using the below method
     */

  //Big-O: O(n)
    public void LoadStopWords(String filePath) {
        try (BufferedReader a = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = a.readLine()) != null) {
                AddStopWord(line.trim().toLowerCase());//we dont need to make them lowercase,but its more professional 
            }
        } catch (IOException e) {
            System.out.println("Error reading stop words file: " + e.getMessage());
        }
    }
    /*
     * adds a word using the normal linked list method
     */


  //Big-O: O(n)
    private void AddStopWord(String word) {
        StopWordNode newNode = new StopWordNode(word);
        if (Head == null) {
            Head = newNode;
        } else {
            StopWordNode current = Head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }
    /*
     * a check if a word belongs to this class,useful later
     */


    //Big-O: O(n)
    public boolean IsStopWord(String word) {
        StopWordNode current = Head;
        while (current != null) {
            if (current.getWord().equals(word)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }
}