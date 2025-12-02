package org.example.salamatak_v01.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "the hospital name shouldn't contain digits and symbols")
    private String name;
    @NotEmpty(message = "in what city this hospital located?")
    @Column(columnDefinition = "varchar(15) not null")
    @Size(min = 4, max = 15, message = "city shouldn't be less than 4 characters or more than 15")
    private String city;
    @Column(columnDefinition = "varchar(12) not null unique")
    @NotEmpty(message = "what is the phone number?")
    @Size(min = 8, max = 12, message = "first name shouldn't be less than 8 characters or more than 12")
    private String phone;
    @NotEmpty(message = "the certificate id is required")
    @Column(columnDefinition = "varchar(50) not null")
    private String certificate;
    @Column(columnDefinition = "varchar(8)")
    @Pattern(regexp = "^(pending|accept|reject)$", message = "the active stat can only be pending accept or reject")
    private String is_active;
}