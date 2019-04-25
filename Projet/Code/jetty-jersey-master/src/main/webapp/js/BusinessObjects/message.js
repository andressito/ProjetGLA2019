$(document).ready(function() {
   if(localStorage.getItem("userId")){
        if (localStorage.getItem("typeUser") === "passenger") {
            $("#menu").load('../Menu/MenuPassenger.html');
            console.log(localStorage.getItem("typeUser"));
        } else {
            $("#menu").load('../Menu/MenuPilot.html');
        }
        $.ajax({
            url:"http://localhost:8080/ws/user/users/"+localStorage.getItem("userId"),
            type:"GET",
            contentType: "application/json",
            cache: false,
            dataType: "json"
        }).done( function (result) {
            if(result){
            var email = result['email'];
            console.log(email);
            $("#user-email").val(""+email);

            }
        });
    }
 });
function write_message(){
window.location.href="http://localhost:8080/write_message.html";
}
function Envoyer_message(){
        var senderId=localStorage.getItem("userId");
        var content=$("#message").val();
        var receiverId=1;
        var sendingDate = new Date();
        console.log(senderId);
        console.log(receiverId);
        console.log(content);
        console.log(sendingDate);
        var data= '{"content":"'+content+'", "senderId":"'+senderId+'", "receiverId":"'+receiverId+'", "sendingDate":"'+sendingDate+'"}';
        var dataUrl = "http://localhost:8080/ws/message/create";
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
                 swal({
                       title: "ChuChuFly!",
                       text: "sent message !",
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