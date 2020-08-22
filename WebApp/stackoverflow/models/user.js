const mongoose = require('mongoose')
const Schema = mongoose.Schema

const user = new Schema({
    // _id: mongoose.Types.ObjectId,
    Password:  String,
    UserID: String
  })
  
const userModel = mongoose.model('users', user)

module.exports = userModel