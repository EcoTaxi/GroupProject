const express = require('express');
const mongoose = require('mongoose');
const router = express.Router();
const {ensureAuthenticated} = require('../helpers/auth')
//Load Customer Model
require('../models/Customer');
const Customer = mongoose.model('customers');

// Customer Index Page
router.get('/',ensureAuthenticated, (req, res) =>{
  Customer.find({}) 
  .sort({date:'desc'})
  .then(customers =>{
    res.render('customers/index', {
      customers:customers
    });
  });
});

// Add Customer Form
router.get('/add',ensureAuthenticated,(req, res)=> {
  res.render('customers/add')
  });

  // Edit Customer Form
  router.get('/edit/:id',ensureAuthenticated,(req, res)=> {
  Customer.findOne({
    _id: req.params.id
  })
  .then(Customer => { 
    
     
      res.render('customers/edit',{
        Customer:Customer
      });
    
    
  });
});

// Process Form
router.post('/',ensureAuthenticated,(req, res)=>{
  let errors =[];

  if (!req.body.name){
    errors.push({text:'Please add a name'})
  }
  if (!req.body.email){
    errors.push({text:'Please add an email'})
  }
  if (!req.body.phone){
    errors.push({text:'Please add a phone'})
  }
  if (!req.body.dob){
    errors.push({text:'Please add a dob'})
  }
  if (!req.body.address){
    errors.push({text:'Please add an address'})
  }
  if (!req.body.pType){
    errors.push({text:'Please add a pType'})
  }
  
  if(errors.length > 0){
    res.render('/add', {
      errors: errors,
      name: req.body.name,
      email: req.body.email,
      phone: req.body.phone,
      dob: req.body.dob,
      address: req.body.address,
      pType: req.body.pType,
    });
  } else {
    const newUser ={
      name: req.body.name,
      email: req.body.email,
      phone: req.body.phone,
      dob: req.body.dob,
      address: req.body.address,
      pType: req.body.pType,
      user: req.user.id
    }
    new Customer(newUser)
    .save()
    .then(Customer => {
      req.flash('success_msg', 'customer  added');
      res.redirect('/customers');
    })
  }
});

// Edit Form Process
router.put('/:id',ensureAuthenticated, (req, res) => {
  Customer.findOne({
    _id: req.params.id
  })
  .then(Customer =>{
    //new values
    Customer.name =req.body.name;    
    Customer.email= req.body.email,
    Customer.phone= req.body.phone,
    Customer.dob= req.body.dob,
    Customer.address= req.body.address,
    Customer.pType= req.body.pType,
    Customer.save()
    .then(Customer => {
      req.flash('success_msg', 'customer  updated');
      res.redirect('/customers');
    })
  });
});

// Delete Customer
router.delete('/:id',ensureAuthenticated, (req, res) => {
  Customer.remove({_id: req.params.id})
  .then(() =>{
    req.flash('success_msg', 'customer removed');
    res.redirect('/customers');
  });
});

module.exports = router;