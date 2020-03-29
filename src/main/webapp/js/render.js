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
const LOGGER_TPL =  '<div class="collapsible" data-logger-or-appender="logger" data-logger-name="{{Name}}">Logger: {{Name}}</div>' +
                    '<div class="content">' +
                        '<div class="tableGrid">' +
                            '<input readonly value="Name">' +
                            '<input readonly value="{{Name}}">' +
                            '<input type="button" value="EDIT" class="editPropBtn">' +
                            '<input readonly value="Additivity">' +
                            '<input readonly value="{{Additivity}}">' +
                            '<input type="button" value="EDIT" class="editPropBtn">' +
                            '<input readonly value="Level">' +
                            '<input readonly value="{{Level}}">' +
                            '<input type="button" value="EDIT" class="editPropBtn">' +
                        '</div>' +
                        '<div class="attachedAppendersGrid">' +
                            '<input readonly value="ATTACHED APPENDERS:" class="tableHead">' +
                            '<input type="button" value="Attach New" id="attachAppenderBtn">' +
                            '{{#Appenders}}' +
                                '<input readonly value="{{.}}">' +
                                '<input type="button" value="REMOVE" class="removeAppenderBtn">' +
                            '{{/Appenders}}' +
                        '</div>' +
                    '</div>';

// appender table html template for Mustache
const APPENDER_TPL =    '<div class="collapsible" data-logger-or-appender="appender" data-appender-alias="{{Alias}}">Appender: {{Alias}}</div>' +
                        '<div class="content">' +
                            '<div class="tableGrid">' +
                                '<input readonly value="Alias">' +
                                '<input readonly value="{{Alias}}">' +
                                '<input type="button" value="EDIT" class="editPropBtn">' +
                                '<input readonly value="Appender">' +
                                '<input readonly value="{{Appender}}">' +
                                '<input type="button" value="EDIT" class="editPropBtn">' +
                                '{{#appenderProps}}' +
                                    '<input readonly value="{{key}}">' +
                                    '<input readonly value="{{value}}">' +
                                    '<input type="button" value="EDIT" class="editPropBtn">' +
                                '{{/appenderProps}}' +
                            '</div>' +
                        '</div>';