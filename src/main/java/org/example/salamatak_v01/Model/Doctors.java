package org.example.salamatak_v01.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctors
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "first name is required")
    @Column(columnDefinition = "varchar(20) not null")
    @Size(min = 4, max = 20, message = "first name shouldn't be less than 4 characters or more than 20")
    private String first_name;
    @NotEmpty(message = "last name is required")
    @Column(columnDefinition = "varchar(20) not null")
    @Size(min = 4, max = 20, message = "last name shouldn't be less than 4 characters or more than 20")
    private String last_name;
    @NotEmpty(message = "speciality is required")
    @Column(columnDefinition = "varchar(30) not null")
    @Size(min = 4, max = 30, message = "first name shouldn't be less than 4 characters or more than 30")
    private String speciality;
    @NotEmpty(message = "phone number is required")
    @Pattern(regexp = "^[0-9]+$",message = "enter a valid phone number example: 591334444")
    @Size(min = 9, max = 9,message = "enter a valid phone number example: 591334444 with a length of 9 numbers only")
    @Column(columnDefinition = "varchar(9) not null")
    private String phone;
    @NotNull(message = "what is the hospital you work for?")
    @Column(columnDefinition = "int not null")
    private Integer hospital_id;
}