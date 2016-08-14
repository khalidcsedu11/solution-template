package com.tigerit.exam;

import java.util.ArrayList;

public class Query {
    private String firstTableName;
    private String secondTableName;
    private String firstTableAttribute;
    private String secondTableAttribute; 
    private Boolean selectAll;
    private ArrayList<String> columns;

    public String getFirstTableName() {
        return firstTableName;
    }

    public void setFirstTableName(String firstTableName) {
        this.firstTableName = firstTableName;
    }

    public String getSecondTableName() {
        return secondTableName;
    }

    public void setSecondTableName(String secondTableName) {
        this.secondTableName = secondTableName;
    }

    public String getFirstTableAttribute() {
        return firstTableAttribute;
    }

    public void setFirstTableAttribute(String firstTableAttribute) {
        this.firstTableAttribute = firstTableAttribute;
    }

    public String getSecondTableAttribute() {
        return secondTableAttribute;
    }

    public void setSecondTableAttribute(String secondTableAttribute) {
        this.secondTableAttribute = secondTableAttribute;
    }

    public Boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(Boolean selectAll) {
        this.selectAll = selectAll;
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }
}
