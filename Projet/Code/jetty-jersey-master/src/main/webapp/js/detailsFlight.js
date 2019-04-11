$(document).ready(function() {
    if(!localStorage.getItem("userId")){
        window.location.href="http://localhost:8080/";
    }else{
        if(localStorage.getItem("typeUser")=="passenger"){
            $("#menu").load('../Menu/MenuPassenger.html');
        }else{
            $("#menu").load('../Menu/MenuPilot.html');
        }
    }
});