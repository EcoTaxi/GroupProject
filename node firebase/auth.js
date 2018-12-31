var firebase = require('firebase');
require ('firebase/auth');
require ('firebase/database');


var config ={
    apiKey: "AIzaSyAz1nWyvHjJ2qCPzY_izdae6jZ5Dpj6LHU",
    authDomain: "ecotaxiphoneapp-9e80f.firebaseapp.com",
    databaseURL: "https://ecotaxiphoneapp-9e80f.firebaseio.com",
    projectId: "ecotaxiphoneapp-9e80f",
    storageBucket: "ecotaxiphoneapp-9e80f.appspot.com",
    messagingSenderId: "53237770388"


};
firebase.initialize(config);