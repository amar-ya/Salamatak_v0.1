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
public class Admin
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "the name is required")
    @Column(columnDefinition = "varchar(30) not null")
    @Size(min = 4,max=20, message = "the name shouldn't be less than 4 characters or more than 20")
    private String name;
}
