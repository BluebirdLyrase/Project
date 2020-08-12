// Call the dataTables jQuery plugin
function setData(id){
    localStorage.setItem("selected",id);
    console.log("setData"+id);
  }

  var numberPerPage = 12;
  var pageLenght = null;
  var list = new Array();
  var pageList = new Array();
  MostDownload();

////////////////////////////////////Paging//////////////////////////////////////////////

function checkBtn(currentPage) {
    console.log('checkBtn' + currentPage);
    if (currentPage == 1) {
        $("#pageBack").attr("class", "page-link DisableBtn");
    } else {
        $("#pageBack").attr("class", "page-link");
    }
    if (currentPage == pageLenght) {
        $("#pageNext").attr("class", "page-link DisableBtn");
    } else {
        $("#pageNext").attr("class", "page-link");
    }
    for (var i = 1; i <= pageLenght; i++) {
        $("#page" + i).attr("class", "page-link");
    }

    $("#page" + currentPage).attr("class", "page-link bg-primary text-light");
}
$('#paging').click(function(e) {
    e.preventDefault();
}); 


function loadReviewList(currentPage) {
    console.log(currentPage);
    thiscurrentpage = currentPage;
    var begin = ((currentPage - 1) * numberPerPage);
    var end = begin + numberPerPage
    pageList = list.slice(begin, end);
    drawReviewList()   // draws out our data
    checkBtn(currentPage);
}

function loadReviewListBack() {
    currentPage = thiscurrentpage - 1;
    thiscurrentpage = currentPage;
    console.log(currentPage);
    var begin = ((currentPage - 1) * numberPerPage);
    var end = begin + numberPerPage
    pageList = list.slice(begin, end);
    drawReviewList()   // draws out our data
    checkBtn(currentPage);
}

function loadReviewListNext() {
    currentPage = thiscurrentpage + 1;
    thiscurrentpage = currentPage;
    console.log(currentPage);
    var begin = ((currentPage - 1) * numberPerPage);
    var end = begin + numberPerPage
    pageList = list.slice(begin, end);
    drawReviewList()   // draws out our data
    checkBtn(currentPage);
}

function drawReviewList() {
    $("#AppContent").empty();
    for (r = 0; r < pageList.length; r++) {
        $("#AppContent").append(pageList[r])
    }
}


  ///////////////////////////////Most Download//////////////////////////////////////
  function MostDownload(){
  var data = JSON.stringify({"query":"where installs = \"1,000,000,000+\"","qname":"null"});;
  var xhr = new XMLHttpRequest();
  // xhr.withCredentials = true;
  xhr.addEventListener("readystatechange", function () {
      list = [];
      if (this.readyState === 4) {
          // console.log(this.responseText);
          $('#AppContent').empty();
          json = JSON.parse(this.responseText);
          $.each(json, function (index, value) {

      var AppContent = `<div class="col-3"><div class="card shadow mb-4 ">
      <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
      <h7 class="font-weight-bold"> ${value["app"]}</h7>
        <div class="dropdown no-arrow">
          <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
          </a>
          <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
            <a class="dropdown-item"  href="Appdetail.html" onclick="setData(${value["id"]})">More detail</a>
          </div>
        </div>
      </div>
      <!-- Card Body -->
      <div class="card-body">
                    <div class="col-12 h8"> Category : ${value["category"]}</div>
                    <div class="col-12 h8"> Rating : ${value["rating"]}</div>
                    <div class="col-12 h8"> Price : ${value["price"]} THB</div>
                    <div class="col-12 h8"> Content Rating : ${value["content_Rating"]}</div>
      </div>
    </div>
    </div>`
      list.push(AppContent);
// $('#AppContent').append(AppContent);
          });
pageLenght = Math.ceil(list.length / numberPerPage);
console.log("page lenght is " + pageLenght);
loadReviewList(1);
$('#paging').empty();
$('#paging').append(`<li class="page-item" style="white-space: wrap;">
<a class="page-link DisableBtn" id="pageBack" href="#" onclick="loadReviewListBack()">Previous</a>
</li>`);
for (var i = 1; i <= pageLenght; i++) {
        $('#paging').append(`<li class="page-item" style="white-space: wrap;">
    <a class="page-link" id="page${i}" href="#" onclick="loadReviewList(${i})">${i}</a>
</li>`);
}
$('#paging').append(`<li class="page-item" style="white-space: wrap;">
<a class="page-link" id="pageNext" href="#" onclick="loadReviewListNext()">Next</a>
</li>`);
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
  list = [];
  xhr.addEventListener("readystatechange", function () {
      if (this.readyState === 4) {
          // console.log(this.responseText);
          $('#AppContent').empty();
          json = JSON.parse(this.responseText);
          $.each(json, function (index, value) {
          var AppContent = `<div class="col-3"><div class="card shadow mb-4 ">
          <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
          <h7 class="font-weight-bold"> ${value["app"]}</h7>
            <div class="dropdown no-arrow">
              <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
              </a>
              <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
                <a class="dropdown-item"  href="Appdetail.html" onclick="setData(${value["id"]})">More detail</a>
              </div>
            </div>
          </div>
          <!-- Card Body -->
          <div class="card-body">
                        <div class="col-12 h8"> Category : ${value["category"]}</div>
                        <div class="col-12 h8"> Rating : ${value["rating"]}</div>
                        <div class="col-12 h8"> Price : ${value["price"]} THB</div>
                        <div class="col-12 h8"> Content Rating : ${value["content_Rating"]}</div>
          </div>
        </div>
        </div>`
      list.push(AppContent);
// $('#AppContent').append(AppContent);

          });
pageLenght = Math.ceil(list.length / numberPerPage);
console.log("page lenght is " + pageLenght);
loadReviewList(1);
$('#paging').empty();
$('#paging').append(`<li class="page-item" style="white-space: wrap;">
<a class="page-link DisableBtn" id="pageBack" href="#" onclick="loadReviewListBack()">Previous</a>
</li>`);
for (var i = 1; i <= pageLenght; i++) {
        $('#paging').append(`<li class="page-item" style="white-space: wrap;">
    <a class="page-link" id="page${i}" href="#" onclick="loadReviewList(${i})">${i}</a>
</li>`);
}
$('#paging').append(`<li class="page-item" style="white-space: wrap;">
<a class="page-link" id="pageNext" href="#" onclick="loadReviewListNext()">Next</a>
</li>`);
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
  
  
  
  