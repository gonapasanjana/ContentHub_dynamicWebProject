package servlet;

import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.EntryDAO;

@WebServlet("/Likes")
public class IncrementLikesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Explicitly declare serialVersionUID

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int entryId = Integer.parseInt(request.getParameter("entry_id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));

        try {
            // Call EntryDAO method to increment likes for the specified entryId
            EntryDAO.incrementLikes(entryId, userId);

            // Get the updated likes count
            int likesCount = EntryDAO.getLikesCount(entryId);

            // Prepare JSON response
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("likesCount", likesCount);

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
