$(document).ready(function() {
    if(!localStorage.getItem("userId")){
        window.location.href="http://localhost:8080/";
    }else{
        if(localStorage.getItem("typeUser")==="pilot"){
            $("#menu").load('../Menu/MenuPilot.html');

        }else{
            $("#menu").load('../Menu/MenuPassenger.html');
        }
        getServerData("http://localhost:8080/ws/user/users/"+localStorage.getItem("detailsPilotId"),callDone);
    }
});

function getServerData(url, success){
    $.ajax({
        dataType: "json",
        url: url,
        type: "get"
    }).done(success);
}
function callDone(result) {
    console.log(result);
    var templateExample = _.template($('#templateExample').html());
    var firstName=result['firstName'];
    var lastName=result['lastName'];
    var email=result['email'];
    var gsm=result['gsm'];
    var userId=result['userId'];
    $.ajax({
        url: "http://localhost:8080/ws/licence/licences/user/"+userId,
        type: "GET",
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (resultat) {
        var html = templateExample({
            "firstName": JSON.stringify(firstName),
            "lastName": JSON.stringify(lastName),
            "email": JSON.stringify(email),
            "gsm": JSON.stringify(gsm),
            "hFly": JSON.stringify(resultat['numberHoursFlight']),
            "mark": JSON.stringify(resultat['mark'])
        });
        $("#view-profile").append(html);
    });
}