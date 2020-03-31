// to show suggestions for autocomplete
$(document).ready(function(){
    $("#loggerName").autocomplete({
        source: 'getClassNames'
    });
});

/**
 * by click on button "Edit"
 * function add on page info about logger (and buttons and field to edit it)
 * @param loggerName - logger name entered by user (usually matches the name of the class)
 */
function showLogger(loggerName) {
    $.ajax({
        url : "./getLoggerParam",
        data : {
            loggerName : loggerName
        },
        success : function (respJSON) {
            renderByJSON(respJSON);
            if(Object.keys(respJSON[0])[0] !== "Error"){
                // show this button
                $('#save-changes-btn').show();
            }
        }
    });
}

/**
 * event listener to show/hide table by click to tableHead div
 */
$(document).on('click', '.collapsible', function () {
    this.classList.toggle('active');
    let content = this.nextElementSibling;
    if(content.style.maxHeight) {
        content.style.maxHeight = null;
    } else {
        content.style.maxHeight = content.scrollHeight + 'px';
    }
});

/**
 * event listener for ".edit-prop-btn"
 * makes the field with property value mutable
 * toggles button
 */
$(document).on('click', '.edit-prop-btn', function () {
    let $btnElem = $( this );
    let $valueElem = $btnElem.prev();
    //make value field mutable
    $valueElem.removeAttr("readonly");
    //set focus on <input> valueElem
    $valueElem.focus();
    $valueElem.addClass("requires-update");
    //toggle button
    $btnElem.removeClass("edit-prop-btn").addClass("upd-prop-btn");
    $btnElem.val("UPDATE");
});

/**
 * event listener for ".upd-prop-btn"
 * to send ajax for updating required parameter
 */
$(document).on('click', '.upd-prop-btn', function () {
    let $table = $( this ).closest('.table');
    let $btnElem = $( this );
    let $valueElem = $btnElem.prev();
    let $keyElem = $valueElem.prev();

    $.ajax({
        url : "./updParam",
        data : {
            objForChange: $table.attr("data-table-type"),
            objName: $table.attr("id"),
            key: $keyElem.val(),
            newValue: $valueElem.val()
        },
        success : function (response) {
            $valueElem.val(response);
            //change colour and font-width for value that was changed
            $keyElem.addClass("param-was-changed");
            $valueElem.removeClass("requires-update").addClass("param-was-changed");
            //make value field immutable
            $valueElem.attr("readonly", "true");
            //toggle button
            $btnElem.removeClass("upd-prop-btn").addClass("edit-prop-btn");
            $btnElem.val("EDIT");
        }
    });
});

$(document).on('click', '#save-changes-btn', function () {
    let loggerName = $('[data-table-type = logger]').attr("id");
    $.ajax({
        url: "./saveChanges",
        success: function () {
            let url = "./";
            $(location).attr('href',url);
        }
    })
});

$(document).on('click', '#read-props-btn', function () {
    $.ajax({
        url: "./readProps"
    })
});

$(document).on('click', '.detach-appender-btn', function () {
    let $appenderTable = $( this ).closest('.table');
    let appenderAlias = $appenderTable.attr("id");
    let loggerName = $('[data-table-type = logger]').attr("id");

    $.ajax({
        url: "./detachAppender",
        data : {
            loggerName: loggerName,
            appenderAlias: appenderAlias
        },
        success: function (json) {
            if (json.status === "Success") {
                // delete appender table
                $appenderTable.remove();
                alert("Success: " + json.message);
            } else {
                alert("Error: " + json.message);
            }
        }
    })
});

$(document).on('click', '.delete-appender-btn', function () {
    let $appenderTable = $( this ).closest('.table');
    let appenderAlias = $appenderTable.attr("id");

    $.ajax({
        url: "./deleteAppender",
        data : {
            appenderAlias: appenderAlias
        },
        success: function (json) {
            if (json.status === "Success") {
                // delete appender table
                $appenderTable.remove();
                alert("Success: " + json.message);
            } else {
                alert("Error: " + json.message);
            }
        }
    })
});