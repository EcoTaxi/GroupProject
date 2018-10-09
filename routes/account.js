var express = require('express');
var router = express.Router();


router.get('/', function(req, res, next) {
  res.render('account/index');
});

router.post('/login', function(req, res, next) {
  var db = req.db;
  var loginTable = db.get('login');
  loginTable.find({username: req.body.username, password:req.body.password},{}, function(errors,accounts){

   if(accounts.length == 0){
    req.session.username = req.body.username;
    res.render('account/welcome', data);

   }else{

    var data = {msg:'Invalid Acount'}
    res.render('account/index', data);
   }

  });
});

module.exports = router;
