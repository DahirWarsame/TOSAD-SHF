package nl.hu.tosad.domain;

public class RuleHead {
    private String code;
    private String desc;
    private RuleType type;
    private String table;
    private String attribute;


    public RuleHead(String code, String desc, RuleType type, String table, String attribute) {
        super();
        this.code = code;
        this.desc = desc;
        this.type = type;
        this.table = table;
        this.attribute = attribute;
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

    public RuleType getType() {
        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
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


}
