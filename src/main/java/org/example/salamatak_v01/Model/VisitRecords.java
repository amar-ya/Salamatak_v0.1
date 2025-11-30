package org.example.salamatak_v01.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitRecords
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "float")
    private Float height;
    @Column(columnDefinition = "float")
    private Float weight;
    @Column(columnDefinition = "float")
    private Float bmi;
    @Column(columnDefinition = "float")
    private Float blood_pressure;
    @Column(columnDefinition = "float")
    private Float heart_rate;
    @Column(columnDefinition = "float")
    private Float temperature;
    @Column(columnDefinition = "varchar(155)")
    private String allergies;
    @Column(columnDefinition = "int")
    private Integer age;
    @Column(columnDefinition = "varchar(255)")
    private String doctor_comments;
    @Column(columnDefinition = "int")
    private Integer time_id;
    @Column(columnDefinition = "int")
    private Integer doctor_id;
    @Column(columnDefinition = "int")
    private Integer patient_id;
}
