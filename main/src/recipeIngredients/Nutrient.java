package recipeIngredients;

public class Nutrient {

    private String title;
    private double amount;
    private String unit;
    private double percentOfDailyNeeds;

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public double getPercentOfDailyNeeds() {
        return percentOfDailyNeeds;
    }


    @Override
    public String toString() {
        return "Nutrients: " +
                "\ntitle: " + title +
                "\namount: " + amount +
                "\nunit: " + unit +
                "\npercent of daily needs: " + percentOfDailyNeeds;
    }
}
