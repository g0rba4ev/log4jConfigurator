/**
 * function add the tables with object's properties to #tables <div>
 *
 * NOTE: table there is a <div> with grids, not the table
 * @param JSON an object by the contents of which a table will be created
 */
function renderByJSON(JSON){
    let tables = $('#tables'); // get link to the #tables div
    tables.empty(); // clean div #tables (from previous results)

    let html;
    $.each(JSON, function ( index, object) {
        let firstKey = Object.keys( object )[0];

        if(firstKey  === "Name") {
            html = renderLoggerTable( object );
        } else if (firstKey === "Alias") {
            html = renderAppenderTable( object );
        } else {
            tables.append(firstKey + " : " + object[firstKey]);
            return;
        }

        tables.append(html);
    });
}

/**
 * render logger table by template "loggerTpl" Mustache-min.js
 * @param object JSON by the contents of which a table will be created
 * @returns {*}
 */
function renderLoggerTable(object) {
    return Mustache.render(LOGGER_TPL, object);
}

/**
 * render appender table by template "appenderTpl" using Mustache-min.js
 * NOTE: before rendering structure of the object will be changed (for Mustache)
 * @param appenderObj JSON by the contents of which a table will be created
 * @returns {*}
 */
function renderAppenderTable(appenderObj) {
    // prepare appenderObj for our Mustache template
    let objForMustache = {
        Alias: appenderObj["Alias"],
        Appender: appenderObj["Appender"],
        appenderProps: []
    };

    $.each(appenderObj.appenderProps, function (key, value) {
        objForMustache.appenderProps.push(
            {
                key: key,
                value: value
            }
        )
    });

    // doing render
    return Mustache.render(APPENDER_TPL, objForMustache);
}

// logger table html template for Mustache
const LOGGER_TPL =  '<div class="table" id="{{Name}}" data-table-type="logger">' +
                        '<div class="logger-buttons">' +
                            '<input class="attach-existing-btn" type="button" value="+Existing" title="Attach existing appender">' +
                            '<input class="attach-copy-btn" type="button" value="+Copy" title="Attach copy of existing appender">' +
                            '<input class="attach-new-btn" type="button" value="+New" title="Attach new appender">' +
                            '<input class="delete-logger-btn" type="button" value="DELETE LOGGER">' +
                        '</div>' +
                        '<div class="collapsible">Logger: {{Name}}</div>' +
                        '<div class="content">' +
                            '<div class="table-grid">' +
                                '<input readonly value="Name">' +
                                '<input readonly value="{{Name}}">' +
                                '<input class="edit-prop-btn" type="button" value="EDIT">' +
                                '<input readonly value="Additivity">' +
                                '<input readonly value="{{Additivity}}">' +
                                '<input class="edit-prop-btn" type="button" value="EDIT">' +
                                '<input readonly value="Level">' +
                                '<input readonly value="{{Level}}">' +
                                '<input class="edit-prop-btn" type="button" value="EDIT">' +
                            '</div>' +
                        '</div>' +
                    '</div>';

// appender table html template for Mustache
const APPENDER_TPL =    '<div class="table" id="{{Alias}}" data-table-type="appender">' +
                            '<input class="detach-appender-btn" type="button" value="DETACH">' +
                            '<input class="delete-appender-btn" type="button" value="DELETE APPENDER">' +
                            '<div class="collapsible">Appender: {{Alias}}</div>' +
                            '<div class="content">' +
                                '<div class="table-grid">' +
                                    '<input readonly value="Alias">' +
                                    '<input readonly value="{{Alias}}">' +
                                    '<input class="edit-prop-btn" type="button" value="EDIT">' +
                                    '<input readonly value="Appender">' +
                                    '<input readonly value="{{Appender}}">' +
                                    '<input class="edit-prop-btn" type="button" value="EDIT">' +
                                '</div>' +
                                '<div class="appender-props-grid">' +
                                    '{{#appenderProps}}' +
                                        '<input readonly value="{{key}}">' +
                                        '<input readonly value="{{value}}">' +
                                        '<input class="edit-prop-btn" type="button" value="EDIT">' +
                                        '<input class="delete-prop-btn" type="button" value="X">' +
                                    '{{/appenderProps}}' +
                                '</div>' +
                                '<div class="add-new-prop-grid">' +
                                    '<input placeholder="Enter property KEY">' +
                                    '<input placeholder="Enter property VALUE">' +
                                    '<input class="add-new-prop-btn" type="button" value="ADD NEW PROPERTY">' +
                                '</div>' +
                            '</div>' +
                        '</div>';