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
                let saveBtn = document.createElement("button");
                saveBtn.innerText="SAVE CHANGES";
                saveBtn.id = "saveBtn";
                $('#tables').append(saveBtn);
            }
        }
    });
}

/**
 * function add the tables with object's properties to #tables <div>
 *
 * NOTE: table there is a <div> with grids, not the table
 * @param JSON an object by the contents of which a table will be created
 */
function renderByJSON(JSON){
    let tables = $('#tables'); // get link to the #tables div
    tables.empty(); // clean div #tables (from previous results)
    for (let i=0; i<=JSON.length-1; i++){
        if(i === 0){
            renderLoggerTable(tables, JSON[i]);
        } else {
            renderAppenderTable(tables, JSON[i]);
        }
    }
}

/**
 * function add the APPENDER table to #tables
 *
 * NOTE: table there is a <div> with grids, not the table
 * @param tables <div> to which the table-div will be added
 * @param object object from json to be converted to table
 */
function renderAppenderTable(tables, object) {
    //create the head of the table by clicking on which the table will appear
    let tableHead = document.createElement('div');
    tableHead.classList.add('collapsible');
    //add event listener to show/hide table by click to tableHead div
    tableHead.addEventListener('click', collapse);
    tableHead.innerText = "Appender: " + object["Name"]; // appender name

    // create table
    let table = document.createElement('div');
    table.classList.add('tableGrid', 'content'); // display: grid
    for (let key in object) {
        let c1 = document.createElement("input"); // c = column
        let c2 = document.createElement("input");
        let c3 = document.createElement("input");
        let c4 = document.createElement("input");
        c1.readOnly = true;
        c1.value = key;
        c2.readOnly = true;
        c2.value = object[key];
        c3.setAttribute("placeholder", "Enter new value");
        c4.setAttribute("type", "button");
        c4.setAttribute("value", "UPDATE");
        c4.setAttribute("id", "updateBtn");
        table.append(c1, c2, c3, c4);
    }
    tables.append(tableHead);
    tables.append(table);
}

/**
 * function add the LOGGER table to #tables
 *
 * NOTE: table there is a <div> with grids, not the table
 * @param tables <div> to which the table-div will be added
 * @param object object from json to be converted to table
 */
function renderLoggerTable(tables, object){
    //create the head of the table by clicking on which the table will appear
    let tableHead = document.createElement('div');
    tableHead.classList.add('collapsible');
    //add event listener to show/hide table by click to tableHead div
    tableHead.addEventListener('click', collapse);
    let firstKey = Object.keys(object)[0];
    if(firstKey === "LoggerName"){
        tableHead.innerText = "Logger: " + object["LoggerName"]; // logger name
    } else if (firstKey === "Error") {
        tableHead.innerText = "Error: " + object["Error"]; // error
        tables.append(tableHead);
        return;
    }

    // create table
    let table = document.createElement('div');
    table.classList.add('tableGrid', 'content'); // display: grid
    for (let key in object) {
        let c1 = document.createElement("input"); // c = column
        let c2 = document.createElement("input");
        let c3 = document.createElement("input");
        let c4 = document.createElement("input");
        c1.readOnly = true;
        c1.value = key;
        c2.readOnly = true;
        c2.value = object[key];
        c3.setAttribute("placeholder", "Enter new value");
        c4.setAttribute("type", "button");
        c4.setAttribute("value", "UPDATE");
        c4.setAttribute("id", "updateBtn");
        table.append(c1, c2, c3, c4);
    }
    tables.append(tableHead);
    tables.append(table);
}

/**
 * to show/hide table by click to tableHead div
 */
function collapse() {
    this.classList.toggle('active');
    let content = this.nextElementSibling;
    if(content.style.maxHeight) {
        content.style.maxHeight = null;
    } else {
        content.style.maxHeight = content.scrollHeight + 'px';
    }
}