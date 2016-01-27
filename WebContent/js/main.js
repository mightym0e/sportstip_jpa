$.fn.dataTable.ext.order['dom-text'] = function  ( settings, col )
{
    return this.api().column( col, {order:'index'} ).nodes().map( function ( td, i ) {
        var val =  $('input', td).val();
        if(!val) val =  $(td).text(); 
        return val;
    } );
}

$(document).ready(function() {
	
	$("button").button();
	
	$( ".date" ).datepicker();
	
});