package ee.ivkhkdev.nptv23libraryjpa.entity;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    @ManyToMany(mappedBy = "authors",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Book> books;
    private boolean available = true;

    public Author() {}

    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Author(String firstname, String lastname, List<Book> books) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(firstname, author.firstname) && Objects.equals(lastname, author.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("id=").append(id);
        sb.append(", firstname='").append(firstname).append('\'');
        sb.append(", lastname='").append(lastname).append('\'');
        sb.append(", books=").append(Arrays.toString(books.toArray()));
        sb.append(", available=").append(available);
        sb.append('}');
        return sb.toString();
    }
}
