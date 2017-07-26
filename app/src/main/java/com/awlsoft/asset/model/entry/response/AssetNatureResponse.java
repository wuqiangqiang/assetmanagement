package com.awlsoft.asset.model.entry.response;

import java.util.List;

/**
 * Created by yejingxian on 2017/5/17.
 */

public class AssetNatureResponse {

    private List<CategoryGbResponse> categoryGbList;
    private List<ModelResponse> modelList;
    private List<CategoryResponse> categoryList;
    private List<BrandResponse> brandList;

    public List<CategoryGbResponse> getCategoryGbList() {
        return categoryGbList;
    }

    public void setCategoryGbList(List<CategoryGbResponse> categoryGbList) {
        this.categoryGbList = categoryGbList;
    }

    public List<ModelResponse> getModelList() {
        return modelList;
    }

    public void setModelList(List<ModelResponse> modelList) {
        this.modelList = modelList;
    }

    public List<CategoryResponse> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryResponse> categoryList) {
        this.categoryList = categoryList;
    }

    public List<BrandResponse> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<BrandResponse> brandList) {
        this.brandList = brandList;
    }

}
