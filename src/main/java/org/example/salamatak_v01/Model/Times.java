package org.example.salamatak_v01.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Times
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "time is required")
    private Time time;
    @Column(columnDefinition = "varchar(300)")
    private String reason;
    @Column(columnDefinition = "int")
    private Integer doctor_id;
    @Column(columnDefinition = "int")
    private Integer patient_id;
}
