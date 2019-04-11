function ajouterReservation(){
    var seats=$("#Seats").val();
    var userId=localStorage.getItem("userId");
    var flightId = "RB2CNZX";
    var status = "en attente";
    //recuperation de la date d'aujourdhui
    var date=new Date();
    if (seats<1){
        swal({
            title: "ChuChuFly!",
            text: "Error : no reservation for zero(0) places!",
            icon: "error",
        });
    }
    else
    {
        var dataUrl = "http://localhost:8080/ws/flight/flights/"+flightId;
        $.ajax({
            url: dataUrl,
            type: "GET",
            contentType: "application/json",
            cache: false,
            dataType: "json",
        }).done(function (result) {
            console.log(result);
            if(result){
                var flightIdd = result.flightId;
                var price = result.price;
                var remainingSeats = result.remainingSeats;
                console.log(flightIdd);
                if (seats>remainingSeats) {
                    swal({
                        title: "ChuChuFly!",
                        text: "Error : no this disponibilité of places!",
                        icon: "error",
                    });
                }else {
                    var data = '{"userId":"' + userId + '","flightId":"' + flightIdd + '",' +
                        '"nbPlaces":"' + seats + '", ' +
                        '"date":"' + date + '",' +
                        '"status":"' + status + '",' +
                        '"price":"' + price + '"}';
                    var urlData = "http://localhost:8080/ws/reservation/create";
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
                        if (result) {
                            //si resultat bon, is Ok
                            var restant = remainingSeats-seats;
                            console.log(restant);
                            //modification du nombre de placerestante dans fligth
                            $.ajax({
                                url: "http://localhost:8080/ws/flight/flights/"+restant+"/"+flightId,
                                type: "PUT",
                                contentType: "application/json",
                                cache: false,
                                dataType: "json",

                            }).done(function (resultt) {
                                console.log(resultt);
                                if(resultt){
                                    swal({
                                        title: "ChuChuFly!",
                                        text: "Your reservation is saved",
                                        icon: "success",
                                    });
                                }
                            });
                        } else {
                            swal({
                                title: "ChuChuFly!",
                                text: "Error : Reservation not saved!",
                                icon: "error",
                            });
                        }
                    });
                }
            }
        });
    }
}
