var id = localStorage.getItem("selected");
console.log(localStorage.getItem("selected"));
// localStorage.removeItem("selected")
console.log(id);

///////////////////////////////////App Detail//////////////////////////////////////////
var urlSearch = `http://localhost:8080/findOne/${id}`;
var data = null;
var xhr = new XMLHttpRequest();
// xhr.withCredentials = true;
xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4) {
        // console.log(this.responseText);
        json = JSON.parse(this.responseText);
        // console.log(this.responseText);
        // console.log(json[0]["app"]);

        $('#Appname').empty();
        $('#Appname').append(json[0]["app"]);

        var detail = `App Name: ${json[0]["app"]}
                      Category: ${json[0]["category"]}
                      content_rating:${json[0]["content_Rating"]}
                      Genres:${json[0]["genres"]}

                      type:${json[0]["type"]}
                      price:${json[0]["price"]}
                      Rating:${json[0]["rating"]}
                      Reviews:${json[0]["reviews"]}

                      size:${json[0]["size"]}
                      installs:${json[0]["installs"]}
                      Last_Updated:${json[0]["last_Updated"]}
                      Current_Ver:${json[0]["current_Ver"]}
                      Android_Ver:${json[0]["android_Ver"]}                   
        `;
        $('#contentDetail').empty();
        $('#reviewDetail').empty();
        var contentDetail = `<div class="col-12 h5"> Application Name : ${json[0]["app"]}</div>
        <div class="col-12 h5"> Category : ${json[0]["category"]}</div>
        <div class="col-12 h5"> Content Rating : ${json[0]["content_Rating"]}</div>
        <div class="col-12 h5"> Genres : ${json[0]["genres"]}</div>`
        $('#contentDetail').append(contentDetail);

        var reviewtDetail = `<div class="col-12 h5"> Type : ${json[0]["type"]}</div>
         <div class="col-12 h5"> Price : ${json[0]["price"]} THB</div>
        <div class="col-12 h5"> Rating : ${json[0]["rating"]}</div>
        <div class="col-12 h5"> Reviews : ${json[0]["reviews"]}</div>`
        $('#reviewDetail').append(reviewtDetail);

        var softwaretDetail = `<div class="col-12 h5"> Size : ${json[0]["size"]}</div>
        <div class="col-12 h5"> Installs : ${json[0]["installs"]}</div>
        <div class="col-12 h5"> Last Updated :${json[0]["last_Updated"]}</div>
        <div class="col-12 h5"> Current Version : ${json[0]["current_Ver"]}</div>
        <div class="col-12 h5"> Android Version : ${json[0]["android_Ver"]}</div>`;
        $('#softwareDetail').append(softwaretDetail);

        // console.log(detail);

        // $('#detail').append(detail);
    }
});
console.log(urlSearch);
xhr.open("GET", urlSearch);
xhr.send(data);

////////////////////////////////////REVIEW//////////////////////////////////////////////
var numberPerPage = 4;
var pageLenght = null;
var list = new Array();
var pageList = new Array();
var urlSearch = `http://localhost:8080/Review/${id}`;
var data = null;
var xhr = new XMLHttpRequest();
var thiscurrentpage = null;
// xhr.withCredentials = true;
xhr.addEventListener("readystatechange", function () {

    if (this.readyState === 4) {
        // console.log(this.responseText);
        json = JSON.parse(this.responseText);
        // console.log(this.responseText);
        $.each(json, function (index, value) {
            // console.log(value["app"]);
            // console.log(value["review"]);
            // console.log(value["sentiment"]);
            // console.log(value["sentiment_Polarity"]);
            // console.log(value["sentiment_Subjectivity"]);
            var sentiment = value["sentiment"];
            var sentiBG = "";
            var sentiBar = 0;
            if(sentiment=="Negative"){
                sentiBG = "bg-danger"
                sentiBar = value["sentiment_Polarity"] * -100;
            }else{if(sentiment=="Positive"){
                sentiBG = "bg-success"
                sentiBar = value["sentiment_Polarity"] * 100;
            }else{
                sentiBG = "invisible"
                sentiBar = 0;
            }
        }
            var Review = `<div class="col-xl-6">
            <div class="card shadow mb-4 ">
                <div class="card-header progress-bar py-1 ${sentiBG}" role="progressbar" style="width: ${sentiBar}%" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100">
                </div>
                    <div class="card-body">
                        <div class="row" style="margin-left:auto;">
                            ${value["review"]}
                        </div>
                    </div>
                </div>
            </div>
        </div>`
            if (value["review"] != "nan") {
                list.push(Review);
            }
        });

        pageLenght = Math.ceil(list.length / numberPerPage);
        console.log("page lenght is " + pageLenght);

        $('#paging').empty();
        if(pageLenght!=0){
        $('#paging').append(`<li class="page-item" style="white-space: wrap;">
        <a class="page-link" id="pageBack" href="#" onclick="loadReviewListBack()">Previous</a>
      </li>`);
        for (var i = 1; i <= pageLenght; i++) {
            // if (i <= 27) {
                $('#paging').append(`<li class="page-item" style="white-space: wrap;">
            <a class="page-link" id="page${i}" href="#" onclick="loadReviewList(${i})">${i}</a>
        </li>`);
            // }
            // if (i == 28) {
            //     $('#paging').append(`<li class="page-item" style="white-space: wrap;">
            //     <a class="page-link DisableBtn" href="#" >...</a>
            // </li>`);
            // }
        }
        $('#paging').append(`<li class="page-item" style="white-space: wrap;">
        <a class="page-link" id="pageNext" href="#" onclick="loadReviewListNext()">Next</a>
      </li>`);

        loadReviewList(1);
    }else{
        console.log("noreview");
        $('#noReview').empty();
        $('#noReview').append(`There are no reviews for this app`);
    }
    }
});
////////////////////////////////////Paging//////////////////////////////////////////////

function checkBtn(currentPage) {
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
    $("#review").empty();
    for (r = 0; r < pageList.length; r++) {
        $("#review").append(pageList[r])
    }
}


console.log(urlSearch);
xhr.open("GET", urlSearch);
xhr.send(data);


