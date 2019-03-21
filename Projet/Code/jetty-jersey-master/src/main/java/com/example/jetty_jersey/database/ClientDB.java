package com.example.jetty_jersey.database;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import com.example.jetty_jersey.classes.*;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ClientDB {
    private RestHighLevelClient client;

    public ClientDB(){
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }

    public RestHighLevelClient getClient(){
        return client;
    }

    /*Close the client*/
    public void closeClient() throws Exception{
        client.close();
    }

    /*Return the string date int a map into a date.Using the key to find the date*/
    public Date StringToDate(Map<String,Object> map, String key) throws ParseException{
        String d = map.get(key).toString();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse(d);
        return date;
    }
    /*Return the name of the instance into a String*/
    public String getTable(Object o){
        String res;
        if(o instanceof Flight){
            res = "flight" ;
        } else if(o instanceof Licence){
            res = "licence" ;
        } else if(o instanceof Message){
            res = "message" ;
        } else if(o instanceof Plane){
            res = "plane" ;
        } else if(o instanceof Reservation){
            res = "reservation" ;
        } else if(o instanceof User){
            res = "user" ;
        } else {
            res = "unknown";
        }
        return res;
    }
    
    /*Return a table with all the table's lines values*/
    public SearchHit[] arrayTable(String table) throws IOException{
        SearchHit[] searchHits;
        GetIndexRequest request = new GetIndexRequest();
        request.indices(table);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if(exists) {
            SearchRequest searchRequest = new SearchRequest(table);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            searchHits = hits.getHits();
            return searchHits;
        }
        return null;
    }

    /*Take idMax of the specific table*/
    public int getIdMax(String table) throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(table);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        int max = 0;
        if(exists) {
            SearchHit[] sh = arrayTable(table);
            for (SearchHit hit : sh) {
                int id = Integer.parseInt(hit.getId());
                if (max < id) max = id;
            }
        }
        return max;
    }

    /*List map data of a database*/
    public ArrayList<Integer> listIdMap(String table) throws IOException{
        ArrayList<Integer> list = new ArrayList<Integer>();
        SearchHit[] sh = arrayTable(table);
        for (SearchHit hit : sh) {
            int id = Integer.parseInt(hit.getId());
            list.add(id);
        }
        return list;
    }

    public ArrayList<Map<String,Object>> listMap(String table) throws IOException {
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        GetIndexRequest request = new GetIndexRequest();
        request.indices(table);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if(exists) {
            SearchHit[] sh = arrayTable(table);
            for (SearchHit hit : sh)
                list.add(hit.getSourceAsMap());
            return list;
        }
        return list;
    }

    /*INDEX function*/
    public void indexDB(Object o) throws Exception{
        String table = getTable(o);
        if(table.equals("unknown")){
            System.out.println("Not an existing database");
            return;
        }
        GetIndexRequest request = new GetIndexRequest();
        request.indices(table);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        int id = 1;
        if(exists) id = getIdMax(table)+1;
        IndexRequest indReq = new IndexRequest(
                table,
                "info",
                ""+id);
        String jsonString = "";
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        if(table.equals("flight")){
            Flight f = (Flight)o;
            f.setFlightId(""+id);
            jsonString ="{"+
                    "\"idFlight\":\""+f.getFlightId()+"\"," +
                    "\"departureAerodrom\":\""+f.getDepartureAerodrom()+"\"," +
                    "\"arrivalAerodrom\":\""+f.getArrivalAerodrom() +"\"," +
                    "\"date\":\""+f.getDate()+"\"," +
                    "\"atcNumber\":\""+f.getAtcNumber()+"\"," +
                    "\"userId\":\""+f.getUserId()+"\""+
                    "}";
        } else if(table.equals("licence")){
            Licence l = (Licence)o;
            jsonString ="{"+
                    "\"idLicence\":\""+l.getLicenceId() +"\"," +
                    "\"userId\":\""+l.getUserId()+"\"," +
                    "\"dateValidite\":\""+df.format(l.getValidityDate())+"\"" +
                    "}";
        } else if(table.equals("message")){
            Message m = (Message)o;
            jsonString ="{"+
                    "\"idMessage\":\""+m.getMessageId() +"\"," +
                    "\"contenu\":\""+m.getContent()+"\"," +
                    "\"idEmetteur\":\""+m.getSenderId() +"\"," +
                    "\"idDestinataire\":\""+m.getReceiverId()+"\"," +
                    "\"dateEnvoi\":\""+df.format(m.getSendingDate())+"\"" +
                    "}";
        } else if(table.equals("plane")){
            Plane p = (Plane)o;
            jsonString ="{"+
                    "\"atcNumber\":\""+p.getAtcNumber() +"\"," +
                    "\"numberSeats\":\""+p.getNumberSeats()+"\"," +
                    "}";
        } else if(table.equals("reservation")){
            Reservation r = (Reservation)o;
            jsonString ="{"+
                    "\"idReservation\":\""+r.getReservationId()+"\"," +
                    "\"userId\":\""+r.getFlightId()+"\"," +
                    "\"idFlight\":\""+r.getUserId() +"\"," +
                    "\"nbPlaces\":\""+r.getNbPlaces() +"\"," +
                    "\"date\":\""+df.format(r.getDate())+"\"," +
                     "\"price\":\""+r.getPrice()+"\"," +
                    "\"status\":\""+r.getStatus()+"\"" +
                    "}";
        } else {
            User u = (User)o;
            u.setUserId(""+id);
            jsonString ="{"+
                    "\"userId\":\""+u.getUserId()+"\"," +
                    "\"lastName\":\""+u.getLastName()+"\"," +
                    "\"firstName\":\""+u.getFirstName() +"\"," +
                    "\"email\":\""+u.getEmail()+"\"," +
                    "\"gsm\":\""+u.getGsm()+"\"," +
                    "\"birthDate\":\""+u.getBirthDate()+"\"," +
                    "\"password\":\""+u.getPassword()+"\"" +
                    "}";
        }
        indReq.source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = client.index(indReq, RequestOptions.DEFAULT);
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("The document has been created");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("The document has been uploaded");
        }
        
    }

    /*GET functions*/
    public ArrayList<Map<String,Object>> getListTable(String table) throws IOException{
        GetIndexRequest request = new GetIndexRequest();
        request.indices(table);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        return listMap(table);
    }

    /*Return list of a specific table*/
    public ArrayList<Flight> allFlight() throws IOException, ParseException {
        ArrayList<Flight> list = new ArrayList<Flight>();
        GetIndexRequest request = new GetIndexRequest();
        request.indices("flight");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if(exists) {
            ArrayList<Map<String, Object>> mapList = getListTable("flight");
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                Flight lister = new Flight(map.get("plane").toString(),
                        map.get("departureAero").toString(),
                        map.get("date").toString(),
                        map.get("departureTime").toString(),
                        map.get("seats").toString(),
                        map.get("type").toString(),
                        map.get("arrivalAedrome").toString(),
                        map.get("arrivalTime").toString(),
                        map.get("price").toString(),
                        map.get("userId").toString());
                lister.setFlightId(map.get("flightId").toString());
                list.add(lister);
            }
            return list;
        }
        return list;
    }

    public ArrayList<Licence> allLicence() throws IOException, ParseException {
        ArrayList<Licence> list = new ArrayList<Licence>();
        GetIndexRequest request = new GetIndexRequest();
        request.indices("licence");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if(exists) {
            ArrayList<Map<String, Object>> mapList = getListTable("licence");
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                Date date = StringToDate(map, "validityDate");
                list.add(new Licence(map.get("licenceId").toString(), map.get("userId").toString(), date));
            }
            return list;
        }
        return list;
    }

    public ArrayList<Message> allMessage() throws IOException, ParseException {
        ArrayList<Message> list = new ArrayList<Message>();
        GetIndexRequest request = new GetIndexRequest();
        request.indices("message");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if(exists) {
            ArrayList<Map<String, Object>> mapList = getListTable("message");
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                Date date = StringToDate(map, "dateEnvoi");
                list.add(new Message(map.get("messageId").toString(), map.get("content").toString(), map.get("senderId").toString(), map.get("receiverId").toString(), date));
            }
            return list;
        }
        return list;
    }

    public ArrayList<Plane> allPlane() throws IOException, ParseException {
        ArrayList<Plane> list = new ArrayList<Plane>();
        GetIndexRequest request = new GetIndexRequest();
        request.indices("plane");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if(exists) {
            ArrayList<Map<String, Object>> mapList = getListTable("plane");
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                list.add(new Plane(map.get("atcNumber").toString(), Integer.parseInt(map.get("numberSeats").toString())));
            }
            return list;
        }
        return list;
    }

    public ArrayList<Reservation> allReservation() throws IOException, ParseException {
        ArrayList<Reservation> list = new ArrayList<Reservation>();
        GetIndexRequest request = new GetIndexRequest();
        request.indices("reservation");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if(exists) {
            ArrayList<Map<String, Object>> mapList = getListTable("reservation");
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                list.add(new Reservation(map.get("idReservation").toString(), map.get("userId").toString(), map.get("idFlight").toString(), Integer.parseInt(map.get("nbPlaces").toString()), Double.parseDouble(map.get("price").toString()), map.get("status").toString()));
            }
            return list;
        }
        return list;
    }
    
    

    public ArrayList<User> allUser() throws IOException, ParseException {
        ArrayList<User> list = new ArrayList<User>();
        GetIndexRequest request = new GetIndexRequest();
        request.indices("user");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if(exists) {
            ArrayList<Map<String, Object>> mapList = getListTable("user");
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                User test = new User(map.get("firstName").toString(), map.get("lastName").toString(), map.get("email").toString(), map.get("password").toString(),
                        map.get("birthDate").toString(), map.get("gsm").toString());
                test.setUserId(map.get("userId").toString());
                list.add(test);
            }
            return list;
        }
        return list;
    }

    /*Get a specific value of a table by using an id(user,flight, etc)*/
    public Map<String,Object> getValueTable(String table, String id) throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(table);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        if(exists) {
            ArrayList<Map<String, Object>> list = listMap(table);
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                if (map.containsValue(id))
                    return map;
            }
        }
        return null;
    }

    /*UPDATE functions*/

    /*DELETE functions*/

}
