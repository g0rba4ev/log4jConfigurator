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
    let template = $("#loggerTpl").html();
    return Mustache.render(template, object);
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
    let template = $("#appenderTpl").html();
    return Mustache.render(template, objForMustache);
}