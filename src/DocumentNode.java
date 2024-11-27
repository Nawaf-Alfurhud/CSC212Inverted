public class DocumentNode {
	private Document Data;
	private DocumentNode Next;
	/*
	 * this is the node for the Document class
	 * which will be used in the DocumentLL(Linked List)
	 */
	

	public DocumentNode(Document document) {
		this.Data = document;
		this.Next = null;
	}

	public Document getDocument() {
		return Data;
	}

	public DocumentNode getNext() {
		return Next;
	}

	public void setNext(DocumentNode next) {
		this.Next = next;
	}
	
}