
package lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

//The schema fields are misnamed from a lucene I used, I did not get time to reindex with correct field_names
public class LuceneWriterExample {

    public static void main(String[] args) throws IOException, ParseException {

        String indexPath = args[0];

        String filesPath = args[1];

        StandardAnalyzer analyzer = new StandardAnalyzer();
        FSDirectory index = FSDirectory.open(Paths.get(indexPath));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
//        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
//        IndexWriter writer = new IndexWriter(index, config);
//
//
//        List<Path> pagePaths = Files.walk(Paths.get(filesPath))
//                .filter(Files::isRegularFile)
//                .distinct()
//                .filter(p -> p.toString().endsWith("html")).collect(Collectors.toList());
//        int tick = 0;
//        System.out.println("Files - " + pagePaths.size());
//        for (Path path : pagePaths) {
//            if (tick++ % 50 == 0) {
//                System.out.println(tick);
//            }
//            File input = new File(path.toUri());
//            org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");
//            String title = doc.title();
//            String content = doc.getElementsByClass("article").text();
//            System.out.println(title + "\t\t\t" + content);
//            addDoc(writer, title, content);
//        }

        //Query via command Line
        //Enter 'end' to end while loop
        //writer.close;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Query");
            String name = scanner.nextLine();
            if (name.equals("end")) {
                break;
            }

            String queryString = name;
            Query query = new QueryParser("title", analyzer).parse(queryString);

            //Hard-Coded
            int hitsPerPage = 15;
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
            searcher.search(query, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;

            System.out.println("Results -" + hits.length);
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                System.out.println((i + 1) + ". " + d.get("course_code"));
            }
        }
    }

    private static void addDoc(IndexWriter w, String title, String content) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("course_code", title, Field.Store.YES));

        doc.add(new TextField("title", content, Field.Store.YES));
        w.addDocument(doc);
    }

}
