package recipeIngredients;


import java.util.ArrayList;
import java.util.List;

public class ExtendedIngredient {

        private int id;
        private String aisle;
        private String image;
        private String name;
        private double amount;
        private String unit;
        private String unitShort;
        private String unitLong;
        private String originalString;
        private List<String> metaInformation = new ArrayList<>();

        public int getId() {
            return id;
        }

        public String getAisle() {
            return aisle;
        }

        public String getImage() {
            return image;
        }

        public String getName() {
            return name;
        }

        public double getAmount() {
            return amount;
        }

        public String getUnit() {
            return unit;
        }

        public String getUnitShort() {
            return unitShort;
        }

        public String getUnitLong() {
            return unitLong;
        }


        public String getOriginalString() {
            return originalString;
        }


        public List<String> getMetaInformation() {
            return metaInformation;
        }

        @Override
        public String toString() {
            return "ExtendedIngredients:" +
                    "\nname='" + name + '\'' +
                    "\nid=" + id +
                    "\naisle='" + aisle + '\'' +
                    "\nimage='" + image + '\'' +
                    "\namount=" + amount +
                    "\noriginalString='" + originalString + '\'' +
                    "\nmetaInformation=" + metaInformation;
        }



}
