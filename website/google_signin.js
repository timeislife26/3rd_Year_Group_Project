function initGoogleSignIn() {
    gapi.load('auth2', function() {
        gapi.auth2.init({
            client_id: '397818283308-a57r3d3cc2vt4fsmp1tju8desdafdhfg.apps.googleusercontent.com',
        });
    });
}

function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    var id_token = googleUser.getAuthResponse().id_token;

    $.post('../includes/login.inc.php', { id_token: id_token }, function(response) {
        console.log(response);
        window.location.href = 'login.php';
    });

    var verificationCode = generateVerificationCode();
    saveVerificationCode(profile.getEmail(), verificationCode);
    sendVerificationCodeByEmail(profile.getEmail(), verificationCode);
}

function saveVerificationCode(email, verificationCode) {
    $.post('../includes/save_verification_code.php', { email: email, verification_code: verificationCode }, function(response) {
        console.log(response);
    });
}

function sendVerificationCodeByEmail(email, verificationCode) {
    $.post('../includes/send_verification_email.php', { email: email, verification_code: verificationCode }, function(response) {
        console.log(response);
    });
}

function generateVerificationCode() {
    return Math.floor(100000 + Math.random() * 900000);
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function() {
        console.log('User signed out.');
    });
}

document.addEventListener('DOMContentLoaded', function() {
    var script = document.createElement('script');
    script.src = 'https://apis.google.com/js/platform.js';
    script.onload = function() {
        initGoogleSignIn();
    };
    document.head.appendChild(script);
});
