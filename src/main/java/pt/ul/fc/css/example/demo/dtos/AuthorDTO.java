package pt.ul.fc.css.example.demo.dtos;

import java.util.List;

public class AuthorDTO {
    private Long id;
    private String name;
    private List<Long> bookIDs;

    public AuthorDTO() {}

    public void setBookIDs(List<Long> bookIDs) {
        this.bookIDs = bookIDs;
    }

    public AuthorDTO(Long id, String name, List<Long> books) {
        this.id = id;
        this.name = name;
        this.bookIDs = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getBookIDs() {
        return bookIDs;
    }

    public void setBooks(List<Long> books) {
        this.bookIDs = books;
    }

}
