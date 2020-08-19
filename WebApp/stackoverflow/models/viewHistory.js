const mongoose = require('mongoose')
const Schema = mongoose.Schema

const viewHistory = new Schema({
    // _id: mongoose.Types.ObjectId,
    ID:  String,
    Title: String,
    Tags:  Array, 
    Date: String,
    UserID: String
  })
  
const viewHistoryModel = mongoose.model('viewhistories', viewHistory)

module.exports = viewHistoryModel