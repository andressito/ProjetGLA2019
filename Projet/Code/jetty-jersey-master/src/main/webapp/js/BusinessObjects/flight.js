$(document).ready(function() {
    if(localStorage.getItem("userId")){
        window.location.href="http://localhost:8080/";
    }else{
        if(document.getElementById('passenger'))
            if(document.getElementById('passenger').checked){
                document.getElementById('Licence').style.display='none';
            }
            else{
                document.getElementById('Licence').style.display='inline';
            }
    }
});

function detecter(){
    if(document.getElementById('visit').checked){
        document.getElementById('ArrivalA2').style.display='none';
        document.getElementById('ArrivalT2').style.display='none';
    }
    else{
        document.getElementById('ArrivalA2').style.display='inline';
        document.getElementById('ArrivalT2').style.display='inline';
    }
}
//ajouter un vol prevu
function ajouter(){
    var plane=$("#Plane").val();
    var departureAerodrome=$("#DepartureA").val();
    var date=$("#Date").val();
    var departureTime=$("#DepartureT").val();
    var seats=$("#Seats").val();
    var arrivalAerodrome=$("#ArrivalA").val();
    var arrivalTime=$("#ArrivalT").val();
    var price=$("#Price").val();
    var type;
    //condition ^pourverifier si c'estune balade ou pas
    if(document.getElementById('visit').checked){
        type="visit";
        arrivalAerodrome=departureAerodrome;
    }else{type="oneway";}
    var data= '{"atcNumber":"'+plane+'", "departureAerodrom":"'+departureAerodrome+'", "date":"'+date+'","departureTime":"'+departureTime+'","seats":"'+seats+'","type":"'+type+'","arrivalAerodrom":"'+arrivalAerodrome+'","arrivalTime":"'+arrivalTime+'","price":"'+price+'","userId":"1"}';
    var dataUrl = "http://localhost:8080/ws/flight/create";
    //console.log(donner);
    $.ajax({
        url: dataUrl,
        type: "POST",
        data: data,
        contentType: "application/json",
        cache: false,
        dataType: "json",

    }).done(function (result) {
        console.log(result);
    }).success(function (result) {
        if(result){
            //si resultat bon, is Ok
            swal({
                title: "ChuChuFly!",
                text: "Flight recorded successfully!",
                icon: "success",
            });
            //effacement du contenu des inputs
            $("#Plane").val("");
            $("#DepartureA").val("");
            $("#Date").val("");
            $("#DepartureT").val("");
            $("#Seats").val("");
            $("#ArrivalA").val("");
            $("#ArrivalT").val("");
            $("#Price").val("");
            //fin de l'effacement
        }else{
            swal({
                title: "ChuChuFly!",
                text: "Registration error!",
                icon: "error",
            });
        }
    });
}
//pour affcicher la liste des vols
function getFlightList(){
    var departureAerodrome=$("#departureAerodrome").val();
    var departureDate=$("#departureDate").val();
    //enregistrement dans les cookies
    this.createCookie('departureAerodrome',''+departureAerodrome);
    this.createCookie('departureDate',''+departureDate);
    document.location.href = "SearchList.html";
}
function getFlightList2(){
    //recupération reussie
    var dpAero = this.readCookie('departureAerodrome');
    var dpDate = this.readCookie('departureDate');
    var data= '{"departureAerodrom":"'+dpAero+'", "date":"'+dpDate+'"}';
    var dataUrl = "http://localhost:8080/ws/flight/flights";
    $.ajax({
        url: dataUrl,
        type: "GET",
        //data: data,
        contentType: "application/json",
        cache: false,
        dataType: "json",
    }).done(function (result) {
        console.log(result);
        var len = result.length;
        for(var i=0; i<len; i++){
            var flightId = result[i].flightId;
            var atcNumber = result[i].atcNumber;
            var departureAerodrom = result[i].departureAerodrom;
            var date = result[i].date;
            var departureTime = result[i].departureTime;
            var type = result[i].type;
            var arrivalAerodrom = result[i].arrivalAerodrom;
            var arrivalTime = result[i].arrivalTime;
            var price = result[i].price;
            var userId = result[i].userId;

            var tr_str =
                "<div class='col-md-3' ><div class='price-box'>"+
                "<h2 class='pricing-plan'>" + type + "</h2>" +
                "<div class='price'><sup class='currency'>€</sup>" + price + "<small>/seat</small></div>" +
                "<p> From :" + departureAerodrom + "</p>" +
                "<p> To :" + arrivalAerodrom+ "</p>" +
                "<p> Pilot :" + userId + "</p>" +
                "<h6 id='idFlight1'> &#x2605; &#x2605; &#x2606; &#x2606; &#x2606; </h6>" +
                "<a class='btn btn-select-plan btn-sm' onclick='detailsFlight("+ flightId +")' >Details</a>" +
                "</div></div>";
            $("#flightList").append(tr_str);
        }
    });
}
//voir les details d'un vols avec le caractéristique pour reserver
function  detailsFlight(id) {
    var flightId=id;
    console.log(flightId);
    var dataUrl = "http://localhost:8080/ws/flight/flights/"+flightId;
    $.ajax({
        url: dataUrl,
        type: "GET",
        contentType: "application/json",
        cache: false,
        dataType: "json",
    }).done(function (result) {
        console.log(result);
        //a completer pour afficher la page des details d'un vol
    });
}

function createCookie(name,value) {
    document.cookie = name+"="+value+"; path=/";
}
function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}