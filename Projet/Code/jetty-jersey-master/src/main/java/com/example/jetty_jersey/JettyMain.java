package com.example.jetty_jersey;

import com.example.jetty_jersey.classes.Flight;
import com.example.jetty_jersey.classes.Licence;
import com.example.jetty_jersey.classes.Message;
import com.example.jetty_jersey.classes.User;
import com.example.jetty_jersey.database.ClientDB;
import org.apache.commons.codec.digest.DigestUtils;
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
	public static ClientDB c;
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
		handlerPortal.setWelcomeFiles(new String[] {"home.html"});
		ContextHandler handlerPortalCtx = new ContextHandler();
		handlerPortalCtx.setContextPath("/");
		handlerPortalCtx.setHandler(handlerPortal);

		// Activate handlers
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { handlerWebServices, handlerPortalCtx });
		server.setHandler(contexts);

		// Start server
		server.start();

		c = new ClientDB();

		User u1 = new User("Alex","DENVER","alex.denver@gmail.com","azerty","1991-01-27","06 07 08 09 10");
        User u2 = new User("Basil","DENVER","baz.denver@gmail.com","123456","1989-09-21","06 11 12 13 14");
        User u3 = new User("Camille","DENVER","cam.denver@gmail.com","abcdef","1993-05-18","06 15 16 17 18");

        /*c.indexDB(u1);
        c.indexDB(u2);
        c.indexDB(u3);*/

        /*
        ArrayList<User> l = c.allUser();
        for(int i = 0; i < l.size(); i++)
            System.out.println(l.get(i).getFirstName()+", "+l.get(i).getLastName()+", "+l.get(i).getEmail()+", "+l.get(i).getPassword()+", "+l.get(i).getBirthDate()+", "+l.get(i).getGsm());
            */
    }

}
