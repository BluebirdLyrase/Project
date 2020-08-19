const mongoose = require('mongoose')
const viewHistory = require('./models/viewHistory')
const searchingHistory = require('./models/searchingHistory')
const user = require('./models/user')
// #5 Change URL to your local mongodb
const url = "mongodb://localhost:27017/StackOverFlowDB";
// ===============================

mongoose.connect(url, { useNewUrlParser: true, useUnifiedTopology: true, useFindAndModify: false })

function getAllViewHistory(req, res) {
    viewHistory.find({}, function (err, data) {   
        if(err){
            res.status(500).json({ status: "error", message: err});
        }     
        res.json(data);
    });
}

function getAllSearchingHistory(req, res) {
    searchingHistory.find({}, function (err, data) {   
        if(err){
            res.status(500).json({ status: "error", message: err});
        }     
        res.json(data);
    });
}

function addSearchingHistory(req, res) {
    var payload = req.body
    var NewSearchingHistory = new searchingHistory(payload);
    console.log(NewSearchingHistory);
    NewSearchingHistory.save(function (err) {
        if (err) {res.status(500).json(err);
            console.log(err);
        }
        res.json({status : "added NewSearchingHistory"});
    });
}

function addViewHistory(req, res) {
    var payload = req.body
    var NewViewHistory = new viewHistory(payload);
    // console.log(req.body.ID);a
    NewViewHistory.save(function (err) {
        if (err) {res.status(500).json(err);
            console.log(err);
        }
        res.json({status : "added NewViewHistory"});
    });
}

function authen(req, res) {
    user.findOne({UserID:req.body.UserID,Password:req.body.Password}, function (err, data) {   
        if(err){
            res.status(500).json({ status: "error", message: err});
        }
        console.log(data)
        if(data!=null){
        res.json("success");
        }else{
        res.json("not success");   
        }
    });
}

module.exports = {
    getAllViewHistory: getAllViewHistory,
    getAllSearchingHistory: getAllSearchingHistory,
    addViewHistory:addViewHistory,
    addSearchingHistory:addSearchingHistory,
    authen:authen
};