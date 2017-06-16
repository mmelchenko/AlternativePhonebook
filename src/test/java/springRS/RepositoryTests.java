package springRS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import springRS.domain.Contact;
import springRS.domain.ContactRepository;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ContactRepository repository;

    @Test
    public void findAllContacts() {
        Contact contact = new Contact("John", "Doe", "123");

        entityManager.persist(contact);
        entityManager.flush();

        ArrayList<Contact> contacts = (ArrayList<Contact>) repository.findAll();

        assertTrue(contacts.contains(contact));

        assertTrue(contacts.get(contacts.indexOf(contact)).equals(contact));
    }

    @Test
    public void findByNameAndSurname() {
        Contact contact = new Contact("Tom", "Ford", "987");

        entityManager.persist(contact);
        entityManager.flush();

        ArrayList<Contact> contacts = (ArrayList<Contact>) repository.findByNameAndSurname("Tom", "Ford");

        assertTrue(contacts.contains(contact));
    }

}
