package ee.ivkhkdev.nptv23libraryjpa.repository;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //"SELECT * FROM book WHERE `book.authors.`"
    List<Book> findByAuthorsContaining(Author author);
}
