public class DocumentIDNode {
    private int DocID; 
    private DocumentIDNode Next; 
    private int NumberOfTimes;
    /*
     *this is a node class for a method in the 
     *InvertedIndex class 
     *and it has a NumberOfTimes variable which 
     *we will use later 
     */

    public DocumentIDNode(int docID) {
        this.DocID = docID;
        this.Next = null;
    }

    public int getDocID() {
        return DocID;
    }

    public DocumentIDNode getNext() {
        return Next;
    }

    public void setNext(DocumentIDNode next) {
        this.Next = next;
    }
    public int getNumberOfTimes() {
		return NumberOfTimes;
	}

	public void incrementNumberOfTimes() {
		this.NumberOfTimes++;
	}
}