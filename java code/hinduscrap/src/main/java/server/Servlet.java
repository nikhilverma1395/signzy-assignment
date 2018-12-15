package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//The schema fields are misnamed from a lucene I used, I did not get time to reindex with correct field_names

public class Servlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getQueryString());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        String query = request.getParameterMap().get("query")[0];
        try {
            response.getWriter().println(getMatch(query).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private JsonArray getMatch(String querystr) throws IOException, ParseException {


        Query q = new QueryParser("title", JettyServer.getAnalyzer()).parse(querystr);

        int hitsPerPage = 15;
        IndexReader reader = DirectoryReader.open(JettyServer.getIndex());
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        JsonArray array = new JsonArray();

        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            array.add(new JsonPrimitive(d.get("course_code")));
        }
        return array;

    }
}