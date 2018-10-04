(function(){

//intialise firebase

const config ={
    apiKey: "AIzaSyBgPm_5eQmX65-ssoiAkRfRA5mnD6p3QyY",
    authDomain: "ecotaxi-6fe0d.firebaseapp.com",
    databaseURL: "https://ecotaxi-6fe0d.firebaseio.com",
    projectId: "ecotaxi-6fe0d",
    storageBucket: "ecotaxi-6fe0d.appspot.com",
    messagingSenderId: "816306720049"

    



};firebase.initializeApp(config);

//get elements
const textEmail = document.getElementById('txtEmail')

const textPassword = document.getElementById('txtPassword')

const btnLogin = document.getElementById('btnLogin')

const btnSignUP = document.getElementById('btnSignUp')

const btnLogout = document.getElementById('btnLogout')

//add login event

btnLogin.addEventListener('click', e=>{

    const email = txtEmail.value;

    const pass = txtPasword.value;

    const auth = firebase.auth();

    const promise = auth.signinwithEmailandPassword(email,pass);

    promise.catch(e=>console.log(e.message));







});

btnSignUp.addEventListener('click',e=>{

    const email = txtEmail.value;

    const pass = txtPasword.value;

    const auth = firebase.auth();

    const promise = auth.signinwithEmailandPassword(email,pass);

    promise.catch(e=>console.log(e.message));

    promise
    .catch(e=> console.log(e.message));
    

});

btnLogout.addEventListener('click',e=>{

    firebase.auth.signOut();


});

firebase.auth().onStateChanged(firebaseUser =>{
if(firebaseUser){

    console.log(firebaseUser);
    btnLogout.classList.add('hide');

}else{

    console.log('Not Logged in');

}




});

}());