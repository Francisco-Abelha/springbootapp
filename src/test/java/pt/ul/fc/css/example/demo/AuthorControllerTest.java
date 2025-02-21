package pt.ul.fc.css.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.ul.fc.css.example.demo.controllers.AuthorController;
import pt.ul.fc.css.example.demo.entities.Author;
import pt.ul.fc.css.example.demo.entities.Book;
import pt.ul.fc.css.example.demo.repositories.AuthorRepository;
import pt.ul.fc.css.example.demo.repositories.BookRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAuthorById() throws Exception {
        Author author = new Author();
        author.setId(1L);
        author.setName("John");
        author.setSurname("Doe");

        Book book = new Book();
        book.setId(100L);
        author.addBook(book);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        mockMvc.perform(get("/api/author/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.bookIDs[0]").value(100L));
    }

    @Test
    public void testGetAuthorsByName() throws Exception {
        Author author = new Author();
        author.setId(1L);
        author.setName("John");
        Book book = new Book();
        book.setId(100L);
        author.addBook(book);

        when(authorRepository.findByName("John")).thenReturn(Collections.singletonList(author));

        mockMvc.perform(get("/api/authors/search")
                        .param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].bookIDs[0]").value(100L));

    }

    @Test
    public void testGetAllAuthors() throws Exception {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("John Doe");
        Book book1 = new Book();
        book1.setId(100L);
        author1.addBook(book1);

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Jane Smith");
        Book book2 = new Book();
        book2.setId(200L);
        author2.addBook(book2);

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].bookIDs[0]").value(100L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$[1].bookIDs[0]").value(200L));
    }

    @Test
    public void testPostAuthor() throws Exception {
        Author author = new Author();
        author.setName("New Author");

        Author savedAuthor = new Author();
        savedAuthor.setId(10L);
        savedAuthor.setName("New Author");

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        mockMvc.perform(post("/api/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    public void testPutAuthor() throws Exception {
        Author author = new Author();
        author.setId(1L);
        author.setName("Updated Author");

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        mockMvc.perform(put("/api/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
