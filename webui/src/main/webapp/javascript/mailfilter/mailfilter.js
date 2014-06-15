(function($){
	var _module = {};
	function mailfilter(){
		
	};
	 
	mailfilter.prototype.init = function() {
		
		
		$('#DataListSearch').keypress(function() {
			var text = $('#DataListSearch').val();
			var ul = $('#DataListSearchResult');
			var link = $('#DataListSearch').attr('link') + text;
			ul.empty();
			if(text.length > 0) {
				$.getJSON( link, function( data ) {
					$.each( data, function( idx, obj ) {
						 var output = '<a class="reference internal" href="#">'+obj.sender+'</a>';
						 var addit = true;
						 ul.find('li').each(function()
						 {
							 
							 if(output == $(this).text())
							 { 
									addit = false;
							 }
						 });
						 if(addit)
						 ul.append('<li class="toctree-l2"><a class="reference internal" href="#">'+obj.sender+'</a></li>');	
						
					});
				});	
				 
			} 
		});
	};
	_module.mailfilter = new mailfilter();
    return _module;
})($);
 