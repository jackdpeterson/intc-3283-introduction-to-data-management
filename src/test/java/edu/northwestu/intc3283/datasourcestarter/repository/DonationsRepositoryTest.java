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
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Instant;

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
        this.donorsRepository.save(donor);
        
        // Now create a Donation linked to the donor
        Donation donation = new Donation();
        donation.setDonorId(donor.getId());
        donation.setCurrencyCode("USD");
        donation.setCurrencyAmount(100.00);
        donation.setDesignation("Charity Fund");
        donation.setCommittedAt(Instant.now());

        this.donationsRepository.save(donation);
        assertNotNull(donation.getDonationId());
    }

    @Test
    public void canFetchDonationById() {
        // Create and save a Donor
        Donor donor = new Donor();
        donor.setEmail("test@example.com");
        donor.setFirstName("Generous");
        donor.setLastName("Donor");
        this.donorsRepository.save(donor);

        // Create and save a Donation
        Donation donation = new Donation();
        donation.setDonorId(donor.getId());
        donation.setCurrencyCode("USD");
        donation.setCurrencyAmount(100.00);
        donation.setDesignation("Charity Fund");
        donation.setCommittedAt(Instant.now());

        this.donationsRepository.save(donation);

        // Fetch and validate the saved Donation
        this.donationsRepository.findById(donation.getDonationId()).ifPresent(d -> {
            assertNotNull(d.getDonationId());
            assertNotNull(d.getCurrencyCode());
            assertNotNull(d.getCurrencyAmount());
        });
    }

    @Test
    public void throwsIfDonorDoesNotExist() {
        // Create a Donation with a non-existing donor ID
        Donation donation = new Donation();
        donation.setDonorId(999999L);  // Invalid donor ID
        donation.setCurrencyCode("USD");
        donation.setCurrencyAmount(100.00);
        donation.setDesignation("Charity Fund");
        donation.setCommittedAt(Instant.now());

        assertThrows(DbActionExecutionException.class, () -> {
            this.donationsRepository.save(donation);
        });
    }
}