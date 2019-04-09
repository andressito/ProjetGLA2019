var fname;
var lname;
var email;
var gsm;

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

function cancelEdit(){
    $('#edit-fname').val(fname);
    $('#edit-lname').val(lname);
    $('#edit-email').val(email);
    $('#edit-gsm').val(gsm);
}

function update(){

}
function delete_account() {

}