package edu.northwestu.intc3283.datasourcestarter.repository;

import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import edu.northwestu.intc3283.datasourcestarter.reports.TopDonationReportDTO;
import edu.northwestu.intc3283.datasourcestarter.reports.WeeklyDonationRow;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface DonorsRepository extends CrudRepository<Donor, Long> {

    List<Donor> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    List<Donor> findTop10ByOrderByCreatedAtDesc();

    @Query("""
            
                        SELECT
                              d.first_name AS first_name,
                              d.last_name AS last_name,
                              d.email AS email,
                              YEAR(dn.created_at) AS year,
                              MONTH(dn.created_at) AS month,
                              SUM(dn.amount) AS total_donation_amount
                          FROM
                              donors d
                          JOIN
                              donations dn ON d.id = dn.donor_id
                          GROUP BY
                              d.id,
                              YEAR(dn.created_at),
                              MONTH(dn.created_At)
                          ORDER BY
                              YEAR(dn.created_at) DESC,
                              MONTH(dn.created_at) DESC,
                              SUM(dn.amount) DESC
                        LIMIT :limit
            """)
    List<TopDonationReportDTO> findTopDonors(@Param("limit") Integer limit);


    @Query(
            """
                    SELECT
                        DATE_FORMAT(created_at, '%Y-%u') as donation_week,
                        SUM(amount) as                   total_donated
                    FROM donations
                    WHERE
                        created_at BETWEEN :started_at AND :ended_at
                    GROUP BY donation_week
                    ORDER BY donation_week ASC;
                    """
    )
    List<WeeklyDonationRow> weeklyDonationReport(@Param("started_at") LocalDate startedAt,
                                                 @Param("ended_at") LocalDate endedAt);
}
