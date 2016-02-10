try {
	$.fn.dataTable.ext.order['dom-text'] = function  ( settings, col )
	{
	    return this.api().column( col, {order:'index'} ).nodes().map( function ( td, i ) {
	        var val =  $('input', td).val();
	        if(!val) val =  $(td).text(); 
	        return val;
	    } );
	}
} catch (e) {
}


$(document).ready(function() {
	
	$("body").append("<div id='success_box'> </div>");
	
	$("button").button();
	
	$( ".date" ).datepicker();
	
	var paramMsg = getParameterByName("msg");
	
	if(paramMsg){
		showSuccessMsg(paramMsg);
	}
	
});

function showSuccessMsg(succMsg){
	$('#success_box').text(succMsg);
	$("#success_box").fadeIn("slow");
	$("#success_box").delay(1000).fadeOut("slow");
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}