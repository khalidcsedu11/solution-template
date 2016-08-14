package com.tigerit.exam;

import java.util.ArrayList;

public class Result {
    private ArrayList<String> column ;
    private ArrayList<ArrayList<Integer>> row;
    
    public ArrayList<String> getColumn() {
        return column;
    }

    public void setColumn(ArrayList<String> column) {
        this.column = column;
    }

    public ArrayList<ArrayList<Integer>> getRow() {
        return row;
    }

    public void setRow(ArrayList<ArrayList<Integer>> row) {
        this.row = row;
    }
}
