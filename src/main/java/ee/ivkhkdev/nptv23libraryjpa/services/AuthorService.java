package ee.ivkhkdev.nptv23libraryjpa.services;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.entity.Book;
import ee.ivkhkdev.nptv23libraryjpa.helpers.AuthorHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppService;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AuthorRepository;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Spliterator;

@Service
public class AuthorService implements AppService<Author> {

    @Autowired
    private AppHelper<Author> authorAppHelper;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorHelper authorHelper;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public boolean add() {
        Optional<Author> optionalAuthor = authorAppHelper.create();
        if (optionalAuthor.isPresent()) {
            authorRepository.save(optionalAuthor.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Author author) {
        return false;
    }

    @Override
    public boolean delete() {
        Long authorId = authorHelper.remove(authorRepository.findAll());
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            return false;
        }
        Author author = optionalAuthor.get();
        for(int i = 0; i<author.getBooks().size(); i++) {
            Book book = author.getBooks().get(i);
            book.getAuthors().remove(author);
            bookRepository.save(book);
        }
        authorRepository.delete(author);
        return true;
    }

    @Override
    public boolean print() {
         return authorAppHelper.printList(authorRepository.findAll());
    }


}
