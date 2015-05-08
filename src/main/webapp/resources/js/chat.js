$(function() {
	getFocus();
	
	setInterval(function() {
		var refresh = $("#chatForm\\:chatRefresher");
		refresh[0].click();
		
	}, 1000);
	
	
});

var getFocus = function() {
	$("#chatForm\\:chatMsg").focus();
	
}


