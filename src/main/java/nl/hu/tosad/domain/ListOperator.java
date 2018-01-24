package nl.hu.tosad.domain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListOperator extends RuleFunction {
    private String type;
    private String list;

    public ListOperator(String id,String code, String list, String type) {
        super(id,code);
        this.type = type;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public ArrayList<String> getList(){
        List<String> items;
        items =  Arrays.asList(this.list.split(","));
        return (ArrayList<String>) items;
    }
}
