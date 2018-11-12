const mongoose = require('mongoose');
const Schema = mongoose.Schema;

//Create Schema
const CustomerSchema = new Schema({
  name:{
    type:String,
    required: true
  },
  email:{
    type:String,
    required: true
  },
  phone:{
    type:String,
    required: true
  },
  dob:{
    type:String,
    required: true
  },
  address:{
    type: String,
    required: true
  },
  pType:{
    type: String,
    required:true
  },
  date:{
    type: Date,
    default: Date.now
  }
});

mongoose.model('customers', CustomerSchema);