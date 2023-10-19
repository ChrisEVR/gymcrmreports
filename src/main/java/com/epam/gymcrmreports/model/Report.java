package com.epam.gymcrmreports.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document(collection = "reports")
public class Report {
    @Id
    private String id;
    @Field("Username")
    private String username;
    @Field("Trainer First Name")
    private String trainerFirstName;
    @Field("Trainer Last Name")
    private String trainerLastName;
    @Field("Trainer Status")
    private Boolean isActive;
    @Field("Years List")
    private Map<String, Map<String, Long>> trainingSummaryDuration;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainerFirstName() {
        return trainerFirstName;
    }

    public void setTrainerFirstName(String trainerFirstName) {
        this.trainerFirstName = trainerFirstName;
    }

    public String getTrainerLastName() {
        return trainerLastName;
    }

    public void setTrainerLastName(String trainerLastName) {
        this.trainerLastName = trainerLastName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Map<String, Map<String, Long>> getTrainingSummaryDuration() {
        return trainingSummaryDuration;
    }

    public void setTrainingSummaryDuration(Map<String, Map<String, Long>> trainingSummaryDuration) {
        this.trainingSummaryDuration = trainingSummaryDuration;
    }
}
