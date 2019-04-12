$(document).ready(function() {
    var firstName=localStorage.getItem("firstName");
    $("#Profile").html(firstName);
});
    $(function () {
        $("#signOut").click(function () {
            sessionStorage.clear();
            localStorage.clear();
            window.location.href="http://localhost:8080/";
            swal({
                title: "ChuChuFly!",
                text: "Success Sign Out",
                icon: "success"
            });
        });
    });