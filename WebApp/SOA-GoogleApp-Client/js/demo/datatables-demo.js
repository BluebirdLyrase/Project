// Call the dataTables jQuery plugin

$.getJSON("json/googleplaystore.json", function(json) {//this will be replace with service
  console.log(json); // this will show the info it in console

$('#dataTable').DataTable({
  data: json,
  columns: [
      { data: 'App' },
      { data: 'Category' },
    { data:'Rating' },
    { data:'Reviews' },
    { data:'Size' },
    { data:'Installs' },
    { data:'Type' },
    { data:'Price' },
    { data:'Content Rating' },
    { data:'Genres' },
    { data:'Last Updated' },
    { data:'Current Ver' },
    { data:'Android Ver' }
  ]
});
});
