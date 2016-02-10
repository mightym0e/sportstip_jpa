$(document).ready(function() {
	$("#save").click(function(){
		$.ajax({
		    type: 'POST',
		    url: 'DoCreateGameServlet',
		    data: { 
		        'gameHome': $("#gameHome").val(), 
		        'gameGuest': $("#gameGuest").val(),
		        'leagueSelect': $("#leagueSelect").val(),
		        'gameDate': $("#gameDate").val(),
		        'gameDay': $("#gameDay").val()
		    },
		    success: function(msg){
		    	showSuccessMsg(msg);
		    }
		});
	});
	$("#synchronize").click(function(){
		$.ajax({
		    type: 'POST',
		    url: 'DoSynchronizeGamesServlet',
		    success: function(msg){
		        window.location.href = window.location.href+'?msg='+encodeURIComponent(msg);
		    }
		});
	});
	if($('#games_table')){
		$('#games_table').DataTable(  );
	}
	
});