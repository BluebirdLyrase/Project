const mongoose = require('mongoose')
const Schema = mongoose.Schema

const searchingHistory = new Schema({
    // _id: Schema.Types.ObjectId,
    Order:  String,
    Site: String,
    Tagged:  String, 
    "Sort By": String,
    UserID: String,
    "Search Text": String,
    Date: String
  })
  
const searchingHistoryModel = mongoose.model('searchinghistories', searchingHistory)

module.exports = searchingHistoryModel