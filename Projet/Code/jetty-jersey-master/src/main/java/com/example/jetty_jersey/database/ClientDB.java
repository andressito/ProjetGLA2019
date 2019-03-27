package com.example.jetty_jersey.database;


import com.example.jetty_jersey.classes.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
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
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class ClientDB {
    private RestHighLevelClient client;
    private int idMaxFlight, idMaxLicence, idMaxMessage, idMaxPlane, idMaxReservation, idMaxUser;

    public ClientDB() throws IOException{
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        GetRequest getRequest = new GetRequest(
                "idmax",
                "info",
                "1");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        if(!client.exists(getRequest, RequestOptions.DEFAULT)){
            IndexRequest indReq = new IndexRequest(
                    "idmax",
                    "info",
                    "1");
            String jsonString = "{"+
                    "\"flight\":\"0\"," +
                    "\"licence\":\"0\"," +
                    "\"message\":\"0\"," +
                    "\"plane\":\"0\"," +
                    "\"reservation\":\"0\"," +
                    "\"user\":\"0\""+
                    "}";
            indReq.source(jsonString, XContentType.JSON);
            IndexResponse indexResponse = client.index(indReq, RequestOptions.DEFAULT);
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED)
                System.out.println("idmax has been created");
        }
        idMaxFlight = getIdMax2("flight");
        idMaxLicence = getIdMax2("licence");
        idMaxMessage = getIdMax2("message");
        idMaxPlane = getIdMax2("plane");
        idMaxReservation = getIdMax2("reservation");
        idMaxUser = getIdMax2("user");
    }

    public RestHighLevelClient getClient(){
        return client;
    }

    /*Close the client*/
    public void closeClient() throws Exception{
        client.close();
    }

    public boolean canConnect(String email, String mdp) throws IOException, ParseException{
        ArrayList<User> l = allUser();
        for(int i = 0; i<l.size(); i++) {
            User u = l.get(i);
            if (email.equals(u.getEmail()) && DigestUtils.md5Hex(mdp).equals(DigestUtils.md5Hex(u.getPassword()))) return true;
        }
        return false;
    }
    public boolean ifTableExist(String table) throws IOException{
        GetIndexRequest request = new GetIndexRequest();
        request.indices(table);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    /*Return the string date in a map into a date.Using the key to find the date*/
    public Date StringToDate(Map<String,Object> map, String key) throws ParseException{
        String d = map.get(key).toString();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(d);
        return date;
    }

    /*Convert into a Date and return it*/
    public Date StringToDate(String d) throws ParseException{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(d);
        return date;
    }

    /*Create a random user id*/
    public String createId(int len){
        String list = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String id = "";
        Random rand = new Random();
        for (int i = 0 ; i < len; i++){
            id += list.charAt(rand.nextInt(list.length()));
        }
        return id;
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
        if(ifTableExist(table)) {
            SearchRequest searchRequest = new SearchRequest(table);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            return searchHits;
        }
        return null;
    }


    /*Take idMax of the specific table using the variables*/
    public int getIdMax1(String table){
        if(table.equals("flight")) return idMaxFlight;
        else if(table.equals("licence")) return idMaxLicence;
        else if(table.equals("message")) return idMaxMessage;
        else if(table.equals("plane")) return idMaxPlane;
        else if(table.equals("reservation")) return idMaxReservation;
        else return idMaxUser;
    }

    /*Take idMax of the specific table using the database*/
    public int getIdMax2(String table) throws IOException {
        int max = 0;
        SearchHit[] sh = arrayTable("idmax");
        if(sh == null || sh.length == 0)
            return max;
        Map<String,Object> map = sh[0].getSourceAsMap();
        max = Integer.parseInt(map.get(table).toString());
        return max;
    }

    /*Set idMax depending on the table*/
    public void setIdMax(String table, int val){
        if(table.equals("flight")) idMaxFlight = val;
        else if(table.equals("licence")) idMaxLicence = val;
        else if(table.equals("message")) idMaxMessage = val;
        else if(table.equals("plane")) idMaxPlane = val;
        else if(table.equals("reservation")) idMaxReservation = val;
        else idMaxUser = val;
    }
    public boolean updateId(String table, int max) throws IOException{
        UpdateRequest request = new UpdateRequest(
                    "idmax",
                    "info",
                    "1");
        String jsonString = "{" +
                "\""+table+"\":\""+max+"\"" +
                "}";
        request.doc(jsonString, XContentType.JSON);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        setIdMax(table,max);
        return updateResponse.getResult() == DocWriteResponse.Result.UPDATED;
    }

    /*List map data of a database*/
    public ArrayList<Integer> listIdMap(String table) throws IOException{
        ArrayList<Integer> list = new ArrayList<Integer>();
        SearchHit[] sh = arrayTable(table);
        if(sh == null || sh.length == 0)
            return list;
        for (SearchHit hit : sh) {
            int id = Integer.parseInt(hit.getId());
            list.add(id);
        }
        return list;
    }

    /*List the index's values in an list of map*/
    public ArrayList<Map<String,Object>> listMap(String table) throws IOException {
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        SearchHit[] sh = arrayTable(table);
        if(sh == null || sh.length == 0)
            return list;
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
        int id = getIdMax1(table)+1;
        IndexRequest indReq = new IndexRequest(
                table,
                "info",
                ""+id);
        String jsonString = "";
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        if(table.equals("flight")){
            Flight f = (Flight)o;
            jsonString ="{"+
                    "\"flightId\":\""+createId(7)+"\"," +
                    "\"atcNumber\":\""+f.getAtcNumber()+"\"," +
                    "\"departureAerodrom\":\""+f.getDepartureAerodrom()+"\"," +
                    "\"date\":\""+f.getDate()+"\"," +
                    "\"departureTime\":\""+f.getDepartureTime()+"\"," +
                    "\"seats\":\""+f.getSeats()+"\"," +
                    "\"type\":\""+f.getType()+"\"," +
                    "\"arrivalAerodrom\":\""+f.getArrivalAerodrom() +"\"," +
                    "\"arrivalTime\":\""+f.getArrivalTime() +"\"," +
                    "\"price\":\""+f.getPrice()+"\"," +
                    "\"userId\":\""+f.getUserId()+"\""+
                    "}";
        } else if(table.equals("licence")){
            Licence l = (Licence)o;
            jsonString ="{"+
                    "\"licenceId\":\""+l.getLicenceId() +"\"," +
                    "\"userId\":\""+l.getUserId()+"\"," +
                    "\"validityDate\":\""+df.format(l.getValidityDate())+"\"" +
                    "}";
        } else if(table.equals("message")){
            Message m = (Message)o;
            jsonString ="{"+
                    "\"messageId\":\""+m.getMessageId() +"\"," +
                    "\"content\":\""+m.getContent()+"\"," +
                    "\"senderId\":\""+m.getSenderId() +"\"," +
                    "\"receiverId\":\""+m.getReceiverId()+"\"," +
                    "\"sendingDate\":\""+df.format(m.getSendingDate())+"\"" +
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
                    "\"reservationId\":\""+r.getReservationId()+"\"," +
                    "\"userId\":\""+r.getFlightId()+"\"," +
                    "\"flightId\":\""+r.getUserId() +"\"," +
                    "\"nbPlaces\":\""+r.getNbPlaces() +"\"," +
                    "\"date\":\""+df.format(r.getDate())+"\"," +
                     "\"price\":\""+r.getPrice()+"\"," +
                    "\"status\":\""+r.getStatus()+"\"" +
                    "}";
        } else {
            User u = (User)o;
            jsonString ="{"+
                    "\"userId\":\""+createId(6)+"\"," +
                    "\"lastName\":\""+u.getLastName()+"\"," +
                    "\"firstName\":\""+u.getFirstName() +"\"," +
                    "\"email\":\""+u.getEmail()+"\"," +
                    "\"gsm\":\""+u.getGsm()+"\"," +
                    "\"birthDate\":\""+u.getBirthDate()+"\"," +
                    "\"password\":\""+DigestUtils.md5Hex(u.getPassword())+"\"" +
                    "}";
        }
        indReq.source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = client.index(indReq, RequestOptions.DEFAULT);
        String ident = indexResponse.getId();
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("The document has been created");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("The document has been uploaded");
        } else {
            System.out.println("Nothing has been changed");
        }
        updateId(table,id);
    }

    /*Function of creation of instances*/
    public Flight createFlight(Map<String,Object> map){
        Flight f = new Flight(map.get("atcNumber").toString(),map.get("departureAerodrom").toString(),map.get("date").toString(),map.get("departureTime").toString(),map.get("seats").toString(),map.get("type").toString(),map.get("arrivalAerodrom").toString(),map.get("arrivalTime").toString(),map.get("price").toString(),map.get("userId").toString());
        f.setFlightId(map.get("flightId").toString());
        return f;
    }

    public Licence createLicence(Map<String,Object> map) throws ParseException{
        Date date = StringToDate(map,"validityDate");
        return new Licence(map.get("licenceId").toString(),map.get("userId").toString(),date);
    }

    public Message createMessage(Map<String,Object> map) throws ParseException{
        Date date = StringToDate(map,"sendingDate");
        return new Message(map.get("messageId").toString(),map.get("content").toString(),map.get("senderId").toString(),map.get("receiverId").toString(),date);

    }

    public Plane createPlane(Map<String,Object> map){
        return new Plane(map.get("atcNumber").toString(),Integer.parseInt(map.get("numberSeats").toString()));
    }

    public Reservation createReservation(Map<String,Object> map){
        return new Reservation(map.get("reservationId").toString(),map.get("userId").toString(),map.get("flightId").toString(),Integer.parseInt(map.get("nbPlaces").toString()),Double.parseDouble(map.get("price").toString()),map.get("status").toString());
    }

    public User createUser(Map<String,Object> map){
        return new User(map.get("lastName").toString(),map.get("firstName").toString(),map.get("email").toString(),map.get("password").toString(),map.get("birthDate").toString(),map.get("gsm").toString());
    }
    /*GET functions*/
    /*Return list of a specific table by transforming the list of map of the this table*/
    public ArrayList<Flight> allFlight() throws IOException, ParseException {
        ArrayList<Flight> list = new ArrayList<Flight>();
        ArrayList<Map<String,Object>> mapList = listMap("flight");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            Flight f = createFlight(map);
            list.add(f);
        }
        return list;
    }

    public ArrayList<Licence> allLicence() throws IOException, ParseException {
        ArrayList<Licence> list = new ArrayList<Licence>();
        ArrayList<Map<String,Object>> mapList = listMap("licence");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            list.add(createLicence(map));
        }
        return list;
    }

    public ArrayList<Message> allMessage() throws IOException, ParseException {
        ArrayList<Message> list = new ArrayList<Message>();
        ArrayList<Map<String,Object>> mapList = listMap("message");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            list.add(createMessage(map));
        }
        return list;
    }

    public ArrayList<Plane> allPlane() throws IOException, ParseException {
        ArrayList<Plane> list = new ArrayList<Plane>();
        ArrayList<Map<String,Object>> mapList = listMap("plane");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            list.add(createPlane(map));
        }
        return list;
    }

    public ArrayList<Reservation> allReservation() throws IOException, ParseException {
        ArrayList<Reservation> list = new ArrayList<Reservation>();
        ArrayList<Map<String,Object>> mapList = listMap("reservation");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            list.add(createReservation(map));
        }
        return list;
    }

    public ArrayList<User> allUser() throws IOException, ParseException {
        ArrayList<User> list = new ArrayList<User>();
        ArrayList<Map<String,Object>> mapList = listMap("user");
        for(int i = 0; i<mapList.size(); i++) {
            Map<String,Object> map = mapList.get(i);
            list.add(createUser(map));
        }
        return list;
    }

    /*Return a list a flight by specific Aerodrom(departure or arrival)*/
    public ArrayList<Flight> getFlights(String departureAerodromSearched, String arrivalAerodromSearched, String dateSearched, String typeSearched) throws Exception{
        ArrayList<Flight> list = new ArrayList<Flight>();
        ArrayList<Flight> listAfterDate = new ArrayList<Flight>();
        ArrayList<Map<String,Object>> mapList = listMap("flight");
        if(departureAerodromSearched != null && arrivalAerodromSearched == null && dateSearched == null && typeSearched == null) {
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                if (map.get("departureAerodrom").toString().equals(departureAerodromSearched)) {
                    Flight f = createFlight(map);
                    if (Integer.parseInt(f.getSeats()) > 0) list.add(f);
                } //Search with departure aerodrom
            }
        }
        if(departureAerodromSearched == null && arrivalAerodromSearched == null && dateSearched != null && typeSearched == null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                Date d = StringToDate(map,"date");
                if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0)) {
                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                } //Search with date
            }
        }
        if(departureAerodromSearched == null && arrivalAerodromSearched != null && dateSearched == null && typeSearched == null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                if (map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched)) {
                    Flight f = createFlight(map);
                    if (Integer.parseInt(f.getSeats()) > 0) list.add(f);
                } //Search with arrival aerodrom
            }
        }
        if(departureAerodromSearched == null && arrivalAerodromSearched == null && dateSearched == null && typeSearched != null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                if (map.get("type").toString().equals(typeSearched)) {
                    Flight f = createFlight(map);
                    if (Integer.parseInt(f.getSeats()) > 0) list.add(f);
                } //Search with type
            }
        }
        if(departureAerodromSearched != null && arrivalAerodromSearched != null && dateSearched == null && typeSearched == null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                if (map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched)) {
                    Flight f = createFlight(map);
                    if (Integer.parseInt(f.getSeats()) > 0) list.add(f);
                } //Search with departure and arrival aerodrom
            }
        }
        if(departureAerodromSearched != null && arrivalAerodromSearched == null && dateSearched != null && typeSearched == null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                Date d = StringToDate(map,"date");
                if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) > 0)) {
                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                } //Search with departure aerodrom and date
            }
        }
        if(departureAerodromSearched != null && arrivalAerodromSearched == null && dateSearched == null && typeSearched != null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                if (map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("type").toString().equals(typeSearched)) {
                    Flight f = createFlight(map);
                    if (Integer.parseInt(f.getSeats()) > 0) list.add(f);
                } //Search with departure aerodrom and type
            }
        }
        if(departureAerodromSearched == null && arrivalAerodromSearched != null && dateSearched != null && typeSearched == null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                Date d = StringToDate(map,"date");
                if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched))) > 0) {
                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                } //Search with arrival aerodrom and date
            }
        }
        if(departureAerodromSearched == null && arrivalAerodromSearched != null && dateSearched == null && typeSearched != null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                if (map.get("type").toString().equals(typeSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched)) {
                    Flight f = createFlight(map);
                    if (Integer.parseInt(f.getSeats()) > 0) list.add(f);
                } //Search with arrival aerodrom and type
            }
        }
        if(departureAerodromSearched == null && arrivalAerodromSearched == null && dateSearched != null && typeSearched != null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                Date d = StringToDate(map,"date");
                if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("type").toString().equals(typeSearched) && (d.compareTo(StringToDate(dateSearched)) > 0)) {
                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                } //Search with date and type
            }
        }
        if(departureAerodromSearched != null && arrivalAerodromSearched != null && dateSearched != null && typeSearched == null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                Date d = StringToDate(map,"date");
                if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched))) > 0) {
                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                } //Search with date, departure and arrival aerodrom
            }
        }
        if(departureAerodromSearched != null && arrivalAerodromSearched != null && dateSearched == null && typeSearched != null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                if (map.get("type").toString().equals(typeSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched)) {
                    Flight f = createFlight(map);
                    if (Integer.parseInt(f.getSeats()) > 0) list.add(f);
                } //Search with type, departure and arrival aerodrom
            }
        }
        if(departureAerodromSearched == null && arrivalAerodromSearched != null && dateSearched != null && typeSearched != null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                Date d = StringToDate(map,"date");
                if (map.get("type").toString().equals(typeSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched)) {
                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                } //Search with type, date and arrival aerodrom
            }
        }
        if(departureAerodromSearched != null && arrivalAerodromSearched == null && dateSearched != null && typeSearched != null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                Date d = StringToDate(map,"date");
                if (map.get("type").toString().equals(typeSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0)) {
                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                } //Search with type, date and departure aerodrom
            }
        }
        if(departureAerodromSearched != null && arrivalAerodromSearched != null && dateSearched != null && typeSearched != null){
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = mapList.get(i);
                Date d = StringToDate(map,"date");
                if (map.get("type").toString().equals(typeSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0)) {
                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                } //Search with type, date, departure and arrival aerodrom
            }
        }
        if(list.size() == 0) return listAfterDate;
        return list;
    }

    private void addToFlightSearchList(String dateSearched, ArrayList<Flight> list, ArrayList<Flight> listAfterDate, Map<String, Object> map, Date d) throws ParseException {
        Flight f = createFlight(map);
        if ((Integer.parseInt(f.getSeats()) > 0) && (d.compareTo(StringToDate(dateSearched)) == 0)) list.add(f);
        if ((Integer.parseInt(f.getSeats()) > 0) && (d.compareTo(StringToDate(dateSearched)) > 0)) listAfterDate.add(f);
    }

    /*Get a specific value of a table by using an id(user,flight, etc)*/
    public Map<String,Object> getLineTable(String table, String id) throws IOException {
        ArrayList<Map<String,Object>> list = listMap(table);
        for(int i = 0; i<list.size(); i++){
            Map<String,Object> map = list.get(i);
            if(map.containsValue(id))
                return map;
        }
        return null;
    }

    /*UPDATE functions*/
    public void updateLicenceInIndex(Object o) throws Exception{
        String table = getTable(o);
        Licence l = (Licence) o;
        String licenceId = l.getLicenceId();
        UpdateRequest request = new UpdateRequest(
                table,
                "info",
                licenceId);
        String jsonString = "{" +
                "\"validityDate\":\""+l.getValidityDate()+"\"," +
                "}";
        updateCheck(request, jsonString);
    }

    private void updateCheck(UpdateRequest request, String jsonString) throws IOException {
        request.doc(jsonString, XContentType.JSON);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("The document has been created");
        } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("The document has been updated");
        }
    }

    public void updateUserInIndex(Object o) throws Exception{
        String table = getTable(o);
        User u = (User)o;
        String userId = u.getUserId();
        UpdateRequest request = new UpdateRequest(
                table,
                "info",
                userId);
        String jsonString = "{" +
                "\"lastName\":\""+u.getLastName()+"\"," +
                "\"firstName\":\""+u.getFirstName() +"\"," +
                "\"email\":\""+u.getEmail()+"\"," +
                "\"gsm\":\""+u.getGsm()+"\"," +
                "\"birthDate\":\""+u.getBirthDate()+"\"," +
                "\"password\":\""+u.getPassword()+"\"" +
                "}";
        updateCheck(request, jsonString);
    }

    public void updatePlaneInIndex(Object o) throws Exception {
        String table = getTable(o);
        Plane p = (Plane) o;
        String atcNumber = p.getAtcNumber();
        UpdateRequest request = new UpdateRequest(
                table,
                "info",
                atcNumber);
        String jsonString = "{" +
                "\"numberSeats\":\""+p.getNumberSeats()+"\"," +
                "}";
        updateCheck(request, jsonString);
    }

    public void updateFlightInIndex(Object o) throws Exception{
        String table = getTable(o);
        Flight f = (Flight) o;
        String flightId = f.getFlightId();
        UpdateRequest request = new UpdateRequest(
                table,
                "info",
                flightId);
        String jsonString = "{" +
                "\"departureAerodrom\":\""+f.getDepartureAerodrom()+"\"," +
                "\"arrivalAerodrom\":\""+f.getArrivalAerodrom() +"\"," +
                "\"date\":\""+f.getDate()+"\"," +
                "\"atcNumber\":\""+f.getAtcNumber()+"\"," +
                "}";
        updateCheck(request, jsonString);
    }

    /*DELETE function*/
    private void deleteCheck(DeleteRequest request) throws IOException {
        DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful())
            System.out.println("DELETE");
    }


    public void delete(String id, String table) throws IOException {
        SearchHit[] sh = arrayTable(table);
        for (int i = 0; i < sh.length; i++) {
            String _id = sh[i].getId();
            Map<String, Object> map = sh[i].getSourceAsMap();
            Object o;
            if (table.equals("flight")) o = map.get("flightId");
            else if (table.equals("licence")) o = map.get("licenceId");
            else if (table.equals("message")) o = map.get("messageId");
            else if (table.equals("plane")) o = map.get("atcNumber");
            else if (table.equals("reservation")) o = map.get("reservationId");
            else o = map.get("userId");
            if (o.equals(id)) {
                DeleteRequest request = new DeleteRequest(
                        table,
                        "info",
                        _id);
                deleteCheck(request);
            }
        }
    }
}
