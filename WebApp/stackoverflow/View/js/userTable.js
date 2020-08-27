
$(function () {

    var url = "/api/user";
  
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
              { data: 'UserID' },
              { data: 'Password' },
              { data: '_id' , render : function ( data, type, row, meta ) {
                return type === 'display'  ?
                  '<a href="'+data+'" class="btn btn-primary" >edit</a> <a href="'+data+'" class="btn btn-danger" >Delete</a>' :
                  data;
              }},
            ]
          });
      } );
      }
  });
    
  
  })