<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Log4j Configurator</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="jquery-ui.css">
    <script src="js/libs/jquery.js"></script>
    <script src="js/libs/jquery-ui.js"></script>
    <script src="js/libs/mustache.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/render.js"></script>
  </head>

  <body>
    <div class="collapsible" id="mainMenuHeader">
      MAIN MENU
    </div>
    <div class="content" id="mainMenu">
      <input id="read-config-btn" type="button" value="Read configuration">
      <div class="btn-description">
        ← Before editing you should read configuration from file with log4j properties
      </div>
    </div>

    <div id="chooseLogger">
      <input id="loggerName" placeholder="Enter logger name (for example, &quot;package.folder.existing.logger&quot;)">
      <input id="edit-btn" type="button" value="Edit" onclick="showLogger($('#loggerName').val())">
    </div>

    <br>

    <div id="tables">

      <%--place for tables rendered by js--%>
    </div>

    <button id="save-changes-btn" hidden>SAVE CHANGES</button>

  </body>
</html>
