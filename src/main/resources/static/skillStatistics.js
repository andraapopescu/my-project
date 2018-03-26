var getDataUrl = "http://localhost:8080/getData/skillStatistics";
var skill;

$(document).ready(function() {
	
	getData(getDataUrl);

});

function drawLine(grafic,index) {
	new Morris.Bar({
		barGap : 4,
		barSizeRatio : 0.7,
		element : 'skillStatistics_graph'+index,
		data : grafic.data,
		xkey : 'x',
		ykeys : [ 'y' ],
		labels : [ grafic.label ],
		barColors : function(row, series, type) {
			if (type === 'bar') {
				var red = Math.ceil(200 * row.y / this.ymax + 55);
				return 'rgb( 0,0,' + red + ')';
			} else {
				return '#000';
			}
		}
	});
}

function getData(api) {
	$.ajax({
		url : api,
		type : 'GET',
		dataType : 'json',
		async : false,
		success : function(data) {
			data.forEach(function(grafic,index){
				console.log(grafic);
				$("body").append('<div align = "center"> <h2>' + "Statistics for " + grafic.label + 
						" skill " + '</h2></div>');
				$("body").append('<div id="skillStatistics_graph' + index + '" style="width: 60%; margin: 0px auto;"></div>');
			
				drawLine(grafic,index)
			});
		},
		error : function(xhr, message, errorThrown) {
			alert(errorThrown);
		}
	});
}

