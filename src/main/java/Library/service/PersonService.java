package Library.service;

import Library.models.Book;
import Library.models.Person;
import Library.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(int id) {
        Person person = null;
        if (personRepository.findById(id).isPresent()) {
            person = personRepository.findById(id).get();
        }
        return person;
    }

    @Transactional
    public void save(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson) {
        Person person = personRepository.findById(id).get();
        updatePerson.setPassword(person.getPassword());
        updatePerson.setRole(person.getRole());
        updatePerson.setId(id);
        personRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public List<Book> getAllBooksPerson(int id) {
        Person person = null;
        if (personRepository.findById(id).isPresent()) {
            person = personRepository.findById(id).get();
        }
        Hibernate.initialize(person.getBooks());
        person.getBooks().forEach(book -> {
            long diffBetweenTime = Math.abs(book.getDate().getTime() - new Date().getTime());
            if (diffBetweenTime > 30000) {
                book.setCheckDate(true);
            }
        });
        return person.getBooks();
    }
    public Optional<Person> findByFullName(String name) {
        return personRepository.findPersonByName(name);
    }
}
