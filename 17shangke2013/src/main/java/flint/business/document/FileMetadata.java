package flint.business.document;

import kiss.storage.Item;
import entity.Document;

public class FileMetadata extends Item {

	private Document doc;

	public Document getDoc() {
		 return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

}
