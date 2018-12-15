package server;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.FSDirectory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.nio.file.Paths;

class JettyServer {
    private static StandardAnalyzer analyzer;
    private static FSDirectory index;

    public static FSDirectory getIndex() {
        return index;
    }

    public static StandardAnalyzer getAnalyzer() {
        return analyzer;
    }

    private Server server;

    public static void main(String[] args) throws Exception {
        analyzer = new StandardAnalyzer();
        index = FSDirectory.open(Paths.get(args[0]));

        new JettyServer().start();
    }

    void start() throws Exception {

        int maxThreads = 100;
        int minThreads = 11;
        int idleTimeout = 60;

        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);

        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[]{connector});

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(Servlet.class, "/search");


        server.start();

    }

    void stop() throws Exception {
        server.stop();
    }
}