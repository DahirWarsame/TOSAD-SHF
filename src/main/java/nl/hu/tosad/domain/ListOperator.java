package nl.hu.tosad.domain;

import java.util.ArrayList;

public class ListOperator extends RuleFunction{
private String type;
private String list;

public ListOperator(String id, String list, String type) {
	super(id);
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
