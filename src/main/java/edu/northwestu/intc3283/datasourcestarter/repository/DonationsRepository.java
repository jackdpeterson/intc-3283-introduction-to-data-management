package edu.northwestu.intc3283.datasourcestarter.repository;

import edu.northwestu.intc3283.datasourcestarter.entity.Donation;
import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import edu.northwestu.intc3283.datasourcestarter.reports.TopDonationReportDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface DonationsRepository extends CrudRepository<Donation, Long> {

        List<Donation> findByDonorId(Long donorId);

        List<Donation> findByCurrencyCode(String currencyCode);

        List<Donation> findByCurrencyAmountGreaterThan(Double currencyAmount);

        List<Donation> findByCurrencyAmountLessThan(Double currencyAmount);

        List<Donation> findByCurrencyAmountBetween(Double min, Double max);

        List<Donation> findByDesignation(String designation);
}
