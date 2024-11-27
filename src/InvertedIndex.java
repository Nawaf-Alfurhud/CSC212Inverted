public class InvertedIndex {
	private WordNode head;
	int totalWordCount;
	int uniqueWordCount;
	/*
	 * Basically links word to the doc id we found them in
	 */

	public InvertedIndex() {
		head = null;
	}

	/*
	 * kind of a linked list add for the DocumentIDNode method
	 */
	//Big-O: O(n * m)

	public void AddWord(String word, int docID) {
		totalWordCount++;
		if (head == null) {
			head = new WordNode(word);
			head.addDocumentID(docID);
			uniqueWordCount++;

		} else {
			WordNode current = head;
			while (current != null)
			//very important to see that we are doing current not current.next
				{
				if (current.getWord().equals(word)) {
					current.addDocumentID(docID);
					return;
				}
				if (current.getNext() == null)
					break;
				current = current.getNext();
			}
			WordNode newNode = new WordNode(word);
			newNode.addDocumentID(docID);
			current.setNext(newNode);
			uniqueWordCount++;

		}
	}
	

	/*
	 * return a list of document ids associated with a word
	 */
	//Big-O: O(n * m)
	public DocumentIDNode getDocuments(String word) {
		WordNode current = head;
		while (current != null) {
			if (current.getWord().equals(word)) {
				return current.getDocumentIDs();
			}
			current = current.getNext();
		}
		return null;
	}

	public WordNode getHead() {
		return head;
	}
	public int getTotalWordCount() {
	    return totalWordCount;
	}

	public int getUniqueWordCount() {
	    return uniqueWordCount;
	}
}