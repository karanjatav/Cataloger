package com.example.darkknight.cataloger;

/**
 * Created by DARKKNIGHT on 2/12/2017.
 */

public class Object_Profile {
    String profile_name;
    String category;

    public Object_Profile(String profile_name, String category) {
        this.profile_name = profile_name;
        this.category = category;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public String getCategory() {
        return category;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
