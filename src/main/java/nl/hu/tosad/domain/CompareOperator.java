package nl.hu.tosad.domain;

public class CompareOperator extends RuleFunction {
    private String type;
    private String value1;

    public CompareOperator(String id, String value1, String type) {
        super(id);
        this.type = type;
        this.value1 = value1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }
}