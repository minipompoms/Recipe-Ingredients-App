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

        public void setId(int id) {
            this.id = id;
        }

        public String getAisle() {
            return aisle;
        }

        public void setAisle(String aisle) {
            this.aisle = aisle;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUnitShort() {
            return unitShort;
        }

        public void setUnitShort(String unitShort) {
            this.unitShort = unitShort;
        }

        public String getUnitLong() {
            return unitLong;
        }

        public void setUnitLong(String unitLong) {
            this.unitLong = unitLong;
        }

        public String getOriginalString() {
            return originalString;
        }

        public void setOriginalString(String originalString) {
            this.originalString = originalString;
        }

        public List<String> getMetaInformation() {
            return metaInformation;
        }

        public void setMetaInformation(List<String> metaInformation) {
            this.metaInformation = metaInformation;
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
