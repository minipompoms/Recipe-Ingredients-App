package recipeIngredients;

import java.awt.*;

public class Ingredient {

    private int id;
    private String name;
    private String aisle;
    private Image image;
    private int amount;
    private String unit;

    @Override
    public String toString(){
        return "Food{" +
                "id=" + id +
                ", name=" + name +
                ", image='" + image + '\'' +
                ", aisle=" + aisle +
                ", amount=" + amount +
                ", unit=" + unit +
                '}';
    }

}
