package nl.hu.tosad.domain;

public class CompareOperator extends RuleFunction {
    private String type;
    private String value;

    public CompareOperator(String id, String code, String value, String type) {
        super(id,code);
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}