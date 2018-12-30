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
        return "Food: " +
                "\n id: " + id +
                "\n name: " + name +
                "\n image: " + image +
                "\n aisle: " + aisle +
                "\n amount: " + amount +
                "\n unit: " + unit;
    }

}
