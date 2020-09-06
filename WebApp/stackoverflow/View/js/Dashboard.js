
$(function () {

  var url = "/api/viewHistory/";

  // Get data when first time open

  $.get(url, function (data, status) {
    if (status == 'success') {
      console.log(data);
      $(data).ready(function () {
          //set most tag card
        var most_tag = $('#most_tag').text();
        console.log(most_tag);

        var most;
        var alltag = [];
        //loop get TagS 
        for (var i = 0; i < data.length; i++) {
          var obj = data[i];
          obj_tag = obj.Tags;

          console.log(obj_tag);
          alltag = alltag.concat(obj_tag);
        }
        most = getMostFrequentElement(alltag);
        console.log(most);
        $("#most_tag").text(most[0]);

        //end of set tag
        
        var alluse = data.length;
        $('#most_all').text(alluse);
        //set most tag card

        //end of set tag
      });

    }

  });
});

// set most site card
$(function () {

  var url = "/api/searchingHistory/";

  // Get data when first time open
  $.get(url, function (data, status) {
    if (status == 'success') {
      console.log(data);
      $(data).ready(function () {
        var most_site = $('#most_site').text();
        console.log(most_site);

        var most;
        var alltag = [];
        //loop get TagS 
        for (var i = 0; i < data.length; i++) {
          var obj = data[i];
          obj_site = obj.Site;
          console.log(obj_site);
        }
        most = getMostFrequentElement([obj_site]);
        console.log(most);
        $("#most_site").text(most[0]);
      });

    }

  });
});



// This is form Stack Overflow : Xotic750 authur
// It's  a function to find most frequene element  
function getMostFrequentElement(inputArg) {
  var type = typeof inputArg,
    length,
    mostFrequent,
    counts,
    index,
    value;

  if (inputArg === null || type === 'undefined') {
    throw TypeError('inputArg was "null" or "undefined"');
  }

  mostFrequent = [];
  if (type === 'function' || !Object.prototype.hasOwnProperty.call(inputArg, 'length')) {
    mostFrequent[0] = inputArg;
    mostFrequent[1] = 1;
  } else {
    counts = {};
    length = inputArg.length;
    for (index = 0; index < length; index += 1) {
      value = inputArg[index];
      type = typeof value;
      counts[type] = counts[type] || {};
      counts[type][value] = (counts[type][value] || 0) + 1;
      if (!mostFrequent.length || counts[type][value] >= mostFrequent[1]) {
        mostFrequent[0] = value;
        mostFrequent[1] = counts[type][value];
      }
    }
  }

  return mostFrequent;
}

function logMostFrequentElement(inputArg) {
  var mostFrequentElement,
    element,
    text;

  try {
    mostFrequentElement = getMostFrequentElement(inputArg)
    if (mostFrequentElement.length) {
      element = mostFrequentElement[0];
      if (typeof element === 'string') {
        element = '"' + element + '"';
      }

      text = element + ' ( ' + mostFrequentElement[1] + ' times )';
    } else {
      text = 'No elements';
    }
  } catch (e) {
    text = e.message;
  }

  // document.getElementById('out').appendChild(document.createTextNode(text + '\n'));
}

// logMostFrequentElement();
// logMostFrequentElement(1);
// logMostFrequentElement(true);
// logMostFrequentElement(function (x) { return x; });
// logMostFrequentElement(/ab/g);
// logMostFrequentElement([]);
// logMostFrequentElement([1, 2]);
// logMostFrequentElement([1, NaN, 2, NaN, 'NaN']);
// logMostFrequentElement([1, Infinity, 2, Infinity, 'Infinity', -Infinity]);
// logMostFrequentElement(['1', '2', 1, '2', 2]);
// logMostFrequentElement([3, 'a', 'a', 'a', 2, 3, 'a', 3, 'a', 2, 4, 9, 3]);
// logMostFrequentElement([34, 'ab', 'ab', 'ab', 21, 34, 'ab', 34, 'ab', 21, 45, 99, 34]);
// logMostFrequentElement('Also works with strings.');