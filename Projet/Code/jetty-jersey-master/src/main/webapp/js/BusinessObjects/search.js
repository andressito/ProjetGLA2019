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
    document.getElementById("modalContent").style.backgroundImage = "url('images/popup.png')";
    if(localStorage.getItem("userId")) {
        $("#result").empty();
        getServerData("http://localhost:8080/ws/flight/flights/" + flightId, callDone);
    }else{
        $("#result").empty();
        var ht=
            '<a href="../../SignIn.html">Please sign in!</a>'+
            '<a href="../../SignUp.html"> don\'t have an account yet? sign up here!</a>';
        $("#result").append(ht);

    }
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return [year, month, day].join('-');
}



function booking() {
    const  nbPlace = $("#remainingSeats").val();
    const userId=localStorage.getItem("userId");
    const flightId=$("#iF").val();
    const  status="waiting";
    $.ajax({
        url: "http://localhost:8080/ws/flight/flights/"+flightId,
        type: "GET",
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (result) {
        var price= result['price'];
        var today = new Date();
        var date=formatDate(today);
        var data ='{"userId" : "'+userId+'", "flightId":"'+flightId+'","nbPlaces":"'+nbPlace+'"' +
            ', "price": "'+price+'","status":"'+status+'","date": "'+date+'" }';
        lancerMethodePost(data,flightId);
    });
}

function getFlightList2(){
    var dpAero = localStorage.getItem("departureAerodrome");
    var dpDate = localStorage.getItem("departureDate");
    if(dpDate.length===0)
        dpDate="0";
    getServerData("http://localhost:8080/ws/flight/search/"+dpAero+"/"+dpDate,callDoneListSearch);
}

function callDoneListSearch(result) {
    var templateExample = _.template($('#templateListSearch').html());
    for(var i=0; i<result.length; i++){
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
        }).done(function (resultat) {
            var html = templateExample({
                "type":JSON.stringify(type),
                "price":JSON.stringify(price),
                "departureAerodrom":JSON.stringify(departureAerodrom),
                "arrivalAerodrom":JSON.stringify(arrivalAerodrom),
                "remainingSeats":JSON.stringify(remainingSeats),
                "firstName":JSON.stringify(resultat['firstName']),
                "flightId":JSON.stringify(flightId)
            });
            $("#flightList").append(html);
        });
    }
}

function getServerData(url, success){
    $.ajax({
        dataType: "json",
        url: url,
        type: "get"
    }).done(success);
}

function sendMail(address,message) {
    Email.send({
        Host : "smtp.gmail.com",
        Username : "noreplychuchufly@gmail.com",
        Password : "passer123&",
        To : address,
        From : "noreplychuchufly@gmail.com",
        Subject : "Reservation",
        Body : message
    }).then(

    );
}


function callDone(result){
    var templateExample = _.template($('#templateExample').html());
    $.ajax({
        url: "http://localhost:8080/ws/user/users/"+result['userId'],
        type: "GET",
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (resultat) {
        var html = templateExample({
            "type":JSON.stringify(result['type']),
            "price":JSON.stringify(result['price']),
            "departure":JSON.stringify(result['departureAerodrom']),
            "arrival":JSON.stringify(result['arrivalAerodrom']),
            "seats":JSON.stringify(result['remainingSeats']),
            "userName":JSON.stringify(resultat['firstName']),
            "pilotId":JSON.stringify(resultat['userId']),
            "dateDeparture":JSON.stringify(result['date']),
            "timeDeparture":JSON.stringify(result['departureTime']),
            "flightId":JSON.stringify(result['flightId']),
            "name1":JSON.stringify(localStorage.getItem("firstName")),
            "name2":JSON.stringify(localStorage.getItem("lastName")),
            "gsm":JSON.stringify(localStorage.getItem("gsm")),
            "remainingSeats":JSON.stringify(result['remainingSeats']),
            "flight":JSON.stringify(result['flightId'])
        });
        $("#result").append(html);
    });

}

function pilotDetails(pilotId) {
    document.getElementById("modalContent").style.backgroundImage= "url('images/profilePopup.png')";
    getServerData("http://localhost:8080/ws/user/users/" + pilotId,callDoneX);
}


function setFilters(){

}

function callDoneX(result){
    $("#result").empty();
    var templateExample = _.template($('#templateExample2').html());
    if(result["typeUser"]==="pilot"){
        $.ajax({
            url: "http://localhost:8080/ws/licence/licences/user/"+result['userId'],
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

function lancerMethodePost( data,flightId) {
    $.ajax({
        url: "http://localhost:8080/ws/reservation/create",
        type: "POST",
        data: data,
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (result) {
        if(result){
            var message="Dear "+localStorage.getItem("firstName")+" ,\n" +
                "Your seat(s) reservation is registered correctly.\n" +
                "We'll keep you updated as soon as the pilot in charge of the flight "+flightId+" replies to your reservation.\n" +
                "You can also check your reservation status through ChuChuFly platform.\n" +
                "Thank you for your trust\n" +
                "\n" +
                "ChuChuFly Team";
            sendMail(localStorage.getItem("email"),message);
            sendMessageToPilot(flightId);
            swal({
                title: "ChuChuFly!",
                text: "Reservation request sent to the pilot in charge",
                icon: "success"
            }).then(function(){
                window.location.href="http://localhost:8080/myReservations.html";
            });
        }else{
            swal({
                title: "ChuChuFly!",
                text: "An error occured, please try later",
                icon: "error"
            });
        }
    });
}

function sendMessageToPilot(flightId) {
    $.ajax({
        url: "http://localhost:8080/ws/flight/pilotByFlightId/"+flightId,
        type: "GET",
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (result) {
        var email=result['email'];
        var message="Dear "+result['firstName']+" , \n" +
            "You have a new reservation request on your flght no "+flightId+"\n" +
            "Please accept or decline as soon as you can.\n" +
            "Thank you for your trust\n" +
            "\n" +
            "ChuChuFly Team";
        sendMail(email,message);
    });
}

var Email = {
    send: function (a) {
        return new Promise(function (n, e) {
            a.nocache = Math.floor(1e6 * Math.random() + 1),a.Action = "Send";
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