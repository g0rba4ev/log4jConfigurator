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
    <div class="boldBorder">
        <button>Add new logger</button> OR
      <div>
        <input id="className" placeholder="Enter logger name (for example, &quot;your.existing.logger.className&quot;">
        <input id="EditButton" class="button" type="submit" value="Edit" width="40px">
      </div>
    </div>


    <br/>

    <b>CurrentLoggerName</b>
    <table>
      <tr>
        <th>
          Attribute
        </th>
        <th>
          Current value
        </th>
        <th>
          New value
        </th>
        <th>
          Action
        </th>
      </tr>
      <tr>
        <td>Level</td>
        <td id="currentLogLevel">
          <input value="TEST" size="5" readonly>
        </td>
        <td>
          <select>
            <option value="OFF">OFF</option>
            <option value="FATAL">FATAL</option>
            <option value="ERROR">ERROR</option>
            <option value="WARN">WARN</option>
            <option value="INFO">INFO</option>
            <option value="DEBUG">DEBUG</option>
            <option value="TRACE">TRACE</option>
          </select>
        </td>
        <td>
          <button>UPDATE</button>
        </td>
      </tr>
      <tr>
        <td>Pattern</td>
        <td>CurrentPattern (will get from java code in jsp)</td>
        <td>
          <input placeholder="Enter new pattern">
        </td>
        <td>
          <button>UPDATE</button>
        </td>
      </tr>
    </table>



  </body>
</html>
