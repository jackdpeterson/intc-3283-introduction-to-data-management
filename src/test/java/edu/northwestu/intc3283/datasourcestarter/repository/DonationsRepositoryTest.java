package edu.northwestu.intc3283.datasourcestarter.repository;

import edu.northwestu.intc3283.datasourcestarter.config.DatabaseTestContextConfiguration;
import edu.northwestu.intc3283.datasourcestarter.config.jdbc.CustomJdbcConfiguration;
import edu.northwestu.intc3283.datasourcestarter.entity.Donation;
import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
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
class DonationsRepositoryTest {

    @Autowired
    private DonationsRepository donationsRepository;

    @Autowired
    private DonorsRepository donorsRepository;

    @Test
    public void canSaveDonation() {
        // First, create and save a Donor
        Donor donor = new Donor();
        donor.setEmail("test@example.com");
        donor.setFirstName("Generous");
        donor.setLastName("Donor");
        donor.setAddress1("");
        donor.setAddress2("");
        donor.setCity("");
        donor.setZipCode("");
        donor.setState("");
        this.donorsRepository.save(donor);
        
        // Now create a Donation linked to the donor
        Donation donation = new Donation();
        donation.setDonor(AggregateReference.to(donor.getId()));
        donation.setAmount(100);

        this.donationsRepository.save(donation);
        assertNotNull(donation.getId());
    }

    @Test
    public void canFetchDonationById() {
        // Create and save a Donor
        Donor donor = new Donor();
        donor.setEmail("test@example.com");
        donor.setFirstName("Generous");
        donor.setLastName("Donor");
        donor.setAddress2("");
        donor.setAddress1("");
        donor.setCity("");
        donor.setState("");
        donor.setZipCode("");

        this.donorsRepository.save(donor);

        // Create and save a Donation
        Donation donation = new Donation();
        donation.setDonor(AggregateReference.to(donor.getId()));

        donation.setAmount(100);

        this.donationsRepository.save(donation);

        // Fetch and validate the saved Donation
        this.donationsRepository.findById(donation.getId()).ifPresent(d -> {
            assertNotNull(d.getId());
            assertNotNull(d.getAmount());
        });
    }

    @Test
    public void throwsIfDonorDoesNotExist() {
        // Create a Donation with a non-existing donor ID
        Donation donation = new Donation();
        donation.setDonor(AggregateReference.to(999999L));  // Invalid donor ID
        donation.setAmount(100);

        assertThrows(DbActionExecutionException.class, () -> {
            this.donationsRepository.save(donation);
        });
    }
}