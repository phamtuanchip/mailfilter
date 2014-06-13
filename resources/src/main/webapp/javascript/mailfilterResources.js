(function($,flowplayer){
    var _module = {};
    function mailfilterResources(){

    };
    mailfilterResources.prototype.init = function() {
        console.log("Success to load from resource js !");
    };
    _module.mailfilterResources = new mailfilterResources();
    return _module;


})($,flowplayer);