public class NumNode {
    private int DocID;
    private int Score;
    private NumNode Next;
    /*
     * classic node class 
     * useful for ranked retrieval in the queryprocessor class 
     * 
     */

    public NumNode(int docID, int score) {
        this.DocID = docID;
        this.Score = score;
        this.Next = null;
    }

    public int getDocID() {
        return DocID;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        this.Score = score;
    }

    public NumNode getNext() {
        return Next;
    }

    public void setNext(NumNode next) {
        this.Next = next;
    }
}