const mongoose = require('mongoose')
const viewHistory = require('./models/viewHistory')
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

module.exports = {
    getAllViewHistory: getAllViewHistory,

};