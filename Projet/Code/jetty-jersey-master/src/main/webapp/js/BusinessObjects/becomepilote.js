$(document).ready(function() {
    if(localStorage.getItem("userId")){
        if (localStorage.getItem("typeUser") === "passenger") {
            $("#menu").load('../Menu/MenuPassenger.html');
            console.log("passenger");
        } else {
            $("#menu").load('../Menu/MenuPilot.html');
        }
        $.ajax({
            url: "http://localhost:8080/ws/user/users/"+localStorage.getItem("userId"),
            type: "GET",
            contentType: "application/json",
            cache: false,
            dataType: "json"
        }).done(function (result) {
            var firstName = result['firstName'];
            $("#firstnam").val(""+firstName);
            var lastName = result['lastName'];
            $("#lastnam").val(""+lastName);
            var birthDate = result['birthDate'];
            $("#birt").val(""+birthDate);
        });

    }
});
function becomepilot(){
    var firstName=$("#firstnam").val();
    var lastName=$("#lastnam").val();
    var birthDate=$("#birt").val();
    var licence=$("#licenc").val();
    var today = new Date();
    var valDate = new Date(licence);
    today.setMonth(2);
    if(today>valDate){
        $('#validityDate').focus();
        //return false;
         swal({
                     title: "ChuChuFly!",
                     text: "invalid license !",
                     icon: "error",
                        });
    }else{
        var numberHoursFlight="0";
        var mark= "0";
        var userId=localStorage.getItem("userId");
        console.log(userId);
        var data= '{"validityDate":"'+licence+'", "numberHoursFlight":"'+numberHoursFlight+'", "mark":"'+mark+'","userId":"'+userId+'"}';
        var dataUrl = "http://localhost:8080/ws/licence/create";
        $.ajax({
            url: dataUrl,
            type: "POST",
            data: data,
            contentType: "application/json",
            cache: false,
            dataType: "json",
        }).done(function (result) {
            var type="pilot";
            if(result){
                var dataPut= '{"userId":"'+userId+'", "typeUser":"'+type+'"}';
                $.ajax({
                    url: "http://localhost:8080/ws/user/update",
                    type: "PUT",
                    data: dataPut,
                    contentType: "application/json",
                    cache: false,
                    dataType: "json",
                }).done(function (resultt) {
                    if(result){
                     swal({
                        title: "ChuChuFly!",
                        text: "you are pilot !",
                        icon: "success",
                     });
                    }else{
                      swal({
                         title: "ChuChuFly!",
                         text: "invalid license !",
                         icon: "error",
                      });
                    }
                });
            }
        });
    }
}
