package ee.ivkhkdev.nptv23libraryjpa.services;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AuthorHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AuthorService;
import ee.ivkhkdev.nptv23libraryjpa.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorHelper authorHelper;

    public AuthorServiceImpl(AuthorHelper authorHelper, AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.authorHelper = authorHelper;
    }

    @Override
    public boolean add() {
        Optional<Author> optionalAuthor = authorHelper.create();
        if (optionalAuthor.isEmpty()) {
            return false;
        }
        Author author = optionalAuthor.get();
        authorRepository.save(author);
        return true;
    }

    @Override
    public boolean update(Author author){
        return false;
    }
    /*
     * Метод переключает доступность автора, меняя при вызове
     * значение поля available
     */
    @Override
    public boolean changeAvailability() {
        try {
            Long authorId = authorHelper.findIdEntityForChangeAvailability(authorRepository.findAll());
            Optional<Author> optionalAuthor = authorRepository.findById(authorId);
            if (optionalAuthor.isEmpty()) {
                return false;
            }
            Author author = optionalAuthor.get();
            author.setAvailable(!author.isAvailable());
            authorRepository.save(author);
            return true;
        }catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean print() {
         return authorHelper.printList(authorRepository.findAll());
    }


}
