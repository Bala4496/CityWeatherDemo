package ua.bala.citydemo.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Weather implements Serializable {

    private String description;
    private Double temperature;
    private Double feelsLike;
    private Double windSpeed;

}