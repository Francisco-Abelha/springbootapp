package pt.ul.fc.css.example.demo.dtos;

public class BookDTO {

    private String title;
    private Long authorId;

    public BookDTO() {}

    public BookDTO(String title, Long authorId) {
        this.title = title;
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
