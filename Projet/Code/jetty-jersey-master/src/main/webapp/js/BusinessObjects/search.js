$(document).ready(function() {
    if(!localStorage.getItem("userId")){
        $("#menu").load('../Menu/Menu.html');
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