
$(function () {

  // var url = "/api/viewHistory";
  var url = "http://localhost:8080/api/viewHistory"

  // Get data when first time open
  $.get(url, function (data, status) {
    if (status == 'success') {
      console.log(data);
      $(data).ready( function () {
        $('#dataTable').DataTable({
          destroy: true,
          searching: true,
          data: data,
          columns: [
            { data: 'ID' },
            { data: 'Title' },
            { data: 'Tags' },
            { data: 'Date' },
            { data: 'UserID' },
          ]
        });
    } );
    }
});


})