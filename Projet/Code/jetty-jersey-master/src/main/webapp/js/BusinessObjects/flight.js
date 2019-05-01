$(document).ready(function() {
    if(!localStorage.getItem("userId")){
        window.location.href="http://localhost:8080/";
    }else{
        if(localStorage.getItem("typeUser")==="pilot") {
            if (document.getElementById('ArrivalA2')) {
                if (document.getElementById('visit').checked) {
                    document.getElementById('ArrivalA2').style.display = 'none';
                } else {
                    document.getElementById('ArrivalA2').style.display = 'inline';
                }
            }
            $("#menu").load('../Menu/MenuPilot.html');
        }else{
            window.location.href="http://localhost:8080/";
        }
    }
});
function visitDetect() {
    if(document.getElementById('visit').checked){
        document.getElementById('ArrivalA2').style.display='none';
    }else{
        document.getElementById('ArrivalA2').style.display='inline';
    }
}
//ajouter un vol prevu
function ajouter(){
    if($("#plane").val()=="" || $("#plane").val().length <2){
        $('#plane').focus();
        return false;
    }
    if($("#departureAirfield").val()=="" || $("#departureAirfield").val().length <2){
        $('#departureAirfield').focus();
        return false;
    }
    if(!document.getElementById('visit').checked){
        if($("#arrivalAirfield").val()=="" || $("#arrivalAirfield").val().length <2){
            $('#arrivalAirfield').focus();
            return false;
        }
    }

    if($("#departureDate").val()==""){
        $('#departureDate').focus();
        return false;
    }
    if($("#departureTime").val()==""){
        $('#departureAirfield').focus();
        return false;
    }
    if($("#arrivalTime").val()==""){
        $('#arrivalTime').focus();
        return false;
    }
    if($("#numberSeats").val()==""){
        $('#numberSeats').focus();
        return false;
    }
    if($("#price").val()==""){
        $('#price').focus();
        return false;
    }
    var plane=$("#plane").val();
    var departureAirfield=$("#departureAirfield").val();
    var departureDate=$("#departureDate").val();
    var today = new Date();
    var todayM= new Date();
    todayM.setHours(28);
    var valDate = new Date(departureDate);
    today.setMonth(6);
    if(!(today>valDate && valDate>todayM)){
        swal({
            title: "ChuChuFly!",
            text: "Your flight back is too soon far or away ",
            icon: "error"
        });
        return false;
    }
    var departureTime=$("#departureTime").val();
    var arrivalTime=$("#arrivalTime").val();
    var allSeats=$("#allSeats").val();
    var price=$("#price").val();
    var type;
    var arrivalAirfield;
    var userId=localStorage.getItem("userId");
    if(document.getElementById('visit').checked){
        type="visit";
        arrivalAirfield=departureAirfield;
    }else{
        type="oneway";
        arrivalAirfield=$("#arrivalAirfield").val();
    }
    var data= '{"atcNumber":"'+plane+'", "departureAerodrom":"'+departureAirfield+'", "date":"'+departureDate+'","departureTime":"'+departureTime+'","allSeats":"'+allSeats+'", "remainingSeats":"'+allSeats+'", "type":"'+type+'","arrivalAerodrom":"'+arrivalAirfield+'","arrivalTime":"'+arrivalTime+'","price":"'+price+'","userId":"'+userId+'"}';
    var dataUrl = "http://localhost:8080/ws/flight/create";
    $.ajax({
        url: dataUrl,
        type: "POST",
        data: data,
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).success(function (result) {
        if(result){
            //si resultat bon, is Ok
            swal({
                title: "ChuChuFly!",
                text: "Flight registered successfully!",
                icon: "success"
            }).then(function(){
                window.location.href="http://localhost:8080/myFlights.html";
            });
        }else{
            swal({
                title: "ChuChuFly!",
                text: "Registration error!",
                icon: "error"
            });
        }
    });
}
