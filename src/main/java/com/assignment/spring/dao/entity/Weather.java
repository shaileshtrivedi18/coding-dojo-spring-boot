package com.assignment.spring.dao.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Data
@Accessors(chain = true)
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String city;

    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;

    private String country;
    private Double temperature;
}