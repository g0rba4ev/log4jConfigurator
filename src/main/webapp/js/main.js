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
        setMaxHeightEqualScrollHeight(content);
    }
});

/**
 * set element property maxHeight based on scrollHeight of
 * @param element native DOM element
 */
function setMaxHeightEqualScrollHeight(element) {
    element.style.maxHeight = element.scrollHeight + 'px';
}

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

    let key = $keyElem.val();
    let newValue = $valueElem.val();

    $.ajax({
        url : "./updParam",
        data : {
            objForChange: $table.attr("data-table-type"),
            objName: $table.attr("id"),
            key: key,
            newValue: newValue
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

            // update logger name or appender alias on page if if required
            if (key === "Alias")
                updAppenderAliasInTable($table, newValue);
            if (key === "Name")
                updLoggerNameInTable($table, newValue);
        }
    });
});

/**
 * event listener for ".add-new-prop-btn"
 * to send ajax for adding new APPENDER property
 */
$(document).on('click', '.add-new-prop-btn', function () {
    let $content = $( this ).closest('.content');
    let $appenderTable = $( this ).closest('.table');
    let $btn = $( this );
    let $valueField = $btn.prev();
    let $keyField = $valueField.prev();

    let appenderAlias = $btn.closest('.table').attr("id");
    let key = $keyField.val();
    let value = $valueField.val();

    $.ajax({
        url: "./addAppenderProp",
        data : {
            appenderAlias: appenderAlias,
            propertyKey : key,
            propertyValue: value
        },
        success: function () {
            let row =   '<input class="param-was-changed" readonly value="' + key + '">' +
                        '<input class="param-was-changed" readonly value="' + value + '">' +
                        '<input class="edit-prop-btn" type="button" value="EDIT">' +
                        '<input class="delete-prop-btn" type="button" value="X">';
            // add the row (with property) to the table on page
            $appenderTable.find('.appender-props-grid').append( row );
            // clear fields
            $keyField.val("");
            $valueField.val("");
            // change content height
            setMaxHeightEqualScrollHeight( $content[0] );
        },
        error: function(jqXHR) {
            alert( "Error: " + jqXHR.getResponseHeader("Message") );
        },
    })
});

/**
 * event listener for ".delete-prop-btn"
 * to send ajax for deleting APPENDER property
 */
$(document).on('click', '.delete-prop-btn', function () {
    let $content = $( this ).closest('.content');
    let $delBtn = $( this );
    let $updBtn = $delBtn.prev();
    let $valueField = $updBtn.prev();
    let $keyField = $valueField.prev();

    let appenderAlias = $delBtn.closest('.table').attr("id");

    $.ajax({
        url: "./deleteAppenderProp",
        data : {
            appenderAlias: appenderAlias,
            propertyKey : $keyField.val()
        },
        success: function () {
            // remove the row (with property) from table on page
            $keyField.remove();
            $valueField.remove();
            $updBtn.remove();
            $delBtn.remove();
            // change content height
            setMaxHeightEqualScrollHeight( $content[0] );
        }
    })
});

/**
 * event listener for "#save-changes-btn"
 * to send ajax for saving all properties from model to
 * file (with log4j properties) in your project
 */
$(document).on('click', '#save-changes-btn', function () {
    $.ajax({
        url: "./saveChanges",
        success: function (data, textStatus, jqXHR) {
            alert( jqXHR.getResponseHeader("Message"));
            let url = "./";
            $(location).attr('href',url);
        }
    })
});

/**
 * event listener for "#read-config-btn"
 * to send ajax for reading configuration from file (with log4j properties)
 * of your project and put them to model
 */
$(document).on('click', '#read-config-btn', function () {
    $.ajax({
        url: "./readConfig",
        success: function (data, textStatus, jqXHR) {
            // clear tables (may contain non valid data)
            $('#tables').empty();
            alert( jqXHR.getResponseHeader("Message"));
        }
    })
});

/**
 * event listener for ".detach-appender-btn"
 * to send ajax for detaching appender from logger
 */
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
        statusCode: {
            200: function(data, textStatus, jqXHR) {
                // delete appender table from page
                $appenderTable.remove();
                alert( "Success: " + jqXHR.getResponseHeader("Message") );
            },
            400: function(jqXHR) {
                alert( "Error: " + jqXHR.getResponseHeader("Message") );
            }
        }
    })
});

/**
 * event listener for ".delete-appender-btn"
 * to send ajax for complete removal appender:
 * appender will be deleted from model and from all loggers to which it is attached
*/
$(document).on('click', '.delete-appender-btn', function () {
    let $appenderTable = $( this ).closest('.table');
    let appenderAlias = $appenderTable.attr("id");

    $.ajax({
        url: "./deleteAppender",
        data : {
            appenderAlias: appenderAlias
        },
        statusCode: {
            200: function(data, textStatus, jqXHR) {
                alert( "Success: " + jqXHR.getResponseHeader("Message") );
                // delete appender table from page
                $appenderTable.remove();
            },
            400: function(jqXHR) {
                alert( "Error: " + jqXHR.getResponseHeader("Message") );
            }
        }
    })
});

/**
 * event listener for ".delete-logger-btn"
 * to send ajax for complete removal logger:
 * logger will be deleted from model
 */
$(document).on('click', '.delete-logger-btn', function () {
    let $loggerTable = $( this ).closest('.table');

    $.ajax({
        url: "./deleteLogger",
        data : {
            loggerName: $loggerTable.attr("id")
        },
        statusCode: {
            200: function(data, textStatus, jqXHR) {
                alert( "Success: " + jqXHR.getResponseHeader("Message") );
                // clear tables div
                $('#tables').empty();
            },
            400: function(jqXHR) {
                alert( "Error: " + jqXHR.getResponseHeader("Message") );
            }
        }
    })
});

/**
 * update appender alias in two places of $table
 * @param $table
 * @param newValue new value of alias
 */
function updAppenderAliasInTable($table, newValue) {
    $table.attr("id", newValue);
    $table.children(".collapsible").html("Appender: " + newValue);
}

/**
 * update logger name in two places of $table
 * @param $table
 * @param newValue new value of logger name
 */
function updLoggerNameInTable($table, newValue) {
    $table.attr("id", newValue);
    $table.children(".collapsible").html("Logger: " + newValue);
}
