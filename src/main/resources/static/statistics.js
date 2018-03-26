var getDataUrl = "http://localhost:8080/getData/skillStatistics";

var data = JSON.parse('[ {"label" : " java " , "data" : [ { "x" : "Alexandru Pop" , "y" : 8 } , { "x" : "Cristina Dinu" , "y" : 6 } , { "x" : "Bizon Lavinia" , "y" : 8 } ] } , {"label" : " vaadin " , "data" : [ { "x" : "Alexandru Pop" , "y" : 4 } , { "x" : "Cristina Dinu" , "y" : 7 } , { "x" : "Bizon Lavinia" , "y" : 6 } ] } , {"label" : " javascript " , "data" : [ { "x" : "Alexandru Pop" , "y" : 3 } , { "x" : "Cristina Dinu" , "y" : 4 } , { "x" : "Bizon Lavinia" , "y" : 4 } ] } , {"label" : " linux " , "data" : [ { "x" : "Alexandru Pop" , "y" : 6 } , { "x" : "Cristina Dinu" , "y" : 7 } , { "x" : "Bizon Lavinia" , "y" : 0 } ] } , {"label" : " management " , "data" : [ { "x" : "Alexandru Pop" , "y" : 0 } , { "x" : "Cristina Dinu" , "y" : 6 } , { "x" : "Bizon Lavinia" , "y" : 0 } ] } ]');

var mydata;
var newData;


$(document).ready(function(){
      $("body").append('<h1><center>' + " Statistics for the smallest and biggest skills' value " +'</center></h1></br></br>')
	  $("body").append('<div id="statistics_graph"></div>');
	  getData(getDataUrl);
	  
	  newData = getNewJson(mydata);
	
	  Morris.Bar({
	  element: 'statistics_graph',
	  data: newData,
	  xkey: 'y',
	  ykeys: ['a', 'b'],
	  labels: [ 'Min', 'Max' ]
	});
});

function getNewJson(data) {
	newJson = [];
	var vector2 = [];
	
	data.forEach(function(grafic,index){
	var vector = [];
	
	var map = {};
	
		skillName = grafic.label;
			min = 10 ; 
			max = 0 ;
			minName = "";
			maxName = "";
			grafic.data.forEach(function(employees,index2){
			
			if(grafic.data[index2].y <= min && grafic.data[index2].y != 0 ){
					min = grafic.data[index2].y;
					minName = grafic.data[index2].x;
				}
				
				if(grafic.data[index2].y >max){
					max = grafic.data[index2].y;
					maxName = grafic.data[index2].x;
				}
			})
			
			vector = [{x:minName , y: min},{x:maxName , y:max}];
			map["label"] = skillName;
			map["data"] = vector;
			
			
			vector2.push({y:skillName , a:min, b:max});
			newJson.push(map);
				
		});
		
		return vector2;
}	
	function getData(api) {
		$.ajax({
			url : api,
			type : 'GET',
			dataType : 'json',
			async : false,
			success : function(data) {
				mydata = data;
			},
			error : function(xhr, message, errorThrown) {
				alert(errorThrown);
			}
		});
	}