var url = "/api/user/";
$(function () {

  // Get data when first time open
  $.get(url, function (data, status) {
    if (status == 'success') {
      console.log(data);
      $(data).ready(function () {
        $('#dataTable').DataTable({
          destroy: true,
          searching: true,
          data: data,
          columns: [
            { data: 'UserID' },
            { data: 'Password' },
            { data: 'type' },
            {
              data: '_id', render: function (data, type, row, meta) {
                return type === 'display' ?
                  '<botton onclick="Delete(`' + data + '`)" class="btn btn-danger" >Delete</botton> '
                  // + '<botton onclick="Add(`' + data + '`)" class="btn btn-primary" >Edit</botton>'
                  : data;
              }
            },
          ]
        });
      });
    }
  });
});


function Delete(id) {
  $('#confirmModal').modal('toggle');
  $("#confirmdelete").click(function () {
    console.log("delete " + id);
    $.ajax({
      url: url + id,
      type: 'DELETE',
      success: function (result) {
        window.location.href = "user.html";
      }
    });
  });
}

function Add(id) {
  $('#addModal').modal('toggle');
  $("#saveuser").click(function () {

    $.get(url + $("#userid").val(), function (data, status) {
      if (data == 'found') {
       
        if (id == null) {
          var newuser = {
            _id: id,
            UserID: $("#userid").val(),
            Password: $("#password").val(),
            type: $("#type").val(),
          }
          console.log("add new ");
          $.ajax({
            url: '/api/addUser',
            type: 'POST',
            data: newuser,
            success: function (result) {
              window.location.href = "user.html";
            }
          });
        } else {
          console.log('this is editing')
        }
      } else {
        $('#alertModal').modal('toggle');
        console.log("Duplicate UserID")

      }
      //   var edituser = {
      //     _id: id,
      //     UserID: $("#userid").val(),
      //     Password: $("#password").val(),
      //     type: $("#type").val(),
      //   }
      //   console.log("edit " + id);
      //   $.ajax({
      //     url: url + id,
      //     type: 'PUT',
      //     data: edituser,
      //     success: function (result) {
      //       window.location.href = "user.html";
      //     }
      //   });
      // }else{



      // }
    });
  });
}



  // addModal