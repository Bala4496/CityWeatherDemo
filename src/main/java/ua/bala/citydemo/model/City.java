package ua.bala.citydemo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cities")
@Getter
@Setter
@ToString
public class City extends BaseEntity{

    @Column(name = "name")
    private String name;

}
