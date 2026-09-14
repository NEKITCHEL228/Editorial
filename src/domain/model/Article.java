package domain.model;

public class Article {


    public Article(
            int id,
            String title,
            String content,
            Status status
    ) {}


    public enum Status {
        PENDING,
        MODERATING,
        REJECTING,
        PUBLISHED
    }
}

