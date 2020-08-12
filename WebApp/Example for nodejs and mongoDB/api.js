const mongoose = require('mongoose')
const Product = require('./models/productSchema')
// #5 Change URL to your local mongodb
const url = "mongodb://localhost:27017/product";
// ===============================

mongoose.connect(url, { useNewUrlParser: true, useUnifiedTopology: true, useFindAndModify: false })

function getAllProducts(req, res) {

    Product.find({}, function (err, data) {   
        if(err){
            res.status(500).json({ status: "error", message: err});
        }     
        res.json(data);
    });
}

function getProductById(req, res) {
    var pid = req.params.pid;    
    // #6 Get a product by ID
    Product.find({"_id":pid},function (err,data) {
        if (err) res.status(500).json(err);
        res.json(data);
    });
    // ===============================
}

function updateProductById(req, res) {
    var payload = req.body
    var pid = req.params.pid;    
    // #7 Update a product by ID (findByIdAndUpdate)
    Product.findByIdAndUpdate(pid,payload,function (err) {
        if (err) res.status(500).json(err);
        res.json({status : "update product"});
    });
    // ===============================
}

function deleteProductById(req, res) {
    var pid = req.params.pid;    
    // #8 Delete a product by ID (findByIdAndDelete)
    Product.findByIdAndRemove(pid,function (err) {
        if (err) res.status(500).json(err);
        res.json({status : "delete a product"});
    });
    // ===============================
}

function addProduct(req, res) {
    var payload = req.body
    // #9 Add a new product 
    var NewProduct = new Product(payload);
    NewProduct.save(function (err) {
        if (err) res.status(500).json(err);
        res.json({status : "added product"});
    });
    // ===============================
}

module.exports = {
    getAllProducts: getAllProducts,
    getProductById: getProductById,
    addProduct: addProduct,
    updateProductById: updateProductById,
    deleteProductById, deleteProductById
};