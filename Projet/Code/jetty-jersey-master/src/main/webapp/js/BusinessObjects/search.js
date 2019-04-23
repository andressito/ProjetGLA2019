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
    if(sessionStorage.getItem("userId")) {
        $("#result").empty();
        getServerData("http://localhost:8080/ws/flight/flights/" + flightId, callDone);
    }else{
        $("#result").empty();
        var ht=
        '<a href="../../SignIn.html"> Have you an account, please sign-in  </a>'+
            '<a href="../../SignUp.html"> create an account</a>';
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
        lancerMethodePost(data);
    });
}

function getFlightList2(){
    //recupération reussie
    var dpAero = sessionStorage.getItem("departureAerodrome");
    var dpDate = sessionStorage.getItem("departureDate");
    //sessionStorage.removeItem("departureAerodrome");
    //sessionStorage.removeItem("departureDate");
    if(dpDate.length===0)
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
                    '<a class="btn btn-select-plan btn-sm"  href="#myModal2" data-toggle="modal"  onclick="detailsFlight(\'' + flightId + '\');" >Details</a>' +
                    '</div></div>';
                $("#flightList").append(tr_str);
            });
        }
    });
}

function getServerData(url, success){
    $.ajax({
        dataType: "json",
        url: url,
        type: "get"
    }).done(success);
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
            "name1":JSON.stringify(sessionStorage.getItem("firstName")),
            "name2":JSON.stringify(sessionStorage.getItem("lastName")),
            "gsm":JSON.stringify(sessionStorage.getItem("gsm")),
            "remainingSeats":JSON.stringify(result['remainingSeats']),
            "flight":JSON.stringify(result['flightId'])
        });
        $("#result").append(html);
    });

}

function pilotDetails(pilotId) {
    sessionStorage.setItem("detailsPilotId",pilotId)
    window.location.href="http://localhost:8080/DetailsProfile.html";
}

function lancerMethodePost( data) {
    $.ajax({
        url: "http://localhost:8080/ws/reservation/create",
        type: "POST",
        data: data,
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (result) {
        if(result){
            swal({
                title: "ChuChuFly!",
                text: "reservation successfully ",
                icon: "success"
            });
        }else{
            swal({
                title: "ChuChuFly!",
                text: "error reservation ",
                icon: "error"
            });
        }
    });
}