package nl.hu.tosad.domain;

public class Rule {
    private String code;
    private RuleType type;
    private RuleFunction func;
    private String category;
    private String table;
    private String attribute;


    public Rule(String code, RuleType type, RuleFunction func, String category, String table, String attribute) {
        super();
        this.code = code;
        this.type = type;
        this.func = func;
        this.category = category;
        this.table = table;
        this.attribute = attribute;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RuleType getType() {
        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
    }

    public RuleFunction getFunction() {
        return func;
    }

    public void setFunction(RuleFunction type) {
        this.func = type;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "code='" + code + '\'' +
                ", type=" + type.toString() +
                ", func=" + func.toString() +
                ", category='" + category + '\'' +
                ", table='" + table + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }
}
