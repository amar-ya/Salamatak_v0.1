package org.example.salamatak_v01.AiClient;

import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Date;


public interface AiClient {


    String getHealthAdvice(Float height, Float weight, Float bmi, Date birthdate,String gender,String name);

    String getFeelingAdvice(String userDescription);

    String prepareQuestionsToAsksDoctor(Time time, String Speciality, String feeling);
}
