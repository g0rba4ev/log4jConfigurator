/**
 * add appender table (rendered according appenderJSON) to div "#tables" on the page
 * @param appenderJSON json (with appender data) that will be rendered to the table
 */
function addAppenderTable(appenderJSON) {
    let appenderTable = renderAppenderTable(appenderJSON);
    $('#tables').append( appenderTable );
}

/**
 * attach existing appender to logger
 * @param loggerName logger name
 * @param appenderAlias - alias of existing appender
 * @return {boolean} true - if appender attached successfully, otherwise false
 */
function attachExistingAppender(loggerName, appenderAlias) {
    let isAppenderAttached = false;
    $.ajax({
        url: "./attachExistingAppender",
        async: false,
        data : {
            loggerName: loggerName,
            appenderAlias : appenderAlias
        },
        success: function (appenderJson, textStatus, jqXHR) {
            addAppenderTable(appenderJson);
            isAppenderAttached =  true;
            alert( "Success: " + jqXHR.getResponseHeader("Message") );

        },
        error: function(jqXHR) {
            alert( "Error: " + jqXHR.getResponseHeader("Message") );
        },
    });
    return isAppenderAttached;
}

/**
 * create copy of the existing appender and attach this copy to the logger
 * @param loggerName logger name
 * @param existingAppenderAlias alias of existing appender (this appender will be copied)
 * @param newAppenderAlias alias for appender that will be created
 * @return {boolean} true - if appender created and attached successfully, otherwise false
 */
function attachCopyAppender(loggerName, existingAppenderAlias, newAppenderAlias) {
    let isAppenderAttached = false;
    $.ajax({
        url: "./attachCopyAppender",
        async: false,
        data : {
            loggerName: loggerName,
            existingAppenderAlias: existingAppenderAlias,
            newAppenderAlias: newAppenderAlias
        },
        success: function (appenderJson, textStatus, jqXHR) {
            addAppenderTable(appenderJson);
            isAppenderAttached =  true;
            alert( "Success: " + jqXHR.getResponseHeader("Message") );
        },
        error: function(jqXHR) {
            alert( "Error: " + jqXHR.getResponseHeader("Message") );
        },
    });
    return isAppenderAttached;
}

/**
 * create new appender and attach this to the logger
 * @param loggerName logger name
 * @param appenderAlias alias for new appender
 * @param appenderType type of new appender
 * @return {boolean} if appender created and attached successfully, otherwise false
 */
function attachNewAppender(loggerName, appenderAlias, appenderType) {
    let isAppenderAttached = false;
    $.ajax({
        url: "./attachNewAppender",
        async: false,
        data : {
            loggerName: loggerName,
            appenderAlias: appenderAlias,
            appenderType: appenderType
        },
        success: function (appenderJson, textStatus, jqXHR) {
            addAppenderTable(appenderJson);
            isAppenderAttached =  true;
            alert( "Success: " + jqXHR.getResponseHeader("Message") );
        },
        error: function(jqXHR) {
            alert( "Error: " + jqXHR.getResponseHeader("Message") );
        },
    });
    return isAppenderAttached;
}

/**
 * event listener for ".attach-existing-btn"
 * to open dialog for attaching existing APPENDER to logger
 */
$(document).on('click', '.attach-existing-btn', function () {
    let loggerName = $( this ).closest('.table').attr("id");
    let $dialog = $( '#attach-existing-appender-dialog' );

    $dialog.find('.logger-name').val(loggerName);
    $dialog.dialog({
        title: "Attach existing appender to logger",
        height: 350,
        width: 900,
        modal: true,
        buttons: {
            Attach: function() {
                let appenderAlias = $(this).find('.existing-appender-alias').val();
                if( attachExistingAppender(loggerName, appenderAlias) ) {
                    $(this).dialog( "close" );
                }
            },
            Cancel: function() { $(this).dialog( "close" ); }
        }
    });
});

/**
 * event listener for ".attach-copy-btn"
 * to open dialog for attaching to logger copy of existing APPENDER (copy have another alias)
 */
$(document).on('click', '.attach-copy-btn', function () {
    let loggerName = $( this ).closest('.table').attr("id");
    let $dialog = $( '#attach-copy-appender-dialog' );

    $dialog.find('.logger-name').val(loggerName);
    $dialog.dialog({
        title: "Attach copy of existing appender to logger",
        height: 350,
        width: 900,
        modal: true,
        buttons: {
            Attach: function() {
                let existingAppenderAlias = $(this).find('.existing-appender-alias').val();
                let newAppenderAlias = $(this).find('.new-appender-alias').val();

                if( attachCopyAppender(loggerName, existingAppenderAlias, newAppenderAlias) ) {
                    $(this).dialog( "close" );
                }
            },
            Cancel: function() { $(this).dialog( "close" ); }
        }
    });
});

/**
 * event listener for ".attach-new-btn"
 * to open dialog for attaching new APPENDER to logger
 */
$(document).on('click', '.attach-new-btn', function () {
    let loggerName = $( this ).closest('.table').attr("id");
    let $dialog = $( '#attach-new-appender-dialog' );

    $dialog.find('.logger-name').val(loggerName);
    $dialog.dialog({
        title: "Attach appender to logger",
        height: 350,
        width: 900,
        modal: true,
        buttons: {
            Attach: function() {
                let newAppenderAlias = $(this).find('.new-appender-alias').val();
                let newAppenderType = $(this).find('.new-appender-type').val();

                if( attachNewAppender(loggerName, newAppenderAlias, newAppenderType) ) {
                    $(this).dialog( "close" );
                }
            },
            Cancel: function() { $(this).dialog( "close" ); }
        }
    });
});