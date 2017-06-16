package springRS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import springRS.domain.Contact;
import springRS.services.ContactService;
import springRS.util.Util;

import java.util.List;

@RestController
@RequestMapping("/")
public class ContactController {

    @Autowired
    ContactService service;

    private Util util = new Util();

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public Contact create(@RequestBody Contact contact) {
        util.validateContact(contact);

        List<Contact> contactsInDB = service.findContactByNameAndSurname(contact.getName(), contact.getSurname());
        if (contactsInDB.isEmpty()) {
            return service.addContact(contact);
        } else {
            Long id = contactsInDB.get(contactsInDB.indexOf(contact)).getId();
            return service.updateContact(id, contact);
        }
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public Contact update(@Param("id")String id, @RequestBody Contact contact) {
        util.validateContact(contact);

        return service.updateContact(Long.parseLong(id), contact);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public void delete(Long id) {
        service.deleteContact(id);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Contact> allContacts() {
        return service.findAllContacts();
    }

    @RequestMapping(value = {"{path:(?!webjars|static).*$}",
            "{path:(?!webjars|static).*$}/**"}, headers = "Accept=text/html")
    public String unknown() {
        return "Resource is unknown.";
    }
}
