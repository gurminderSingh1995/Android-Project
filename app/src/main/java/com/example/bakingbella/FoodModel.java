package com.example.bakingbella;

public class FoodModel {
    String foodName;
    String foodWeight;
    String foodDesc;
    String foodCategory;
    String foodPrice;
    String foodImage;

    public FoodModel(){}
    public FoodModel(String foodName, String foodPrice, String foodWeight, String foodDesc, String foodCategory, String foodImage ) {
        this.foodName = foodName;
        this.foodWeight = foodWeight;
        this.foodDesc = foodDesc;
        this.foodCategory = foodCategory;
        this.foodPrice = foodPrice;
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodWeight() {
        return foodWeight;
    }

    public void setFoodWeight(String foodWeight) {
        this.foodWeight = foodWeight;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }
    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }
}
