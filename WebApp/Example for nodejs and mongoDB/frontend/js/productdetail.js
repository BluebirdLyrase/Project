$(function () {

    var searchParams = new URLSearchParams(window.location.search);
    var pid = searchParams.get("pid");
    var url = "/api/products/" + pid;

    // Get data when first time open
    getData();

    function getData() {
        // #14 Get a selected product and display as a form
        // use $.get
        // $.get(url,{serialno:pid}, function (data, status) {
        $.get(url, function (data, status) {
            if (status == 'success') {
                $.each(data, function (index, item) {
                    console.log(index + ' : ' + item['name']);
                    $('#preview').attr('src',item['photo']);
                    $('#photo').attr('value',item['photo']);
                    $('#serialno').attr('value',item['serialno']);
                    $('#name').attr('value',item['name']);
                    $('#category').attr('value',item['category']);
                    $('#price').attr('value',item['price']);

        
                });
            }
        });
        // ===============================
    }

    // Update photo when URL has changed
    $("#photo").change(function () {
        $("#preview").attr("src", $("#photo").val());
    })

    // Save edited product data
    $("#saveproduct").click(function () {
        var editproduct = {
            serialno: $("#serialno").val(),
            name: $("#name").val(),
            category: $("#category").val(),
            price: $("#price").val(),
            photo: $("#photo").val()
        }
        $.ajax({
            url: url,
            type: 'PUT',
            data: editproduct,
            success: function (result) {
                //Show updated status
                $("#modalbody").text("Updated product " + pid);
                $('#alertModal').modal('toggle');
                // Refresh data
                getData();
            }
        });
    });

    $("#deleteproduct").click(function () {
        $('#confirmModal').modal('toggle');
    });

    $("#confirmdelete").click(function () {
        // #15 Get a delete product and go back to product list 
        // use $.get and winidow.location.href
        $.ajax({
            url: url,
            type: 'DELETE',
            success: function (result) {
                //Show updated status
                // $("#modalbody").text("Updated product " + pid);
                // $('#alertModal').modal('toggle');
                // Refresh data
                // getData();
                window.location.href = "product.html";
            }
        });
        // ===============================
    });
});