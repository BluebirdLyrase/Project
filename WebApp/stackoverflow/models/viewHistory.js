const mongoose = require('mongoose')
const Schema = mongoose.Schema

const viewHistory = new Schema({
    ID:  String,
    Title: String,
    Tags:  Array, 
    Date: String
  })
  
const viewHistoryModel = mongoose.model('viewhistories', viewHistory)

module.exports = viewHistoryModel