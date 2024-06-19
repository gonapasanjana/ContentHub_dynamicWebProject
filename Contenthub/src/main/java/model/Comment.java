package model;

public class Comment {
    private int id;
    private int entryId;
    private String commentText;

    public Comment(int id, int entryId, String commentText) {
        this.id = id;
        this.entryId = entryId;
        this.commentText = commentText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
