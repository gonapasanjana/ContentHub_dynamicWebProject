<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, model.Entry, model.User, model.Comment, java.util.ArrayList" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String username = user.getUsername();

    List<Entry> entries = null;
    try {
        @SuppressWarnings("unchecked")
        List<Entry> tempEntries = (List<Entry>) request.getAttribute("entries");
        entries = tempEntries;
    } catch (ClassCastException e) {
        entries = new ArrayList<>();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        .edit-form {
            display: none; /* Initially hide the edit form */
        }
    </style>
    <script>
        function showEditForm(entryId) {
            // Hide all edit forms first
            var editForms = document.getElementsByClassName("edit-form");
            for (var i = 0; i < editForms.length; i++) {
                editForms[i].style.display = "none";
            }
            // Show the edit form for the clicked entry
            var editForm = document.getElementById("edit-form-" + entryId);
            editForm.style.display = "block";
        }

       
    </script>
</head>
<body>
    <div class="container mt-4">
        <h1>Welcome, <%= username %>!</h1>
        <hr>
        <h2>Your Content:</h2>
        <ul class="list-group">
            <% if (entries != null && !entries.isEmpty()) { %>
                <% for (Entry entry : entries) { %>
                    <li class="list-group-item">
                        <div class="mb-2">
                            <h4><%= entry.getTitle() %></h4>
                            <p><%= entry.getContent() %></p>
                            
                            <!-- Likes count -->
                            <div class="row align-items-center">
                                <div class="col-auto pr-0">
                                    <i class="fas fa-thumbs-up"></i>
                                </div>
                                <div class="col">
                                    <p class="mb-0"><%= entry.getLikesCount() %></p>
                                </div>
                            </div>
                            
                            <!-- Comments -->
                            <i>Comments:</i>
                            <ul class="list-unstyled">
                                <% List<Comment> comments = entry.getComments(); %>
                                <% if (comments != null && !comments.isEmpty()) { %>
                                    <% for (Comment comment : comments) { %>
                                        <li><%= comment.getCommentText() %></li>
                                    <% } %>
                                <% } else { %>
                                    <li>No comments yet.</li>
                                <% } %>
                            </ul>
                            
                            <!-- Edit link -->
                            <a href="javascript:void(0);" onclick="showEditForm(<%= entry.getId() %>);" class="btn btn-sm btn-primary">Edit</a>
                            
                            <!-- Delete form -->
                            <form id="delete-form-<%= entry.getId() %>" action="myEntries" method="post" class="d-inline">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="<%= entry.getId() %>">
                                <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                            </form>
                            
                            <!-- Edit form -->
                            <div id="edit-form-<%= entry.getId() %>" class="edit-form mt-3">
                                <form action="editEntry" method="post">
                                    <input type="hidden" name="id" value="<%= entry.getId() %>">
                                    <div class="form-group">
                                        <label for="title">Title:</label>
                                        <input type="text" class="form-control" id="title" name="title" value="<%= entry.getTitle() %>">
                                    </div>
                                    <div class="form-group">
                                        <label for="content">Content:</label>
                                        <textarea class="form-control" id="content" name="content" rows="3"><%= entry.getContent() %></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Save</button>
                                </form>
                            </div>
                        </div>
                    </li>
                <% } %>
            <% } else { %>
                <li class="list-group-item">No entries found.</li>
            <% } %>
        </ul>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
