package springRS;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import springRS.domain.Contact;
import springRS.domain.ContactRepository;
import springRS.services.ContactService;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class ServiceLayerTests {

    @TestConfiguration
    static class ContactServiceTestContextConfiguration {

        @Bean
        public ContactService contactService() {
            return new ContactService();
        }
    }

    @Autowired
    private ContactService contactService;

    @MockBean
    private ContactRepository repository;

    @Before
    public void setup() {
        Contact john = new Contact("John", "Doe", "111");
        Contact tom = new Contact("Tom", "Ford", "222");

        ArrayList<Contact> allContacts = new ArrayList<Contact>();
        allContacts.add(john);
        allContacts.add(tom);

        Mockito.when(repository.findAll()).thenReturn(allContacts);
        Mockito.when(repository.findByNameAndSurname(john.getName(), john.getSurname()))
                .thenReturn(allContacts.subList(0, 1));
        Mockito.when(repository.save(john)).thenReturn(john);
        Mockito.when(repository.exists(1L)).thenReturn(true);
        Mockito.when(repository.findOne(1L)).thenReturn(tom);
        Mockito.when(repository.save(tom)).thenReturn(tom);
    }

    @Test
    public void listOfAllContacts() {
        Contact test1 = new Contact("John", "Doe", "111");
        Contact test2 = new Contact("Tom", "Ford", "222");

        ArrayList<Contact> contacts = (ArrayList<Contact>) contactService.findAllContacts();

        assertTrue(test1.equals(contacts.get(0)));
        assertTrue(test2.equals(contacts.get(1)));
    }

    @Test
    public void listOfContactsByNameAndSurname() {
        Contact test1 = new Contact("John", "Doe", "111");

        List<Contact> contacts = contactService
                .findContactByNameAndSurname(test1.getName(), test1.getSurname());

        assertTrue(test1.equals(contacts.get(0)));
    }

    @Test
    public void addContact() {
        Contact test1 = new Contact("John", "Doe", "111");

        assertTrue(contactService.addContact(test1).equals(test1));
    }

    @Test
    public void updateContact() {
        Contact test2 = new Contact("Tom", "Ford", "222");

        assertTrue(contactService.updateContact(1L, test2).equals(test2));
    }

}
