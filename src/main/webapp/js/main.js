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
        }
    });
}

/**
 * function add the tables with object's properties to #tables <div>
 *
 * NOTE: table there is a <div> with grids, not the <table>
 * @param JSON an object by the contents of which a table will be created
 */
function renderByJSON(JSON){
    let tables = $('#tables'); // get link to the #tables div
    tables.empty(); // clean div #tables (from previous results)
    for (let i=0; i<=JSON.length-1; i++){
        addTableByObject(tables, JSON[i]);
    }
}

/**
 * function add the table to #tables
 *
 * NOTE: table there is a <div> with grids, not the <table>
 * @param tables <div> to which the table-div will be added
 * @param object object from json to be converted to table
 */
function addTableByObject(tables, object) {
    //create the head of the table by clicking on which the table will appear
    let tableHead = document.createElement('div');
    tableHead.classList.add('collapsible');
    let firstKey = Object.keys(object)[0];
    if(firstKey === "LoggerName"){
        tableHead.innerText = "Logger: " + object["LoggerName"]; // logger name
    } else if (firstKey === "Error") {
        tableHead.innerText = "Error: " + object["Error"]; // error
    } else {
        tableHead.innerText = "Appender: " + firstKey; // appender name
    }
    //add event listener to show/hide table bu click to tableHead div
    tableHead.addEventListener('click', function(){
        this.classList.toggle('active');
        let content = this.nextElementSibling;
        if(content.style.maxHeight) {
            content.style.maxHeight = null;
        } else {
            content.style.maxHeight = content.scrollHeight + 'px';
        }
    })
    // create table
    let table = document.createElement('div');
    table.classList.add('tableGrid', 'content'); // display: grid
    for (let key in object) {
        let c1 = document.createElement("input"); // c = column
        c1.readOnly = true;
        c1.value = key;
        let c2 = document.createElement("input");
        c2.readOnly = true;
        c2.value = object[key];
        let c3 = document.createElement("input");
        c3.setAttribute("placeholder", "Enter new value");
        let c4 = document.createElement("input");
        c4.setAttribute("type", "button");
        c4.setAttribute("value", "UPDATE");
        table.append(c1, c2, c3, c4);
    }
    tables.append(tableHead);
    tables.append(table);
}