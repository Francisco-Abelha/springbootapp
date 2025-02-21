package pt.ul.fc.css.example.demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pt.ul.fc.css.example.demo.dtos.AuthorDTO;
import pt.ul.fc.css.example.demo.entities.Author;
import pt.ul.fc.css.example.demo.entities.Book;
import pt.ul.fc.css.example.demo.repositories.AuthorRepository;

@RestController
@RequestMapping("/api")
public class AuthorController {
  private final AuthorRepository authorRepository;

  public AuthorController(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @Operation(
      summary = "Retrieve an author by ID",
      description = "Fetches an author by their unique identifier.")
  @GetMapping("/author/{id}")
  public AuthorDTO getAuthor(@PathVariable Long id) {

    Author author =
        this.authorRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Author not found"));

    return new AuthorDTO(author.getId(), author.getName(), getBookIds(author.getBooks()));
  }

  @Operation(
      summary = "Search authors by name",
      description = "Retrieves a list of authors whose names match the provided query string.")
  @GetMapping("/authors/search")
  public List<AuthorDTO> getAuthorsByName(@RequestParam String name) {
    return this.authorRepository.findByName(name).stream()
        .map(
            author ->
                new AuthorDTO(author.getId(), author.getName(), getBookIds(author.getBooks())))
        .collect(Collectors.toList());
  }

  private List<Long> getBookIds(List<Book> books) {
    return books.stream().map(book -> book.getId()).collect(Collectors.toList());
  }

  @Operation(summary = "Retrieve all authors", description = "Retrieves a list of all authors.")
  @GetMapping("/authors")
  public List<AuthorDTO> getAllAuthors() {
    return this.authorRepository.findAll().stream()
        .map(
            author ->
                new AuthorDTO(author.getId(), author.getName(), getBookIds(author.getBooks())))
        .collect(Collectors.toList());
  }

  @Operation(
      summary = "Create a new author",
      description = "Creates a new author and returns the ID of the saved author.")
  @PostMapping("/author")
  public Long postAuthor(@NonNull @RequestBody Author author) {
    Author saved = authorRepository.save(author);
    return saved.getId();
  }

  @Operation(
      summary = "Create or update an author",
      description =
          "Creates a new author or updates an existing one. Returns the ID of the saved author.")
  @PutMapping("/author")
  public Long putAuthor(@NonNull @RequestBody Author author) {
    Author saved = authorRepository.save(author);
    return saved.getId();
  }

  @Operation(summary = "Home page view", description = "Renders the home page view (index).")
  @RequestMapping(value = "/index")
  public ModelAndView home() {
    return new ModelAndView("index");
  }
}
