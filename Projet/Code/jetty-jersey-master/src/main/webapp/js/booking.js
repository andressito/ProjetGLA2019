$(document).ready(function() {
    if(!localStorage.getItem("userId")){
        window.location.href="http://localhost:8080/";
    }else{
        if(localStorage.getItem("typeUser")==="passenger"){
            $("#menu").load('../Menu/MenuPassenger.html');
        }else{
            $("#menu").load('../Menu/MenuPilot.html');
        }
        var flightIdBooking= localStorage.getItem("flightIdBooking");
        getServerData("http://localhost:8080/ws/flight/flights/" +flightIdBooking,callDoneFlightDetails);
        getServerData("http://localhost:8080/ws/user/users/" +sessionStorage.getItem("userId"),callDoneUserDetails);
    }
});

function callDoneFlightDetails(result){
    var templateExample = _.template($('#templateExample').html());
    var html = templateExample({
        'type':JSON.stringify(result['type']),
        'price':JSON.stringify(result['price']),
        "departure":JSON.stringify(result['departureAerodrom']),
        "arrival":JSON.stringify(result['arrivalAerodrom']),
        "seats":JSON.stringify(result['remainingSeats']),
        "userName":JSON.stringify(result['userId'])
    });
    $("#flightDetails").append(html);
    sessionStorage.setItem("seats",result['remainingSeats']);
}

function callDoneUserDetails(result){
    var templateExample = _.template($('#informationUser').html());
    var html = templateExample({
        "name1":JSON.stringify(result['firstName']),
        "name2":JSON.stringify(result['lastName']),
        "gsm":JSON.stringify(result['gsm']),
        "seats":JSON.stringify(sessionStorage.getItem("seats"))
    });
    $("#userList").append(html);
}

function getServerData(url, success){
    $.ajax({
        dataType: "json",
        url: url,
        type: "get"
    }).done(success);
}

function testOnclick() {
    const flightId=localStorage.getItem("flightIdBooking");
    const userId=localStorage.getItem("userId");
    const  nbPlace = $("#seats").val();
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

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return [year, month, day].join('-');
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