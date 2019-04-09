$(document).ready(function() {
if(localStorage.getItem("userId")){
$("#menu").load('../Menu/MenuPilot.html');
    $.ajax({
                url: "http://localhost:8080/ws/user/users/"+localStorage.getItem("userId"),
                type: "GET",
                contentType: "application/json",
                cache: false,
                dataType: "json"
                }).done(function (result) {
                    var test = result;



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

   /* var d = new Date();
    var month = d.getMonth()+1;
    var day = d.getDate();
    var output = d.getFullYear() + '/' +(month<10 ? '0' : '') + month + '/' +(day<10 ? '0' : '') + day;
    var d1=Date.parse(output);
    var d2=Date.parse(licence);
    console.log(d2);
    if (d1 > d2) {
     swal({
             title: "ChuChuFly!",
             text: "invalid license !",
             icon: "error",
                });*/
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
            if(result){
                var dataPut= '{"userId":"'+userId+'", "numberHoursFlight":"'+numberHoursFlight+'", "mark":"'+mark+'","userId":"'+userId+'"}';
                $.ajax({
                    url: "http://localhost:8080/ws/user/users/",
                    type: "PUT",
                    data: data,
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

                      window.location.href = "http://localhost:8080/";
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
};$(document).ready(function() {
if(localStorage.getItem("userId")){
$("#menu").load('../Menu/MenuPilot.html');
    $.ajax({
                url: "http://localhost:8080/ws/user/users/"+localStorage.getItem("userId"),
                type: "GET",
                contentType: "application/json",
                cache: false,
                dataType: "json"
                }).done(function (result) {
                    var test = result;



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

   /* var d = new Date();
    var month = d.getMonth()+1;
    var day = d.getDate();
    var output = d.getFullYear() + '/' +(month<10 ? '0' : '') + month + '/' +(day<10 ? '0' : '') + day;
    var d1=Date.parse(output);
    var d2=Date.parse(licence);
    console.log(d2);
    if (d1 > d2) {
     swal({
             title: "ChuChuFly!",
             text: "invalid license !",
             icon: "error",
                });*/
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
            console.log(result);
            if(result){
                $.ajax({
                    url: "http://localhost:8080/ws/user/users/"+userId,
                    type: "PUT",
                    data: data,
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

                      window.location.href = "http://localhost:8080/";
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
};