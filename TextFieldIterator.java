package IndexingP;

import java.io.*;

import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

/*
IMPORTANT:
In order for this class to work, the source text file (whose path is sent as a parameter to the constructor)
should follow the following format:

1. The first line will contain the field names. They will be: A. separated by a single tab and B. in the same order of
 the fields themselves (see 3).
2.  The name of fields that need to be tokenized (content usually) must start with a $ symbol.
 
3. from the second line on each line should contain the fields' contents, separated by a single tab.
*/

public class TextFieldIterator implements FieldIterator {

	
	public TextFieldIterator(String Path) throws IOException {
		
		//once we read the first line LineNumber will be incremented to 0.
		LineNumber=-1;
		
		TextReader = new BufferedReader(new FileReader(new File(Path)));

		FieldNames=TextReader.readLine().split("\t");		
	}
	
	public String GetCurrentContent() {
		return FieldContents[FieldNumber];		
	}

	public String GetCurrentName(){
		return FieldNames[FieldNumber];
	}

	public boolean NextDocument() throws Exception{
		if(!ReadLine())
			return false;
		
		FieldNumber=-1;
		LineNumber++;
		
		return true;		
	}
	
	//Read line from file into our object.
	protected boolean ReadLine() throws Exception{

		String line=TextReader.readLine();
		if(line==null){
			FieldContents=null;
			return false;
		}
		
		FieldContents=line.split("\t");
		if(FieldContents.length!=FieldNames.length)
			throw new Exception("Number of field names doesn't match number of values in line "+(LineNumber+1));
		
		return true;
		
	}

	public boolean NextField() {
		FieldNumber++;		
		return FieldNumber<FieldNames.length;
	}
	
	public void close() throws IOException{
		TextReader.close();
	}
	
	protected String[] FieldContents;
	protected String[] FieldNames;
	protected BufferedReader TextReader;
	protected int FieldNumber;
	protected int LineNumber;
}
