$(document).ready(function() {
    if(localStorage.getItem("save")){
        if(sessionStorage.getItem("email")){
            var email = sessionStorage.getItem("email");
        }else{
            var email=localStorage.getItem("save");
            sessionStorage.setItem("email",email);

        }
        $("#menu2").show();
        $("#menu1").hide();
        $('#id1').val(email);
    }else{
        $("#menu1").show();
        $("#menu2").hide();
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