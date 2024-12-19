package ee.ivkhkdev.nptv23libraryjpa.services;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.helpers.AuthorHelperImpl;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppService;
import ee.ivkhkdev.nptv23libraryjpa.repository.AuthorRepository;
import ee.ivkhkdev.nptv23libraryjpa.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AppService<Author> {

    private AuthorHelperImpl authorAppHelper;
    private AuthorRepository authorRepository;
    private AuthorHelperImpl authorHelper;
    private BookRepository bookRepository;

    public AuthorServiceImpl(AuthorHelperImpl authorAppHelper, AuthorRepository authorRepository, AuthorHelperImpl authorHelper, BookRepository bookRepository) {
        this.authorAppHelper = authorAppHelper;
        this.authorRepository = authorRepository;
        this.authorHelper = authorHelper;
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean add() {
        Optional<Author> optionalAuthor = authorAppHelper.create();
        if (optionalAuthor.isEmpty()) {
            return false;
        }
        Author author = optionalAuthor.get();
        authorRepository.save(author);
        return true;
    }

    @Override
    public boolean update(Author author) {
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
            if(author.isAvailable()){
                author.setAvailable(false);
            }else{
                author.setAvailable(true);
            }
            authorRepository.save(author);
            return true;
        }catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean print() {
         return authorAppHelper.printList(authorRepository.findAll());
    }


}
