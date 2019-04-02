$(document).ready(function() {
    if(!localStorage.getItem("userId")){
        window.location.href="http://localhost:8080/";
    }else{
        if(document.getElementById('ArrivalA2')){
            if(document.getElementById('visit').checked){
                document.getElementById('ArrivalA2').style.display='none';
            }
            else{
                document.getElementById('ArrivalA2').style.display='inline';
            }
        }
        $("#menu").load('../Menu/MenuPilot.html');
    }
});
function visitDetect() {
    if(document.getElementById('visit').checked){
        document.getElementById('ArrivalA2').style.display='none';
    }else{
        document.getElementById('ArrivalA2').style.display='inline';
    }
}
//ajouter un vol prevu
function ajouter(){
    if($("#plane").val()=="" || $("#plane").val().length <2){
        $('#plane').focus();
        return false;
    }
    if($("#departureAirfield").val()=="" || $("#departureAirfield").val().length <2){
        $('#departureAirfield').focus();
        return false;
    }
    if(!document.getElementById('visit').checked){
        if($("#arrivalAirfield").val()=="" || $("#arrivalAirfield").val().length <2){
            $('#arrivalAirfield').focus();
            return false;
        }
    }

    if($("#departureDate").val()==""){
        $('#departureDate').focus();
        return false;
    }
    if($("#departureTime").val()==""){
        $('#departureAirfield').focus();
        return false;
    }
    if($("#arrivalTime").val()==""){
        $('#arrivalTime').focus();
        return false;
    }
    if($("#numberSeats").val()==""){
        $('#numberSeats').focus();
        return false;
    }
    if($("#price").val()==""){
        $('#price').focus();
        return false;
    }
    var plane=$("#plane").val();
    var departureAirfield=$("#departureAirfield").val();
    var departureDate=$("#departureDate").val();
    var today = new Date();
    var todayM= new Date();
    todayM.setHours(28);
    var valDate = new Date(departureDate);
    today.setMonth(6);
    console.log(today);
    console.log(todayM);
    //valdate sup todM et valdate inf today
    if(!(today>valDate && valDate>todayM)){
        swal({
            title: "ChuChuFly!",
            text: "Your flight back is too soon far or away  ",
            icon: "error"
        });
        return false;
    }
    var departureTime=$("#departureTime").val();
    var arrivalTime=$("#arrivalTime").val();
    var numberSeats=$("#numberSeats").val();
    var price=$("#price").val();
    var type;
    var arrivalAirfield;
    var userId=localStorage.getItem("userId");
    if(document.getElementById('visit').checked){
        type="visit";
        arrivalAirfield=departureAirfield;
    }else{
        type="oneway";
        arrivalAirfield=$("#arrivalAirfield").val();
    }
    var data= '{"atcNumber":"'+plane+'", "departureAerodrom":"'+departureAirfield+'", "date":"'+departureDate+'","departureTime":"'+departureTime+'","seats":"'+numberSeats+'","type":"'+type+'","arrivalAerodrom":"'+arrivalAirfield+'","arrivalTime":"'+arrivalTime+'","price":"'+price+'","userId":"'+userId+'"}';
    var dataUrl = "http://localhost:8080/ws/flight/create";
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
                icon: "success"
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
                icon: "error"
            });
        }
    });
}
//pour affcicher la liste des vols
/*function getFlightList(){
    var departureAerodrome=$("#departureAerodrome").val();
    var departureDate=$("#departureDate").val();
    //enregistrement dans les cookies
    this.createCookie('departureAerodrome',''+departureAerodrome);
    this.createCookie('departureDate',''+departureDate);
    document.location.href = "SearchList.html";
}*/
function getFlightList2(){
    //recupération reussie
    var dpAero = sessionStorage.getItem("departureAerodrome");
    var dpDate = sessionStorage.getItem("departureDate");
    sessionStorage.removeItem("departureAerodrome");
    sessionStorage.removeItem("departureDate");
    if(dpDate.length==0)
        dpDate="0";
    $.ajax({
        url: "http://localhost:8080/ws/flight/search/"+dpAero+"/"+dpDate,
        type: "GET",
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (result) {
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
            var nameUser;
            $.ajax({
                url: "http://localhost:8080/ws/user/users/"+userId,
                type: "GET",
                contentType: "application/json",
                cache: false,
                dataType: "json"
            }).done(function (result) {
                console.log(result);
               sessionStorage.setItem("nameUserSearch",result['firstName']);
            });
            console.log(sessionStorage.getItem("nameUserSearch"));
            var tr_str =
                "<div class='col-md-3' ><div class='price-box'>"+
                "<h2 class='pricing-plan'>" + type + "</h2>" +
                "<div class='price'><sup class='currency'>€</sup>" + price + "<small>/seat</small></div>" +
                "<p> From :" + departureAerodrom + "</p>" +
                "<p> To :" + arrivalAerodrom+ "</p>" +
                "<p> Pilot :" + sessionStorage.getItem("nameUserSearch") + "</p>" +
                "<h6 id='idFlight1'> &#x2605; &#x2605; &#x2606; &#x2606; &#x2606; </h6>" +
                "<a class='btn btn-select-plan btn-sm' onclick='detailsFlight("+ flightId +")' >Details</a>" +
                "</div></div>";
            $("#flightList").append(tr_str);

        }
        sessionStorage.removeItem("nameUserSearch");
    });
}
//voir les details d'un vols avec le caractéristique pour reserver
function  detailsFlight(id) {
    var flightId=id;
    console.log(flightId);
    var dataUrl = "http://localhost:8080/ws/flight/flights/"+flightId;
    /*$.ajax({
        url: dataUrl,
        type: "GET",
        contentType: "application/json",
        cache: false,
        dataType: "json",
    }).done(function (result) {
        console.log(result);
        //a completer pour afficher la page des details d'un vol
    });*/
}

/*function createCookie(name,value) {
    document.cookie.sup;
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
}*/