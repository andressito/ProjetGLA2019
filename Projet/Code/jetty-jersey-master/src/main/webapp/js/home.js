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