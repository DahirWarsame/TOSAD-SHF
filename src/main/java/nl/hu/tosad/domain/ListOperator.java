package nl.hu.tosad.domain;

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

}
