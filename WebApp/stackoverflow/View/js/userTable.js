
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
                '<botton onclick="Delete(`'+data+'`)" class="btn btn-danger" >Delete</botton>' : data;
              }},
            ]
          });
      } );
      }
  });
  });

  
  function Delete(id){
    Model(id);
  }

  function Model(id){
    $('#confirmModal').modal('toggle');
    $("#confirmdelete").click(function () {
      console.log("delete "+id);
      // #15 Get a delete product and go back to product list 
      // use $.get and winidow.location.href
      $.ajax({
          url: '/api/user/'+id,
          type: 'DELETE',
          success: function (result) {
              window.location.href = "user.html";
          }
      });
      // ===============================
  });
  }
