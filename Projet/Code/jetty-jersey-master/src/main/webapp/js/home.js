$(document).ready(function() {
    if(localStorage.getItem("save")){
        if(sessionStorage.getItem("email")){
            var email = sessionStorage.getItem("email");

        }else{
            var email=localStorage.getItem("save");
            sessionStorage.setItem("email",email);

        }

        $.get("../Menu/MenuPilot.html", function(data) {
            $("#menu").html(data);
        });

    }else{
        $.get("../Menu/Menu.html", function(data) {
            $("#menu").html(data);
        });

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