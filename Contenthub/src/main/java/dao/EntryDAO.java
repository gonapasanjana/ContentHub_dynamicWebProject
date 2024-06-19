package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Comment;
import model.Entry;
import util.DBConnection;

public class EntryDAO {

    public static void incrementLikes(int entryId,int userId) throws SQLException {
    	String sql = "INSERT INTO entry_likes (entry_id, user_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, entryId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public static void addComment(int entryId, String commentText) throws SQLException {
        String sql = "INSERT INTO comments (entry_id, comment_text) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, entryId);
            stmt.setString(2, commentText);
            stmt.executeUpdate();
        }
    }

    public static List<Comment> getComments(int entryId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT id, entry_id, comment_text FROM comments WHERE entry_id = ? ";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, entryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int commentId = rs.getInt("id");
                    String commentText = rs.getString("comment_text");
                    Comment comment = new Comment(commentId, entryId, commentText);
                    comments.add(comment);
                }
            }
        }
        return comments;
    }

    
    
    public static int getLikesCount(int entryId) throws SQLException {
        int likesCount = 0;
        String sql = "SELECT COUNT(*) AS count FROM entry_likes WHERE entry_id = ? ";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, entryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    likesCount = rs.getInt("count");
                }
            }
        }
        return likesCount;
    }
    public static List<Entry> getAllEntries() {
        List<Entry> entries = new ArrayList<>();
        String sql = "SELECT * FROM entries ";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Entry entry = new Entry(id, userId, title, content);
                entries.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entries;
    }
}
