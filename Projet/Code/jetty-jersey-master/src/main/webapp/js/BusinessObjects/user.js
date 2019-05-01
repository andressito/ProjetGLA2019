$(document).ready(function() {
    if(localStorage.getItem("userId")){
        window.location.href="http://localhost:8080/";
    }else{
        if(document.getElementById('passenger')) {
            if (document.getElementById('passenger').checked) {
                document.getElementById('Licence').style.display = 'none';
            } else {
                document.getElementById('Licence').style.display = 'inline';
            }
        }
        if(localStorage.getItem("singUp")){
            localStorage.clear();
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

function fenvoi() {

    if($("#firstName").val()==="" ){
        swal({
            title: "ChuChuFly!",
            text: "firstname can't be empty",
            icon: "error"
        });
        return false;
    }
    if($("#lastName").val()==='' ){
        swal({
            title: "ChuChuFly!",
            text: "lastname can't be empty",
            icon: "error"
        });
        return false;
    }
    if($("#birthDate").val()===""){
        swal({
            title: "ChuChuFly!",
            text: "birthdate can't be empty",
            icon: "error"
        });
        return false;
    }
    if(document.getElementById('pilot').checked){
        if($("#validityDate").val()===""){
            swal({
                title: "ChuChuFly!",
                text: "License expiration date can't be empty",
                icon: "error"
            });
            return false;
        }
    }
    if($("#email").val()===""){
        swal({
            title: "ChuChuFly!",
            text: "email can't be empty",
            icon: "error"
        });
        return false;
    }
    if($("#password").val()===""){
        swal({
            title: "ChuChuFly!",
            text: "password can't be empty",
            icon: "error"
        });
        return false;
    }
    if($("#passwordConfirm").val()===""){
        swal({
            title: "ChuChuFly!",
            text: "paswword can't be empty",
            icon: "error"
        });
        return false;
    }

    const birth= $("#birthDate").val();
    const d = new Date();
    const month = d.getMonth()+1;
    const day = d.getDate();
    const output = d.getFullYear() + '/' +(month<10 ? '0' : '') + month + '/' +(day<10 ? '0' : '') + day;
    const passe1= $("#password").val();
    const passe2= $("#passwordConfirm").val();
    const longueurp1 = passe1.length;
    const d1=Date.parse(birth);
    const d2=Date.parse(output);
    const tmp=d2-d1;
    const days = Math.floor(tmp / 31536000000);
    if(passe1 != passe2) {
        swal({
            title: "ChuChuFly!",
            text: "passwords don't Match",
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
            text: "birth date is not valid",
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
            swal({
                title: "ChuChuFly!",
                text: "license expiration date is not valid",
                icon: "error"
            });
            return false;
        }
        addPilot();
    }
}

function addPassenger(){
    const fistName=$("#firstName").val();
    const lastName=$("#lastName").val();
    const birthDate=$("#birthDate").val();
    const email=$("#email").val();
    const gsm=$("#gsm").val();
    const password=$("#password").val();
    const type="passenger";
    const user = "{ \"firstName\":\""+fistName+"\" , \"lastName\":\""+lastName+"\" , \"birthDate\": " +
        "\""+birthDate+"\" , \"email\":\""+email+"\", \"gsm\":\""+gsm+", \"type\":\""+type+"\" ,\"password\": \""+password+"\"}";
    const licence='{ "licenceId": "null"}';
    const combinedObj = {};
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
        if (result) {
            swal({
                title: "ChuChuFly!",
                text: "Congrats, you've been registered successfully. \n" +
                    "Please log in to your account",
                icon: "success"
            }).then(function() {
                window.location.href="http://localhost:8080/SignIn.html";
            });
        } else {
            swal({
                title: "ChuChuFly!",
                text: "Email used",
                icon: "error"
            });
        }
    });
}

function addPilot(){
    const fistName=$("#firstName").val();
    const lastName=$("#lastName").val();
    const birthDate=$("#birthDate").val();
    const email=$("#email").val();
    const gsm=$("#gsm").val();
    const password=$("#password").val();
    const type="pilot";
    const validityDate= $("#validityDate").val();
    const user = "{ \"firstName\":\""+fistName+"\" , \"lastName\":\""+lastName+"\" , \"birthDate\": " +
        "\""+birthDate+"\" , \"email\":\""+email+"\", \"gsm\":\""+gsm+", \"type\":\""+type+"\" ,\"password\": \""+password+"\"}";
    const licence='{ "validityDate": "'+validityDate+'"}';
    const combinedObj = {};
    combinedObj["user"] = user;
    combinedObj["licence"] = licence;
    $.ajax({
        url: "http://localhost:8080/ws/user/create",
        type: "POST",
        data: JSON.stringify(combinedObj),
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).success(function (result) {
        if(result) {
            if (result) {
                swal({
                    title: "ChuChuFly!",
                    text: "Congrats, you've been registered successfully. \n" +
                        "Please log in to your account",
                    icon: "success"
                }).then(function() {
                    window.location.href="http://localhost:8080/SignIn.html";
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
        const email =$("#email").val();
        const password=$("#password").val();
        if(email.length===0){
            swal({
                title: "ChuChuFly!",
                text: "email can't be empty",
                icon: "error"
            });
            return false;
        }
        if(password.length===0){
            swal({
                title: "ChuChuFly!",
                text: "password can't be empty",
                icon: "error"
            });
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
                    localStorage.setItem("firstName",result['firstName']);
                    localStorage.setItem("lastName",result['lastName']);
                    localStorage.setItem("email",result['email']);
                    localStorage.setItem("gsm",result['gsm']);
                    localStorage.setItem("userId",result['userId']);
                    localStorage.setItem("typeUser", result['typeUser']);
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

