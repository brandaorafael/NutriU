package brandao.rafael.NutriU.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "ingredient")
public class Ingredient {

    public Ingredient() {}

    public Ingredient(Integer id, String name, Integer calories, Integer carbohydrates, Integer proteins, Integer fat) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fat = fat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column
    @NotNull
    @Size(min = 1)
    private String name;

    @Column
    @NotNull
    private Integer calories;

    @Column
    @NotNull
    private Integer carbohydrates;

    @Column
    @NotNull
    private Integer proteins;

    @Column
    @NotNull
    private Integer fat;
}
