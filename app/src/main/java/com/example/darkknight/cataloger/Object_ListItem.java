package com.example.darkknight.cataloger;

/**
 * Created by DARKKNIGHT on 2/12/2017.
 */

public class Object_ListItem {
    String listItem, quantity, total_cost, usersList, item_type, quantity_unit, price, item_position;

    public Object_ListItem(String listItem, String item_type, String quantity, String quantity_unit, String total_cost,
                           String usersList, String price, String item_position) {
        this.listItem = listItem;
        this.item_type = item_type;
        this.quantity = quantity;
        this.total_cost = total_cost;
        this.usersList = usersList;
        this.quantity_unit = quantity_unit;
        this.price = price;
        this.item_position = item_position;
    }

    public Object_ListItem(String listItem, String usersList) {
        this.listItem = listItem;
        this.usersList = usersList;
    }

    public String getListItem() {
        return listItem;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public String getUsersList() {
        return usersList;
    }

    public String getItem_type() {
        return item_type;
    }

    public String getQuantity_unit() {
        return quantity_unit;
    }

    public String getPrice() {
        return price;
    }

    public String getItem_position() {
        return item_position;
    }

    public void setItem_position(String item_position) {
        this.item_position = item_position;
    }

    public void setListItem(String listItem) {
        this.listItem = listItem;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public void setUsersList(String usersList) {
        this.usersList = usersList;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public void setQuantity_unit(String quantity_unit) {
        this.quantity_unit = quantity_unit;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
