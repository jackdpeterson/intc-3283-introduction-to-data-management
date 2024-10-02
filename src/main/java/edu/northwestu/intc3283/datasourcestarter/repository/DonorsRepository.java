package edu.northwestu.intc3283.datasourcestarter.repository;

import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import edu.northwestu.intc3283.datasourcestarter.reports.TopDonationReportDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface DonorsRepository extends CrudRepository<Donor, Long> {

    List<Donor>  findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    List<Donor> findTop10ByOrderByCreatedAtDesc();

    @Query("""

            SELECT
                  d.first_name AS firstName,
                  d.last_name AS lastName,
                  d.email AS email,
                  YEAR(dn.committed_at) AS year,\s
                  MONTH(dn.committed_at) AS month,
                  SUM(dn.currency_amount) AS totalDonationAmount
              FROM
                  donors d
              JOIN
                  donations dn ON d.donor_id = dn.donor_id
              GROUP BY
                  d.donor_id,
                  YEAR(dn.committed_at),
                  MONTH(dn.committed_at)
              ORDER BY
                  YEAR(dn.committed_at) DESC,
                  MONTH(dn.committed_at) DESC,
                  SUM(dn.currency_amount) DESC;
""")
    List<TopDonationReportDTO> findTopDonations();
}
