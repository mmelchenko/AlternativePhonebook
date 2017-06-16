package springRS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springRS.controllers.ContactController;
import springRS.domain.Contact;
import springRS.services.ContactService;

import java.util.Arrays;
import java.util.List;
import springRS.util.Util;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService service;

    @Test
    public void getAllContacts() throws Exception {
        Contact contact = new Contact("Mike", "Tyson", "999");

        List<Contact> allContacts = Arrays.asList(contact);

        given(service.findAllContacts()).willReturn(allContacts);

        mockMvc.perform(get("/all")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(contact.getName())))
                .andExpect(jsonPath("$[0].surname", is(contact.getSurname())))
                .andExpect(jsonPath("$[0].phone", is(contact.getPhone())));
    }

    @Test
    public void createContact() throws Exception {
        Contact contact = new Contact("Arturo", "Gatti", "777");

        given(service.addContact(contact)).willReturn(contact);

        mockMvc.perform(post("/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(Util.asJson(contact)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(contact.getName())))
                .andExpect(jsonPath("$.surname", is(contact.getSurname())))
                .andExpect(jsonPath("$.phone", is(contact.getPhone())));

    }

    @Test
    public void updateContact() throws Exception {
        Contact contact = new Contact("Evander", "Holyfield", "555");

        given(service.addContact(contact)).willReturn(contact);
        given(service.updateContact(1L, contact)).willReturn(contact);

        mockMvc.perform(post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJson(contact)))
                .andExpect(status().isOk());

        contact.setPhone("5959");

        mockMvc.perform(put("/update")
        .param("id", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(Util.asJson(contact)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(contact.getName())))
                .andExpect(jsonPath("$.surname", is(contact.getSurname())))
                .andExpect(jsonPath("$.phone", is(contact.getPhone())));
    }

    @Test
    public void deleteContact() throws Exception {
        Contact contact = new Contact("David", "Tua", "111");

        given(service.addContact(contact)).willReturn(contact);

        mockMvc.perform(post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJson(contact)))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/delete")
        .param("id", "1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
