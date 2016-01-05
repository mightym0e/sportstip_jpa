$(document).ready(function() {
	$("#save").click(function(){
		$.ajax({
		    type: 'POST',
		    url: 'DoCreateLeagueServlet',
		    data: { 
		        'name': $("#leagueName").val(), 
		        'sport': $("#leagueSport").val()
		    },
		    success: function(msg){
		        alert(msg);
		    }
		});
	});
});