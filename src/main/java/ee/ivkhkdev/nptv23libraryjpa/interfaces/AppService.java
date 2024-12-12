package ee.ivkhkdev.nptv23libraryjpa.interfaces;

import java.util.List;

public interface AppService<T> {
    boolean add();
    boolean update(T t);
    boolean changeAvailability();
    boolean print();
}
