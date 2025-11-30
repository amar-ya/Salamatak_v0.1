package org.example.salamatak_v01.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patients
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "National id is required")
    @Column(columnDefinition = "varchar(10) not null unique")
    @Pattern(regexp = "^[0-9]+$", message = "national id can contain only numbers")
    @Size(min = 10, max = 10, message = "enter a valid national ID example: 1000111222")
    private String national_id;
    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password should contain only latter's and numbers")
    @Size(min=6 , max = 20, message = "The password should be between 6 and 20 characters")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;
    @NotEmpty(message = "First name is required")
    @Column(columnDefinition = "varchar(20) not null")
    @Size(min = 4, max = 20, message = "First name shouldn't be less than 4 characters or more than 20")
    @Pattern(regexp = "^[a-zA-Z]+$",message = "Name shouldn't contain numbers or symbols")
    private String first_name;
    @NotEmpty(message = "Last name is required")
    @Column(columnDefinition = "varchar(20) not null")
    @Size(min = 4, max = 20, message = "Last name shouldn't be less than 4 characters or more than 20")
    @Pattern(regexp = "^[a-zA-Z]+$",message = "Name shouldn't contain numbers or symbols")
    private String last_name;
    @NotNull(message = "Date of birth is required")
    @Column(columnDefinition = "DATETIME not null")
    private Date birth_date;
    @NotEmpty(message = "are you male or female?")
    @Pattern(regexp = "^(male|female)$", message = "gender can only be male or female")
    @Column(columnDefinition = "varchar(6) not null")
    private String gender;
    @Column(columnDefinition = "varchar(3) not null")
    @Pattern(regexp = "(?i)^(A|B|AB|O)[+-]$",message = "this is not correct blood type")
    private String blood_type;
    @Column(columnDefinition = "varchar(9) not null")
    @NotEmpty(message = "phone number is required")
    @Pattern(regexp = "^[0-9]+$", message = "phone number can only have numbers")
    @Size(min = 9, max = 9,message = "enter valid phone number example: 591334444")
    private String phone;
}
