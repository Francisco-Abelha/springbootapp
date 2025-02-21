package pt.ul.fc.css.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import pt.ul.fc.css.example.demo.entities.Author;
import pt.ul.fc.css.example.demo.entities.Book;
import pt.ul.fc.css.example.demo.repositories.AuthorRepository;
import pt.ul.fc.css.example.demo.repositories.BookRepository;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            // save a few customers
            authorRepository.save(new Author("Jack", "Bauer"));
            authorRepository.save(new Author("Chloe", "O'Brian"));
            authorRepository.save(new Author("Kim", "Bauer"));
            authorRepository.save(new Author("David", "Palmer"));
            authorRepository.save(new Author("Michelle", "Dessler"));

            // fetch all customers
            log.info("Authors found with findAll():");
            log.info("-------------------------------");
            for (Author author : authorRepository.findAll()) {
                log.info(author.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            authorRepository.findById(1L).ifPresent(author -> {
                log.info("Author found with findById(1L):");
                log.info("--------------------------------");
                log.info(author.toString());
                log.info("");
            });

            // fetch customers by last name (Bauer)
            log.info("Authors found with findByName('Bauer'):");
            log.info("--------------------------------------------");
            authorRepository.findByName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            log.info("");


            // New book 24 hours for Jack Bauer
            log.info("New book '24 hours' by Jack Bauer");
            log.info("--------------------------------------------");
            if (!authorRepository.findByName("Jack").isEmpty()) {
                Author author = authorRepository.findByName("Jack").get(0);
                Book book = new Book("24 hours", authorRepository.findByName("Jack").get(0));
                bookRepository.save(book);
                authorRepository.save(author);
                log.info("--------------------------------");
                log.info(book.toString());
            } else {
                log.warn("Nenhum autor encontrado com o nome 'Jack'");
            }
            log.info("");

            // fetch customers by last name (Bauer). Now with a book
            log.info("Authors found with findByName('Bauer') now with Book:");
            log.info("--------------------------------------------");
            authorRepository.findByName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            log.info("");
        };
    }
}
