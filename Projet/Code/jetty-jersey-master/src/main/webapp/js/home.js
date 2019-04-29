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
            document.getElementById('pilotHome').style.display='none';
        }else{
            document.getElementById("bg").style.backgroundImage = "url('images/town.jpg')";
            $("#menu").load('../Menu/MenuPilot.html');
            document.getElementById('search').style.display='none';
            document.getElementById('flight').style.display='inline';
            getServerData("http://localhost:8080/ws/reservation/reservationPilot/" + userId,callDone);
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
                        "</table>";
                    $("#flight").append(table);
                }else{

                    var table=
                        "<div class=\"upcomingFlight\"><br><h1> No flights to display </h1>"+
                        "<br>"+
                        "<a href='AddFlight.html'><h5> Add a new flight here</h5></a>"+
                        "</div>";
                    $("#flight").append(table);
                }

            });
        }
    }else{
        $("#menu").load('../Menu/Menu.html');
        document.getElementById("bg").style.backgroundImage = "url('images/slide_1.jpg')";
        document.getElementById('search').style.display='inline';
        document.getElementById('flight').style.display='none';
        document.getElementById('pilotHome').style.display='none';
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
            "status": JSON.stringify(result[i].status),
            "reserId":JSON.stringify(result[i].reservationId)
        });
        $("#myReservations").append(html);
    }

}

function acceptReservation(reservationId,userId,flightId){
    var status="accept";
    var data='{"reservationId":"'+reservationId+'", "status":"'+status+'"}';
    $.ajax({
        url:"http://localhost:8080/ws/reservation/reservations/state",
        type:"PUT",
        data: data,
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).success( function (result) {
        var status="accept";
        sendMail(status,userId,flightId,reservationId);
    });
}

function declineReservation(reservationId,userId,flightId){
    var status="decline";
    var data='{"reservationId":"'+reservationId+'", "status":"'+status+'"}';
    $.ajax({
        url:"http://localhost:8080/ws/reservation/reservations/state",
        type:"PUT",
        data: data,
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).success( function (result) {
        var status="decline";
        sendMail(status,userId,flightId,reservationId);
    });

}

function userDetails(userId) {
    getServerData("http://localhost:8080/ws/user/users/" + userId,callDone2);
}

function flightDetails(flightId) {
    getServerData("http://localhost:8080/ws/flight/flights/" + flightId,callDone3);
}

function callDone2(result){
    $("#result").empty();
    var templateExample = _.template($('#templateExample2').html());
    if(result["typeUser"]==="pilot"){
        $.ajax({
            url: "http://localhost:8080/ws/licence/licences/user/"+sessionStorage.getItem("detailsPilotId"),
            type: "GET",
            contentType: "application/json",
            cache: false,
            dataType: "json"
        }).done(function (resultat) {
            var html = templateExample({
                "typeUser":JSON.stringify(result['typeUser']),
                "firstName": JSON.stringify(result['firstName']),
                "lastName": JSON.stringify(result['lastName']),
                "email": JSON.stringify(result['email']),
                "gsm": JSON.stringify(result['gsm']),
                "hFly": JSON.stringify(resultat['numberHoursFlight']),
                "mark": JSON.stringify(resultat['mark'])
            });
            $("#result").append(html);
        });
    }else{
        var html = templateExample({
            "typeUser":JSON.stringify(result['typeUser']),
            "firstName": JSON.stringify(result['firstName']),
            "lastName": JSON.stringify(result['lastName']),
            "email": JSON.stringify(result['email']),
            "gsm": JSON.stringify(result['gsm']),
            "hFly": JSON.stringify("0"),
            "mark": JSON.stringify("0")
        });
        $("#result").append(html);
    }

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

function sendMail(status,userId,flightId,reservationId) {
    $.ajax({
        url: "http://localhost:8080/ws/user/users/"+userId,
        type: "GET",
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (result) {
        $.ajax({
            url: "http://localhost:8080/ws/flight/flights/"+flightId,
            type: "GET",
            contentType: "application/json",
            cache: false,
            dataType: "json"
        }).done(function (resultat) {
            var message="";
            if(status==="decline"){
                message= "Dear "+result['firstName']+" , <br\>" +
                    "We're sorry to announce your reservation n° "+reservationId+" has been declined by the pilot.\n" +
                    "Sign in to ChuChuFly and find more interesting flights!\n" +
                    "Thank you for your trust\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "ChuChuFly Team";
            }else{
                message="Dear "+result['firstName']+",\r " +
                    "Congratulations, your reservation n° "+reservationId+" has been accepted by the pilot.\n " +
                    "Your departure will take place on "+resultat['date']+" at "+resultat['departureTime']+"  from "+resultat['departureAerodrom']+" to "+resultat['arrivalAerodrom']+"\n" +
                    "Please be on time and ready to enjoy your flight! \n\n " +
                    "Thank you for your trust\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "ChuChuFly Team";
            }
            Email.send({
                Host: "smtp.gmail.com",
                Username: "noreplychuchufly@gmail.com",
                Password: "passer123&",
                To: result['email'],
                From: "noreplychuchufly@gmail.com",
                Subject: "Reservation",
                Body: message
            }).then(

            );
            swal({
                title: "ChuChuFly!",
                text: "Flight registered successfully!",
                icon: "success"
            }).then((willDelete) => {
                if (willDelete) {
                    window.location.href="http://localhost:8080";
                }
            });

        });

    });

}

var Email = {
    send: function (a) {
        return new Promise(function (n, e) {
            a.nocache = Math.floor(1e6 * Math.random() + 1), a.Action = "Send";
            var t = JSON.stringify(a);
            Email.ajaxPost("https://smtpjs.com/v3/smtpjs.aspx?", t, function (e) { n(e) })
        })
    }, ajaxPost: function (e, n, t) {
        var a = Email.createCORSRequest("POST", e);
        a.setRequestHeader("Content-type", "application/x-www-form-urlencoded"), a.onload = function () {
            var e = a.responseText; null != t && t(e) },
            a.send(n)
    }, ajax: function (e, n) {
        var t = Email.createCORSRequest("GET", e);
        t.onload = function () {
            var e = t.responseText;
            null != n && n(e)
        }, t.send()
    },
    createCORSRequest: function (e, n) {
        var t = new XMLHttpRequest;
        return "withCredentials" in t ? t.open(e, n, !0) : "undefined" != typeof XDomainRequest ? (t = new XDomainRequest).open(e, n) : t = null, t }
};


//faire l'envoie de mail pour la  confirmation de reservation ou l'annulation