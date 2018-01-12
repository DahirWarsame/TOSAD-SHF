package nl.hu.tosad.domain;

public class RangeOperator extends Operator{
private String min;
private String max;
private String type;

public RangeOperator(String min, String max, String type) {
	super();
	this.min = min;
	this.max = max;
	this.type = type;
}

public String getMin() {
	return min;
}

public void setMin(String min) {
	this.min = min;
}

public String getMax() {
	return max;
}

public void setMax(String max) {
	this.max = max;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

}
