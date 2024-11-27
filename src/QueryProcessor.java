public class QueryProcessor {
        
	private InvertedIndex InvertedIndex;
	private DocumentProcessor DocumentProcessor;
	/*
	 * good luck trying to understand this mess 
	 * i barely got this working after days of trying 
	 * i will try explaining to the best of my ability
	 */
	
        
	public QueryProcessor(InvertedIndex invertedIndex, DocumentProcessor documentProcessor) {
		this.InvertedIndex = invertedIndex;
		this.DocumentProcessor = documentProcessor;
	}
	

	/*
	 * this takes a sentence from the user and splits it if it has 
	 * more than one word and also uses the ANDORPRESEDNECE method
	 */
	//Big-O: O(n * m)

	public DocumentIDNode ProcessANDOR(String query) {
		
		String[] words = query.split("\\s+");

		
		return ANDORPRESEDENCE(words);
	}

	/*
	 * notice that we use a tail here
	 * because its way better as its big O(1) for adding at the end of the list
	 * and uses the addtoresult method below 
	 */
	//Big-O: O(n * m)
	private DocumentIDNode GetAllDocuments() {
		DocumentIDNode resultHead = null, resultTail = null;
		for (int i = 0; i < DocumentProcessor.getDocumentList().getSize(); i++) {
			int docID = DocumentProcessor.getDocumentList().getDocument(i).getDocumentId();
			resultHead = AddToResult(resultHead, resultTail, docID);
			if (resultTail == null) {
				resultTail = resultHead; 
			} else {
				resultTail = resultTail.getNext();
			}
		}
		return resultHead;
	}
	

	/*
	 *checks if a its not excluded then adds it 
	 */
	//Big-O: O(n * m)
	private DocumentIDNode NOT(DocumentIDNode TotalDocs, DocumentIDNode ExcludedDocs) {
		DocumentIDNode resultHead = null, resultTail = null;
		DocumentIDNode currentTotal = TotalDocs;
		while (currentTotal != null) {
			boolean Excluded = false;
			DocumentIDNode CurrentExcluded = ExcludedDocs;
			while (CurrentExcluded != null) {
				if (currentTotal.getDocID() == CurrentExcluded.getDocID()) {
					Excluded = true;
					break;
				}
				CurrentExcluded = CurrentExcluded.getNext();
			}
			if (!Excluded) {
				resultHead = AddToResult(resultHead, resultTail, currentTotal.getDocID());
				if (resultTail == null) {
					resultTail = resultHead;
				} else {
					resultTail = resultTail.getNext();
				}
			}
			currentTotal = currentTotal.getNext();
		}
		return resultHead;
	}

	/*
	 * Basically makes the AND part of a query always processed first 
	 */
	//Big-O: O(n * m)
	private DocumentIDNode ANDORPRESEDENCE(String[] query) {
		DocumentIDNode Result = null; 
		DocumentIDNode ANDResult = null;
		boolean WaitingOr = false; 

		for (int i = 0; i < query.length; i++) {
			String term = query[i];

			if (!term.equals("AND") && !term.equals("OR") && !term.equals("NOT"))
			//just checks if the term is not a part of the processing queries and just a normal word
			{

				DocumentIDNode CurrentList = GetDocumentsIDForWord(term);

				
				if (i > 0 && query[i-1].equals("NOT")) {
					DocumentIDNode AllDocs = GetAllDocuments();
					CurrentList = NOT(AllDocs, CurrentList);
				}

				if (ANDResult == null) {
				
					ANDResult = CurrentList;
				} else if (i > 0 && query[i-1].equals("AND")) {
				
					ANDResult = Intersection(ANDResult, CurrentList);
				}

		
				if (i > 0 && query[i-1].equals("OR")) {
					if (Result == null) {
						Result = ANDResult; 
					} else {
						Result = Union(Result, ANDResult); 
					}
					ANDResult = CurrentList; 
					WaitingOr = true;
				}
			}
		}

	
		if (ANDResult != null) {
			if (Result == null || !WaitingOr) {
				Result = ANDResult;
			} else {
				Result = Union(Result, ANDResult);
			}
		}

		return Result;
	}

	//Big-O: O(n)
	private DocumentIDNode GetDocumentsIDForWord(String word) {
		return InvertedIndex.getDocuments(word); 
	}

	//Big-O: O(n*(L1 + L2))
	private DocumentIDNode Intersection(DocumentIDNode list1, DocumentIDNode list2) {
		DocumentIDNode ResultHead = null, ResultTail = null;

		while (list1 != null && list2 != null) {
			if (list1.getDocID() == list2.getDocID()) {
				ResultHead = AddToResult(ResultHead, ResultTail, list1.getDocID());
				if (ResultTail == null) {
					ResultTail = ResultHead; 
				} else {
					ResultTail = ResultTail.getNext();
				}
				list1 = list1.getNext();
				list2 = list2.getNext();
			} else if (list1.getDocID() < list2.getDocID()) {
				list1 = list1.getNext();
			} else {
				list2 = list2.getNext();
			}
		}

		return ResultHead;
	}

	

	//Big-O: O(L1 + L2)
	private DocumentIDNode Union(DocumentIDNode list1, DocumentIDNode list2) {
		DocumentIDNode ResultHead = null, ResultTail = null;

	
		while (list1 != null) {
			if (ResultHead == null) {
				ResultHead = new DocumentIDNode(list1.getDocID());
				ResultTail = ResultHead;
			} else {
				ResultTail.setNext(new DocumentIDNode(list1.getDocID()));
				ResultTail = ResultTail.getNext();
			}
			list1 = list1.getNext();
		}

	
		while (list2 != null) {
			boolean isDuplicate = false;
			DocumentIDNode current = ResultHead;

			while (current != null) {
				if (current.getDocID() == list2.getDocID()) {
					isDuplicate = true;
					break;
				}
				current = current.getNext();
			}

			if (!isDuplicate) {
				ResultTail.setNext(new DocumentIDNode(list2.getDocID()));
				ResultTail = ResultTail.getNext();
			}

			list2 = list2.getNext();
		}
		

		return ResultHead;
	}


	//Big-O: O(n)
	private DocumentIDNode AddToResult(DocumentIDNode head, DocumentIDNode tail, int docID) {
	
		DocumentIDNode current = head;
		while (current != null) {
			if (current.getDocID() == docID) {
				return head; 
			}
			current = current.getNext();
		}

	
		DocumentIDNode newNode = new DocumentIDNode(docID);

	
		if (head == null) {
			head = newNode; 
			tail = newNode; 
		} else {
			if (tail != null) {
				tail.setNext(newNode); 
			}
			tail = newNode; 
		}

		return head; 
	}

	//Big-O: O(n * m)
	public NumNode RankedRetrieval(String query) {
		NumNode scoreHead = null;

		String[] terms = query.toLowerCase().split("\\s+");

		for (String term : terms) {
			DocumentIDNode docNode = InvertedIndex.getDocuments(term);

			while (docNode != null) {
				int docID = docNode.getDocID();
				int termFrequency = docNode.getNumberOfTimes();

				scoreHead = AddScore(scoreHead, docID, termFrequency);

				docNode = docNode.getNext();
			}
		}

	
		return SortByScoreAscending(scoreHead);
	}

	//Big-O: O(n)
	private NumNode AddScore(NumNode head, int docID, int scoreToAdd) {
		NumNode current = head, previous = null;

		while (current != null) {
			if (current.getDocID() == docID) {
				current.setScore(current.getScore() + scoreToAdd); 
				return head;
			}
			previous = current;
			current = current.getNext();
		}

		
		NumNode newNode = new NumNode(docID, scoreToAdd);
		if (previous == null) {
			return newNode; 
		} else {
			previous.setNext(newNode);
		}
		return head;
	}

	//Big-O: O(n^2)
	private NumNode SortByScoreAscending(NumNode head) {
		if (head == null || head.getNext() == null) {
			return head; 
		}

	
		NumNode sorted = null; 

		while (head != null) {
			
			NumNode minNode = head;
			NumNode minPrev = null;
			NumNode current = head;
			NumNode prev = null;

			while (current != null) {
				if (current.getScore() < minNode.getScore()) { 
					minNode = current;
					minPrev = prev;
				}
				prev = current;
				current = current.getNext();
			}

			
			if (minPrev != null) {
				minPrev.setNext(minNode.getNext());
			} else {
				head = head.getNext(); 
			}

			
			minNode.setNext(sorted);
			sorted = minNode;
		}

		return sorted; 
	}


}