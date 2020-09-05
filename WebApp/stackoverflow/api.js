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
        if (err) {
            res.status(500).json({ status: "error", message: err });
        }
        res.json(data);
    });
}

function getAllSearchingHistory(req, res) {
    searchingHistory.find({}, function (err, data) {
        if (err) {
            res.status(500).json({ status: "error", message: err });
        }
        res.json(data);
    });
}

function getAllUser(req, res) {
    user.find({}, function (err, data) {
        if (err) {
            res.status(500).json({ status: "error", message: err });
        }
        res.json(data);
    });
}

function addSearchingHistory(req, res) {
    var newId = new mongoose.mongo.ObjectId();
    var payload = req.body; // add _id
    payload._id = newId;
    console.log(payload);
    var NewSearchingHistory = new searchingHistory(payload);
    console.log(NewSearchingHistory);
    NewSearchingHistory.save(function (err) {
        if (err) {
            res.status(500).json(err);
            console.log(err);
        }
        res.json({ status: "added NewSearchingHistory" });
    });
}

function addViewHistory(req, res) {
    var newId = new mongoose.mongo.ObjectId();
    var payload = req.body;
    payload._id = newId; // add _id
    var NewViewHistory = new viewHistory(payload);
    // console.log(req.body.ID);a
    NewViewHistory.save(function (err) {
        if (err) {
            res.status(500).json(err);
            console.log(err);
        }
        res.json({ status: "added NewViewHistory" });
    });
}

function addUser(req, res) {
    var newId = new mongoose.mongo.ObjectId();
    var payload = req.body;
    payload._id = newId; // add _id
    var NewUser = new user(payload);
    var isPassed = true;
    // console.log(req.body.ID);

    //check to prevent empty userID
    if(payload.UserID == null){
        res.json(" Empty UserID ");
        isPassed = false
    }

    if(payload.Password == null){
        res.json(" Empty Password ");
        isPassed = false
    }

    if(isPassed){
    //check if that UserID is already exist
    user.findOne({ UserID: payload.UserID }, function (err, data) {
        if (data != null) {
            res.json(" Duplicate UserID! ");
        }else{
            NewUser.save(function (err) {
                if (err) {
                    res.status(500).json(err);
                    console.log(err);
                }else{
                    res.json("Successfully add new User");
                }
            });
        }
    });
}
}

function deleteSearchingHistory(req, res) {
    var id = req.params.id;    
    searchingHistory.findByIdAndRemove(id,function (err) {
        if (err) res.status(500).json(err);
        res.json({status : "delete a data"});
    });
    // ===============================
}

function deleteViewHistory(req, res) {
    var id = req.params.id;    
    viewHistory.findByIdAndRemove(id,function (err) {
        if (err) res.status(500).json(err);
        res.json({status : "delete a data"});
    });
    // ===============================
}

function deleteUser(req, res) {
    var id = req.params.id;    
    user.findByIdAndRemove(id,function (err) {
        if (err) res.status(500).json(err);
        res.json({status : "delete a data"});
    });
    // ===============================
}

function authen(req, res) {
    user.findOne({ UserID: req.body.UserID, Password: req.body.Password }, function (err, data) {
        console.log(req)
        if (err) {
            res.status(500).json({ status: "error", message: err });
        }
        console.log(data)
        if (data != null) {
            res.json("success");
        } else {
            res.json("not success");
        }
    });
}

function checkConnection(req, res) {
    status = mongoose.connection.readyState;
    // console.log(status);
    res.json(status)
    //     ready states being:
    // 0: disconnected
    // 1: connected
    // 2: connecting
    // 3: disconnecting

}

module.exports = {
    getAllViewHistory: getAllViewHistory,
    getAllSearchingHistory: getAllSearchingHistory,
    getAllUser: getAllUser,
    addViewHistory: addViewHistory,
    addSearchingHistory: addSearchingHistory,
    addUser: addUser,
    deleteViewHistory: deleteViewHistory,
    deleteSearchingHistory:deleteSearchingHistory,
    deleteUser: deleteUser,
    authen: authen,
    checkConnection: checkConnection
};