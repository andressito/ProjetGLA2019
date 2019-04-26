$(document).ready(function() {
    if(localStorage.getItem("firstName") || localStorage.getItem("userId")){
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

function nothing(){

}

function addPassenger(){
    const fistName=$("#firstName").val();
    const lastName=$("#lastName").val();
    const bithDate=$("#birthDate").val();
    const email=$("#email").val();
    const gsm=$("#gsm").val();
    const password=$("#password").val();
    const type="passenger";
    const user = "{ \"firstName\":\""+fistName+"\" , \"lastName\":\""+lastName+"\" , \"birthDate\": " +
    "\""+bithDate+"\" , \"email\":\""+email+"\", \"gsm\":\""+gsm+", \"type\":\""+type+"\" ,\"password\": \""+password+"\"}";
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
                localStorage.setItem("singUp","ok");
                window.location.href="http://localhost:8080/SignIn.html";
                /*$.ajax({
                    url: "http://localhost:8080/ws/user/users/email/"+email,
                    type: "GET",
                    contentType: "application/json",
                    cache: false,
                    dataType: "json"
                }).done(function (result) {
                    localStorage.setItem("firstName", result['firstName']);
                    sessionStorage.setItem("firstName", result['firstName']);
                    localStorage.setItem("userId", result['userId']);
                    sessionStorage.setItem("userId", result['userId']);
                    localStorage.setItem("typeUser", result['typeUser']);
                    sessionStorage.setItem("typeUser", result['typeUser']);
                    window.location.href = "http://localhost:8080/";
                });*/
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
    const bithDate=$("#birthDate").val();
    const email=$("#email").val();
    const gsm=$("#gsm").val();
    const password=$("#password").val();
    const type="pilot";
    const validityDate= $("#validityDate").val();
    const user = "{ \"firstName\":\""+fistName+"\" , \"lastName\":\""+lastName+"\" , \"birthDate\": " +
        "\""+bithDate+"\" , \"email\":\""+email+"\", \"gsm\":\""+gsm+", \"type\":\""+type+"\" ,\"password\": \""+password+"\"}";
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
    }).done(function (result) {

    }).success(function (result) {
        if(result) {
            if (result) {
                localStorage.setItem("singUp","ok");
                swal({
            		title: "ChuChuFly!",
                    text: "Congrats, you've been registered successfully. \n" +
                        "Please log in to your account",
                        icon: "success"
            		})
            		.then((willDelete) => {
            		  if (willDelete) {
                          window.location.href="http://localhost:8080/SignIn.html";
            		  }
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
            console.log(result);
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
                    sessionStorage.setItem("lastName",result['lastName']);
                    sessionStorage.setItem("email",result['email']);
                    sessionStorage.setItem("gsm",result['gsm']);
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

