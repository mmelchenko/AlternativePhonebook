package springRS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springRS.domain.Contact;
import springRS.domain.ContactRepository;
import springRS.util.Util;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactRepository repository;

    @Test
    public void createContactTest() throws Exception {
        Contact contact = new Contact("Crag", "Hak", "666");

        mockMvc.perform(post("/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(Util.asJson(contact)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(contact.getName())))
                .andExpect(jsonPath("$.surname", is(contact.getSurname())))
                .andExpect(jsonPath("$.phone", is(contact.getPhone())));

        List<Contact> contacts = repository.findAll();

        assertTrue(contacts.contains(contact));
    }

    @Test
    public void updateContactTest() throws Exception {
        Contact contact = repository.findByNameAndSurname("Jack", "Bauer").get(0);

        String newPhone = "777";
        Long id = contact.getId();
        contact.setPhone(newPhone);

        mockMvc.perform(put("/update")
        .param("id", id.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .content(Util.asJson(contact)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(contact.getName())))
                .andExpect(jsonPath("$.surname", is(contact.getSurname())))
                .andExpect(jsonPath("$.phone", is(contact.getPhone())));

        String phoneFromDB = repository.findOne(id).getPhone();

        assertTrue(newPhone.equals(phoneFromDB));
    }

    @Test
    public void deleteContactTest() throws Exception {
        Contact contact = repository.findByNameAndSurname("Jack", "Bauer").get(0);

        Long id = contact.getId();

        mockMvc.perform(delete("/delete")
                .param("id", id.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<Contact> contacts = repository.findAll();

        assertTrue(!contacts.contains(contact));
    }
}
