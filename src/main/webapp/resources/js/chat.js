$(function() {
	$("#chatForm\\:chatMsg").focus();
	
	setInterval(function() {
		var refresh = $("#chatForm\\:chatRefresher");
		refresh[0].click();
		
	}, 1000);
	

});


