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
    <script src="js/attachAppender.js"></script>
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
      <input class="logger-name" id="loggerName" placeholder="Enter logger name (for example, &quot;package.folder.existing.logger&quot;)">
      <input id="edit-btn" type="button" value="Edit" onclick="showLogger($('#loggerName').val())">
    </div>

    <br>

    <div id="tables">
      <%--place for tables rendered by js--%>
    </div>
    <button id="save-changes-btn" hidden>SAVE CHANGES</button>

    <div id="attach-existing-appender-dialog" hidden>
      <div class="attach-appender-grid">
        <input readonly value="Logger:">
        <input class="logger-name" readonly value="">
        <input readonly value="Attaching appender:">
        <input class="existing-appender-alias" placeholder="Enter alias of existing appender">
      </div>
    </div>

    <div id="attach-copy-appender-dialog" hidden>
      <div class="attach-appender-grid">
        <input readonly value="Logger:">
        <input class="logger-name" readonly value="">
        <input readonly value="Create copy of:">
        <input class="existing-appender-alias" placeholder="Enter alias of existing appender">
        <input readonly value="With alias:">
        <input class="new-appender-alias" placeholder="Enter alias for new appender">
      </div>
    </div>

    <div id="attach-new-appender-dialog" hidden>
      <div class="attach-appender-grid">
        <input readonly value="Logger:">
        <input class="logger-name" readonly value="">
        <input readonly value="Appender alias:">
        <input class="new-appender-alias" placeholder="Enter alias for new appender">
        <input readonly value="Appender type:">
        <input class="new-appender-type" placeholder="Enter type of new appender">
      </div>
    </div>

  </body>
</html>
