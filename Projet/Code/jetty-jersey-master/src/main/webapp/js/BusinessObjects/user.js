$(document).ready(function() {
    if(localStorage.getItem("firstName") || localStorage.getItem("userId")){
        window.location.href="http://localhost:8080/";
    }else{
        if(document.getElementById('passenger'))
            if(document.getElementById('passenger').checked){
                document.getElementById('Licence').style.display='none';
            }
            else{
                document.getElementById('Licence').style.display='inline';
            }
    }
});
function passenger_detecter(){
        if(document.getElementById('passenger').checked){
            document.getElementById('Licence').style.display='none';
        }
        else{
            document.getElementById('Licence').style.display='inline';
        }
}
function pilot_detecter(){
    if(document.getElementById('pilot').checked){
        document.getElementById('Licence').style.display='inline';
    }
    else{
        document.getElementById('Licence').style.display='none';
    }
}

function tester(x) {
    if($("#"+x).val()=="" || $("#"+x).val().length <2){
        $('#'+x).focus();
        return false;
    }
}



function fenvoi() {
    if($("#firstName").val()=="" || $("#firstName").val().length <2){
        $('#firstName').focus();
        return false;
    }
    if($("#lastName").val()=='' || $("#lastName").val().length <2){
        $('#lastName').focus();
        return false;
    }
    if($("#birthDate").val()==""){
        $('#birthDate').focus();
        return false;
    }
    if(document.getElementById('pilot').checked){
        if($("#validityDate").val()==""){
            $('#validityDate').focus();
            return false;
        }
    }
    if($("#email").val()==""){
        $('#email').focus();
        return false;
    }
    if($("#password").val()==""){
        $('#password').focus();
        return false;
    }
    if($("#passwordConfirm").val()==""){
        $('#passwordConfirm').focus();
        return false;
    }

    var birth= $("#birthDate").val();
    var d = new Date();
    var month = d.getMonth()+1;
    var day = d.getDate();
    var output = d.getFullYear() + '/' +(month<10 ? '0' : '') + month + '/' +(day<10 ? '0' : '') + day;
    var passe1= $("#password").val();
    var passe2= $("#passwordConfirm").val();
    var longueurp1 = passe1.length;
    var d1=Date.parse(birth);
    var d2=Date.parse(output);
    var tmp=d2-d1;
    var days = Math.floor(tmp / 31536000000);
    if(passe1 != passe2) {
        swal({
            title: "ChuChuFly!",
            text: "passwords D'ont Match",
            icon: "error"
        });
        return false;
    }
    if(longueurp1 < 6){
        swal({
            title: "ChuChuFly!",
            text: "passwords not strong enough",
            icon: "error"
        });
        return false;
    }
    if(days<20 || days>90){
        swal({
            title: "ChuChuFly!",
            text: "birth date is not allowed",
            icon: "error"
        });
        return false;
    }
    if(document.getElementById('passenger').checked){
        addPassenger();
    }else{
        var date= $("#validityDate").val();
        var today = new Date();
        var valDate = new Date(date);
        today.setMonth(2);
        if(today>valDate){
            $('#validityDate').focus();
            return false;
        }
        addPilot();
    }
}

function addPassenger(){
    var fistName=$("#firstName").val();
    var lastName=$("#lastName").val();
    var bithDate=$("#birthDate").val();
    var email=$("#email").val();
    var gsm=$("#gsm").val();
    var password=$("#password").val();
    var type="passenger";
    var user = "{ \"firstName\":\""+fistName+"\" , \"lastName\":\""+lastName+"\" , \"birthDate\": " +
    "\""+bithDate+"\" , \"email\":\""+email+"\", \"gsm\":\""+gsm+", \"type\":\""+type+"\" ,\"password\": \""+password+"\"}";
    var licence='{ "licenceId": "null"}';
    var combinedObj = {};
    combinedObj["user"] = user;
    combinedObj["licence"] = licence;
    $.ajax({
        url: "http://localhost:8080/ws/user/create",
        type: "POST",
        data: JSON.stringify(combinedObj),
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (result) {

    }).success(function (result) {
        if(result) {
            if (result) {
                $.ajax({
                    url: "http://localhost:8080/ws/user/users/email/"+email,
                    type: "GET",
                    contentType: "application/json",
                    cache: false,
                    dataType: "json"
                }).done(function (result) {
                    var test = result;
                    console.log(result['firstName']);
                    localStorage.setItem("firstName", result['firstName']);
                    sessionStorage.setItem("firstName", result['firstName']);
                    localStorage.setItem("userId", result['userId']);
                    sessionStorage.setItem("userId", result['userId']);
                    localStorage.setItem("typeUser", result['typeUser']);
                    sessionStorage.setItem("typeUser", result['typeUser']);
                    window.location.href = "http://localhost:8080/";
                });
            } else {
                swal({
                    title: "ChuChuFly!",
                    text: "Email used",
                    icon: "error"
                });
            }
        }
    });
}

function addPilot(){
    var fistName=$("#firstName").val();
    var lastName=$("#lastName").val();
    var bithDate=$("#birthDate").val();
    var email=$("#email").val();
    var gsm=$("#gsm").val();
    var password=$("#password").val();
    var type="pilot";
    var validityDate= $("#validityDate").val();
    console.log("ok");
    var user = "{ \"firstName\":\""+fistName+"\" , \"lastName\":\""+lastName+"\" , \"birthDate\": " +
        "\""+bithDate+"\" , \"email\":\""+email+"\", \"gsm\":\""+gsm+", \"type\":\""+type+"\" ,\"password\": \""+password+"\"}";
    var licence='{ "validityDate": "'+validityDate+'"}';
    var combinedObj = {};
    combinedObj["user"] = user;
    combinedObj["licence"] = licence;
    $.ajax({
        url: "http://localhost:8080/ws/user/create",
        type: "POST",
        data: JSON.stringify(combinedObj),
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (result) {

    }).success(function (result) {
        if(result) {
            if (result) {
                $.ajax({
                    url: "http://localhost:8080/ws/user/users/email/" + email,
                    type: "GET",
                    contentType: "application/json",
                    cache: false,
                    dataType: "json"
                }).done(function (result) {
                    var test = result;
                    console.log(result['firstName']);
                    localStorage.setItem("firstName", result['firstName']);
                    sessionStorage.setItem("firstName", result['firstName']);
                    localStorage.setItem("userId", result['userId']);
                    sessionStorage.setItem("userId", result['userId']);
                    localStorage.setItem("typeUser", result['typeUser']);
                    sessionStorage.setItem("typeUser", result['typeUser']);
                    window.location.href = "http://localhost:8080/";
                });
            } else {
                swal({
                    title: "ChuChuFly!",
                    text: "Email used",
                    icon: "error"
                });
            }
        }
    });
}

$(function(){
    $("#btSignIn").click(function(){
        var email =$("#email").val();
        var password=$("#password").val();
        if(email.length<5){
            $('#email').focus();
            return false;
        }
        if(password.length<5){
            $('#password').focus();
            return false;
        }
        $.ajax({
            url:"http://localhost:8080/ws/user/signin",
            type:"POST",
            data:"{\"email\":\""+email+"\" , \"password\": \""+password+"\"}",
            contentType: "application/json",
            cache: false,
            dataType: "json"
        }).success( function (result) {
            if(result){
                $.ajax({
                    url:"http://localhost:8080/ws/user/users/email/"+email ,
                    type:"GET",
                    contentType: "application/json",
                    cache: false,
                    dataType: "json"
                }).done(function (result) {
                    var test = result;
                    console.log(result['firstName']);
                    localStorage.setItem("firstName",result['firstName']);
                    sessionStorage.setItem("firstName",result['firstName']);
                    localStorage.setItem("userId",result['userId']);
                    sessionStorage.setItem("userId",result['userId']);
                    localStorage.setItem("typeUser", result['typeUser']);
                    sessionStorage.setItem("typeUser", result['typeUser']);
                    window.location.href="http://localhost:8080/";
                });
            }else{
                swal({
                    title: "ChuChuFly!",
                    text: "Bad email or password ",
                    icon: "error"
                });
            }
        });
    });
});
