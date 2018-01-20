
package com.tm.halfway.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCategoriesResponse extends BaseResponse {

    @SerializedName("categories")
    private List<Category> Categories;

    public List<Category> getCategories() {
        return Categories;
    }

    public void setCategories(List<Category> categories) {
        Categories = categories;
    }

}
