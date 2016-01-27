$(document).ready(function() {

	if($('#tips_table')){
		$('#tips_table').DataTable( 
				{
			        "columns": [
			            null,
			            null,
			            { "orderDataType": "dom-text", type: 'string' },
			            { "orderDataType": "dom-text", type: 'string' },
			            null,
			            null
			        ]
			    }
		);
	}
	
	$( "#gamesFilter, #tipsFilter" ).button();
	
	$('#gamesFilter').click(function(){
		if($(this).attr("checked") == "checked")window.location.href = 'ShowTipsServlet';
		else window.location.href = 'ShowTipsServlet?filter_games=future';
	});
	
	$('#tipsFilter').click(function(){
		if($(this).attr("checked") == "checked")window.location.href = 'ShowTipsServlet';
		else window.location.href = 'ShowTipsServlet?filter_tips=open';
	});
	
	$("body").on("keyup", "tr td input", function(){
		$(this).closest('tr').addClass("edited");
	});
	
	$("#save").click(function(){
		var params = {};
		$("tr.edited").each(function(){
			var id = $(this).attr("id");
			var points_home = $(this).find("input.points_home").eq(0).val();
			var points_guest = $(this).find("input.points_guest").eq(0).val();
			params[id] = [];
			params[id].push(points_home);
			params[id].push(points_guest);
		});
		$.ajax({
	        url: "DoCreateTipsServlet",
	        data: params
	    })
	    .done (function(data) {
	    	alert("Tips erfolgreich gespeichert."); 
	    	$('.edited').removeClass('edited');
	    })
	    .fail (function()     { alert("Fehler"); })
	    ;
	});
	
});