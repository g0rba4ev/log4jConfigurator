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
                let saveChangesBtn = document.createElement("button");
                saveChangesBtn.innerText="SAVE CHANGES";
                saveChangesBtn.id = "saveChangesBtn";
                $('#tables').append(saveChangesBtn);
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
        renderTable(tables, JSON[i]);
    }
}

/**
 * function add the APPENDER or LOGGER table to #tables
 *
 * NOTE: table there is a <div> with grids, not the table
 * @param tables <div> to which the table-div will be added
 * @param object  from json to be converted to table
 */
function renderTable(tables, object) {
    //create the head of the table by clicking on which the table will appear
    let tableHead = document.createElement('div');
    tableHead.classList.add('collapsible');
    //add event listener to show/hide table by click to tableHead div
    tableHead.addEventListener('click', collapse);

    let firstKey = Object.keys(object)[0];
    if(firstKey === "Error"){
        tableHead.innerText = "Error: " + object["Error"]; // error
        tables.append(tableHead);
        return;
    } else if (firstKey === "Name") {
        tableHead.innerText = "Logger: " + object["Name"]; // logger name
        tableHead.setAttribute("data-logger-or-appender", "logger");
        tableHead.setAttribute("data-logger-name", object["Name"])
    } else if (firstKey === "Alias") {
        tableHead.innerText = "Appender: " + object["Alias"]; // appender alias
        tableHead.setAttribute("data-logger-or-appender", "appender");
        tableHead.setAttribute("data-appender-alias", object["Alias"])
    }

    // create table
    let table = document.createElement('div');
    table.classList.add('tableGrid', 'content'); // display: grid
    for (let key in object) {
        let c1 = document.createElement("input"); // c = cell  in the row
        let c2 = document.createElement("input");
        let c3 =  document.createElement("input");
        c1.readOnly = true;
        c1.value = key;
        c2.readOnly = true;
        c2.value = object[key];
        c3.setAttribute("type", "button");
        c3.value = "EDIT";
        c3.classList.add("editPropBtn");
        table.append(c1, c2, c3);
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


/**
 * event listener for ".editPropBtn"
 * makes the field with property value mutable
 * toggles button
 */
$(document).on('click', '.editPropBtn', function () {
    let btnElem = this;
    let valueElem = this.previousSibling;
    //make value field mutable
    valueElem.removeAttribute("readonly");
    //toggle button
    btnElem.classList.remove("editPropBtn");
    btnElem.value = "UPDATE";
    btnElem.classList.add("updPropBtn");
});

/**
 * event listener for ".updPropBtn"
 * to send ajax for updating required parameter
 */
$(document).on('click', '.updPropBtn', function () {
    let tableHeadElem = this.parentNode.previousSibling;
    let btnElem = this;
    let valueElem = this.previousSibling;
    let keyElem = valueElem.previousSibling;

    let objForChange = tableHeadElem.getAttribute("data-logger-or-appender");
    let objName;
    if ( objForChange === "logger") {
        objName = tableHeadElem.getAttribute("data-logger-name");
    } else {
        objName = tableHeadElem.getAttribute("data-appender-alias");
    }
    $.ajax({
        url : "./updParam",
        data : {
            objForChange : objForChange,
            objName : objName,
            key: keyElem.value,
            newValue: valueElem.value
        },
        success : function (response) {
            valueElem.value = response;
            valueElem.classList.toggle("boldText");
            keyElem.classList.toggle("boldText");
            //change colour for value that was changed
            keyElem.classList.add("paramWasChanged");
            valueElem.classList.add("paramWasChanged");
            //make value field immutable
            valueElem.setAttribute("readonly", "true");
            //toggle button
            btnElem.classList.remove("updPropBtn");
            btnElem.value = "EDIT";
            btnElem.classList.add("editPropBtn");
        }
    });
});

$(document).on('click', '#saveChangesBtn', function () {
    let loggerTableHeadElem = this.parentNode.firstChild; // parentNode -> tables ->(child) tableHead
    let loggerName = loggerTableHeadElem.getAttribute("data-logger-name");
    $.ajax({
        url: "./saveChanges",
        success: function () {
            let url = "./";
            $(location).attr('href',url);
        }
    })
});