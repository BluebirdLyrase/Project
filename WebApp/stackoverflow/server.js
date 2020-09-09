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
app.use(express.static('View'))
// ===============================


var port = process.env.PORT || 8095; 

// ROUTES FOR OUR API
// =============================================================================
var router = express.Router();              // get an instance of the express Router

var api = require('./api');

//get Data
router.get('/viewHistory', api.getAllViewHistory);
router.get('/searchingHistory', api.getAllSearchingHistory);
router.get('/user', api.getAllUser);

//add Data
router.post('/addViewHistory',api.addViewHistory);
router.post('/addSearchingHistory',api.addSearchingHistory)
router.post('/addUser',api.addUser)

//delete Data
router.delete('/viewHistory/:id',api.deleteViewHistory)
router.delete('/searchingHistory/:id',api.deleteSearchingHistory)
router.delete('/user/:id',api.deleteUser)

//User Management
// router.put('/user/:id',api.editUser)
router.get('/user/:userid',api.findUser)

//Connection with plugin
router.post('/authen',api.authen)
router.post('/checkConnection',api.checkConnection)

//Dashboard
router.get('/distinctTags',api.distinctTags)
//return array of distunct Tags and count
//[
//     {
//         "_id": "java",
//         "count": 7,
//         "Tags": "java"
//     },
//     {
//         "_id": "javascript",
//         "count": 3,
//         "Tags": "javascript"
//     }
// ]
router.get('/findViewByUser/:userid',api.findViewByUser)
router.get('/findSearchingByUser/:userid',api.findSearchingByUser)
router.get('/distinctTagsByUser/:userid',api.distinctTagsByUser)
// Example URL for api 
// http://localhost:8095/api/findViewByUser/Pisit

// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', cors(), router);

// #10 Start the server
app.listen(port);
console.log(port);