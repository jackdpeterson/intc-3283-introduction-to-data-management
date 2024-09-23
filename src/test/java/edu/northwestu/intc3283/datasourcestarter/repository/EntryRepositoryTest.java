package edu.northwestu.intc3283.datasourcestarter.repository;

import edu.northwestu.intc3283.datasourcestarter.config.DatabaseTestContextConfiguration;
import edu.northwestu.intc3283.datasourcestarter.config.jdbc.CustomJdbcConfiguration;
import edu.northwestu.intc3283.datasourcestarter.entity.Entry;
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
class EntryRepositoryTest {

    @Autowired
    private EntryRepository entryRepository;

    @Test
    public void canSave() {
        Entry e = new Entry();
        e.setEmail("test@example.com");
        e.setName("Test user");
        e.setAge(8);
        e.setAddress("Test 45600 Ne 67th Street 98074");
        e.setHuman(Boolean.TRUE);
        this.entryRepository.save(e);
        assertNotNull(e.getId());
    }


    @Test
    public void canFetchAndSave() {
        Entry e = new Entry();
        e.setName("Test user");
        e.setEmail("test1@example.com");
        e.setAge(8);
        e.setAddress("Test 45600 Ne 67th Street 98074");
        e.setHuman(Boolean.TRUE);
        this.entryRepository.save(e);

        this.entryRepository.findById(e.getId()).ifPresent(entry -> {
            assertNotNull(entry.getId());
            assertNotNull(entry.getName());
            assertNotNull(entry.getEmail());
            assertNotNull(entry.getAge());
            assertNotNull(entry.getAddress());
            assertNotNull(entry.getHuman());
        });
    }

    @Test
    public void throwsIfDuplicateEmailIsSupplied() {
        Entry e = new Entry();
        e.setName("Test user");
        e.setEmail("test1@example.com");
        e.setAge(8);
        e.setAddress("Test 45600 Ne 67th Street 98074");
        e.setHuman(Boolean.TRUE);
        this.entryRepository.save(e);

        Entry e2 = new Entry();
        e2.setName("Test user 2");
        e2.setEmail("test1@example.com");
        e.setAge(8);
        e.setAddress("Test 45600 Ne 67th Street 98074");
        e.setHuman(Boolean.TRUE);

        assertThrows(DbActionExecutionException.class, () -> {
            this.entryRepository.save(e2);
        });

    }
}