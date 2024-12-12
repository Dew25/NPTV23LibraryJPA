package ee.ivkhkdev.nptv23libraryjpa.helpers;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.entity.Book;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class BookHelper implements AppHelper<Book> {

    @Autowired private Input input;

    @Override
    public Optional<Book> create() {
        Book book = new Book();
        System.out.print("Название книги: ");
        book.setTitle(input.getString());
        System.out.print("Год издания книги: ");
        book.setPublishedYear(input.getInt());
        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Book book) {
        return Optional.empty();
    }

    @Override
    public boolean printList(List<Book> books) {
        try {
            if(books.isEmpty()){
                System.out.println("Список книг пуст");
                return false;
            }
            System.out.println("---------- Список книг --------");
            for(int i=0;i<books.size();i++) {
                Book book = books.get(i);
                if(!book.isAvailable())continue;
                StringBuilder sbBookAuthors = new StringBuilder();
                for(int j=0;j<book.getAuthors().size();j++) {
                    sbBookAuthors.append(book.getAuthors().get(j).getFirstname())
                                 .append(" ")
                                 .append(book.getAuthors().get(j).getLastname())
                                 .append(". ");
                }
                System.out.printf("%d. %s. %s%d%n",
                        book.getId(),
                        book.getTitle(),
                        sbBookAuthors.toString(),
                        book.getPublishedYear()
                );
            }
            return true;
        }catch (Exception e){
            System.out.println("Error authorAppHelper.printList(authors): "+e.getMessage());
        }
        return false;
    }

    @Override
    public Long findIdEntityForChangeAvailability(List<Book> books) {
        this.printList(books);
        System.out.print("Выберите номер книги для удаления: ");
        return (long) input.getInt();
    }
}
