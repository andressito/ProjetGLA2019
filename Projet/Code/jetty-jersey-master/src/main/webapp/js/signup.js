function fenvoi() {

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
    }
    if(longueurp1 < 6){
        swal({
            title: "ChuChuFly!",
            text: "passwords not strong enough",
            icon: "error"
        });
    }
    if(days<20 || days>80){
        swal({
            title: "ChuChuFly!",
            text: "birth date is not allowed",
            icon: "error"
        });
    }
    ajouterUser();
}

function ajouterUser(){
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