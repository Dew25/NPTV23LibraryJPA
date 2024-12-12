package ee.ivkhkdev.nptv23libraryjpa.interfaces;

import java.util.List;
import java.util.Optional;

public interface AppHelper <T>{
    Optional<T> create();
    Optional<T> edit (T t);
    boolean printList(List<T> ts);
    Long findIdEntityForChangeAvailability(List<T> ts);
}
