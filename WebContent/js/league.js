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
		    	showSuccessMsg(msg);
		    }
		});
	});
	
	if($('#leagues_table')){
		$('#leagues_table').DataTable( 
				{
			        "columns": [
			            null,
			            null,
			            null
			        ]
			    }
		);
	}
});