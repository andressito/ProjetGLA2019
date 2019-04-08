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