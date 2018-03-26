var mydata;
var indexes;
var indexesNames;

var empId = getParameterByName('id');
var empName = getParameterByName('name');
var text = "The professional evolution of the employee ";

var getDataUrl = "http://localhost:8080/getData/" + empId;


$(document).ready(function() {
	$("body").append('<h1><center>' + text + empName + '</center></h1></br></br>')
	$("body").append('<div id="history_skill_chart"></div>');
	
	getData(getDataUrl);
	drawLine(mydata, indexes);
	
});

function drawLine(data, indexes) {
	var xaxis = indexes[0];
	console.log(xaxis);
	indexes.shift();
	new Morris.Line({
		element : 'history_skill_chart',
		data : data,
		xkey : xaxis,
		ykeys : indexes,
		labels : indexes
	});
}

function getData(api) {
	$.ajax({
		url : api,
		type : 'GET',
		dataType : 'json',
		async : false,
		success : function(data) {
			mydata = data;
			indexes = getIndexesForData(data[0]);
		},
		error : function(xhr, message, errorThrown) {
			alert("error");
		}
	});
}

function getIndexesForData(data) {
	var keys = [];
	for ( var key in data) {
		keys.push(key);
	}
	return keys;
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    
    if (!results) {
    	return null;
    }
    if (!results[2]) {
    	return '';
    }
    
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
