function detecter(){
    console.log("ok André!");
    if(document.getElementById('visit').checked){
        document.getElementById('ArrivalA').style.display='none';
        document.getElementById('ArrivalT').style.display='none';
        document.getElementById('labelAT').style.display='none';
        document.getElementById('labelAA').style.display='none';
    }
    else{
        document.getElementById('ArrivalA').style.display='inline';
        document.getElementById('ArrivalT').style.display='inline';
        document.getElementById('labelAT').style.display='inline';
        document.getElementById('labelAA').style.display='inline';
    }
}