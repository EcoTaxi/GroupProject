const functions = require('firebase-functions');
const express = require('express');
const engines = require('consolidate');
const admin = require("firebase-admin");
const firebase = require ('firebase');
const app = express();


app.engine('hbs', engines.handlebars);
app.set('view', './views');
app.set('view engine', 'hbs');

// const config = {
//   apiKey: " AIzaSyAz1nWyvHjJ2qCPzY_izdae6jZ5Dpj6LHU",
//   authDomain: "ecotaxiphoneapp-9e80f.firebaseapp.com",
//   databaseURL: "https://ecotaxiphoneapp-9e80f.firebaseio.com",
//   storageBucket: "ecotaxiphoneapp-9e80f.appspot.com",
// };
  firebase.initializeApp({
    credential:admin.credential.cert({
    project_id: "ecotaxiphoneapp-9e80f",    
  private_key: "-----BEGIN PRIVATE KEY-----\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCxJ5zcLgw7siMI\ntsoMSjKxE00Hnyz1LqwUhJ3AdkKnP9bOM86CljKZRG4JQLvwrfo41pYIupVgvYX6\nSwV/eZcVXtRL0F/jDun3cIsR2e4hUNGg8k1pNt3E17FnmHBNjGY9OOyGxWISV58v\nipfe2iGEXNmefpOyld1pP7GS/m/gklq3DVMoucGLT6k0OEhQi26qFImyuf5bCWi8\nATsnO0DmPnQy7QiVXg9D8dg5SslvlKFaCbJS7ccKRJUXxroHDsz7Lb2XnkGZPXuo\nYcsAYulBl2cNfH2gYjwYUNbFvt1bl2JkDXlwbvlT0kSFjwtO/XaCeWbYgyxtQt3q\ntdJteOTnAgMBAAECggEAPH07pLy+ZZW+O6f6VnbxVyAxgaQ6vB8rF7huivx2Ztg3\n9uGepl01urqwXc1yPsyQ715qDWH0RnuAJ0X9gxjX2y6ti5ODYirB/cC6Y6aHlm2b\nnnqNrxrSX3eMtkRJOFlzwXzR2nTEN2Ie0yNeFvRhVGpFzkYHFYfxeZILhpcFSWbt\nbNj7zRiHtZaT8p374UCOoRk1z74GnybUyMi7aaR6Dx+TFGWNevuIUp1iFXAR8gcw\nR8h6kAIdb+HgFtx0VeGoyllEjod/5vTECQbqn0OD6pT7bFDe3FrQuJ10LOliNnei\n63wsNYoDGjJD1dpeEYpfnau0ULwvGHATY25uNOa0hQKBgQDtxnMNpmPzDCqFvAjE\n1ZzdDBmqkax21VfTN53b0uU1R0F5CtGIosa+4F1aD848jZyyS7D9vIGuVbAf3Ohn\ngk756c5HUTY1rQodko1Ls1nvhVPaWpRvPocj2TWZSDaKFf7LxQRIo/nORJHno9OQ\nmU+ORnoCHemqHZrWiGjzbnherQKBgQC+u7AKTk7F8aDSh1+BM3AeEKJVDz4Wmd/f\nC/yvIwIBH6cvGzf7Ml0RtQrZdK+evsb+05TnoKi5RzQgkKgvit000UAsrX4ANtnS\nDjkVEnjIbXi6ip9l+HNoJoIeCw0XbMS/kFcPl2nOfWu1d29HKWzBFIr8Ovf1yp4v\nyNQERQ5oYwKBgDTl1WrP/aRwrHUcJAdGTqOOsZcU2X2GcQd4dBIXlQ6JT7OSCJOR\nQw3r9Fi483TdPGMeG74XVfJm9cvg5YNBpjhLH60WEC1Y1ctGc1DIEdYLn2AQjr1A\n9+etxxLW0etegVTr+8v9TvHop10VQVgGA4DvhO07omRKOcxIhLDDftvxAoGANNA9\nv6zNTSxKDuGjRWBb5EdQ2+K7m4CCJ/3muwwjivIb1gRgbPMWKEgMOAiCdJ949Kjb\nq4OmFJ+txHKpz3hRJkXm0jg8Eo0dpXeAwN/7h+wgKO8nkhHIywcWsoD24AyjMjQi\nl1Oi3aWHBJmMu5Sx44jvr0YY0PuI78xZQNFqWucCgYAJGYX7cpDag436/MZJbE74\nbF+bBdNjz7lxmZr4wlgHYNqu3lElrari0SpxX+QG39Ppe9y1oXaDW1F3V65nDxjo\noNfLRW5QClRe4sNk3onCgEaSBk2bTMrq+6tjZ2hj745Xbuohzr15++IZyHmoHlKQ\n75BbzncmWH78HhfW4QqZjQ==\n-----END PRIVATE KEY-----\n",
  client_email: "firebase-adminsdk-vdadk@ecotaxiphoneapp-9e80f.iam.gserviceaccount.com",
}),
databaseURL:"https://ecotaxiphoneapp-9e80f.firebaseio.com/"

  });

  // firebase.database().ref('Test').push(
  //   {name:" flip"}
  // );
  
  
  

 

    const ref = firebase.database().ref("Test");    
    ref.orderByKey().endAt("name").on("child_added", function(snapshot) {
      console.log(snapshot.key,snapshot.val());
    });


 
// app.get('/', (request, response)=>{
//     getUser().then(Test =>{
//     response.render('index',{Test});
//   })

// });

// const port = process.env.PORT ||5000;

// app.listen(port, () =>{
//   console.log(`Server started on port ${port}`);
// });
//  exports.app = functions.https.onRequest(app);