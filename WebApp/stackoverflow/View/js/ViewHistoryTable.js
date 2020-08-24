
$(function () {

  var url = "/api/viewHistory";

  // Get data when first time open
  $.get(url, function (data, status) {
    if (status == 'success') {
      console.log(data);
      $(data).ready( function () {
        var table = $('#dataTable').DataTable({
          destroy: true,
          searching: true,
          data: data,
          columns: [
            // { data: '_id' },
            { data: 'ID' },
            { data: 'Title' },
            { data: 'Tags' },
            { data: 'Date' },
            { data: 'UserID' },
            { data: '_id' , render : function ( data, type, row, meta ) {
              return type === 'display'  ?
                '<a href="'+data+'" class="btn btn-danger" >Delete</a>' :
                data;
            }},
          ]
        });
    } );

    table.button( '3-1' ).text( 'Not available' );



    }
});
  

})