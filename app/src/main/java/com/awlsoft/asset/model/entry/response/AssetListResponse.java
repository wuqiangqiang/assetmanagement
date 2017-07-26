package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by yejingxian on 2017/5/17.
 */
public class AssetListResponse {

    /**
     * pageNum : 1
     * pageSize : 50
     * size : 4
     * startRow : 1
     * endRow : 4
     * total : 4
     * pages : 1
     * list : [{"keeper_id":1,"batch_no":"1489050832181","model_id":1,"brand_id":1,"rfid_code":"111111","category_id":1,"durable_years":0,"price":1,"buy_date":"2017-03-09","name":"a","id":1,"state":1,"workarea_id":11,"create_date":1489206609000},{"keeper_id":0,"batch_no":"654123789","model_id":0,"brand_id":0,"rfid_code":"3232564154","category_id":0,"durable_years":2,"price":6.3,"buy_date":"2017-04-06","name":"123","id":24,"state":0,"workarea_id":0,"create_date":1491485868000},{"keeper_id":0,"batch_no":"1491484061172","model_id":0,"brand_id":0,"rfid_code":"888888800008888","category_id":0,"durable_years":1,"price":12,"buy_date":"2017-04-01","name":"教学电脑","id":30,"state":0,"workarea_id":0,"create_date":1491730918000},{"keeper_id":0,"batch_no":"1491484061172","model_id":0,"brand_id":0,"rfid_code":"777771111777777","category_id":0,"durable_years":1,"price":12.33,"buy_date":"2017-04-05","name":"共用资产","id":31,"state":0,"workarea_id":0,"create_date":1491730918000}]
     * prePage : 0
     * nextPage : 0
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 8
     * navigatepageNums : [1]
     * navigateFirstPage : 1
     * navigateLastPage : 1
     * firstPage : 1
     * lastPage : 1
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int navigateFirstPage;
    private int navigateLastPage;
    private int firstPage;
    private int lastPage;
    private List<AssetResponse> list;
    private List<Integer> navigatepageNums;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<AssetResponse> getList() {
        return list;
    }

    public void setList(List<AssetResponse> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }


}
