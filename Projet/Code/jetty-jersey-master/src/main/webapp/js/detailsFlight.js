const ok="";
$(document).ready(function() {
    if(!localStorage.getItem("userId")){
        window.location.href="http://localhost:8080/";
    }else {
        if (localStorage.getItem("typeUser") === "passenger") {
            $("#menu").load('../Menu/MenuPassenger.html');
        } else {
            $("#menu").load('../Menu/MenuPilot.html');
        }
        if(window.location.pathname === "/DetailsFlight.html"){
            $.ajax({
                url: "http://localhost:8080/ws/flight/flights/" + sessionStorage.getItem("saveIdFlight"),
                type: "GET",
                contentType: "application/json",
                cache: false,
                dataType: "json"
            }).done(function (result) {
                console.log(result);
                const flightId = result['flightId'];
                const departureAerodrom = result['departureAerodrom'];
                const type = result['type'];
                const arrivalAerodrom = result['arrivalAerodrom'];
                const remainingSeats= result['remainingSeats'];
                const price = result['price'];
                const userId = result['userId'];
                const dateFlight = result['date'];
                const departureTime= result['departureTime'];
                const arrivalTime= result['arrivalTime'];
                $.ajax({
                    url: "http://localhost:8080/ws/user/users/"+userId,
                    type: "GET",
                    contentType: "application/json",
                    cache: false,
                    dataType: "json"
                }).done(function (result) {
                    const userName=result['firstName'];
                    var ress =
                        '<div class="col-md-3" ></div>'+
                        '<div class="col-md-5" ><div class="price-box">'+
                        '<h2 class="pricing-plan">' + type + '</h2>' +
                        '<div class="price"><sup class="currency">€</sup>' + price + '<small>/seat</small></div>' +
                        '<p> From : ' + departureAerodrom + '</p>' +
                        '<p> Date : ' + dateFlight + '</p>' +
                        '<p> Departure Time : ' + departureTime + '</p>' +
                        '<p> Arrival Time : ' + arrivalTime + '</p>' +
                        '<p> To : ' + arrivalAerodrom+ '</p>' +
                        '<p> Remaining Seats : ' + remainingSeats+ '</p>' +
                        '<p> Pilot : ' + userName+ '</p>' +
                        '<h6 id="idFlight1"> &#x2605; &#x2605; &#x2606; &#x2606; &#x2606; </h6>' +
                        '<a class="btn btn-select-plan btn-sm"   onclick="booking(\'' + flightId + '\',\'' + userId + '\');" > Booking </a>' +
                        '</div></div>';
                    $("#flightList").append(ress);
                });

            });
        }else if(window.location.pathname === "/Booking.html"){
            var flightIdBooking= localStorage.getItem("flightIdBooking");
            getServerData("http://localhost:8080/ws/flight/flights/" +flightIdBooking,callDoneFlightDetails);

        }
    }
});

function booking(flightId,userId) {
    localStorage.setItem("flightIdBooking",flightId);
    window.location.href="http://localhost:8080/Booking.html";
}

function getServerData(url, success){
    $.ajax({
        dataType: "json",
        url: url,
        type: "get",
    }).done(success);
}


function callDone(result){
    var templateExample = _.template($('#templateExample').html());

    var html = templateExample({
        "type":JSON.stringify(result['type']),
        "price":JSON.stringify(result['price']),
        "departure":JSON.stringify(result['departureAerodrom']),
        "arrival":JSON.stringify(result['arrivalAerodrom']),
        "seats":JSON.stringify(result['remainingSeats']),
        "userName":JSON.stringify(result['userId'])
    });
    $("#result").append(html);
}