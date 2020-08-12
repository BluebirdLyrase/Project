// Call the dataTables jQuery plugin
function setData(id){
  localStorage.setItem("selected",id);
  console.log(id);
}
MostDownload();
///////////////////////////////Most Download//////////////////////////////////////
function MostDownload(){
var data = JSON.stringify({"query":"where installs = \"1,000,000,000+\"","qname":"null"});;
var xhr = new XMLHttpRequest();
// xhr.withCredentials = true;
xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4) {
        // console.log(this.responseText);
        json = JSON.parse(this.responseText);
        $('#dataTable').DataTable({
          destroy: true,
          searching: false,
          data: json,
          columns: [
            { data: 'app' },
            { data: 'category' },
            { data: 'rating' },
            // { data: 'reviews' },
            // { data: 'size' },
            // { data: 'installs' },
            // { data: 'type' },
            { data: 'price' },
            { data: 'content_Rating' },
            // { data: 'genres' },
            // { data: 'Last Updated' },
            // { data: 'Current Ver' },
            // { data: 'Android Ver' }
            { data: 'id' ,
            render: function ( data, type, row ) {
              return `<a href="Appdetail.html" onclick="setData(${data})">more detail</a>`;
          }
          }
          ]
        });
    }
});

xhr.open("POST", `http://localhost:8080/search`);
xhr.setRequestHeader("Content-Type", "application/json");
xhr.send(data);
}


/////////////////////////////Find all////////////////////////////////////////
function Search(){

  var statement = " " ;
  var where = [];
  console.log("Search Btn");
  var qname;
  var name = $("#name").val();
  var type = $("#type").val();
  var Category = $("#catagory").val();
  var Content_Rating = $("#content_Rating").val();
  var installs = $("#installs").val();
  var reviewMin = $("#reviewMin").val();
  var reviewMax = $("#reviewMax").val();
  var priceMin = $("#priceMin").val();
  var priceMax = $("#priceMax").val();
  var ratingMin = $("#ratingMin").val();
  var ratingMax = $("#ratingMax").val();
  

  if(installs != ""){
    // console.log("installs:"+installs);
    // if(installs == 0){
    // where.push(`installs = "${installs}"`);
    // }else{
    // where.push(`installs = "${installs+"plus"}"`);
    // }
    console.log("installs"+installs);
    where.push(`installs = "${installs}"`);
  }

  if(Category != ""){
    console.log("cat:"+Category);
    where.push(`Category = "${Category}"`);
  }

  if(type!= ""){
    console.log("cat:"+type);
    where.push(`Type = "${type}"`);
  }


  if(Content_Rating != ""){
    console.log("CR:"+Content_Rating);
    where.push(`Content_Rating = "${Content_Rating}"`);

  }

  if(name != ""){
    // console.log("name:"+name);
    // where.push(`app like "%${name}%"`);
    qname = name;
  }else{
    qname = "null"
  }

  if(reviewMin != "" && reviewMax != "" && reviewMin<=reviewMax){
    console.log("review:"+reviewMin+"-"+reviewMax);
    where.push(`reviews between ${reviewMin} and ${reviewMax}`);
  }

  if( priceMin!= "" && priceMax != "" && priceMin<=priceMax){
    console.log("price:"+priceMin+"-"+priceMax);
    where.push(`price between ${priceMin} and ${priceMax}`);
  }
  if( ratingMin!= "" && ratingMax != "" && ratingMin<=ratingMax){
    console.log("rating:"+ratingMin+"-"+ratingMax);
    where.push(`rating between ${ratingMin} and ${ratingMax}`);
  }




  console.log(where.length)
  if(where.length != 0){
    statement = statement + " where ";

    for(i=0;i<where.length-1;i++){
      console.log(where[i])
      statement =  statement + where[i] + " and  "
    }
    console.log("last : "+where[where.length-1])
    statement =  statement + where[where.length-1]
    console.log(statement);
  }else{
    statement = "null";
  }

 
var data = JSON.stringify({"query":statement,"qname":qname});
var xhr = new XMLHttpRequest();
// xhr.withCredentials = true;
xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4) {
        // console.log(this.responseText);
        json = JSON.parse(this.responseText);
        $('#dataTable').DataTable({
          destroy: true,
          searching: false,
          data: json,
          columns: [

            { data: 'app' },
            { data: 'category' },
            { data: 'rating' },
            // { data: 'reviews' },
            // { data: 'size' },
            // { data: 'installs' },
            // { data: 'type' },
            { data: 'price' },
            { data: 'content_Rating' },
            // { data: 'genres' },
            // { data: 'Last Updated' },
            // { data: 'Current Ver' },
            // { data: 'Android Ver' }
            { data: 'id' ,
            render: function ( data, type, row ) {
              return `<a href="Appdetail.html" onclick="setData(${'`'+data+'`'})">more detail</a>`;
          }
          }
          ]
        });
    }
});
console.log(data);
xhr.open("POST", "http://localhost:8080/search");
xhr.setRequestHeader("Content-Type", "application/json");
xhr.send(data);
}
////////////////////////////////////////////////////////////////////////////

/////////////////////////////////Build Category Selecter////////////////////

 //////////////installs//////////////////
 var data = null;
 var xhr = new XMLHttpRequest();
 // xhr.withCredentials = true;
 xhr.addEventListener("readystatechange", function () {
     if (this.readyState === 4) {
       console.log(this.responseText);
         Data = JSON.parse(this.responseText);
         console.log('Data:'+Data);
 
         var selectcontent_installs = document.getElementById("installs");
         $.each(Data, function( index, value ) {
           var option = document.createElement("option");
           option.text = value["installs"];
          option.value = value["installs"];
          selectcontent_installs.appendChild(option);
         });
     }
 });
 xhr.open("GET", "http://localhost:8080/installs");
 xhr.send(data);
 //////////////installs//////////////////


 //////////////content_Rating//////////////////
 var data = null;
 var xhr = new XMLHttpRequest();
 // xhr.withCredentials = true;
 xhr.addEventListener("readystatechange", function () {
     if (this.readyState === 4) {
       console.log(this.responseText);
         Data = JSON.parse(this.responseText);
         console.log('Data:'+Data);
 
         var selectcontent_Rating = document.getElementById("content_Rating");
         $.each(Data, function( index, value ) {
           var option = document.createElement("option");
           option.text = value["content_Rating"];
          option.value = value["content_Rating"];
          selectcontent_Rating.appendChild(option);
         });
     }
 });
 xhr.open("GET", "http://localhost:8080/content_rating");
 xhr.send(data);
 //////////////content_Rating//////////////////


//////////////category//////////////////
var data = null;
var xhr = new XMLHttpRequest();
// xhr.withCredentials = true;
xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4) {
      console.log(this.responseText);
        Data = JSON.parse(this.responseText);
        console.log('Data:'+Data);
        
        var selectCategory = document.getElementById("catagory");
        $.each(Data, function( index, value ) {
          var option = document.createElement("option");
          option.text = value["category"];
          option.value = value["category"];
          selectCategory.appendChild(option);
        });
    }
});
xhr.open("GET", "http://localhost:8080/categoryList");
xhr.send(data);
//////////////category//////////////////

/////////////////////////Type///////////////////////////
var selectCategory = document.getElementById("type");
  var option = document.createElement("option");
  option.text = "Free"
  option.value = "Free"
  selectCategory.appendChild(option);
  var option2 = document.createElement("option");
  option2.text = "Paid"
  option2.value = "Paid"
  selectCategory.appendChild(option2);


///////////////////////////////////////////////////////////////////////////



