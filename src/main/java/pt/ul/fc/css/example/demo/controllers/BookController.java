package pt.ul.fc.css.example.demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.example.demo.dtos.AuthorDTO;
import pt.ul.fc.css.example.demo.dtos.BookDTO;
import pt.ul.fc.css.example.demo.entities.Book;
import pt.ul.fc.css.example.demo.entities.Author;
import pt.ul.fc.css.example.demo.repositories.BookRepository;
import pt.ul.fc.css.example.demo.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Operation(
            summary = "Create a new book",
            description = "Creates a new book associated with an existing author and returns the created book."
    )
    @ApiResponse(responseCode = "200", description = "Book created successfully.")
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDto) {
        Author author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);

        author.addBook(savedBook);
        authorRepository.save(author);

        BookDTO returnBook = new BookDTO(savedBook.getTitle(), savedBook.getAuthor().getId());
        return ResponseEntity.ok(returnBook);

    }

    @Operation(
            summary = "List all books",
            description = "List all books in the database."
    )
    @GetMapping
    public List<BookDTO> listBooks() {
        return this.bookRepository.findAll()
                .stream()
                .map(book -> new BookDTO(book.getTitle(), book.getAuthor().getId()))
                .collect(Collectors.toList());

    }

}
