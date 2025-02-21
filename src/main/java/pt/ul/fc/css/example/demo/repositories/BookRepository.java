package pt.ul.fc.css.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.example.demo.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
