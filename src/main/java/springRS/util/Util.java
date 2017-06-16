package springRS.util;

import springRS.domain.Contact;
import springRS.exceptions.BadRequestException;

public class Util {

    private boolean isContactValid(Contact contact) {
        if (contact.getName().isEmpty()||
                contact.getSurname().isEmpty()||
                contact.getPhone().isEmpty()) {
            return false;
        }
        return true;
    }

    public void validateContact(Contact contact) {
        if (!isContactValid(contact)) {
            throw new BadRequestException("Bad request. Please, verify populated data.");
        }
    }

    public static String asJson(Contact contact) {
        return "{\"name\":\""+ contact.getName() +"\", " +
                "\"surname\":\""+ contact.getSurname() +"\", " +
                "\"phone\":\""+ contact.getPhone() +"\"}";
    }
}
