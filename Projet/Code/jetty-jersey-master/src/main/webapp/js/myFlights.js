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
        document.getElementById("bg").style.backgroundImage = "url('images/paris.jpg')";
        $("#menu").load('../Menu/MenuPilot.html');
        $.ajax({
            url: "http://localhost:8080/ws/flight/flightByUserId/"+userId,
            type: "GET",
            contentType: "application/json",
            cache: false,
            dataType: "json"
        }).done(function (result) {
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
                    "<td>"+result['departureAerodrom']+"</td>"+
                    "<td>"+result['arrivalAerodrom']+"</td>"+
                    "<td>"+result['date']+"</td>"+
                    "<td>"+result['allSeats']+"</td>"+
                    "<td>"+result['remainingSeats']+"</td>"+
                    "<td> <button value='"+result['flightId']+"' onclick='DetailsFLightHome' class=\"btn-details\"> Details</button></td>"+
                    "</tr>"+
                    "</tbody>"+
                    "</table>"+
                    "</div>";
                $("#flight").append(table);
            }else{

                var table=
                    "<div class=\"upcomingFlight\"><br><h1> No flights to display </h1>"+
                    "<br>"+
                    "<a href='AddFlight.html'><h5> Add a new flight here</h5></a>"+
                    "</div>";
                $("#myFlights").append(table);
            }

        });
    }
});