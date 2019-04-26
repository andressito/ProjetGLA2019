$(document).ready(function() {
    if(localStorage.getItem("userId")){
        var firstName;
        var typeUser;
        var userId;
        if(sessionStorage.getItem("firstName") && sessionStorage.getItem("userId") && sessionStorage.getItem("typeUser")){
            firstName = sessionStorage.getItem("firstName");
            typeUser =sessionStorage.getItem("typeUser");
            userId = sessionStorage.getItem("userId");
            sessionStorage.removeItem("departureAerodrome");
            sessionStorage.removeItem("departureDate");
        }else{
            firstName = localStorage.getItem("firstName");
            typeUser =localStorage.getItem("typeUser");
            userId = localStorage.getItem("userId");
            sessionStorage.setItem("typeUser",typeUser);
            sessionStorage.setItem("firstName",firstName);
            sessionStorage.setItem("userId",userId);
        }
        //document.getElementById("bg").style.backgroundImage = "url('images/paris.jpg')";
        $("#menu").load('../Menu/MenuPilot.html');
        getServerData("http://localhost:8080/ws/flight/flightByUserId/" + userId,callDone)
        /*$.ajax({
            url: "http://localhost:8080/ws/flight/flightByUserId/"+userId,
            type: "GET",
            contentType: "application/json",
            cache: false,
            dataType: "json"
        }).done(function (result) {
            console.log(result);
            console.log(result.length);
            if(result.length>0){
                var table=
                    "<div class=\"upcomingFlight\"><h1> "+firstName+"'s flights list </h1>"+
                    "<br><br><br><table class=\"table\">"+
                    "<thead>"+
                    "<tr>" +
                    "<td>Departure</td>"+
                    "<td>Arrival</td>"+
                    "<td>Date departure</td>"+
                    "<td>Initial Seats</td>"+
                    "<td>Remaining Seats</td>"+
                    "<td>Details</td>"+
                    "</tr>"+
                    "</thead>"+
                    "<tbody>"+
                    "<tr>" +
                    "<td>"+result[0].departureAerodrom+"</td>"+
                    "<td>"+result[0].arrivalAerodrom+"</td>"+
                    "<td>"+result[0].date+"</td>"+
                    "<td>"+result[0].allSeats+"</td>"+
                    "<td>"+result[0].remainingSeats+"</td>"+
                    "<td> <button value='"+result[0].flightId+"' onclick='DetailsFLightHome' class=\"btn-details\"> Details</button></td>"+
                    "</tr>"+
                    "</tbody>"+
                    "</table>"+
                    "</div>";
                $("#myFlights").append(table);
            }else{

                var table=
                    "<div class=\"upcomingFlight\"><br><h1> No flights to display </h1>"+
                    "<br>"+
                    "<a href='AddFlight.html'><h5> Add a new flight here</h5></a>"+
                    "</div>";
                $("#myFlights").append(table);
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
    var templateExample = _.template($('#templateFlight').html(),{res : result});
    /*for(var i=0; i<result.length; i++) {
        var html = templateExample({
            "flightId": JSON.stringify(result[i].flightId),
            "userId": JSON.stringify(result[i].userId),
            "nbSeats": JSON.stringify(result[i].nbPlaces),
            "price": JSON.stringify(result[i].price),
            "status": JSON.stringify(result[i].status)
        });
        $("#myFlight").append(html);
    }*/

}