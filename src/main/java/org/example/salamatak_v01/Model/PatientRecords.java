package org.example.salamatak_v01.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PatientRecords
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "float ")
    @Max(value = 3,message = "please input the height in meter")
    private Float height;
    @Column(columnDefinition = "float ")
    private Float weight;
    @Column(columnDefinition = "float ")
    private Float bmi;
    @Column(columnDefinition = "float ")
    private Float blood_pressure;
    @Column(columnDefinition = "float ")
    private Float heart_rate;
    @Column(columnDefinition = "float ")
    private Float temperature;
    @Column(columnDefinition = "varchar(155) ")
    private String allergies;
}
