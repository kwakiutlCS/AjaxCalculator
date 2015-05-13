var validRefresh = true;

$(function() {
	setInterval(function() {
		if (validRefresh) {
			var refresh = $("#calculatorForm\\:statsRefresher");
			refresh[0].click();
		}
	}, 1000);
	
	
//	$("#calculatorForm\\:drawButton").on("click", function() {
//		validRefresh = false;
//	});
//	
//	var ev = new $.Event('style'), orig = $.fn.css;
//	$.fn.css = function() {
//		$(this).trigger(ev);
//		return orig.apply(this, arguments);
//	}
//	
//	$(".ui-lightbox-content-wrapperui-lightbox-content-wrapper").bind('style', function(e) {
//	   alert("closing");
//	   validRefresh = true;
//	});
});


