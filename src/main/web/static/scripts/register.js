jQuery(document).ready(function($) {
	var h=$(window).height()-$('.header').height()-$('.footer').height();
	$('.container-fluid').css('min-height',h+'px');

	$(window).on('resize scroll',function(){
		var h=$(window).height()-$('.header').height()-$('.footer').height();
		$('.container-fluid').css('min-height',h+'px');

	})

	$(document).on('click','.self_motion',function(){
		$(this).toggleClass('check');

	});
});


