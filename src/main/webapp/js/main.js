$(document).ready(function(){
    $("#className").autocomplete({
        source: 'getClassNames'
    });
});

function showLogger(className) {
    $.ajax({
        url : "./getLoggerParam",
        data : {
            className : className
        },
        success : function(JSON) {
            alert("Success ajax");
        }
    });
}