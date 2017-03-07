package com.example.darkknight.cataloger;

/**
 * Created by DARKKNIGHT on 2/12/2017.
 */

public class Object_Items {
    private String itemName;
    private String itemQuantityUnit;
    private String itemType;
    private String itemPrice;
    private String itemGroupName;
    private String profile_name;
    private String isAdded;

    public Object_Items(String itemName, String itemQuantityUnit, String itemType, String itemPrice, String itemGroupName, String profile_name, String isAdded) {
        this.itemName = itemName;
        this.itemQuantityUnit = itemQuantityUnit;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.itemGroupName = itemGroupName;
        this.profile_name = profile_name;
        this.isAdded = isAdded;
    }

    public String getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(String isAdded) {
        this.isAdded = isAdded;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemQuantityUnit() {
        return itemQuantityUnit;
    }

    public String getItemType() {
        return itemType;
    }

    public int getItemPrice() {

        return Integer.parseInt(itemPrice);
    }

    public String getItemGroupName() {
        return itemGroupName;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemQuantityUnit(String itemQuantityUnit) {
        this.itemQuantityUnit = itemQuantityUnit;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemGroupName(String itemGroupName) {
        this.itemGroupName = itemGroupName;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }
}