package com.tigerit.exam;

import static com.tigerit.exam.IO.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
*/

public class Solution implements Runnable {
    
    private Table[] tableInfo(){
        int totalTable = 0;
        String tableName;
        int numOfRows = 0;
        int numOfColumns = 0;
        String columns[];
        int[][] rows= null;
        ArrayList<String> tokenizedString;        
        totalTable = readLineAsInteger(); 
        Table[] table = new Table[totalTable];

        for(int i=0; i<totalTable ; i++){
            table[i] = new Table();
        }
        
        for(int i=0 ; i<totalTable ; i++){
            tableName = readLine();
            tokenizedString = new ArrayList<String>();
            tokenizedString = (ArrayList<String>) tokenizeString(readLine()," ");
            numOfColumns = Integer.parseInt(tokenizedString.get(0));
            numOfRows = Integer.parseInt(tokenizedString.get(1));
            columns = new String[numOfColumns];
            rows = new int[numOfRows][numOfColumns];
            tokenizedString = (ArrayList<String>) tokenizeString(readLine()," ").clone();            
            
            for(int k=0; k<numOfColumns ; k++){
                columns[k] = tokenizedString.get(k);
            }  
            
            for(int k=0 ; k<numOfRows ; k++){    
                tokenizedString = (ArrayList<String>) tokenizeString(readLine()," ").clone();
                for(int j=0 ; j<numOfColumns ; j++){
                    rows[k][j] = Integer.parseInt(tokenizedString.get(j));   
                }
            }
            table[i].setTableName(tableName);
            table[i].setNumOfColumn(numOfColumns);
            table[i].setNumOfRow(numOfRows);
            table[i].setColumns(columns);
            table[i].setRows(rows);       
        }        
        return table;
    }
    
    private Query queryInfo(){        
        ArrayList<String> query;
        String firstTableName = null;
        String secondTableName = null;
        String firstTableAttribute = null;
        String secondTableAttribute = null; 
        Boolean selectAll = null;
        ArrayList<String> columns = new ArrayList<String>();        
        Query q = new Query();
        
        for(int i=0 ; i<4 ; i++){           
            query = (ArrayList<String>) tokenizeString(readLine()," .=,").clone();
            if(query.size() == 0){
                i--;
                continue;
            }
            if(query.get(0).equalsIgnoreCase("SELECT")){
                if(query.get(1).equals("*")){
                    selectAll = true;
                }
                else{
                    selectAll = false;
                    for(int j=2; j<query.size(); j+=2){
                        columns.add(query.get(j));
                    }
                }
            }
            else if(query.get(0).equalsIgnoreCase("FROM")){
                firstTableName = query.get(1);
            }
            else if(query.get(0).equalsIgnoreCase("JOIN")){
                secondTableName = query.get(1);                 
            }
            else {
                firstTableAttribute = query.get(2);
                secondTableAttribute = query.get(4);
            }
        }        
        q.setFirstTableName(firstTableName);
        q.setSecondTableName(secondTableName);
        q.setFirstTableAttribute(firstTableAttribute);
        q.setSecondTableAttribute(secondTableAttribute);
        q.setColumns(columns) ;
        q.setSelectAll(selectAll);                 
        return q;        
    }
    
    private ArrayList<String> tokenizeString(String str, String del) {
        int i=0;
        ArrayList<String> tokens=new ArrayList<String>();
        StringTokenizer multiTokenizer = new StringTokenizer(str, del);        
        
        while (multiTokenizer.hasMoreTokens()){
            tokens.add(multiTokenizer.nextToken());
        }    
        return tokens;
    }
    
    private Search searchTable(Query q, Table[] table) {
        String[] columns;
        Search s = new Search();
        columns = table[s.getFirstTableNameIndex()].getColumns();
        columns = table[s.getSecondTableNameIndex()].getColumns();

        // searching table and column indexes specified in the query 
        for(int k=0; k<table.length ; k++){
            if(table[k].getTableName().equals(q.getFirstTableName())){
                s.setFirstTableNameIndex(k); 
            }
            else if(table[k].getTableName().equals(q.getSecondTableName())){
                s.setSecondTableNameIndex(k); 
            }
        }
        
        for(int i=0; i<columns.length ; i++){
            if(columns[i].equals(q.getFirstTableAttribute())){
                s.setFirstTableAttributeIndex(i);
                break;
            }
        }

        for(int i=0; i<columns.length ; i++){
            if(columns[i].equals(q.getSecondTableAttribute())){
                s.setSecondTableAttributeIndex(i);
                break;
            }
        }
        return s;
    }

    private Result result(Table[] table, Query q, Search s) {
        int item;
        Result res = new Result();
        ArrayList<ArrayList<Integer>> myArray = new ArrayList<ArrayList<Integer>>();        
        ArrayList<String> columns = new ArrayList<String>();
        // first table information        
        Table myTable = table[s.getFirstTableNameIndex()];
        int numOfRows = myTable.getNumOfRow();
        int[][] myRows = myTable.getRows();
        String[] cols = myTable.getColumns();
        // second table information
        Table myTable2 = table[s.getSecondTableNameIndex()];
        int numOfRows2 = myTable2.getNumOfRow();
        int[][] myRows2 = myTable2.getRows();
        String[] cols2 = myTable2.getColumns();
        
        if(q.isSelectAll()){
            for(int p=0; p<cols.length ; p++){
                columns.add(cols[p]);
            }
            for(int p=0; p<cols2.length ; p++){
                columns.add(cols2[p]);
            }  
            q.setColumns(columns); 
        }
        
        for(int i=0 ; i<numOfRows ; i++){
            item = myRows[i][s.getFirstTableAttributeIndex()];
            
            for(int j=0 ; j<numOfRows2 ; j++){
                if(myRows2[j][s.getSecondTableAttributeIndex()] == item){
                    myArray.add(addRows(myRows,myRows2,i,j,myTable,myTable2,q));
                }
            }
        }
        res.setColumn(q.getColumns());
        res.setRow(myArray); 
        return res;
    }
    
    private ArrayList<Integer> addRows(int[][] firstRow, int[][] secondRow, int a, int b, Table myTable, Table myTable2, Query q) {
        ArrayList<Integer> rows = new ArrayList<Integer>();
        ArrayList<String> columns = q.getColumns();
        String[] myColumn = myTable.getColumns();
        String[] myColumn2 = myTable2.getColumns();

        if(q.isSelectAll()){
            for(int i=0 ; i<firstRow[a].length ; i++){
                rows.add(firstRow[a][i]);
            }            
            for(int i=0 ; i<secondRow[b].length ; i++){
                rows.add(secondRow[b][i]); 
            }
        }
        else{
            for(int j=0 ; j<columns.size() ; j++){
                
                int ret = compare(myColumn,columns.get(j)); 
                if(ret >= 0){
                    rows.add(firstRow[a][ret]);
                }
                else{
                     ret = compare(myColumn2,columns.get(j));
                     rows.add(secondRow[b][ret]);                     
                }
            }
        }
        return rows;
    }

    private int compare(String[] myColumn, String columns) {
        int flag = -1;

        for(int k=0; k<myColumn.length ; k++){
            if(myColumn[k].equals(columns)){
                flag = k;
                break;
            }                    
        }
        return flag;
    }
    
    private Result sortRows(Result res){
        ArrayList<Integer> temp= new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> joinRows = res.getRow();
        boolean swap = false;
        
        for(int i = 0; i < joinRows.size()-1 ; i++)
            {
                swap = false;
                
                for(int j = 1; j < (joinRows.size() -i); j++)
                {                    
                    for(int p=0 ; p<joinRows.get(i).size() ; p++){

                        if( joinRows.get(j-1).get(p) > joinRows.get(j).get(p))
                        {
                            temp.clear();
                            for(int k=0; k<joinRows.get(j-1).size(); k++){
                                temp.add(joinRows.get(j-1).get(k)); 
                            }
                            joinRows.get(j-1).clear();
                            for(int k=0; k<joinRows.get(j).size(); k++){
                                joinRows.get(j-1).add(joinRows.get(j).get(k));
                            }
                            joinRows.get(j).clear();                        
                            for(int k=0; k<temp.size(); k++){
                                joinRows.get(j).add(temp.get(k));
                            }
                        }
                        swap =true;
                        break;
                    }
                }
                if(swap == false){
                    break;
                }
            }  
        res.setRow(joinRows);
        return res;
    }

    private void display(Result res) {
        ArrayList<String> col = res.getColumn();
        ArrayList<ArrayList<Integer>> row = res.getRow();
 
        for(int i=0; i<col.size() ; i++){
            System.out.print(col.get(i));
            if( i+1 < col.size()){
                System.out.print(" ");
            }
        }
        
        System.out.println();
        
        for(int i=0 ; i<row.size(); i++){
            for(int j=0 ; j<row.get(i).size(); j++){
                System.out.print(row.get(i).get(j));
                if( j+1 < row.get(i).size()){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        
        System.out.println();
    }

    @Override
    public void run() {
        Integer testCase = readLineAsInteger();
        Integer numOfQuery;
                
        for(int i=1 ; i<= testCase ; i++){
            Table[] table = tableInfo();            // saving information of database tables
            numOfQuery = readLineAsInteger();
            if(numOfQuery>=1){
                printLine("Test: "+i);            
            }            
            for(int j=0; j<numOfQuery ; j++){                
                Query q = queryInfo();              // saving query information                                
                Search s = searchTable(q,table);    // searching required parameters in tables
                Result res = result(table,q,s);     // finding result of the query
                Result res2 = sortRows(res);        //sorting the rows lexicographically
                display(res2);                      // display query result 
                readLine();
            }
        }
    }
}