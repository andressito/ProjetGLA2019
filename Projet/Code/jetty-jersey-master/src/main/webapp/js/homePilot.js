$(document).ready(function() {
    if(localStorage.getItem("userId")){
        var typeUser;
        typeUser =localStorage.getItem("typeUser");
        if(typeUser==="pilot")
            $("#menu").load('../Menu/MenuPilot.html');
        else
            window.location.href="http://localhost:8080/";
    }else{
        window.location.href="http://localhost:8080/";
    }
});

function searchFlight(){
    var departureAerodrome=$("#departureAerodrome").val();
    var departureDate=$("#departureDate").val();
    localStorage.setItem("departureAerodrome",departureAerodrome);
    localStorage.setItem("departureDate",departureDate);
    document.location.href = "SearchList.html";
}