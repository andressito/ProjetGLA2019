$(document).ready(function() {
    if(localStorage.getItem("save")){
        //sessionStorage.clear();
        //localStorage.clear();
        if(sessionStorage.getItem("email")){
            var email = sessionStorage.getItem("email");

        }else{
            var email=localStorage.getItem("save");
            sessionStorage.setItem("email",email);

        }
        $("#menu").load('../Menu/MenuPilot.html');
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