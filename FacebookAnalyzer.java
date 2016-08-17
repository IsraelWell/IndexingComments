package IndexingP;

import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.pattern.PatternTokenizer;
import org.apache.lucene.analysis.standard.StandardFilter;


public class FacebookAnalyzer extends Analyzer {

	protected  TokenStreamComponents createComponents(String FieldName){
		// TODO Auto-generated method stub

		
		return new TokenStreamComponents(new FacebookTokenizer());
	}
    }
