package com.tigerit.exam;

public class Table {
    private String tableName;
    private int numOfColumn;
    private int numOfRow;
    private String columns[];
    private int rows[][];

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getNumOfColumn() {
        return numOfColumn;
    }

    public void setNumOfColumn(int noOfColumn) {
        this.numOfColumn = noOfColumn;
    }

    public int getNumOfRow() {
        return numOfRow;
    }

    public void setNumOfRow(int noOfRow) {
        this.numOfRow = noOfRow;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public int[][] getRows() {
        return rows;
    }

    public void setRows(int[][] rows) {
        this.rows = rows;
    }
    
}
