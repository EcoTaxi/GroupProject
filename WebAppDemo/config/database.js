if(process.env.Node_ENV === 'production'){
  module.exports ={mongoURI:
  'mongodb://Kraszu:Doopakoni@1@ds131983.mlab.com:31983/kraszu1'}
}else{
  module.exports ={mongoURI:'mongodb://localhost/EcoTaxi-dev'}
}