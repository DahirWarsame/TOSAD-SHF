package nl.hu.tosad.domain;

public class CompareOperator extends RuleFunction {
    private String type;
    private String value;

    public CompareOperator(String id, String code, String value, String type) {
        super(id,code);
        this.type = defineOperator(type);
        this.value = value;
    }
    private String defineOperator(String operatorType) {
        switch (operatorType){
            case "NE" :
                operatorType =  "=!";
                break;
            case "E" :
                operatorType = "=!";
                break;
            case "LT" :
                operatorType = "<";
                break;
            case "GT" :
                operatorType = ">";
                break;
            case "LOET" :
                operatorType = "<=";
                break;
            case "GOET" :
                operatorType = ">=";
                break;
        }
        return operatorType;
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