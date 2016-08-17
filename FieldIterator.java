package IndexingP;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.lucene.document.FieldType;


//1. This class is used to iterate once over a data source, and to generate fields contents one by one.
//2. Returned values are all strings, and the selection of field type fields is done on client side.
//3. "Document" and "Fields" are just borrowed names for "source for a document" and "source for a field" 

public interface FieldIterator {
		
	//Get current field name and content
	String GetCurrentName();
	String GetCurrentContent();
	
	//Move on the next field\document
	boolean NextDocument() throws Exception;	 
	boolean NextField();
	
	void close() throws IOException;
	 
}
