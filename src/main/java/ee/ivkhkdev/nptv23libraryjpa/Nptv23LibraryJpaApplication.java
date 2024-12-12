package ee.ivkhkdev.nptv23libraryjpa;

import ee.ivkhkdev.nptv23libraryjpa.services.AuthorService;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.Input;
import ee.ivkhkdev.nptv23libraryjpa.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.TransactionProperties;

@SpringBootApplication
public class Nptv23LibraryJpaApplication implements CommandLineRunner {

	@Autowired
	private Input input;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private BookService bookService;
    @Autowired
    private TransactionProperties transactionProperties;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("------ Библиотека группы NPTV23 с базой данных ------");
		System.out.println("--------------------------------------");
		boolean repeat=true;
		do{
			System.out.println("Список задач: ");
			System.out.println("0. Выйти из программы");
			System.out.println("1. Добавить автора");
			System.out.println("2. Добавить книгу");
			System.out.println("3. Список авторов");
			System.out.println("4. Список книг");
			System.out.println("5. Удалить автора");
			System.out.println("6. Удалить книгу");

			System.out.print("Введите номер задачи: ");
			int task = Integer.parseInt(input.getString());
			switch (task) {
				case 0:
					repeat = false;
					break;
				case 1:
					if(authorService.add()){
						System.out.println("Автор добавлен");
					}else{
						System.out.println("Автора добавить не удалось");
					};
					break;
				case 2:
					if(bookService.add()){
						System.out.println("Книга добавлена");
					}else{
						System.out.println("Книгу добавить не удалось");
					};
					break;
				case 3:
					authorService.print();
					break;
				case 4:
					bookService.print();
					break;
				case 5:
					authorService.changeAvailability();
					break;
				case 6:
					bookService.changeAvailability();
					break;
				default:
					System.out.println("Выберите задачу из списка!");
			}
			System.out.println("--------------------------------------");
		}while(repeat);
		System.out.println("До свидания :)");

	}

	public static void main(String[] args) {
		SpringApplication.run(Nptv23LibraryJpaApplication.class, args);
	}

}
