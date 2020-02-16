<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Log4j Configurator</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="stylesheet" href="jquery-ui.css">
    <script  type="text/javascript" src="js/jquery.js"></script>
    <script  type="text/javascript" src="js/jquery-ui.js"></script>
    <script  type="text/javascript" src="js/main.js"></script>
  </head>

  <body>
    <div id="mainMenu" class="boldBorder">
        <input id="className" placeholder="Enter logger name (for example, &quot;your.existing.logger.className&quot;">
        <input id="editButton" type="button" value="Edit" onclick="showLogger($('#className').val())">
        <div class="centerTextDiv">OR</div>
        <input id="addNewLoggerBtn" type="button" value="Add new">
    </div>

    <br/>

    <div id="tables">
      <%--place for tables rendered by js--%>
    </div>

  </body>
</html>
