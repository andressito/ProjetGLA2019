$(document).ready(function() {
    if(localStorage.getItem("userId")){
        var firstName;
        var typeUser;
        var userId;
        if(sessionStorage.getItem("firstName") && sessionStorage.getItem("userId") && sessionStorage.getItem("typeUser")){
            firstName = sessionStorage.getItem("firstName");
            typeUser =sessionStorage.getItem("typeUser");
            userId = sessionStorage.getItem("userId");
        }else{
            firstName = localStorage.getItem("firstName");
            typeUser =localStorage.getItem("typeUser");
            userId = localStorage.getItem("userId");
            sessionStorage.setItem("typeUser",typeUser);
            sessionStorage.setItem("firstName",firstName);
            sessionStorage.setItem("userId",userId);
        }
        if(typeUser=="passenger"){
            $("#menu").load('../Menu/MenuPassenger.html');
        }else{
            $("#menu").load('../Menu/MenuPilot.html');
        }


    }else{
        $("#menu").load('../Menu/Menu.html');
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

function getFlightList(){
    var departureAerodrome=$("#departureAerodrome").val();
    var departureDate=$("#departureDate").val();
    //enregistrement dans les cookies
    this.createCookie('departureAerodrome',''+departureAerodrome);
    this.createCookie('departureDate',''+departureDate);
    document.location.href = "SearchList.html";
}

function createCookie(name,value) {
    document.cookie = name + "=" + value + "; path=/";
}