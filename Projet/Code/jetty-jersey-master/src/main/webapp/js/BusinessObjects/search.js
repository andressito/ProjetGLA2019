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

function detailsFlight(flightId) {
    console.log(flightId);
    $("#flightList").remove();
}

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
            const flightId = result[i].flightId;
            const departureAerodrom = result[i].departureAerodrom;
            const type = result[i].type;
            const arrivalAerodrom = result[i].arrivalAerodrom;
            const remainingSeats= result[i].remainingSeats;
            const price = result[i].price;
            const userId = result[i].userId;
            $.ajax({
                url: "http://localhost:8080/ws/user/users/"+userId,
                type: "GET",
                contentType: "application/json",
                cache: false,
                dataType: "json"
            }).done(function (result) {
                var tr_str =
                    '<div class="col-md-3" ><div class="price-box">'+
                    '<h2 class="pricing-plan">' + type + '</h2>' +
                    '<div class="price"><sup class="currency">€</sup>' + price + '<small>/seat</small></div>' +
                    '<p> From :' + departureAerodrom + '</p>' +
                    '<p> To :' + arrivalAerodrom+ '</p>' +
                    '<p> Remaining Seats :' + remainingSeats+ '</p>' +
                    '<p> Pilot :' + result["firstName"] + '</p>' +
                    '<h6 id="idFlight1"> &#x2605; &#x2605; &#x2606; &#x2606; &#x2606; </h6>' +
                    '<a class="btn btn-select-plan btn-sm"   onclick="detailsFlight(\'' + flightId + '\');" >Details</a>' +
                    '</div></div>';
                $("#flightList").append(tr_str);
            });

        }

    });
}