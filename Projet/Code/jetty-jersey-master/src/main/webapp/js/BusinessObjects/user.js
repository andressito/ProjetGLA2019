function pilot_detecter(){
    if(document.getElementById('pilot').checked){
        document.getElementById('Licence').style.display='inline';
        document.getElementById('passenger').checked="false";
    }
    else{
        document.getElementById('Licence').style.display='none';
        document.getElementById('passenger').checked="true";
    }
}

function passenger_detecter(){
    if(document.getElementById('passenger').checked){
        document.getElementById('Licence').style.display='none';
        document.getElementById('pilot').checked="false";
    }
    else{
        document.getElementById('Licence').style.display='inline';
        document.getElementById('pilot').checked="true";
    }
}

