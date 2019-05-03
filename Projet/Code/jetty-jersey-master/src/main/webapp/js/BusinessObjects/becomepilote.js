$(document).ready(function () {
    if (localStorage.getItem("userId")) {
        if (localStorage.getItem("typeUser") === "passenger") {
            $("#menu").load('../Menu/MenuPassenger.html');
        }else {
            window.location.href = "http://localhost:8080";
        }
        $.ajax({
            url: "http://localhost:8080/ws/user/users/" + localStorage.getItem("userId"),
            type: "GET",
            contentType: "application/json",
            cache: false,
            dataType: "json"
        }).done(function (result) {
            var firstName = result['firstName'];
            $("#firstnam").val("" + firstName);
            var lastName = result['lastName'];
            $("#lastnam").val("" + lastName);
            var birthDate = result['birthDate'];
            $("#birt").val("" + birthDate);
        });
    } else {
        window.location.href = "http://localhost:8080";
    }
});

function becomepilot() {
    var licence = $("#licenc").val();
    var today = new Date();
    var valDate = new Date(licence);
    today.setMonth(2);
    if (today > valDate) {
        $('#validityDate').focus();
        //return false;
        swal({
            title: "ChuChuFly!",
            text: "license expiration date is not valid",
            icon: "error"
        });
    } else {
        var numberHoursFlight = "0";
        var mark = "0";
        var userId = localStorage.getItem("userId");
        var data = '{"validityDate":"' + licence + '", "numberHoursFlight":"' + numberHoursFlight + '", "mark":"' + mark + '","userId":"' + userId + '"}';
        var dataUrl = "http://localhost:8080/ws/licence/create";
        $.ajax({
            url: dataUrl,
            type: "POST",
            data: data,
            contentType: "application/json",
            cache: false,
            dataType: "json"
        }).done(function (result) {
            var type = "pilot";
            if (result) {
                var dataPut = '{"userId":"' + userId + '", "typeUser":"' + type + '"}';
                update(dataPut);
            }
        });
    }
}

function update(dataPut) {
    $.ajax({
        url: "http://localhost:8080/ws/user/update",
        type: "PUT",
        data: dataPut,
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).success(function (result) {
        if (result) {
            swal({
                title: "ChuChuFly!",
                text: "Congratulations, you became officially a ChuChuFly Pilot",
                icon: "success"
            }).then(function () {
                localStorage.removeItem("typeUser");
                localStorage.setItem("typeUser", "pilot");
                window.location.href = "http://localhost:8080";
            });
        } else {
            swal({
                title: "ChuChuFly!",
                text: "Something wrong !",
                icon: "error"
            });
        }
    });
}
