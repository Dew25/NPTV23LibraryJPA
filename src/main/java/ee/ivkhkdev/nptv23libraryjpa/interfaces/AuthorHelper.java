package ee.ivkhkdev.nptv23libraryjpa.interfaces;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;

import java.util.List;

public interface AuthorHelper extends AppHelper<Author> {
    List<Long> listAuthorsId(List<Author> authors);
}
