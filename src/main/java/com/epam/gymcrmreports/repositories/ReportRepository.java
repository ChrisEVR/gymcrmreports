package com.epam.gymcrmreports.repositories;

import com.epam.gymcrmreports.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    Report findByUsername(String username);
}
