package ee.ivkhkdev.nptv23libraryjpa.repository;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    //query = "SELECT * FROM author WHERE `firstname`= ? AND `lastname` = ?";
    Optional<Author> findByFirstnameAndLastname(String firstName, String lastName);


}
