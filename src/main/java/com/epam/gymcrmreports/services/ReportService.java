package com.epam.gymcrmreports.services;

import com.epam.gymcrmreports.model.Report;
import com.epam.gymcrmreports.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class ReportService {
    private static final Logger logger = Logger.getLogger(ReportService.class.getName());

    @Value("${spring.activemq.broker-url}")
    public String brokerUrl;

    @Autowired
    ReportRepository reportRepository;

    @JmsListener(destination = "reports")
    public void updateWorkload(Map<String, Object> workload){
        String username = (String) workload.get("username");
        Report report = reportRepository.findByUsername(username) == null ?
                new Report() : reportRepository.findByUsername(username);
        Long trainingDuration = (Long) workload.get("trainingDuration");
        String[] date = workload.get("trainingDate").toString().split("-");

        Map<String, Map<String, Long>> summaryTrainingDuration = updateDuration(
                report,
                date,
                trainingDuration,
                (Boolean) workload.get("add")
        );

        if(report != null){
            report.setId(report.getId());
        }
        report.setUsername((String) workload.get("username"));
        report.setTrainerFirstName((String) workload.get("firstName"));
        report.setTrainerLastName((String) workload.get("lastName"));
        report.setActive((Boolean) workload.get("isActive"));
        report.setTrainingSummaryDuration(summaryTrainingDuration);

        reportRepository.save(report);
    }

    public Map<String, Map<String, Long>> updateDuration(
            Report report,
            String[] date,
            Long trainingDuration,
            Boolean add
    ){
        logger.info("date:" + Arrays.toString(date));
        String year = date[0];
        String month = date[1];
        Map<String, Map<String, Long>> trainingSummary = report != null && report.getTrainingSummaryDuration() != null ?
                report.getTrainingSummaryDuration() : new HashMap<>();

        if(!add){
            trainingDuration *= -1;
        }

        if(trainingSummary.containsKey(year)){
            Map<String, Long> yearSummary = trainingSummary.get(year);
            Long duration = yearSummary.containsKey(month) ?
                    yearSummary.get(month) + trainingDuration : trainingDuration;

            yearSummary.put(month, duration);
            trainingSummary.put(year, yearSummary);
        } else {
            Map<String, Long> monthSummary = new HashMap<>();
            monthSummary.put(month, trainingDuration);
            trainingSummary.put(year, monthSummary);
        }

        return trainingSummary;
    }
}
