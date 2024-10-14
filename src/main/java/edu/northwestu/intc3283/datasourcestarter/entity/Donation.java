package edu.northwestu.intc3283.datasourcestarter.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant;

@Table("donations")
@Data
public class Donation {

    @Id
    private Long id;

    @CreatedDate
    private Instant createdAt;

    @Column("donor_id")
    private AggregateReference<Donor, Long> donor;

    private Integer amount;
}
