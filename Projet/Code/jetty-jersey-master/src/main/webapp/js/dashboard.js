var fname;
var lname;
var email;
var gsm;
var userId;

$(document).ready(function() {
    var id = localStorage.getItem("userId");
    if(id!=null){
        $.ajax({
            url:"http://localhost:8080/ws/user/users/"+id,
            type:"GET",
            contentType: "application/json",
            cache: false,
            dataType: "json"
        }).success( function (result) {
            if(result){
                $("#dash-fname").append(result["firstName"]);
                $("#dash-lname").append(result["lastName"]);
                $("#dash-role").append(result["typeUser"]);
                $("#dash-email").append(result["email"]);
                $("#dash-gsm").append(result["gsm"]);
                $("#dash-date").append(result["birthDate"]);
                $("#edit-fname").val(result["firstName"]);
                fname=result["firstName"];
                lname= result["lastName"];
                email=result["email"];
                gsm=result["gsm"];
                userId=result["userId"];
                $("#edit-lname").val(result["lastName"]);
                $("#edit-email").val(result["email"]);
                $("#edit-gsm").val(result["gsm"]);
            }
            else{
                swal({
                    title: "ChuChuFly!",
                    text: "Sorry, we couldn't load your data, please try later",
                    icon: "error"
                });
            }
        });
    }else{
        window.location.href="http://localhost:8080/";
    }
});

function view() {
    document.getElementById('edit-profile').style.display='none';
    document.getElementById('profile-settings').style.display='none';
    document.getElementById('verify-profile').style.display='none';
    document.getElementById('delete-profile').style.display='none';
    document.getElementById('view-profile').style.display='block';
}
function edit(){
    document.getElementById('edit-profile').style.display='block';
    document.getElementById('profile-settings').style.display='none';
    document.getElementById('verify-profile').style.display='none';
    document.getElementById('delete-profile').style.display='none';
    document.getElementById('view-profile').style.display='none';   
}
function settings(){
    document.getElementById('edit-profile').style.display='none';
    document.getElementById('profile-settings').style.display='block';
    document.getElementById('verify-profile').style.display='none';
    document.getElementById('delete-profile').style.display='none';
    document.getElementById('view-profile').style.display='none';   
}
function verify(){
    document.getElementById('edit-profile').style.display='none';
    document.getElementById('profile-settings').style.display='none';
    document.getElementById('verify-profile').style.display='block';
    document.getElementById('delete-profile').style.display='none';
    document.getElementById('view-profile').style.display='none';   
}
function Delete(){
    document.getElementById('edit-profile').style.display='none';
    document.getElementById('profile-settings').style.display='none';
    document.getElementById('verify-profile').style.display='none';
    document.getElementById('delete-profile').style.display='block';
    document.getElementById('view-profile').style.display='none';   
}

function cancelEditUser(){
    $('#edit-fname').val(fname);
    $('#edit-lname').val(lname);
    $('#edit-email').val(email);
    $('#edit-gsm').val(gsm);
}

function updateUser(){
    var newFname=$("#edit-fname").val();
    var newLname=$("#edit-lname").val();
    var newEmail=$("#edit-email").val();
    var newGsm=$("#edit-gsm").val();
    userId=localStorage["userId"];
    var data= '{"firstName":"'+newFname+'", "lastName":"'+newLname+'", "email":"'+newEmail+'","gsm":"'+newGsm+'","userId":"'+userId+'"}';
    var dataUrl = "http://localhost:8080/ws/user/update";
    $.ajax({
        url: dataUrl,
        type: "PUT",
        data: data,
        contentType: "application/json",
        cache: false,
        dataType: "json",
    }).done(function (result) {
        if(result){
            swal({
                title: "ChuChuFly!",
                text: "Profile updated successfully!",
                icon: "success",
            });
            window.location.href="http://localhost:8080/dashboard.html";
        }else{
            swal({
                title: "ChuChuFly!",
                text: "An error ocurred, please try later",
                icon: "error",
            });
        }
    });
}

function delete_account() {
    userId=localStorage["userId"];
    var dataUrl = "http://localhost:8080/ws/user/delete/"+userId;
    $.ajax({
        url: dataUrl,
        type: "DELETE",
        contentType: "application/json",
        cache: false,
        dataType: "json"
    }).done(function (result) {
        if(result){
            swal({
                title: "ChuChuFly!",
                text: "Profile deleted successfully!",
                icon: "success",
            });
            localStorage.clear();
            sessionStorage.clear();
            window.location.href="http://localhost:8080/home.html";
        }else{
            swal({
                title: "ChuChuFly!",
                text: "An error ocurred, please try later",
                icon: "error",
            });
        }
    });
}

function updatePassword(){

}

function cancelEditPassword(){

}

function saveFiles(){

}

function cancelSavingFiles(){

}
