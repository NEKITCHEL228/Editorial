package domain.model;

public class Article {

    public int id;
    public String title;
    public String content;
    public Status status;
    public String publishedAt;

    public Article(
            int id,
            String title,
            String content,
            Status status,
            String publishedAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.publishedAt = publishedAt;
    }


    public enum Status {
        PENDING,
        MODERATING,
        REJECTED,
        PUBLISHED
    }
}

