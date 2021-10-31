package com.example.bakingbella;

import java.util.List;

public class CategoriesData {
    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    String categories;
    public CategoriesData(String categories) {
        this.categories = categories;
    }


    String foodCategory, foodDesc, foodName, foodPrice, foodWeight;

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodWeight() {
        return foodWeight;
    }

    public void setFoodWeight(String foodWeight) {
        this.foodWeight = foodWeight;
    }



    String name;
    Integer image;
    Integer picture;
    public CategoriesData(){}

    public CategoriesData(String categories, String foodCategory, String foodDesc, String foodName, String foodPrice, String foodWeight) {
        this.categories = categories;
        this.foodCategory = foodCategory;
        this.foodDesc = foodDesc;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodWeight = foodWeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getPicture() {
        return picture;
    }

    public void setPicture(Integer picture) {
        this.picture = picture;
    }
}
