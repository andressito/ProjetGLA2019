$(document).ready(function() {
    if(!localStorage.getItem("userId")){
        window.location.href="http://localhost:8080/";
    }else{
        getServerData("http://localhost:8080/ws/user/users/"+sessionStorage.getItem("detailsPilotId"),callDone);
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
    var templateExample = _.template($('#templateExample').html());
    $.ajax({
        url: "http://localhost:8080/ws/licence/licences/user/"+sessionStorage.getItem("detailsPilotId"),
        type: "GET",
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (resultat) {
        var html = templateExample({
            "firstName": JSON.stringify(result['firstName']),
            "lastName": JSON.stringify(result['lastName']),
            "email": JSON.stringify(result['email']),
            "gsm": JSON.stringify(result['gsm']),
            "hFly": JSON.stringify(resultat['numberHoursFlight']),
            "mark": JSON.stringify(resultat['mark'])
        });
        $("#view-profile").append(html);
    });
}
function callDone2(result) {
    var templateExample1 = _.template($('#templateExample1').html());
    var html = templateExample1({
        "hFly": JSON.stringify(result['numberHoursFlight']),
        "hours": JSON.stringify(result['mark'])
    });
    $("#view-profile").append(html);
}