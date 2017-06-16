package springRS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springRS.domain.Contact;
import springRS.domain.ContactRepository;
import springRS.exceptions.ContactNotFoundException;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;

    public List<Contact> findAllContacts() {
        return repository.findAll();
    }

    public List<Contact> findContactByNameAndSurname(String name, String surname) {
        return repository.findByNameAndSurname(name, surname);
    }

    public Contact addContact(Contact contact) {
        return repository.save(contact);
    }

    public Contact updateContact(Long id, Contact updatedContact) {
        checkId(id);

        Contact contact = repository.findOne(id);
        contact.setName(updatedContact.getName());
        contact.setSurname(updatedContact.getSurname());
        contact.setPhone(updatedContact.getPhone());

        return repository.save(contact);
    }

    public void deleteContact(Long id) {
        checkId(id);
        repository.delete(id);
    }

    private void checkId(Long id) {
        if (!repository.exists(id)) {
            System.out.println("Contact with id = " + id + " was not found.");
            throw new ContactNotFoundException();
        }
    }
}
