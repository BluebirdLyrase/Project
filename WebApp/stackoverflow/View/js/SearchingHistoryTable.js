
$(function () {

    var url = "/api/searchingHistory";
    // var url = "http://localhost:8095/api/searchHistory"
  
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
              // { data: '_id' },
              { data: 'Search Text' },
              { data: 'Tagged'},
              { data: 'Sort By'},
              { data: 'Order'},
              { data: 'Site' },
              { data: 'Date' },
              { data: 'UserID' },
            ]
          });
      } );
      }
  });
    
  
  })