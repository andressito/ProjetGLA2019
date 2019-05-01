$(document).ready(function() {
    if(localStorage.getItem("userId")){
        var typeUser;
        if(sessionStorage.getItem("firstName") && sessionStorage.getItem("userId") && sessionStorage.getItem("typeUser")){
            sessionStorage.removeItem("departureAerodrome");
            sessionStorage.removeItem("departureDate");
        }else{
            typeUser =localStorage.getItem("typeUser");
        }
        if(typeUser==="passenger"){
            $("#menu").load('../../Menu/MenuPassenger.html');
        }else{
            console.log("pilot");
            $("#menu").load('../../Menu/MenuPilot.html');
        }
    }else{
        $("#menu").load('../../Menu/Menu.html');
    }
});