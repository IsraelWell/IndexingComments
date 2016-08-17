package IndexingP;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryCachingPolicy;
import org.apache.lucene.search.RegexpQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.xml.sax.DocumentHandler;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;

public class Main
{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Indexer.CreateIndex();
		
	
	
		
		
		
		//a quick demonstration of Lucene search performed on our indexed data.
		
		Path p= Paths.get(Indexer.WorkPaths.IndexDestination);
		Directory dir = FSDirectory.open(p);
		IndexSearcher s = new IndexSearcher(DirectoryReader.open(dir));
		Query name = new TermQuery(new Term("mk_name", "בנימין נתניהו"));
		Query comment = new PhraseQuery("$content","ישראל");
		BooleanQuery.Builder builder =new BooleanQuery.Builder();
		builder.add(name, Occur.MUST);
		builder.add(comment, Occur.MUST);
		BooleanQuery n_c=builder.build();
		TopDocs hits = s.search(n_c, 100);
		System.out.println(hits.totalHits);
		
		
		
		/* 
		
		 
		uncomment this if you want to test the method "tokenize" which FacebookTokenizer use.		
		String comment="הפגיעה בחמאס לא יכולה להיות צבאית בלבד, אלא ובעיקר מדינית. מציאות עגומה לא משנים בכוח הזרוע, אלא באמצעים דיפלומטיים וכלכליים ארוכי-טווח.     :חיוך::עצב:אני לא: רוצה   איך עושים את זה?:פרצוף:קראו את הטור שכתבתי ופורסם אמש בגלובס - Globes        ";
		String[] terms=FacebookTokenizer.Tokenize(comment);
		for(String term:terms)
			System.out.println(term);
			
		*/
			
	
}
}