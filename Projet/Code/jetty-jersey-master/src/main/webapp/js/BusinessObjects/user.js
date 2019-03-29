$(document).ready(function() {

    if(localStorage.getItem("save")){
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
        if($("#licencePilot").val()==""){
            $('#licencePilot').focus();
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


    //continuer les tester avant d'envoyer le tout




    var birth= $("#birthDate").val();
    var d = new Date();
    var month = d.getMonth()+1;
    var day = d.getDate();
    var output = d.getFullYear() + '/' +(month<10 ? '0' : '') + month + '/' +(day<10 ? '0' : '') + day;
    var passe1= $("#password").val();
    var passe2= $("#passwordConfirm").val();
    var longueurp1 = passe1.length;
    var longueurp2 = passe2.length;
    console.log(longueurp1);
    d1=Date.parse(birth);
    d2=Date.parse(output);
    tmp=d2-d1;
    days = Math.floor(tmp / 31536000000);
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
        ajouterPassenger();
    }
    else{
        ajoutePilot();
    }
}

function ajouterPassenger(){
    var fistName=$("#firstName").val();
    var lastName=$("#lastName").val();
    var bithDate=$("#birthDate").val();
    var email=$("#email").val();
    var password=$("#password").val();
    $.ajax({
        url: "http://localhost:8080/ws/user/create",
        type: "POST",
        data: "{ \"firstName\":\""+fistName+"\" , \"lastName\":\""+lastName+"\" , \"birthDate\": \""+bithDate+"\" , \"email\":\""+email+"\" ,\"password\": \""+password+"\"}",
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (result) {

    }).success(function (result) {
        if(result){
            localStorage.setItem("save",email);
            localStorage.setItem("FirstName",fistName);
            sessionStorage.setItem("email",email);
            window.location.href="http://localhost:8080/";
            swal({
                title: "ChuChuFly!",
                text: "Success Sign Up",
                icon: "success"
            });
        }else{
            swal({
                title: "ChuChuFly!",
                text: "Email used",
                icon: "error"
            });
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
                localStorage.setItem("save",email);
                sessionStorage.setItem("email",email);
                window.location.href="http://localhost:8080/";
                swal({
                    title: "ChuChuFly!",
                    text: "Success Sign In",
                    icon: "success"
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

