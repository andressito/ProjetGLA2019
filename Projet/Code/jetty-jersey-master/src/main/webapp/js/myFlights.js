$(document).ready(function() {
    if(localStorage.getItem("userId")){
        var firstName;
        var typeUser;
        var userId;
        typeUser =localStorage.getItem("typeUser");
        userId = localStorage.getItem("userId");
        if (typeUser==="pilot") {
            $("#menu").load('../Menu/MenuPilot.html');
            getServerData("http://localhost:8080/ws/flight/flightByUserId/" + userId,callDone4);
        }else{
            window.location.href="http://localhost:8080";
        }
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
        "arrivalTime": JSON.stringify(result['arrivalTime']),
        "remainingSeats": JSON.stringify(result['remainingSeats'])
    });
    $("#result").append(html);
}

function callDone4(result){
    var templateExample = _.template($('#templateExample4').html());
    for(var i=0; i<result.length; i++) {
        var html = templateExample({
            "departure": JSON.stringify(result[i].departureAerodrom),
            "arrival": JSON.stringify(result[i].arrivalAerodrom),
            "dateDeparture": JSON.stringify(result[i].date),
            "iniSeats": JSON.stringify(result[i].allSeats),
            "remSeats": JSON.stringify(result[i].remainingSeats),
            "flightId":JSON.stringify(result[i].flightId)
        });
        $("#myFlights").append(html);
    }

}