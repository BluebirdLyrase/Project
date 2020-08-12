// call the packages we need
// #1 Add express package to the app /////////////////////////////////
var express = require('express');
// ===============================

var app = express();   
var cors = require('cors');       

// #2 Add body-parser package to the app
var bodyParser = require('body-parser');
// ===============================


// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(cors());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// #3 Serve static content in folder frontend
// app.use(express.static('frontend'))
// ===============================


var port = process.env.PORT || 8080; 

// ROUTES FOR OUR API
// =============================================================================
var router = express.Router();              // get an instance of the express Router

var api = require('./api');
router.get('/viewHistory', api.getAllViewHistory);
// router.put('/viewHistory', api.getAllViewHistory);

// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', cors(), router);

// #10 Start the server
app.listen(port);