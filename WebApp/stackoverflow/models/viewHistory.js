const mongoose = require('mongoose')
const Schema = mongoose.Schema

const viewHistory = new Schema({
    // ID:  String,
    // Title: String,
    // Tags:  String, 
    // Date: String
  })
  
const viewHistoryModel = mongoose.model('users', viewHistory)

module.exports = viewHistoryModel