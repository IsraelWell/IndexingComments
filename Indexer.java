/*
1. This class creates and index and stores it in a directory. I
2. Currently the data source is a text file, but that can be changed with very minor modifications.
3. Class uses TextFieldIterator (under FieldIterator interface) to iterate over the data and then it creates
 appropriate fields. 
*/
package IndexingP;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.IndexableFieldType;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

public class Indexer {

	//Put here paths for source and destination. 
	static class WorkPaths{
		static String IndexDestination="C:\\Users\\Israel\\Documents\\Summer Project\\result";
		static String DataSource="C:\\Users\\Israel\\Documents\\Summer Project\\DataSource.txt";
	}
	
	public static void CreateIndex() throws Exception{
		try{
			// create index writer
			Path p= Paths.get(Indexer.WorkPaths.IndexDestination);
			Directory dir = FSDirectory.open(p);
			IndexWriterConfig config = new IndexWriterConfig(new FacebookAnalyzer()); 
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);   //If index already exist override it.
			IndexWriter iWriter = new IndexWriter(dir,config);
	
			//Currently only TextFieldIterator is available
			//If needed inherit implement FieldIterator with different implementation.
			FieldIterator TextReader = new TextFieldIterator(Indexer.WorkPaths.DataSource); 
			Document dc;
			while(TextReader.NextDocument()){
				dc = new Document();
				while(TextReader.NextField()){	
					if(TextReader.GetCurrentName().charAt(0)=='$')	//Fields that start with $ are tokenized.
						dc.add(new TextField(TextReader.GetCurrentName(), TextReader.GetCurrentContent(), Store.YES));	
					else
						dc.add(new StringField(TextReader.GetCurrentName(), TextReader.GetCurrentContent(), Store.YES));	
				}				
				iWriter.addDocument(dc);
			}
							
			TextReader.close();
			iWriter.close();
			}
			catch(IOException ex)
			{}
		
	}	
}
