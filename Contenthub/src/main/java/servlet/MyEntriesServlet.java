package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Comment;
import model.Entry;
import model.User;
import util.DBConnection;

@WebServlet("/myEntries")
public class MyEntriesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            List<Entry> entries = getUserEntriesWithDetails(connection, user.getId());
            request.setAttribute("entries", entries);
            request.getRequestDispatcher("MyEntries.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if (action == null || !action.equals("delete")) {
            response.sendRedirect("MyEntries.jsp");
            return;
        }

        int entryId = Integer.parseInt(request.getParameter("id"));

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);  // Begin transaction

            // Perform the delete operations
            deleteComments(connection, entryId);
            deleteEntryLikes(connection, entryId);
            deleteEntry(connection, entryId);

            connection.commit();  // Commit transaction
            response.sendRedirect(request.getContextPath() + "/myEntries");
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection connection = DBConnection.getConnection()) {
                if (connection != null && !connection.getAutoCommit()) {
                    connection.rollback();  // Rollback transaction on error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }

    private List<Entry> getUserEntriesWithDetails(Connection connection, int userId) throws SQLException {
        List<Entry> entries = new ArrayList<>();
        String sql = "SELECT e.id, e.title, e.content, " +
                     "COUNT(el.user_id) AS likesCount " +
                     "FROM entries e " +
                     "LEFT JOIN entry_likes el ON e.id = el.entry_id " +
                     "WHERE e.user_id = ? " +
                     "GROUP BY e.id, e.title, e.content";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    int likesCount = resultSet.getInt("likesCount");

                    List<Comment> comments = getCommentsForEntry(connection, id);

                    Entry entry = new Entry(id, userId, title, content);
                    entry.setLikesCount(likesCount);
                    entry.setComments(comments);

                    entries.add(entry);
                }
            }
        }
        return entries;
    }

    private List<Comment> getCommentsForEntry(Connection connection, int entryId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE entry_id = ? ";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int commentId = resultSet.getInt("id");
                    String commentText = resultSet.getString("comment_text");
                    Comment comment = new Comment(commentId, entryId, commentText);
                    comments.add(comment);
                }
            }
        }
        return comments;
    }

    private void deleteEntry(Connection connection, int entryId) throws SQLException {
        String sql = "DELETE FROM entries WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entryId);
            statement.executeUpdate();
        }
    }

    private void deleteComments(Connection connection, int entryId) throws SQLException {
        String sql = "DELETE FROM comments WHERE entry_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entryId);
            statement.executeUpdate();
        }
    }

    private void deleteEntryLikes(Connection connection, int entryId) throws SQLException {
        String sql = "DELETE FROM entry_likes WHERE entry_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entryId);
            statement.executeUpdate();
        }
    }
}
