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
    console.log(NewUser);
    NewUser.save(function (err) {
        if (err) {
            res.status(500).json(err);
        } else {
            res.json({ status: "added NewUser" });
        }
    });
}

function findUser(req, res) {
    var userID = req.params.userid;
    //check if that UserID is already exist
    user.findOne({ UserID: userID }, function (err, data) {
        console.log('sent '+userID)
        if (data != null) {
            console.log('Dupe')
            res.json("Duplicate");
        } else {
            console.log('Success')
            res.json("Success");
        }
    });
}

// function editUser(req, res) {
//     var payload = req.body
//     var id = req.params.id; 
//     console.log(payload) 
//     user.findByIdAndUpdate(id,payload,function (err) {
//         if (err) res.status(500).json(err);
//         res.json({status : "update user"});
//     });
// }


function deleteSearchingHistory(req, res) {
    var id = req.params.id;
    searchingHistory.findByIdAndRemove(id, function (err) {
        if (err) res.status(500).json(err);
        res.json({ status: "delete a data" });
    });
    // ===============================
}

function deleteViewHistory(req, res) {
    var id = req.params.id;
    viewHistory.findByIdAndRemove(id, function (err) {
        if (err) res.status(500).json(err);
        res.json({ status: "delete a data" });
    });
    // ===============================
}

function deleteUser(req, res) {
    var id = req.params.id;
    user.findByIdAndRemove(id, function (err) {
        if (err) res.status(500).json(err);
        res.json({ status: "delete a data" });
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
    findUser: findUser,
    // editUser: editUser,
    deleteViewHistory: deleteViewHistory,
    deleteSearchingHistory: deleteSearchingHistory,
    deleteUser: deleteUser,
    authen: authen,
    checkConnection: checkConnection
};