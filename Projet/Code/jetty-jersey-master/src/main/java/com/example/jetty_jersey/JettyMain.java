package com.example.jetty_jersey;

import com.example.jetty_jersey.classes.Flight;
import com.example.jetty_jersey.classes.Licence;
import com.example.jetty_jersey.classes.Message;
import com.example.jetty_jersey.database.ClientDB;
import org.apache.http.HttpHost;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JettyMain {

	public static void main(String[] args) throws Exception {
		// Initialize the server
		Server server = new Server();

		// Add a connector
		ServerConnector connector = new ServerConnector(server);
		connector.setHost("0.0.0.0");
		connector.setPort(8080);

		connector.setIdleTimeout(30000);
		server.addConnector(connector);

		// Configure Jersey
		ResourceConfig rc = new ResourceConfig();
		rc.packages(true, "com.example.jetty_jersey.ws");
		rc.register(JacksonFeature.class);
		rc.register(LoggingFilter.class);

		// Add a servlet handler for web services (/ws/*)
		ServletHolder servletHolder = new ServletHolder(new ServletContainer(rc));
		ServletContextHandler handlerWebServices = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handlerWebServices.setContextPath("/ws");
		handlerWebServices.addServlet(servletHolder, "/*");

		// Add a handler for resources (/*)
		ResourceHandler handlerPortal = new ResourceHandler();
		handlerPortal.setResourceBase("src/main/webapp");
		handlerPortal.setDirectoriesListed(false);
		handlerPortal.setWelcomeFiles(new String[] { "home.html" });
		ContextHandler handlerPortalCtx = new ContextHandler();
		handlerPortalCtx.setContextPath("/");
		handlerPortalCtx.setHandler(handlerPortal);

		// Activate handlers
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { handlerWebServices, handlerPortalCtx });
		server.setHandler(contexts);

		// Start server
		server.start();

        ClientDB c = new ClientDB();

        /*
        Flight f1 = new Flight("F123","DAKAR","DAKAR",new Date(),"AC123","ID125");
        Flight f2 = new Flight("F124","MEAUX","BOURGET",new Date(),"AC125","ID128");
        Flight f3 = new Flight("F125","BOURGET","BOURGET",new Date(),"AC127","ID127");
        Licence l1 = new Licence("L123","ID125",new Date());
        Licence l2 = new Licence("L124","ID128",new Date());
        Licence l3 = new Licence("L125","ID127",new Date());
        Message m1 = new Message("M123","juste un test","ID125","ID126", new Date());
        Message m2 = new Message("M124","juste un autre test","ID125","ID127", new Date());

		c.indexDB(f1);
        c.indexDB(f2);
        c.indexDB(l1);
        c.indexDB(l2);
        c.indexDB(m1);
        c.indexDB(m2);


        ArrayList<Map<String,Object>> list = c.getListTable("flight");
        for(int i = 0; i<list.size(); i++){
            Map<String,Object> map = list.get(i);
            System.out.println(map.toString());
        }*/

		c.closeClient();
		/*RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost", 9200, "http")));
		IndexRequest request = new IndexRequest(
				"licence",
				"doc",
				"1");
		String jsonString ="{"+
				"\"idLicence\":\""+l.getIdLicence() +"\"," +
				"\"userId\":\""+l.getUserId()+"\"," +
				"\"dateValidite\":\""+l.getDateValidite()+"\"" +
				"}";
		request.source(jsonString, XContentType.JSON);
		IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
		String index = indexResponse.getIndex();
		String type = indexResponse.getType();
		String id = indexResponse.getId();
		long version = indexResponse.getVersion();
		if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {

		} else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {

		}
		ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
		if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

		}
		if (shardInfo.getFailed() > 0) {
			for (ReplicationResponse.ShardInfo.Failure failure :
					shardInfo.getFailures()) {
				String reason = failure.reason();
			}
		}*/

		/*
		GetRequest getRequest = new GetRequest(
				"licence",
				"info",
				"1");
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		String index = getResponse.getIndex();
		String type = getResponse.getType();
		String id = getResponse.getId();

		if (getResponse.isExists()) {
			long version = getResponse.getVersion();
			String sourceAsString = getResponse.getSourceAsString();
			Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
			byte[] sourceAsBytes = getResponse.getSourceAsBytes();
			System.out.println(sourceAsMap.toString());
		} else {
			client.close();
		}*/
	}

}
