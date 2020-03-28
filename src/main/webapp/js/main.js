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
                $('#saveChangesBtn').removeAttr("hidden");
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
            //change colour and font-width for value that was changed
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
    let loggerTableHeadElem = $('#tables').firstChild; // parentNode -> tables ->(child) tableHead
    let loggerName = loggerTableHeadElem.getAttribute("data-logger-name");
    $.ajax({
        url: "./saveChanges",
        success: function () {
            let url = "./";
            $(location).attr('href',url);
        }
    })
});

$(document).on('click', '#readPropsBtn', function () {
    $.ajax({
        url: "./readProps"
    })
});

