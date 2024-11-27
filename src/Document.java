public class Document {
    private int DocumentId;
    private String Content;
    /*
     * this will contain the string and the id number
     * of every single line in the cvs file
     */

    public Document(int documentId, String content) {
        this.DocumentId = documentId;
        this.Content = content;
    }

    public int getDocumentId() {
        return DocumentId;
    }

    public String getContent() {
        return Content;
    }
}