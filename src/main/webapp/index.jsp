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

  </body>
</html>
