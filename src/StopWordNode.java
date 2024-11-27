public class StopWordNode {
    private String Word;
    private StopWordNode Next;
    /*
     * a node for StopWordsLL
     */

    public StopWordNode(String word) {
        this.Word = word;
        this.Next = null;
    }

    public String getWord() {
        return Word;
    }

    public StopWordNode getNext() {
        return Next;
    }

    public void setNext(StopWordNode next) {
        this.Next = next;
    }
}