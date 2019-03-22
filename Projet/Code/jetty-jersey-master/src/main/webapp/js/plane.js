function ajouter(){
    var atcNumber=$("#atcNumber").val();
    var numberSeats=$("#numberSeats").val();
    //condition ^pourverifier si c'estune balade ou pas







    var data= '{"atcNumber":"'+atcNumber+'", "numberSeats":"'+numberSeats+'"}';
    var urlData = "http://localhost:8080/ws/plane/create";
    console.log(data);
    $.ajax({
        url: urlData,
        type: "POST",
        data: data,
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
                text: "Plane saved withe succes!",
                icon: "success",
            });

        }else{
            swal({
                title: "ChuChuFly!",
                text: "Error : Plane not saved!",
                icon: "error",
            });
        }
    });
}