public class WordNode {
	private String Word;
	private DocumentIDNode DocumentIDs;
	private WordNode Next;
	/*
	 * this is a node class in a linked list in the invertedindex class i know its a
	 * bit weird to include a linkedlist in an otherwise normal class but i think
	 * this is considered normal professionally and it also uses another node
	 * class(DocumentIDNode) as a linkedlist (i know its a unique representation )
	 */

	public WordNode(String word) {
		this.Word = word;
		this.DocumentIDs = null;
		this.Next = null;
	}

	public String getWord() {
		return Word;
	}

	public DocumentIDNode getDocumentIDs() {
		return DocumentIDs;
	}
	/*
	 * exactly like a normal linked list add method
	 * and also uses the NumberOfTimes variable we talked about before
	 * for its use look in the inverted index 
	 */

	//Big-O: O(n)
	public void addDocumentID(int docID) {
		if (DocumentIDs == null) {
			DocumentIDs = new DocumentIDNode(docID);
			DocumentIDs.incrementNumberOfTimes();

		} else {
			DocumentIDNode current = DocumentIDs;
			while (current != null) {
				if (current.getDocID() == docID) {
					current.incrementNumberOfTimes();

					return;
				}
				if (current.getNext() == null)
					break;
				current = current.getNext();
			}
			DocumentIDNode newNode = new DocumentIDNode(docID);
			newNode.incrementNumberOfTimes();
			current.setNext(newNode);

		}
	}

	public WordNode getNext() {
		return Next;
	}

	public void setNext(WordNode next) {
		this.Next = next;
	}
}