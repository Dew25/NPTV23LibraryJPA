/**
 * В этом классе есть зависимость от authorRepository, что приводит к смешению слоев приложения.
 * Слой представления (главный клас и классы реализующие AppHelper), это все классы, которые могут общаться с пользователем и
 * имеют возможность печатать что-то в консоле, а также считывать данные введенные пользователем.
 * Для выполнения воли пользователя этот слой обращается к слою сервисов.
 * Слой сервисов (AppService), это классы, которые имеют инструменты бизнес логики и могут выполнять уточнение
 * задач через обращение к слою хелперов и для сохранения в базе к слою репозиториев.
 * Слой репозиториев умеет сохранять и считывать данные из базы. Ничего другого он не умеет и использоваться
 * должен сервисами для получения данных из базы и сохранения данных в базу.
 *
 * Слой хелперов не должен связываться с репозиторием напрямую. Если это есть, как в этом классе, значит надо
 * производить рефакторинг приложения с целью перемещения задачи связанной с репозиторием в слой сервисов
 * Решение этой задачи выполнено в ветке refactoringDev
 */
package ee.ivkhkdev.nptv23libraryjpa.helpers;
import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AuthorRepository;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorHelper implements AppHelper<Author> {
    @Autowired
    private Input input;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Optional<Author> create() {
        try {
            Author author = new Author();
            System.out.print("Имя автора: ");
            author.setFirstname(input.getString());
            System.out.print("Фамилия автора: ");
            author.setLastname(input.getString());
            return Optional.of(author);
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> edit(Author author) {
        return Optional.empty();
    }

    @Override
    public boolean printList(List<Author> authors) {
        try {
            if(authors.isEmpty()){
                System.out.println("Список авторов пуст");
                return false;
            }
            System.out.println("---------- Список авторов --------");
            for(int i=0;i<authors.size();i++) {
                Author author = authors.get(i);
                System.out.printf("%d. %s %s%n",
                        author.getId(),
                        author.getFirstname(),
                        author.getLastname()
                );
            }
            return true;
        }catch (Exception e){
            System.out.println("Error authorAppHelper.printList(authors): "+e.getMessage());
        }
        return false;
    }

    @Override
    public Long remove(List<Author> authors) {
        this.printList(authors);
        System.out.println("Выберите номер автора для удаления: ");
        return (long) input.getInt();
    }

    public List<Author> listAuthorsForBook(List<Author> authors) {
        this.printList(authors);
        System.out.print("Сколько авторов у книги: ");
        int countAuthorsForBook = input.getInt();
        List<Author> authorsForBook = new ArrayList<>();
        for(int i=0;i<countAuthorsForBook;i++) {
            System.out.printf("Выберите %d-го автора из списка: ",i+1);
            Optional<Author> optionalAuthor = authorRepository.findById((long) input.getInt());
            optionalAuthor.ifPresent(authorsForBook::add);
        }
        return authorsForBook;
    }
}

