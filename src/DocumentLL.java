public class DocumentLL {
    private DocumentNode Head;
    private int Size;
    /*
     * just a normal linked list class
     * with a getDocument method which find 
     * a document based on a given index(id number)
     */

    public DocumentLL() {
        Head = null;
        Size = 0;
    }

  //Big-O: O(n)
    public void addDocument(Document doc) {
        DocumentNode newNode = new DocumentNode(doc);
        if (Head == null) {
            Head = newNode;
        } else {
            DocumentNode current = Head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        Size++;
    }

    public int getSize() {
        return Size;
    }

  //Big-O: O(n)
    public Document getDocument(int index) {
        if (index < 0 || index >= Size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        DocumentNode current = Head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getDocument();
    }
}