import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DocumentProcessor {
	private DocumentLL DocumentList;
	private StopWordsLL StopWords;
	/*
	 * this is complicated class so ill explain it method by method in short 1-it
	 * loads a document 2-filters words 3-builds the inverted index
	 */

	//Big-O: O(n)
	public DocumentProcessor(String stopWordsFilePath) {
		DocumentList = new DocumentLL();
		StopWords = new StopWordsLL();
		StopWords.LoadStopWords(stopWordsFilePath);// go to class stopwordsLL
	}

	//Big-O: O(n * m)
	private String[] Filtering(String content) 
	/*
	 * take a sentence,splits it 
	 * handles all punctuation marks and then adds it to an array called filtered words
	 * 
	 */
	{
		String[] RawWords = content.toLowerCase().split("\\s+");
		String[] FilteredWords = new String[RawWords.length];
		int count = 0;

		for (String word : RawWords) {
			word = word.replaceAll("[^a-zA-Z0-9]", "");

			if (!StopWords.IsStopWord(word) && !word.isEmpty()) {
				FilteredWords[count++] = word;
			}
		}

		String[] result = new String[count];
		System.arraycopy(FilteredWords, 0, result, 0, count);
		return result;
	}

	//Big-O: O(n * m * a)
	public void LoadDocuments(String filePath) 
	/*
	 * reads the cvs files and skips the first line because it isnt useful 
	 * and stops reading when it encounters an empty line
	 * we have an array called parts which has 2 contents the first one
	 * is the id the second one is the string or the sentence 
	 */
	{
		try (BufferedReader a = new BufferedReader(new FileReader(filePath))) {
			String line;

			a.readLine(); // We are skipping the first line here

			while ((line = a.readLine()) != null) {
				line = line.trim();// for the "whitespace" of lines
				if (line.isEmpty())
					continue;
				
			
				String[] parts = line.split(",", 2);
				/*
				 * splits the line into two parts once it detects  
				 * the first ","
				 * the reason for the number 2 
				 * is that a sentence may have a lot of commas
				 * but we only want to split it once
				 * with the first comma being the one 
				 * that indicates the ending of the 
				 * doc id and the beginning of the string content
				 */

				try {
					int documentId = Integer.parseInt(parts[0].trim());
					/*
					 *converts the document id from a string
					 *(since it is handled this way)
					 *to a number using parseInt 
					 */
					String content = parts[1].trim();
					/*
					 * again just removing whitespace
					 */
					String[] Sentences = Filtering(content);

					Document doc = new Document(documentId, String.join(" ", Sentences));
					/*
					 * we use join since the Filtering method returns 
					 * an array of words,we could use another way to do this
					 * but its the most obvious solution 
					 */
					DocumentList.addDocument(doc);
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
        
	private InvertedIndex InvertedIndex = new InvertedIndex();
	

	//Big-O: O(n * m * a * b)

	public void BuildInvertedIndex() 
	/*
	 * goes through a loop of documents
	 * splits the strings into singular words
	 * then adds the word using its doc id
	 */
	{
		for (int i = 0; i < DocumentList.getSize(); i++) {
			Document doc = DocumentList.getDocument(i);
			String[] words = doc.getContent().split("\\s+");
			for (String word : words) {
				InvertedIndex.AddWord(word, doc.getDocumentId());
			}
		}
	}
        
	public InvertedIndex getInvertedIndex() {
		return InvertedIndex;
	}

	public DocumentLL getDocumentList() {
		return DocumentList;
	}
}