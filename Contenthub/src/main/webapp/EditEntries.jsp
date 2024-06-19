<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Entry</title>
</head>
<body>
    <h1>Edit Entry</h1>
    <form action="myEntries" method="post">
        <input type="hidden" name="action" value="editSubmit">
        <input type="hidden" name="id" value="${entry.id}">
        Title: <input type="text" name="title" value="${entry.title}"><br>
        Content: <textarea name="content">${entry.content}</textarea><br>
        <button type="submit">Save Changes</button>
    </form>
    <br>
    <a href="myEntries">Cancel</a>
</body>
</html>
