$(document).ready(function() {
    if(localStorage.getItem("userId")) {
        var firstName;
        var typeUser;
        var userId;

        firstName = localStorage.getItem("firstName");
        typeUser = localStorage.getItem("typeUser");
        userId = localStorage.getItem("userId");

        //document.getElementById("bg").style.backgroundImage = "url('images/paris.jpg')";
        if (typeUser === "passenger") {
            $("#menu").load('../Menu/MenuPassenger.html');
            document.getElementById('myReservations').style.display="'inline";
        } else {
            $("#menu").load('../Menu/MenuPilot.html');
        }
        getServerData("http://localhost:8080/ws/reservation/reservations/user/" + userId,callDone);
    }else{
        window.location.href="http://localhost:8080";
    }
});

function getServerData(url, success){
    $.ajax({
        dataType: "json",
        url: url,
        type: "get"
    }).done(success);
}

function callDone(result){
    if(result.length==0){
        $("#reservations").empty();
        //var html="test";
        $("#reservations").append(html);
    }else {
        var templateExample = _.template($('#templateExample').html());
        for (var i = 0; i < result.length; i++) {
            var html = templateExample({
                "reservationId": JSON.stringify(result[i].reservationId),
                "flightId": JSON.stringify(result[i].flightId),
                "nbSeats": JSON.stringify(result[i].nbPlaces),
                "price": JSON.stringify(result[i].price),
                "status": JSON.stringify(result[i].status)
            });
            $("#myReservations").append(html);
        }
    }

}

function flightDetails(flightId) {
    getServerData("http://localhost:8080/ws/flight/flights/" + flightId,callDone3);
}

function callDone3(result){
    $("#result").empty();
    var templateExample = _.template($('#templateExample3').html());
    var html = templateExample({
        "type": JSON.stringify(result['type']),
        "price": JSON.stringify(result['price']),
        "departure": JSON.stringify(result['departureAerodrom']),
        "arrival": JSON.stringify(result['arrivalAerodrom']),
        "dateDeparture": JSON.stringify(result['date']),
        "timeDeparture":JSON.stringify(result['departureTime']),
        "allSeats":JSON.stringify(result['allSeats']),
        "arrivalTime": JSON.stringify(result['arrivalAerodrom']),
        "remainingSeats": JSON.stringify(result['remainingSeats'])
    });
    $("#result").append(html);
}
