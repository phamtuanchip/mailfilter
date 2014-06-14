(function($){
	var _module = {};
	function mailfilter(){
		
	};
	mailfilter.prototype.init = function() {
		$('#DataListSearch').keypress(function() {
		    var link = 'http://localhost:8080/rest/mailfilter/api/search/' + $('#DataListSearch').val();
			$.getJSON( link, function( data ) {
			var items =[];
			$.each( data, function( id, sender ) {
				items.push( "<li id='" + id + "'>" + sender + "</li>" );
			});
			$("#DataListSearchResult", {
			"class": "my-new-list",
			html: items.join( "" )
			}).appendTo( "body" );
			});
			 
		});
	};
	_module.mailfilter = new mailfilter();
    return _module;
})($);
 