$(document).ready(function() {
    if(!localStorage.getItem("userId")){
        $("#menu").load('../Menu/Menu.html');
    }else{
        if (localStorage.getItem("typeUser") === "passenger") {
            $("#menu").load('../Menu/MenuPassenger.html');
        } else {
            $("#menu").load('../Menu/MenuPilot.html');
        }
    }
    getFlightList2();

    var carte = L.map('map').setView([46.3630104, 2.9846608], 6);
    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(carte);
});

function detailsFlight(flightId) {
    sessionStorage.setItem("saveIdFlight",flightId);
    window.location.href="http://localhost:8080/DetailsFlight.html";
    //$("#flightList").remove();
    /*$.ajax({
        url: "http://localhost:8080/ws/flight/flights/"+flightId,
        type: "GET",
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (result) {
        console.log(result);
        $("#fh5co-hero").load("http://localhost:8080/DetailsFlight.html");
        /*const flightId = result['flightId'];
        const departureAerodrom = result['departureAerodrom'];
        const type = result['type'];
        const arrivalAerodrom = result['arrivalAerodrom'];
        const remainingSeats= result['remainingSeats'];
        const price = result['price'];
        const userId = result['userId'];
        const dateFlight = result['date'];
        const departureTime= result['departureTime'];
        const arrivalTime= result['arrivalTime'];
        console.log(departureAerodrom);*/
        /*var tr_str =
            '<div class="col-md-3" ><div class="price-box">'+
            '<h2 class="pricing-plan">' + type + '</h2>' +
            '<div class="price"><sup class="currency">€</sup>' + price + '<small>/seat</small></div>' +
            '<p> From :' + departureAerodrom + '</p>' +
            '<p> To :' + arrivalAerodrom+ '</p>' +
            '<p> Remaining Seats :' + remainingSeats+ '</p>' +
            '<p> Pilot :' +  + '</p>' +
            '<h6 id="idFlight1"> &#x2605; &#x2605; &#x2606; &#x2606; &#x2606; </h6>' +
            '<a class="btn btn-select-plan btn-sm"   onclick="detailsFlight(\'' + flightId + '\');" >Details</a>' +
            '</div></div>';
        $("#flightList").append(tr_str);*/
        /*var ress =
            '<div class="col-md-5" ><div class="price-box">'+
            '<h2 class="pricing-plan">' + type + '</h2>' +
            '<div class="price"><sup class="currency">€</sup>' + price + '<small>/seat</small></div>' +
            '<p> From :' + departureAerodrom + '</p>' +
            '<p> Date :' + dateFlight + '</p>' +
            '<p> Departure Time :' + departureTime + '</p>' +
            '<p> Arrival Time :' + departureAerodrom + '</p>' +
            '<p> To :' + arrivalTime+ '</p>' +
            '<p> Remaining Seats :' + remainingSeats+ '</p>' +
            '<p> Pilot :' +  + '</p>' +
            '<h6 id="idFlight1"> &#x2605; &#x2605; &#x2606; &#x2606; &#x2606; </h6>' +
            '<a class="btn btn-select-plan btn-sm"   onclick="detailsFlight(\'' + flightId + '\');" >Details</a>' +
            '</div></div>';
    });*/

}

function getFlightList2(){
    //recupération reussie
    var dpAero = sessionStorage.getItem("departureAerodrome");
    var dpDate = sessionStorage.getItem("departureDate");
    //sessionStorage.removeItem("departureAerodrome");
    //sessionStorage.removeItem("departureDate");
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