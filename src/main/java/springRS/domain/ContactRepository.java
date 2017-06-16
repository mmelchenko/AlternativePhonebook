package springRS.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("select c from contacts c where c.name = :name and c.surname = :surname")
    List<Contact> findByNameAndSurname(@Param("name") String name, @Param("surname") String surname);
}
