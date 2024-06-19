package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.EntryDAO;
import model.Comment;

@WebServlet("/C")
public class AddCommentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Explicitly declare serialVersionUID

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int entryId = Integer.parseInt(request.getParameter("entry_id"));
        String commentText = request.getParameter("comment_text");

        try {
            // Call EntryDAO method to add comment for the specified entryId
            EntryDAO.addComment(entryId, commentText);

            // Get the updated list of comments
            List<Comment> comments = EntryDAO.getComments(entryId);

            // Prepare JSON response
            JSONArray commentsArray = new JSONArray();
            for (Comment comment : comments) {
                JSONObject commentJson = new JSONObject();
                commentJson.put("commentText", comment.getCommentText());
                commentsArray.put(commentJson);
            }

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("comments", commentsArray);

            // Send JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An error occurred while processing your request.");
        }
    }
}
