package nl.hu.tosad.domain;

public class RuleType {
private String name;
private String code;
private String desc;
private String type;
public RuleType(String name, String code, String desc, String type) {
	super();
	this.name = name;
	this.code = code;
	this.desc = desc;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public String getType() {
	return desc;
}
public void setType(String type) {
	this.type = type;
}


}
