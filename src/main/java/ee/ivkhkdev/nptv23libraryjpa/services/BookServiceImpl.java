package ee.ivkhkdev.nptv23libraryjpa.services;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.entity.Book;
import ee.ivkhkdev.nptv23libraryjpa.helpers.AuthorHelperImpl;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.BookService;
import ee.ivkhkdev.nptv23libraryjpa.repository.AuthorRepository;
import ee.ivkhkdev.nptv23libraryjpa.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final AppHelper<Book> bookHelper;
    private final AuthorHelperImpl authorHelper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(AppHelper<Book> bookHelper, AuthorHelperImpl authorHelper, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookHelper = bookHelper;
        this.authorHelper = authorHelper;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    @Override
    public boolean add() {
        try {
            Optional<Book> optionalBook = bookHelper.create();//книга без авторов
            if(optionalBook.isEmpty()) {
                return false;
            }
            Book book = optionalBook.get();
            List<Long> listIdAuthorsBook = authorHelper.listAuthorsId((List<Author>) authorRepository.findAll());
            if(listIdAuthorsBook.isEmpty()) {return false;}
            List<Author> bookAuthors = new ArrayList<>();
            for(Long id : listIdAuthorsBook) {
                Optional<Author> optionalAuthor = authorRepository.findById(id);
                if(optionalAuthor.isEmpty()) {return false;}
                Author author = optionalAuthor.get();
                author.getBooks().add(book);
                bookAuthors.add(author);
            }
            book.setAuthors(bookAuthors);
            Book savedBook = bookRepository.save(book);
            return true;
        }catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean update(Book book) {
        return false;
    }

    @Override
    public boolean changeAvailability() {
        Long bookId = bookHelper.findIdEntityForChangeAvailability(bookRepository.findAll());
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isEmpty()) {
            return false;
        }
        Book book = optionalBook.get();
        if(book.isAvailable()){
            book.setAvailable(false);
        }else{
            book.setAvailable(true);
        }
        bookRepository.save(book);
        return true;
    }

    @Override
    public boolean print() {
        return bookHelper.printList(bookRepository.findAll());
    }
}
