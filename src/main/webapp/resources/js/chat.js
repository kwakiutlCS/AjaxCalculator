$(function() {
	getFocus();
	
	setInterval(function() {
		var refresh = $("#chatForm\\:chatRefresher");
		refresh[0].click();
		
	}, 1000);
});

var getFocus = function() {
	setSecret();
	
	var textField = $("#chatForm\\:chatMsg");

	textField.focus();
	textField.val("");
	
	$('.textArea').scrollTop($('.textArea')[0].scrollHeight);
}


var setSecret = function() {
	$(".chatUserLink").click(function() {
		var textField = $("#chatForm\\:chatMsg");
		textField.val("/secret "+$(this).html()+" ");
		textField.focus();
		
		return false;
	});
	
	
}

