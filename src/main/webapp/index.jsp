<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Log4j Configurator</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="stylesheet" href="jquery-ui.css">
    <script type="text/javascript" src="js/libs/jquery.js"></script>
    <script type="text/javascript" src="js/libs/jquery-ui.js"></script>
    <script type="text/javascript" src="js/libs/mustache.min.js"></script>
    <script type="text/javascript" src="js/main.js"></script>
    <script type="text/javascript" src="js/render.js"></script>
  </head>

  <body>
    <div id="mainMenuHeader" class="collapsible boldBorder" align="center">
      MAIN MENU
    </div>
    <div id="mainMenu" class="content">
      <input id="readPropsBtn" type="button" value="Read properties">
      <div class="btnDescription">
        < - Before editing you should read properties from file
      </div>
    </div>

    <div id="chooseLogger" class="boldBorder">
        <input id="loggerName" placeholder="Enter logger name (for example, &quot;package.folder.existing.logger&quot;)">
        <input id="editBtn" type="button" value="Edit" onclick="showLogger($('#loggerName').val())">
    </div>

    <br/>

    <div id="tables">

      <%--place for tables rendered by js--%>
    </div>

    <button id="saveChangesBtn" hidden="true">SAVE CHANGES</button>

    <script id="loggerTpl" type="x-tmpl-mustache">
        <div class="collapsible" data-logger-or-appender="logger" data-logger-name="{{Name}}">Logger: {{Name}}</div>
        <div class="content">
          <div class="tableGrid">
            <input readonly value="Name">
            <input readonly value="{{Name}}">
            <input type="button" value="EDIT" class="editPropBtn">
            <input readonly value="Additivity">
            <input readonly value="{{Additivity}}">
            <input type="button" value="EDIT" class="editPropBtn">
            <input readonly value="Level">
            <input readonly value="{{Level}}">
            <input type="button" value="EDIT" class="editPropBtn">
          </div>
          <div class="attachedAppendersGrid">
              <input readonly value="ATTACHED APPENDERS:" class="tableHead">
              <input type="button" value="Attach New" id="attachAppenderBtn">
            {{#Appenders}}
              <input readonly value="{{.}}">
              <input type="button" value="REMOVE" class="removeAppenderBtn">
            {{/Appenders}}
           </div>
        </div>
    </script>

    <script id="appenderTpl" type="x-tmpl-mustache">
        <div class="collapsible" data-logger-or-appender="appender" data-appender-alias="{{Alias}}">Appender: {{Alias}}</div>
        <div class="content">
          <div class="tableGrid">
            <input readonly value="Alias">
            <input readonly value="{{Alias}}">
            <input type="button" value="EDIT" class="editPropBtn">
            <input readonly value="Appender">
            <input readonly value="{{Appender}}">
            <input type="button" value="EDIT" class="editPropBtn">
            {{#appenderProps}}
              <input readonly value="{{key}}">
              <input readonly value="{{value}}">
              <input type="button" value="EDIT" class="editPropBtn">
            {{/appenderProps}}
          </div>

        </div>
    </script>

  </body>
</html>
