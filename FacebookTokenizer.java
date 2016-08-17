package IndexingP;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.automaton.RegExp;
import org.apache.lucene.util.automaton.RegExp;



public class FacebookTokenizer extends Tokenizer {

	protected CharTermAttribute charTermAttribute =
	        addAttribute(CharTermAttribute.class);
				
        public boolean incrementToken() throws IOException {
         
        	clearAttributes();
        	if(CurrentTerm>=Terms.length)
        		return false;
        	this.charTermAttribute.setEmpty();
        	this.charTermAttribute.append(Terms[CurrentTerm]);
        	CurrentTerm++;
        	return true;
        }
 
        static String[] Tokenize(String StringToTokenize){
        	
        	Pattern icon = Pattern.compile(":(\\p{L}{0,15}):");
        	Matcher m = icon.matcher(StringToTokenize);
        	StringBuffer bf = new StringBuffer();
        	while(m.find())
        	{
        		m.appendReplacement(bf, " _$1_ ");
        	}
        	m.appendTail(bf);      			
        	StringToTokenize=bf.toString();      	
        	
        	StringToTokenize = StringToTokenize.replaceAll("(?!_)\\p{Punct}", " "); //remove punctuation.
        	StringToTokenize= StringToTokenize.replaceAll("\\d+", " ");
        	String[] Processed = StringToTokenize.split("\\s+");	//Split to words without space
        	return Processed;        	       	
        }
        
        
        
        public void reset() throws IOException {
        	super.reset();        	        	
        	
        	char[] buffer = new char[1024];
			int numChars;
	        StringBuilder stringBuilder = new StringBuilder();	   
	        try {
	            while ((numChars =
	                this.input.read(buffer, 0, buffer.length)) != -1) {
	                stringBuilder.append(buffer, 0, numChars);
	            }
	        }
	        catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	        
	        String StringToTokenize = stringBuilder.toString();
			Terms=Tokenize(StringToTokenize);
			CurrentTerm=0;
        	        	
        };
       
     
        String[] Terms;
        int CurrentTerm;
}