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

    public boolean canConnect(String email, String mdp) throws IOException {
        ArrayList<User> l = allUser();
        for(User u : l) {
            if (email.equals(u.getEmail()) && mdp.equals(u.getPassword())) return true;
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
        return df.parse(d);
    }

    /*Convert into a Date and return it*/
    public Date StringToDate(String d) throws ParseException{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(d);
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

    /*Return true if the user id already exists, false if not*/
    public boolean ifUserIdExist(String id) throws IOException {
        ArrayList<User> l = allUser();
        for(User u : l){
            if(u.getUserId().equals(id)) return true;
        }
        return false;
    }

    public boolean ifFlightIdExist(String id) throws IOException {
        ArrayList<Flight> l = allFlight();
        for(Flight f : l){
            if(f.getFlightId().equals(id)) return true;
        }
        return false;
    }

    /*INDEX function*/
    /*The licence argument is for if the object is a user and if he is a pilot, else it's null*/
    public void indexDB(Object o, Licence licence) throws Exception{
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
        if(table.equals("flight")){
            Flight f = (Flight)o;
            String tmpId = createId(7);
            while(ifFlightIdExist(tmpId))
                tmpId = createId(7);
            f.setFlightId(tmpId);
            jsonString ="{"+
                    "\"flightId\":\""+tmpId+"\"," +
                    "\"atcNumber\":\""+f.getAtcNumber()+"\"," +
                    "\"departureAerodrom\":\""+f.getDepartureAerodrom()+"\"," +
                    "\"date\":\""+f.getDate()+"\"," +
                    "\"departureTime\":\""+f.getDepartureTime()+"\"," +
                    "\"allSeats\":\""+f.getAllSeats()+"\"," +
                    "\"remainingSeats\":\""+f.getRemainingSeats()+"\"," +
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
                    "\"validityDate\":\""+l.getValidityDate()+"\"" +
                    "}";
        } else if(table.equals("message")){
            Message m = (Message)o;
            jsonString ="{"+
                    "\"messageId\":\""+m.getMessageId() +"\"," +
                    "\"content\":\""+m.getContent()+"\"," +
                    "\"senderId\":\""+m.getSenderId() +"\"," +
                    "\"receiverId\":\""+m.getReceiverId()+"\"," +
                    "\"sendingDate\":\""+m.getSendingDate()+"\"" +
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
                    "\"date\":\""+r.getDate()+"\"," +
                     "\"price\":\""+r.getPrice()+"\"," +
                    "\"status\":\""+r.getStatus()+"\"" +
                    "}";
        } else {
            User u = (User)o;
            String tmpId = createId(6);
            while(ifUserIdExist(tmpId))
                tmpId = createId(6);
            u.setUserId(tmpId);
            jsonString = "{"+
                    "\"userId\":\""+tmpId+"\"," +
                    "\"lastName\":\""+u.getLastName()+"\"," +
                    "\"firstName\":\""+u.getFirstName() +"\"," +
                    "\"email\":\""+u.getEmail()+"\"," +
                    "\"gsm\":\""+u.getGsm()+"\"," +
                    "\"birthDate\":\""+u.getBirthDate()+"\"," +
                    "\"password\":\""+u.getPassword()+"\"," +
                    "\"typeUser\":\""+u.getTypeUser()+"\""+
                    "}";
        }
        indReq.source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = client.index(indReq, RequestOptions.DEFAULT);
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println(table+" has been created");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println(table +" has been uploaded");
        } else {
            System.out.println("Nothing has been changed");
        }
        updateId(table,id);
        if(table.equals("user")){
            User u = (User)o;
            if(u.getTypeUser().equals("pilot") && licence != null) {
                id = getIdMax1("licence")+1;
                licence.setUserId(u.getUserId());
                table = getTable(licence);
                IndexRequest indReq1 = new IndexRequest(
                        table,
                        "info",
                        ""+id);
                jsonString = "{" +
                        "\"licenceId\":\"" + createId(5) + "\"," +
                        "\"userId\":\"" + licence.getUserId() + "\"," +
                        "\"validityDate\":\"" + licence.getValidityDate() + "\"," +
                        "\"mark\":\"" + licence.getMark() + "\"," +
                        "\"numberHoursFlight\":\"" + licence.getNumberHoursFlight() + "\"" +
                        "}";

                indReq1.source(jsonString, XContentType.JSON);
                IndexResponse indexResponse1 = client.index(indReq1, RequestOptions.DEFAULT);
                if (indexResponse1.getResult() == DocWriteResponse.Result.CREATED) {
                    System.out.println("licence has been created");
                } else if (indexResponse1.getResult() == DocWriteResponse.Result.UPDATED) {
                    System.out.println("licence has been uploaded");
                } else {
                    System.out.println("Nothing has been changed");
                }
                updateId(table, id);
            }
        }
    }

    /*Function of creation of instances*/
    public Flight createFlight(Map<String,Object> map){
        Flight f = new Flight(map.get("atcNumber").toString(),map.get("departureAerodrom").toString(),map.get("date").toString(),map.get("departureTime").toString(),Integer.parseInt(map.get("allSeats").toString()),Integer.parseInt(map.get("remainingSeats").toString()),map.get("type").toString(),map.get("arrivalAerodrom").toString(),map.get("arrivalTime").toString(),map.get("price").toString(),map.get("userId").toString());
        f.setFlightId(map.get("flightId").toString());
        return f;
    }

    public Licence createLicence(Map<String,Object> map) {
        return new Licence(map.get("licenceId").toString(),map.get("userId").toString(),map.get("validityDate").toString(),Integer.parseInt(map.get("mark").toString()),Integer.parseInt(map.get("numberHoursFlight").toString()));
    }

    public Message createMessage(Map<String,Object> map) {
        return new Message(map.get("messageId").toString(),map.get("content").toString(),map.get("senderId").toString(),map.get("receiverId").toString(),map.get("sendingDate").toString());
    }

    public Plane createPlane(Map<String,Object> map){
        return new Plane(map.get("atcNumber").toString(),Integer.parseInt(map.get("numberSeats").toString()));
    }

    public Reservation createReservation(Map<String,Object> map){
        Reservation r = new Reservation(map.get("reservationId").toString(),map.get("userId").toString(),map.get("flightId").toString(),Integer.parseInt(map.get("nbPlaces").toString()),Double.parseDouble(map.get("price").toString()),map.get("status").toString());
        //r.setDate(map.get("date").toString());
        return r;
    }

    public User createUser(Map<String,Object> map){
        User newUser= new User(map.get("firstName").toString(),map.get("lastName").toString(),map.get("email").toString(),map.get("password").toString(),map.get("birthDate").toString(),map.get("gsm").toString(),map.get("typeUser").toString());
        newUser.setUserId(map.get("userId").toString());
        return newUser;
    }
    /*GET functions*/

    /*Return the user using a specific email address*/
    public User getUserByEmail(String email) throws IOException{
        ArrayList<User> l = allUser();
        for (User u : l) {
            if(u.getEmail().equals(email)) return u;
        }
        return null;
    }

    /*Return the user using a specific id*/
    public User getUserById(String id) throws IOException{
        ArrayList<User> l = allUser();
        for (User u : l) {
            if(u.getUserId().equals(id)) return u;
        }
        return null;
    }

    /*Return list of a specific table by transforming the list of map of the this table*/
    public ArrayList<Flight> allFlight() throws IOException{
        ArrayList<Flight> list = new ArrayList<Flight>();
        ArrayList<Map<String,Object>> mapList = listMap("flight");
        for(Map<String,Object> map : mapList) {
            Flight f = createFlight(map);
            list.add(f);
        }
        return list;
    }

    public ArrayList<Licence> allLicence() throws IOException {
        ArrayList<Licence> list = new ArrayList<Licence>();
        ArrayList<Map<String,Object>> mapList = listMap("licence");
        for(Map<String,Object> map : mapList) {
            list.add(createLicence(map));
        }
        return list;
    }

    public ArrayList<Message> allMessage() throws IOException {
        ArrayList<Message> list = new ArrayList<Message>();
        ArrayList<Map<String,Object>> mapList = listMap("message");
        for(Map<String,Object> map : mapList) {
            list.add(createMessage(map));
        }
        return list;
    }

    public ArrayList<Plane> allPlane() throws IOException {
        ArrayList<Plane> list = new ArrayList<Plane>();
        ArrayList<Map<String,Object>> mapList = listMap("plane");
        for(Map<String,Object> map : mapList) {
            list.add(createPlane(map));
        }
        return list;
    }

    public ArrayList<Reservation> allReservation() throws IOException {
        ArrayList<Reservation> list = new ArrayList<Reservation>();
        ArrayList<Map<String,Object>> mapList = listMap("reservation");
        for(Map<String,Object> map : mapList) {
            list.add(createReservation(map));
        }
        return list;
    }

    public ArrayList<User> allUser() throws IOException {
        ArrayList<User> list = new ArrayList<User>();
        ArrayList<Map<String,Object>> mapList = listMap("user");
        for(Map<String,Object> map : mapList) {
            list.add(createUser(map));
        }
        return list;
    }

    /*Return a list a flight by specific Aerodrom(departure or arrival)*/
    public ArrayList<Flight> getFlights(String departureAerodromSearched, String arrivalAerodromSearched, String dateSearched, String typeSearched,String priceSearched, String seatsSearched) throws Exception{
        int cNull = 0;
        if(departureAerodromSearched == null) cNull++;
        if(arrivalAerodromSearched == null) cNull++;
        if(dateSearched == null) cNull++;
        if(typeSearched == null) cNull++;
        if(priceSearched == null) cNull++;
        if(seatsSearched == null) cNull++;
        if(cNull == 6){
            return allFlight();
        }
        else if(cNull == 5) return auxFlights1(departureAerodromSearched, arrivalAerodromSearched, dateSearched, typeSearched, priceSearched, seatsSearched);
        else if(cNull == 4) return auxFlights2(departureAerodromSearched, arrivalAerodromSearched, dateSearched, typeSearched, priceSearched, seatsSearched);
        else if(cNull == 3) return auxFlights3(departureAerodromSearched, arrivalAerodromSearched, dateSearched, typeSearched, priceSearched, seatsSearched);
        else if(cNull == 2) return auxFlights4(departureAerodromSearched, arrivalAerodromSearched, dateSearched, typeSearched, priceSearched, seatsSearched);
        else if(cNull == 1) return auxFlights5(departureAerodromSearched, arrivalAerodromSearched, dateSearched, typeSearched, priceSearched, seatsSearched);
        else return auxFlights6(departureAerodromSearched, arrivalAerodromSearched, dateSearched, typeSearched, priceSearched, seatsSearched);
    }

    //Search function for one argument
    public ArrayList<Flight> auxFlights1(String departureAerodromSearched, String arrivalAerodromSearched, String dateSearched, String typeSearched,String priceSearched, String seatsSearched) throws Exception{
        ArrayList<Flight> list = new ArrayList<Flight>();
        ArrayList<Flight> listAfterDate = new ArrayList<Flight>();
        ArrayList<Map<String,Object>> mapList = listMap("flight");
        for (Map<String, Object> map : mapList) {
            if (departureAerodromSearched != null) {
                if (map.get("departureAerodrom").toString().equals(departureAerodromSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                    list.add(createFlight(map));
                //Search with departure aerodrom
            }
            else if (arrivalAerodromSearched != null) {
                if (map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                    list.add(createFlight(map));
                //Search with arrival aerodrom
            }
            else if (dateSearched != null) {
                Date d = StringToDate(map, "date");
                if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                //Search with date
            }
            else if (typeSearched != null) {
                if (map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                    list.add(createFlight(map));
                //Search with type
            }
            else if (priceSearched != null) {
                if (Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                    list.add(createFlight(map));
                //Search with price
            }
            else {
                if (Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                    list.add(createFlight(map));
                //Search with seats
            }
        }
        if(list.size() == 0) return listAfterDate;
        return list;
    }

    //Search function for two arguments
    public ArrayList<Flight> auxFlights2(String departureAerodromSearched, String arrivalAerodromSearched, String dateSearched, String typeSearched,String priceSearched, String seatsSearched) throws Exception {
        ArrayList<Flight> list = new ArrayList<Flight>();
        ArrayList<Flight> listAfterDate = new ArrayList<Flight>();
        ArrayList<Map<String, Object>> mapList = listMap("flight");
        for (Map<String, Object> map : mapList) {
            if (departureAerodromSearched != null) {
                if(arrivalAerodromSearched != null) {
                    if (map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                        list.add(createFlight(map));
                }//Search with departure and arrival aerodrom
                else if (dateSearched != null) {
                    Date d = StringToDate(map, "date");
                    if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) > 0 && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) > 0)) {
                        addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                    } //Search with departure aerodrom and date
                }
                else if (typeSearched != null) {
                    if (map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                        list.add(createFlight(map));
                    //Search with departure aerodrom and type
                }
                else if (priceSearched != null) {
                    if (map.get("departureAerodrom").toString().equals(departureAerodromSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                        list.add(createFlight(map));
                }//Search with departure aerodrom and price
                else{
                    if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                        list.add(createFlight(map));
                }//Search with departure aerodrom and seats
            }
            else if(arrivalAerodromSearched != null){
                if (dateSearched != null) {
                    Date d = StringToDate(map, "date");
                    if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && d.compareTo(StringToDate(dateSearched)) > 0 && Integer.parseInt(map.get("remainingSeats").toString()) > 0) {
                        addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                    } //Search with arrival aerodrom and date
                }
                else if (typeSearched != null) {
                    if (map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                        list.add(createFlight(map));
                    //Search with arrival aerodrom and type
                }
                else if (priceSearched != null) {
                    if (map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                        list.add(createFlight(map));
                }//Search with arrival aerodrom and price
                else{
                    if(map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                        list.add(createFlight(map));
                }//Search with arrival aerodrom and seats
            }
            else if (dateSearched != null) {
                Date d = StringToDate(map, "date");
                if(typeSearched != null) {
                    if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("type").toString().equals(typeSearched) && (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) > 0) {
                        addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                    } //Search with date and type
                }
                else if(priceSearched != null){
                    if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) > 0) {
                        addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                    } //Search with date and price
                }
                else{
                    if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched) && (d.compareTo(StringToDate(dateSearched)) > 0)) {
                        addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                    } //Search with date and seats
                }
            }
            else if(priceSearched != null && seatsSearched != null){
                if(Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                    list.add(createFlight(map));
                //Search with price and seats
            }
        }
        if(list.size() == 0) return listAfterDate;
        return list;
    }

    /*Search function for three arguments*/
    public ArrayList<Flight> auxFlights3(String departureAerodromSearched, String arrivalAerodromSearched, String dateSearched, String typeSearched,String priceSearched, String seatsSearched) throws Exception {
        ArrayList<Flight> list = new ArrayList<Flight>();
        ArrayList<Flight> listAfterDate = new ArrayList<Flight>();
        ArrayList<Map<String,Object>> mapList = listMap("flight");
        for (Map<String, Object> map : mapList) {
            if (departureAerodromSearched != null) {
                if (arrivalAerodromSearched != null) {
                    if (dateSearched != null) {
                        Date d = StringToDate(map, "date");
                        if ((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && d.compareTo(StringToDate(dateSearched)) > 0 && Integer.parseInt(map.get("remainingSeats").toString()) > 0) {
                            addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                        } //Search with date, departure and arrival aerodrom
                    } else if (typeSearched != null) {
                        if (map.get("type").toString().equals(typeSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                            list.add(createFlight(map));
                        //Search with type, departure and arrival aerodrom
                    } else if (priceSearched != null) {
                        if (Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                            list.add(createFlight(map));
                        //Search with price, departure and arrival aerodrom
                    } else {
                        if (Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched))
                            list.add(createFlight(map));
                        //Search with seats, departure and arrival aerodrom
                    }
                }
                else if(dateSearched != null){
                    Date d = StringToDate(map, "date");
                    if(typeSearched != null){
                        if (map.get("type").toString().equals(typeSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) > 0) {
                            addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                        } //Search with departure aerodrom,date and type
                    } else if(priceSearched != null){
                        if (Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) > 0) {
                            addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                        } //Search with departure aerodrom,date and price
                    } else {
                        if (Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0)) {
                            addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                        } //Search with departure aerodrom,date and seats
                    }
                }
                else if(typeSearched != null){
                    if(priceSearched != null){
                        if (map.get("type").toString().equals(typeSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("price").toString().equals(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                            list.add(createFlight(map));
                        //Search with departure aerodrom,type and price
                    } else {
                        if (map.get("type").toString().equals(typeSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                            list.add(createFlight(map));
                        //Search with departure aerodrom,type and seats
                    }
                }
                else {
                    if (Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && map.get("departureAerodrom").toString().equals(departureAerodromSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                        list.add(createFlight(map));
                    //Search with departure aerodrom,price and seats
                }
            }
            else if(arrivalAerodromSearched != null){
                if(dateSearched != null){
                    Date d = StringToDate(map, "date");
                    if(typeSearched != null){
                        if (map.get("type").toString().equals(typeSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) > 0) {
                            addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                        } //Search with arrival aerodrom,date and type
                    } else if(priceSearched != null){
                        if (Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) > 0) {
                            addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                        } //Search with arrival aerodrom,date and price
                    } else {
                        if (Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0)) {
                            addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                        } //Search with arrival aerodrom,date and seats
                    }
                } else if (typeSearched != null) {
                    if(priceSearched != null){
                        if (map.get("type").toString().equals(typeSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && map.get("price").toString().equals(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                            list.add(createFlight(map));
                        //Search with arrival aerodrom,type and price
                    } else {
                        if (map.get("type").toString().equals(typeSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                            list.add(createFlight(map));
                        //Search with arrival aerodrom,type and seats
                    }
                } else {
                    if (Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                        list.add(createFlight(map));
                    //Search with arrival aerodrom,price and seats
                }
            } else if(dateSearched != null) {
                Date d = StringToDate(map, "date");
                if(typeSearched != null){
                    if(priceSearched != null) {
                        if (map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) > 0) {
                            addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                        } //Search with date, type and price
                    } else {
                        if (map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0)) {
                            addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                        } //Search with date, type and seats
                    }
                } else {
                    if (Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0)) {
                        addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                    } //Search with date, price and seats
                }
            } else {
                if (Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                    list.add(createFlight(map));
                //Search with type,price and seats
            }
        }
        if(list.size() == 0) return listAfterDate;
        return list;
    }

    /*Search function for four arguments*/
    public ArrayList<Flight> auxFlights4(String departureAerodromSearched, String arrivalAerodromSearched, String dateSearched, String typeSearched,String priceSearched, String seatsSearched) throws Exception {
        ArrayList<Flight> list = new ArrayList<Flight>();
        ArrayList<Flight> listAfterDate = new ArrayList<Flight>();
        ArrayList<Map<String,Object>> mapList = listMap("flight");
        for (Map<String, Object> map : mapList) {
            if(departureAerodromSearched != null){
                if(arrivalAerodromSearched != null){
                    if(dateSearched != null) {
                        Date d = StringToDate(map, "date");
                        if(typeSearched != null){
                            if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0){
                                addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                            } //Search with date, type, departure and arrival aerodrom
                        } else if(priceSearched != null){
                            if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0){
                                addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                            } //Search with date, price, departure and arrival aerodrom
                        } else {
                            if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched)){
                                addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                            } //Search with date, seats, departure and arrival aerodrom
                        }
                    } else if(typeSearched != null) {
                        if(priceSearched != null){
                            if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                                list.add(createFlight(map));
                            //Search with type, price, departure and arrival aerodrom
                        } else {
                            if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                                list.add(createFlight(map));
                            //Search with type, seats, departure and arrival aerodrom
                        }
                    } else {
                        if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                            list.add(createFlight(map));
                        //Search with price, seats, departure and arrival aerodrom
                    }
                } else if(dateSearched != null) {
                    Date d = StringToDate(map, "date");
                    if(typeSearched != null){
                        if(priceSearched != null){
                            if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0)
                                addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                            //Search with departure aerodrom, date, type and price
                        } else {
                            if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                                addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                            //Search with departure aerodrom, date, type and seats
                        }
                    } else {
                        if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                            addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                        //Search with departure aerodrom, date, price and seats
                    }
                } else {
                    if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                        list.add(createFlight(map));
                    //Search with departure aerodrom, type, price and seats
                }
            } else if(arrivalAerodromSearched != null) {
                if(dateSearched != null) {
                    Date d = StringToDate(map, "date");
                    if(typeSearched != null){
                        if(priceSearched != null){
                            if(map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) > 0 && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched))
                                addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                            //Search with arrival aerodrom, date, type and price
                        } else {
                            if((map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched) && map.get("type").toString().equals(typeSearched)))
                                addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                            //Search with arrival aerodrom, date, type and seats
                        }
                    } else {
                        if(map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                            addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                        //Search with arrival aerodrom, date, price and seats
                    }
                } else {
                    if(map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                        list.add(createFlight(map));
                    //Search with departure aerodrom, type, price and seats
                }
            } else {
                Date d = StringToDate(map, "date");
                if((d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                    list.add(createFlight(map));
                //Search with date, type, price and seats
            }
        }
        if(list.size() == 0) return listAfterDate;
        return list;
    }

    /*Search function for five arguments*/
    public ArrayList<Flight> auxFlights5(String departureAerodromSearched, String arrivalAerodromSearched, String dateSearched, String typeSearched,String priceSearched, String seatsSearched) throws Exception {
        ArrayList<Flight> list = new ArrayList<Flight>();
        ArrayList<Flight> listAfterDate = new ArrayList<Flight>();
        ArrayList<Map<String,Object>> mapList = listMap("flight");
        for (Map<String, Object> map : mapList) {
            Date d = StringToDate(map, "date");
            if(departureAerodromSearched != null){
                if(arrivalAerodromSearched != null){
                    if(dateSearched != null){
                        if (typeSearched != null){
                            if(priceSearched != null){
                                if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) > 0){
                                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                                }//Search with date, type, price, departure and arrival aerodrom
                            } else {
                                if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched)){
                                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                                }//Search with date, type, seats, departure and arrival aerodrom
                            }
                        } else {
                            if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched)){
                                addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                            }//Search with date, price, seats, departure and arrival aerodrom
                        }
                    } else {
                        if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched)){
                            list.add(createFlight(map));
                        }//Search with type, price, seats, departure and arrival aerodrom
                    }
                } else {
                    if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched)){
                        addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                    }//Search with departure aerodrom, date, type, price and seats
                }
            } else {
                if(map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched)){
                    addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
                }//Search with arrival aerodrom, date, type, price and seats
            }
        }
        if(list.size() == 0) return listAfterDate;
        return list;
    }

        /*Search function for all arguments*/
    public ArrayList<Flight> auxFlights6(String departureAerodromSearched, String arrivalAerodromSearched, String dateSearched, String typeSearched,String priceSearched, String seatsSearched) throws Exception {
        ArrayList<Flight> list = new ArrayList<Flight>();
        ArrayList<Flight> listAfterDate = new ArrayList<Flight>();
        ArrayList<Map<String,Object>> mapList = listMap("flight");
        for (Map<String, Object> map : mapList) {
            Date d = StringToDate(map, "date");
            if(map.get("departureAerodrom").toString().equals(departureAerodromSearched) && map.get("arrivalAerodrom").toString().equals(arrivalAerodromSearched) && (d.compareTo(StringToDate(dateSearched)) == 0) || (d.compareTo(StringToDate(dateSearched)) > 0) && map.get("type").toString().equals(typeSearched) && Integer.parseInt(map.get("price").toString()) <= Integer.parseInt(priceSearched) && Integer.parseInt(map.get("remainingSeats").toString()) >= Integer.parseInt(seatsSearched))
                addToFlightSearchList(dateSearched, list, listAfterDate, map, d);
        }
        if(list.size() == 0) return listAfterDate;
        return list;
    }
    private void addToFlightSearchList(String dateSearched, ArrayList<Flight> list, ArrayList<Flight> listAfterDate, Map<String, Object> map, Date d) throws ParseException {
        Flight f = createFlight(map);
        if ((f.getRemainingSeats() > 0) && (d.compareTo(StringToDate(dateSearched)) == 0)) list.add(f);
        if ((f.getRemainingSeats() > 0) && (d.compareTo(StringToDate(dateSearched)) > 0)) listAfterDate.add(f);
    }

    /*Get a specific value of a table by using an id(user,flight, etc)*/
    public Map<String,Object> getLineTable(String table, String id) throws IOException {
        ArrayList<Map<String,Object>> list = listMap(table);
        for(Map<String,Object> map : list){
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
        for (SearchHit s : sh) {
            String _id = s.getId();
            Map<String, Object> map = s.getSourceAsMap();
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
