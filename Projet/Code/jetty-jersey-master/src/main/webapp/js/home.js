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
        if(typeUser==="passenger"){
            document.getElementById("bg").style.backgroundImage = "url('images/slide_1.jpg')";
            console.log("passenger");
            $("#menu").load('../Menu/MenuPassenger.html');
            document.getElementById('search').style.display='inline';
            document.getElementById('flight').style.display='none';
        }else{
            document.getElementById("bg").style.backgroundImage = "url('images/town.jpg')";
            $("#menu").load('../Menu/MenuPilot.html');
            document.getElementById('search').style.display='none';
            document.getElementById('flight').style.display='inline';
            $.ajax({
                url: "http://localhost:8080/ws/flight/flightByUserId/"+userId,
                type: "GET",
                contentType: "application/json",
                cache: false,
                dataType: "json"
            }).done(function (result) {
                if(result){
                    var table=
                        "<div class=\"upcomingFlight\"><h1> Upcoming Flight </h1>"+
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
                        "<td>"+result['remainingSeats']+"</td>"+
                        "<td>"+result['remainingSeats']+"</td>"+
                        "<td> <button value='"+result['flightId']+"' onclick='DetailsFLightHome' class=\"btn-details\"> Details</button></td>"+
                        "</tr>"+
                        "</tbody>"+
                        "</table>"+
                        "</div>";
                    $("#flight").append(table);
                }else{
                    var table ="<h3> Pas de vol à venir</h3>";
                    $("#flight").append(table);
                }

            });
        }
    }else{
        $("#menu").load('../Menu/Menu.html');
        document.getElementById("bg").style.backgroundImage = "url('images/slide_1.jpg')";
        document.getElementById('search').style.display='inline';
        document.getElementById('flight').style.display='none';
    }
});

$(function () {
    $("#signOut").click(function () {
        sessionStorage.clear();
        localStorage.clear();
        window.location.href="http://localhost:8080/";
        swal({
            title: "ChuChuFly!",
            text: "Success Sign Out",
            icon: "success"
        });
    });
});

function searchFlight(){
    var departureAerodrome=$("#departureAerodrome").val();
    var departureDate=$("#departureDate").val();
    sessionStorage.setItem("departureAerodrome",departureAerodrome);
    sessionStorage.setItem("departureDate",departureDate);
    document.location.href = "SearchList.html";

}