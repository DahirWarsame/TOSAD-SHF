package nl.hu.tosad.domain;

import java.util.ArrayList;

public class ListOperator extends Operator{
private String type;
private ArrayList<String> list;

public ListOperator(ArrayList<String>list, String type) {
	super();
	this.type = type;
	this.list=list;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

}
