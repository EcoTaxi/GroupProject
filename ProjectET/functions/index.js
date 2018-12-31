const functions = require('firebase-functions');
const path = require('path');
const express = require('express');
const engines = require('consolidate');
const admin = require("firebase-admin");
const firebase = require ('firebase');
const exphbs  = require('express-handlebars');
//const methodOverride = require('method-override');
const app = express();




// app.use(express.static(path.join(__dirname, 'public')));
//   const config = {
//   apiKey: "AIzaSyAz1nWyvHjJ2qCPzY_izdae6jZ5Dpj6LHU",
//   authDomain: "ecotaxiphoneapp-9e80f.firebaseapp.com",
//   databaseURL: "https://ecotaxiphoneapp-9e80f.firebaseio.com",
//   projectId: "ecotaxiphoneapp-9e80f",
//   storageBucket: "ecotaxiphoneapp-9e80f.appspot.com",
//   messagingSenderId: "53237770388"
// };
// firebase.initializeApp(config);
//   firebase.initializeApp({
//     credential:admin.credential.cert({
//     project_id: "ecotaxiphoneapp-9e80f",    
//   private_key: "-----BEGIN PRIVATE KEY-----\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCxJ5zcLgw7siMI\ntsoMSjKxE00Hnyz1LqwUhJ3AdkKnP9bOM86CljKZRG4JQLvwrfo41pYIupVgvYX6\nSwV/eZcVXtRL0F/jDun3cIsR2e4hUNGg8k1pNt3E17FnmHBNjGY9OOyGxWISV58v\nipfe2iGEXNmefpOyld1pP7GS/m/gklq3DVMoucGLT6k0OEhQi26qFImyuf5bCWi8\nATsnO0DmPnQy7QiVXg9D8dg5SslvlKFaCbJS7ccKRJUXxroHDsz7Lb2XnkGZPXuo\nYcsAYulBl2cNfH2gYjwYUNbFvt1bl2JkDXlwbvlT0kSFjwtO/XaCeWbYgyxtQt3q\ntdJteOTnAgMBAAECggEAPH07pLy+ZZW+O6f6VnbxVyAxgaQ6vB8rF7huivx2Ztg3\n9uGepl01urqwXc1yPsyQ715qDWH0RnuAJ0X9gxjX2y6ti5ODYirB/cC6Y6aHlm2b\nnnqNrxrSX3eMtkRJOFlzwXzR2nTEN2Ie0yNeFvRhVGpFzkYHFYfxeZILhpcFSWbt\nbNj7zRiHtZaT8p374UCOoRk1z74GnybUyMi7aaR6Dx+TFGWNevuIUp1iFXAR8gcw\nR8h6kAIdb+HgFtx0VeGoyllEjod/5vTECQbqn0OD6pT7bFDe3FrQuJ10LOliNnei\n63wsNYoDGjJD1dpeEYpfnau0ULwvGHATY25uNOa0hQKBgQDtxnMNpmPzDCqFvAjE\n1ZzdDBmqkax21VfTN53b0uU1R0F5CtGIosa+4F1aD848jZyyS7D9vIGuVbAf3Ohn\ngk756c5HUTY1rQodko1Ls1nvhVPaWpRvPocj2TWZSDaKFf7LxQRIo/nORJHno9OQ\nmU+ORnoCHemqHZrWiGjzbnherQKBgQC+u7AKTk7F8aDSh1+BM3AeEKJVDz4Wmd/f\nC/yvIwIBH6cvGzf7Ml0RtQrZdK+evsb+05TnoKi5RzQgkKgvit000UAsrX4ANtnS\nDjkVEnjIbXi6ip9l+HNoJoIeCw0XbMS/kFcPl2nOfWu1d29HKWzBFIr8Ovf1yp4v\nyNQERQ5oYwKBgDTl1WrP/aRwrHUcJAdGTqOOsZcU2X2GcQd4dBIXlQ6JT7OSCJOR\nQw3r9Fi483TdPGMeG74XVfJm9cvg5YNBpjhLH60WEC1Y1ctGc1DIEdYLn2AQjr1A\n9+etxxLW0etegVTr+8v9TvHop10VQVgGA4DvhO07omRKOcxIhLDDftvxAoGANNA9\nv6zNTSxKDuGjRWBb5EdQ2+K7m4CCJ/3muwwjivIb1gRgbPMWKEgMOAiCdJ949Kjb\nq4OmFJ+txHKpz3hRJkXm0jg8Eo0dpXeAwN/7h+wgKO8nkhHIywcWsoD24AyjMjQi\nl1Oi3aWHBJmMu5Sx44jvr0YY0PuI78xZQNFqWucCgYAJGYX7cpDag436/MZJbE74\nbF+bBdNjz7lxmZr4wlgHYNqu3lElrari0SpxX+QG39Ppe9y1oXaDW1F3V65nDxjo\noNfLRW5QClRe4sNk3onCgEaSBk2bTMrq+6tjZ2hj745Xbuohzr15++IZyHmoHlKQ\n75BbzncmWH78HhfW4QqZjQ==\n-----END PRIVATE KEY-----\n",
//   client_email: "firebase-adminsdk-vdadk@ecotaxiphoneapp-9e80f.iam.gserviceaccount.com",
// }),
// databaseURL:"https://ecotaxiphoneapp-9e80f.firebaseio.com/"

//   });

  // firebase.database().ref('User1/User').update(
  //   {name:" Maciej Tomasz ",
  //    cardName: "visa",
  //     dob :"10-13-77",
  //     address:"25 dasdd dasds",
  //     expire:"23.12.22",
  //     password:"zenon3",
  //     phoneNum:"003535005454544",
  //     csv:"666",
  //     visaNum:"xxxx xxxx xxxx xxxxx"}
  // );
  // const toRem= firebase.database().ref("User/-LSufKlCfnudkfZnbbvk");
  // toRem.remove();
  
  // // for geting data where child is specified by name not by -LSufNAhRlOEoEmO1z4P type
  // //return single line with kay that is equal to in thos case will be name: John Doe
  // const ref = firebase.database().ref("User1");    
  // ref.orderByKey().equalTo("name").on("child_added", function(snapshot) {
  //   console.log(snapshot.key,snapshot.val());
  // });

 
//    //for geting data when structure involve -LSufNAhRlOEoEmO1z4P type 
//   // return whole entry data
//   const ref = firebase.database().ref("User");    
//   ref.orderByChild("email").equalTo("mikehunt2013@hotmail.com").on("child_added", function(snapshot) {
//     console.log(snapshot.key,snapshot.val());
//  });

  // //   for all kind of data 
  // //  return entry as array User1 { DOB: '10-13-7', cardName: 'visa', name: ' flip' }
  // //  or Car { '-LT31kBCWu3J1gkngxka': { location: 'yup :D' },
  // //  '-LT31vOTqQAsU2IoN6zD': { location: 'yup :D' },
  // //  '-LT320pZPrOnZx19qOXO': { location: 'working on it now :P :D' },
  //   const Ola = firebase.database().ref("Trip").orderByValue().on("value", function(snapshot) {
  //    // snapshot.forEach(function(data){
  //     console.log(snapshot.key,snapshot.val());
  //  // });
  // });

 
//  app.get('/', (request, response)=>{
//   const ref = firebase.database().ref("User");    
//   ref.orderByChild("email").equalTo("mikehunt2013@hotmail.com").on("child_added", function(snapshot){
//     response.render('index',ref);
  
//   })
//  });
// Handlebars Middleware
app.engine('handlebars', exphbs({
  defaultLayout: 'main'
}));
app.set('view engine', 'handlebars');


app.get('/', (req, res) => {
   res.render('index')
});
app.get('/index', (req, res) => {
  res.render('index')
});
// Customer info Route
app.get('/customer_info', (req, res)=>{
  res.render('customer_info')
});

//Customer to bookings
app.get('/booking',(req, res) => {  
  res.render('booking');
});
//  Car Details
app.get('/car_details', (req, res)=> {  
res.render('car_details')
});

 const port = process.env.PORT ||5000;

app.listen(port, () =>{
   console.log(`Server started on port ${port}`);
 });
   exports.app = functions.https.onRequest(app);