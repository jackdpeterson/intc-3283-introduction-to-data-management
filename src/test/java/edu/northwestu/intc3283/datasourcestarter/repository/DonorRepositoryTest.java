package edu.northwestu.intc3283.datasourcestarter.repository;

import edu.northwestu.intc3283.datasourcestarter.config.DatabaseTestContextConfiguration;
import edu.northwestu.intc3283.datasourcestarter.config.jdbc.CustomJdbcConfiguration;
import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = DatabaseTestContextConfiguration.class)
@Import(CustomJdbcConfiguration.class)
class DonorRepositoryTest {

    @Autowired
    private DonorsRepository donorsRepository;

    @Test
    public void canSave() {
        Donor e = new Donor();
        e.setEmail("test@example.com");
        e.setAddress1("");
        e.setAddress2("");
        e.setCity("");
        e.setState("");
        e.setZipCode("");
        e.setFirstName("Generous");
        e.setLastName("Donor");
        this.donorsRepository.save(e);
        assertNotNull(e.getId());
    }


    @Test
    public void canFetchAndSave() {
        Donor e = new Donor();
        e.setEmail("test@example.com");
        e.setFirstName("Generous");
        e.setLastName("Donor");
        e.setAddress1("");
        e.setAddress2("");
        e.setCity("");
        e.setState("");
        e.setZipCode("");
        this.donorsRepository.save(e);

        this.donorsRepository.findById(e.getId()).ifPresent(entry -> {
            assertNotNull(entry.getId());
            assertNotNull(entry.getFirstName());
            assertNotNull(entry.getEmail());
        });
    }

    @Test
    public void throwsIfDuplicateEmailIsSupplied() {
        Donor e = new Donor();
        e.setEmail("test@example.com");
        e.setFirstName("Generous");
        e.setLastName("Donor");
        e.setAddress1("");
        e.setAddress2("");
        e.setCity("");
        e.setState("");
        e.setZipCode("");
        this.donorsRepository.save(e);

        Donor e2 = new Donor();
        e.setEmail("test@example.com");
        e.setFirstName("Generous 2");
        e.setLastName("Donor");
        e.setAddress1("");
        e.setAddress2("");
        e.setCity("");
        e.setState("");
        e.setZipCode("");

        assertThrows(DbActionExecutionException.class, () -> {
            this.donorsRepository.save(e2);
        });

    }
}