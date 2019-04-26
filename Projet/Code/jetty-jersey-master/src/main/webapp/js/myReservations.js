$(document).ready(function() {
    if(localStorage.getItem("userId")) {
        var firstName;
        var typeUser;
        var userId;
        if (sessionStorage.getItem("firstName") && sessionStorage.getItem("userId") && sessionStorage.getItem("typeUser")) {
            firstName = sessionStorage.getItem("firstName");
            typeUser = sessionStorage.getItem("typeUser");
            userId = sessionStorage.getItem("userId");
            sessionStorage.removeItem("departureAerodrome");
            sessionStorage.removeItem("departureDate");
        } else {
            firstName = localStorage.getItem("firstName");
            typeUser = localStorage.getItem("typeUser");
            userId = localStorage.getItem("userId");
            sessionStorage.setItem("typeUser", typeUser);
            sessionStorage.setItem("firstName", firstName);
            sessionStorage.setItem("userId", userId);
        }
        document.getElementById("bg").style.backgroundImage = "url('images/paris.jpg')";
        if (typeUser === "passenger") {
            $("#menu").load('../Menu/MenuPassenger.html');
            document.getElementById('myReservations').style.display="'inline";
        } else {
            $("#menu").load('../Menu/MenuPilot.html');
        }

        getServerData("http://localhost:8080/ws/reservation/reservations/user/" + userId,callDone);
            /*$.ajax({
                url: "http://localhost:8080/ws/reservation/reservations/user/" + userId,
                type: "GET",
                contentType: "application/json",
                cache: false,
                dataType: "json"
            }).done(function (result) {
                if (result.length>0) {
                    console.log(result);
                    var table =
                        "<div class=\"flightsList\"><h1> Reservations list </h1>" +
                        "<br><br><br><table class=\"table\">" +
                        "<thead>" +
                        "<tr>" +
                        "<td>Departure</td>" +
                        "<td>Arrival</td>" +
                        "<td>Date departure</td>" +
                        "<td>Initial Seats</td>" +
                        "<td>Remaining Seats</td>" +
                        "<td>Details</td>" +
                        "</tr>" +
                        "</thead>" +
                        "<tbody>" +
                        "<tr>" +
                        "<td>" + result['departureAerodrom'] + "</td>" +
                        "<td>" + result['arrivalAerodrom'] + "</td>" +
                        "<td>" + result['date'] + "</td>" +
                        "<td>" + result['allSeats'] + "</td>" +
                        "<td>" + result['remainingSeats'] + "</td>" +
                        "<td> <button value='" + result['flightId'] + "' onclick='DetailsFLightHome' class=\"btn-details\"> Details</button></td>" +
                        "</tr>" +
                        "</tbody>" +
                        "</table>" +
                        "</div>";
                    $("#myReservations").append(table);
                } else {
                    var table1 =
                        "<div class=\"upcomingFlight\"><br><h1> No Reservations to display </h1>" +
                        "<br>" +
                        "<a href='home.html'><h5> Search for a convenient flight and book it here </h5></a>" +
                        "</div>";
                    $("#myReservations").append(table1);
                }
            });*/

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
    console.log(result);
    var templateExample = _.template($('#templateExample').html());
    for(var i=0; i<result.length; i++) {
        var html = templateExample({
            "flightId": JSON.stringify(result[i].flightId),
            "userId": JSON.stringify(result[i].userId),
            "nbSeats": JSON.stringify(result[i].nbPlaces),
            "price": JSON.stringify(result[i].price),
            "status": JSON.stringify(result[i].status)
        });
        $("#myReservations").append(html);
    }

}
