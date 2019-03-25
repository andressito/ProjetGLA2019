function detecter(){
    if(document.getElementById('visit').checked){
        document.getElementById('ArrivalA2').style.display='none';
        document.getElementById('ArrivalT2').style.display='none';
    }
    else{
        document.getElementById('ArrivalA2').style.display='inline';
        document.getElementById('ArrivalT2').style.display='inline';
    }
}
//ajouter un vol prevu
function ajouter(){
    var plane=$("#Plane").val();
    var departureAerodrome=$("#DepartureA").val();
    var date=$("#Date").val();
    var departureTime=$("#DepartureT").val();
    var seats=$("#Seats").val();
    var arrivalAerodrome=$("#ArrivalA").val();
    var arrivalTime=$("#ArrivalT").val();
    var price=$("#Price").val();
    var type;
    //condition ^pourverifier si c'estune balade ou pas
    if(document.getElementById('visit').checked){
        type="visit";
        arrivalAerodrome=departureAerodrome;
    }else{type="oneway";}
    var donner= '{"atcNumber":"'+plane+'", "departureAerodrom":"'+departureAerodrome+'", "date":"'+date+'","departureTime":"'+departureTime+'","seats":"'+seats+'","type":"'+type+'","arrivalAerodrom":"'+arrivalAerodrome+'","arrivalTime":"'+arrivalTime+'","price":"'+price+'","userId":"1"}';
    var ajouterLien = "http://localhost:8080/ws/flight/create";
    //console.log(donner);
    $.ajax({
        url: ajouterLien,
        type: "POST",
        data: donner,
        contentType: "application/json",
        cache: false,
        dataType: "json",

    }).done(function (result) {
        console.log(result);
    }).success(function (result) {
        if(result){
            //si resultat bon, is Ok
            swal({
                title: "ChuChuFly!",
                text: "Vol enrégistré avec succès!",
                icon: "success",
            });
            //effacement du contenu des inputs
            $("#Plane").val("");
            $("#DepartureA").val("");
            $("#Date").val("");
            $("#DepartureT").val("");
            $("#Seats").val("");
            $("#ArrivalA").val("");
            $("#ArrivalT").val("");
            $("#Price").val("");
            //fin de l'effacement
        }else{
            swal({
                title: "ChuChuFly!",
                text: "Erreur d'enregistrement!",
                icon: "error",
            });
        }
    });
}