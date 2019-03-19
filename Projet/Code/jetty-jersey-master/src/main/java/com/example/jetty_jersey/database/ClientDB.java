package com.example.jetty_jersey.database;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import com.example.jetty_jersey.classes.*;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

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
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
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
    /* Return the first free id of the index, -1 if base isn't listed*/
    /*public int collectID(String table) throws IOException {
        boolean b = true;
        int id = 1;
        while (b && !table.equals("unknown")) {
            GetRequest getRequest = new GetRequest(
                    table,
                    "info",
                    ""+id
            );
            getRequest.fetchSourceContext(new FetchSourceContext(false));
            getRequest.storedFields("_none_");
            b = client.exists(getRequest, RequestOptions.DEFAULT);
            if (b)
                id++;
        }
        if(table.equals("unknown"))
            return -1;

        if(table.equals("flight")){
            if(idMaxFlight < id)
                idMaxFlight = id;
        } else if(table.equals("licence")){
            if(idMaxLicence < id)
                idMaxLicence = id;
        } else if(table.equals("message")){
            if(idMaxMessage < id)
                idMaxMessage = id;
        } else if(table.equals("planeS")){
            if(idMaxPlane < id)
                idMaxPlane = id;
        } else if(table.equals("reservation")){
            if(idMaxReservation < id)
                idMaxReservation = id;
        } else {
            if(idMaxUser < id)
                idMaxUser = id;
        }
        return id;
    }
*/

    /*Return a table with all the table's lines values*/
    public SearchHit[] arrayTable(String table) throws IOException{
        SearchRequest searchRequest = new SearchRequest(table);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        return searchHits;
    }

    /*Take idMax of the specific table*/
    public int getIdMax(String table) throws IOException {
        int max = 0;
        SearchHit[] sh = arrayTable(table);
        for (SearchHit hit : sh) {
            int id = Integer.parseInt(hit.getId());
            if(max < id) max = id;
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
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        SearchHit[] sh = arrayTable(table);
        for (SearchHit hit : sh)
            list.add(hit.getSourceAsMap());
        return list;
    }

    /*INDEX function*/
    public void indexDB(Object o) throws Exception{
        String table = getTable(o);
        if(table.equals("unknown")){
            System.out.println("Not an existing database");
            return;
        }
        int id = getIdMax(table)+1;
        IndexRequest indReq = new IndexRequest(
                table,
                "info",
                ""+id);
        String jsonString = "";
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        if(table.equals("flight")){
            Flight f = (Flight)o;
            jsonString ="{"+
                    "\"idFlight\":\""+f.getFlightId()+"\"," +
                    "\"departureAerodrom\":\""+f.getDepartureAerodrom()+"\"," +
                    "\"arrivalAerodrom\":\""+f.getArrivalAerodrom() +"\"," +
                    "\"date\":\""+df.format(f.getDate())+"\"," +
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
            jsonString ="{"+
                    "\"userId\":\""+id+"\"," +
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
        String ident = indexResponse.getId();
        /*String index = indexResponse.getIndex();
        String type = indexResponse.getType();
        long version = indexResponse.getVersion();*/
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("The document has been created");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("The document has been uploaded");
        }
        /*ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("Not all the shards has been written correctly in the document");
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println(reason);
            }
        }*/
        /*
        if(table.equals("user")) {
            UpdateRequest request = new UpdateRequest(
                    table,
                    "info",
                    id);
            jsonString = "{" +
                    "\"userId\":\""+id+"\"" +
                    "}";
            request.doc(jsonString, XContentType.JSON);
            UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
            if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
                System.out.println("The document has been created");
            } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                System.out.println("The document has been uploaded");
            } else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
                System.out.println("The document has been deleted");
            } else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {
                System.out.println("Nothing happened");
            }
        }*/
    }

    /*GET functions*/
    public ArrayList<Map<String,Object>> getListTable(String table) throws IOException{
        return listMap(table);
    }

    /*Return list of a specific table*/
    public ArrayList<Flight> allFlight() throws IOException, ParseException {
        ArrayList<Flight> list = new ArrayList<Flight>();
        ArrayList<Map<String,Object>> mapList = getListTable("flight");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            Date date = StringToDate(map,"date");
            list.add(new Flight(map.get("flightId").toString(),map.get("departureAerodrom").toString(),map.get("arrivalAerodrom").toString(),date,map.get("atcNumber").toString(),map.get("userId").toString()));
        }
        return list;
    }

    public ArrayList<Licence> allLicence() throws IOException, ParseException {
        ArrayList<Licence> list = new ArrayList<Licence>();
        ArrayList<Map<String,Object>> mapList = getListTable("licence");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            Date date = StringToDate(map,"validityDate");
            list.add(new Licence(map.get("licenceId").toString(),map.get("userId").toString(),date));
        }
        return list;
    }

    public ArrayList<Message> allMessage() throws IOException, ParseException {
        ArrayList<Message> list = new ArrayList<Message>();
        ArrayList<Map<String,Object>> mapList = getListTable("message");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            Date date = StringToDate(map,"dateEnvoi");
            list.add(new Message(map.get("messageId").toString(),map.get("content").toString(),map.get("senderId").toString(),map.get("receiverId").toString(),date));
        }
        return list;
    }

    public ArrayList<Plane> allPlane() throws IOException, ParseException {
        ArrayList<Plane> list = new ArrayList<Plane>();
        ArrayList<Map<String,Object>> mapList = getListTable("plane");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            list.add(new Plane(map.get("atcNumber").toString(),Integer.parseInt(map.get("numberSeats").toString())));
        }
        return list;
    }

    public ArrayList<Reservation> allReservation() throws IOException, ParseException {
        ArrayList<Reservation> list = new ArrayList<Reservation>();
        ArrayList<Map<String,Object>> mapList = getListTable("reservation");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            list.add(new Reservation(map.get("idReservation").toString(),map.get("userId").toString(),map.get("idFlight").toString(),Integer.parseInt(map.get("nbPlaces").toString()),Double.parseDouble(map.get("price").toString()),map.get("status").toString()));
        }
        return list;
    }

    public ArrayList<User> allUser() throws IOException, ParseException {
        ArrayList<User> list = new ArrayList<User>();
        ArrayList<Map<String,Object>> mapList = getListTable("user");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            Date date = StringToDate(map,"birthDate");
            list.add(new User(map.get("lastName").toString(),map.get("firstName").toString(),map.get("email").toString(),map.get("gsm").toString(),date,map.get("password").toString()));
        }
        return list;
    }

    /*Get a specific value of a table by using an id(user,flight, etc)*/
    public Map<String,Object> getValueTable(String table, String id) throws IOException {
        ArrayList<Map<String,Object>> list = listMap(table);
        for(int i = 0; i<list.size(); i++){
            Map<String,Object> map = list.get(i);
            if(map.containsValue(id))
                return map;
        }
        return null;
    }

    /*UPDATE functions*/

    /*DELETE functions*/

}
