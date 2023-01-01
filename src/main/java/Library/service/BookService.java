package Library.service;

import Library.models.Book;
import Library.models.Person;
import Library.repositories.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (!sortByYear) {
            return bookRepository.findAll();
        } else {
            return bookRepository.findAll(Sort.by("year"));
        }
    }

    public List<Book> findAllWithPagination(Integer page, Integer bookPerPage, boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, bookPerPage, Sort.by("year"))).getContent();
        } else {
            return bookRepository.findAll(PageRequest.of(page, bookPerPage)).getContent();
        }
    }
    public Book findById(int id) {
        Book book = null;
        if (bookRepository.findById(id).isPresent()) {
            book = bookRepository.findById(id).get();
        }
        return book;
    }
    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updateBook) {
        updateBook.setId(id);
        bookRepository.save(updateBook);
    }

    public List<Book> findByNameStartingWith(String name) {
        return bookRepository.findByNameStartingWith(name);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void setBookPerson(Person person, int id) {
        bookRepository.findById(id).ifPresent(book-> {
            book.setOwner(person);
            book.setDate(new Date());
            System.out.println(new Date().getTime());
        });
    }

    @Transactional
    public void release(int id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            book.setDate(null);
        });
    }
    public Person getBookOwner(int id) {
        Person person = null;

        if (bookRepository.findById(id).map(Book::getOwner).isPresent()) {
            person = bookRepository.findById(id).map(Book::getOwner).get();
        }
        return person;
    }
}
