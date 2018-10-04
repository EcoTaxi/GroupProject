var express = require("express");
var app = express();
var path = require("path")


app.get('/', function(req, res){

    res.sendFile(path.join(__dirname +'//index.html'));

});

//server listening on port 8000 to view enter localhost:8000 in browser
app.listen(8000);

console.log("Server Running at port 8000");


