package com.example.jetty_jersey.database;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import com.example.jetty_jersey.classes.*;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ClientDB {
    private RestHighLevelClient client;
    private int idMaxFlight,idMaxLicence,idMaxMessage,idMaxPlane,idMaxReservation,idMaxUser;

    public ClientDB(){
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        idMaxFlight = 0;
        idMaxLicence = 0;
        idMaxMessage = 0;
        idMaxPlane = 0;
        idMaxReservation = 0;
        idMaxUser = 0;
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
    /*Modify idMax of the specific table*/
    public void setIdMax(String table,int n){
        if(table.equals("flight")) idMaxFlight += n;
        else if(table.equals("licence")) idMaxLicence += n;
        else if(table.equals("message")) idMaxMessage += n;
        else if(table.equals("plane")) idMaxPlane += n;
        else if(table.equals("reservation")) idMaxReservation += n;
        else idMaxUser += n;
    }
    /*Take idMax of the specific table*/
    public int getIdMax(String table){
        if(table.equals("flight")) return idMaxFlight;
        else if(table.equals("licence")) return idMaxLicence;
        else if(table.equals("message")) return idMaxMessage;
        else if(table.equals("plane")) return idMaxPlane;
        else if(table.equals("reservation")) return idMaxReservation;
        else return idMaxUser;
    }

    /*List id and map data of a database*/
    public ArrayList<Integer> listIdMap(String table) throws IOException{
        ArrayList<Integer> list = new ArrayList<Integer>();
        int id = 1;
        int idMax = getIdMax(table);
        for(int i = 0; i< idMax; i++) {
            GetRequest getRequest = new GetRequest(
                    table,
                    "info",
                    ""+id
            );
            boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
            if (exists) {
                list.add(id);
                id++;
            }
        }
        return list;
    }

    public ArrayList<Map<String,Object>> listMap(String table) throws IOException {
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        int id = 1;
        int idMax = getIdMax(table);
        for(int i = 0; i < idMax; i++) {
            GetRequest getRequest = new GetRequest(
                    table,
                    "info",
                    ""+id
            );
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
            if (exists) {
                list.add(getResponse.getSourceAsMap());
                id++;
            }
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
        setIdMax(table,1);
        String id = ""+getIdMax(table);
        IndexRequest indReq = new IndexRequest(
                table,
                "info",
                id
        );
        String jsonString = "";
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        if(table.equals("flight")){
            Flight f = (Flight)o;
            jsonString ="{"+
                    "\"idFlight\":\""+f.getIdFlight()+"\"," +
                    "\"departureAerodrom\":\""+f.getDepartureAerodrom()+"\"," +
                    "\"arrivalAerodrom\":\""+f.getArrivalAerodrom() +"\"," +
                    "\"date\":\""+df.format(f.getDate())+"\"," +
                    "\"atcNumber\":\""+f.getAtcNumber()+"\"," +
                    "\"userId\":\""+f.getUserId()+"\"" +
                    "}";
        } else if(table.equals("licence")){
            Licence l = (Licence)o;
            jsonString +="{"+
                    "\"idLicence\":\""+l.getIdLicence() +"\"," +
                    "\"userId\":\""+l.getUserId()+"\"," +
                    "\"dateValidite\":\""+df.format(l.getDateValidite())+"\"" +
                    "}";
        } else if(table.equals("message")){
            Message m = (Message)o;
            jsonString +="{"+
                    "\"idMessage\":\""+m.getIdMessage() +"\"," +
                    "\"contenu\":\""+m.getContenu()+"\"," +
                    "\"idEmetteur\":\""+m.getIdEmetteur() +"\"," +
                    "\"idDestinataire\":\""+m.getIdDestinataire()+"\"," +
                    "\"dateEnvoi\":\""+df.format(m.getDateEnvoi())+"\"" +
                    "}";
        } else if(table.equals("plane")){
            Plane p = (Plane)o;
            jsonString +="{"+
                    "\"atcNumber\":\""+p.getAtcNumber() +"\"," +
                    "\"numberSeats\":\""+p.getNumberSeats()+"\"," +
                    "}";
        } else if(table.equals("reservation")){
            Reservation r = (Reservation)o;
            jsonString +="{"+
                    "\"idReservation\":\""+r.getIdReservation()+"\"," +
                    "\"userId\":\""+r.getIdFlight()+"\"," +
                    "\"idFlight\":\""+r.getIdUser() +"\"," +
                    "\"nbPlaces\":\""+r.getNbPlaces() +"\"," +
                    "\"date\":\""+df.format(r.getDate())+"\"," +
                     "\"price\":\""+r.getPrice()+"\"," +
                    "\"status\":\""+r.getStatus()+"\"" +
                    "}";
        } else {
            User u = (User)o;
            jsonString +="{"+
                    "\"userId\":\""+u.getIdUser()+"\"," +
                    "\"lastName\":\""+u.getLastName()+"\"," +
                    "\"firstName\":\""+u.getFirstName() +"\"," +
                    "\"email\":\""+u.getEmail()+"\"," +
                    "\"gsm\":\""+u.getGsm()+"\"," +
                    "\"birthDate\":\""+df.format(u.getBirthDate())+"\"," +
                    "\"password\":\""+u.getPassword()+"\"" +
                    "}";
        }
        indReq.source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = client.index(indReq, RequestOptions.DEFAULT);
        /*String index = indexResponse.getIndex();
        String type = indexResponse.getType();
        String id = indexResponse.getId();
        long version = indexResponse.getVersion();*/
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("The document has been created");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("The document has been uploaded");
        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("Not all the shards has been written correctry in the document");
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println(reason);
            }
        }
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
            list.add(new Flight(map.get("idFlight").toString(),map.get("departureAerodrom").toString(),map.get("arrivalAerodrom").toString(),date,map.get("atcNumber").toString(),map.get("userId").toString()));
        }
        return list;
    }

    public ArrayList<Licence> allLicence() throws IOException, ParseException {
        ArrayList<Licence> list = new ArrayList<Licence>();
        ArrayList<Map<String,Object>> mapList = getListTable("licence");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            Date date = StringToDate(map,"dateValidite");
            list.add(new Licence(map.get("idLicence").toString(),map.get("userId").toString(),date));
        }
        return list;
    }

    public ArrayList<Message> allMessage() throws IOException, ParseException {
        ArrayList<Message> list = new ArrayList<Message>();
        ArrayList<Map<String,Object>> mapList = getListTable("message");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            Date date = StringToDate(map,"dateEnvoi");
            list.add(new Message(map.get("idMessage").toString(),map.get("contenu").toString(),map.get("idEmetteur").toString(),map.get("idDestinataire").toString(),date));
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
            Date date = StringToDate(map,"date");
            list.add(new Reservation(map.get("idReservation").toString(),map.get("userId").toString(),map.get("idFlight").toString(),Integer.parseInt(map.get("nbPlaces").toString()),date,Double.parseDouble(map.get("price").toString()),map.get("status").toString()));
        }
        return list;
    }

    public ArrayList<User> allUser() throws IOException, ParseException {
        ArrayList<User> list = new ArrayList<User>();
        ArrayList<Map<String,Object>> mapList = getListTable("user");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            Date date = StringToDate(map,"birthDate");
            list.add(new User(Integer.parseInt(map.get("userId").toString()),map.get("lastName").toString(),map.get("firstName").toString(),map.get("email").toString(),map.get("gsm").toString(),date,map.get("password").toString()));
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
