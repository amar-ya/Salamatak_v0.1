package org.example.salamatak_v01.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hospitals
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "hospital's name is required")
    @Column(columnDefinition = "varchar(40) not null")
    @Size(min = 4, max = 40, message = "name shouldn't be less than 4 characters or more than 40")
    private String name;
    @NotEmpty(message = "in what city this hospital located?")
    @Column(columnDefinition = "varchar(15) not null")
    @Size(min = 4, max = 15, message = "city shouldn't be less than 4 characters or more than 15")
    private String city;
    @Column(columnDefinition = "varchar(12) not null unique")
    @NotEmpty(message = "what is the phone number?")
    @Size(min = 8, max = 12, message = "first name shouldn't be less than 8 characters or more than 12")
    private String phone;
    @Column(columnDefinition = "boolean")
    private Boolean is_active = false;
}