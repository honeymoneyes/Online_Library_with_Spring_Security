package Library.utils;

import Library.models.Person;
import Library.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PeopleValidator implements Validator {

    private final PersonRepository personRepository;

    @Autowired
    public PeopleValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if(personRepository.findPersonByName(person.getName()).isPresent()) {
            errors.rejectValue("name","","A person with that name already exists!");
        }
    }
}
