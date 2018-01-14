package nl.hu.tosad.domain;

public class RuleType {
    private String id;
    private String code;
    private String desc;
    private String type;
    private String othertable;
    private String otherattribute;

    public RuleType(String id, String code, String desc, String type, String othertable, String otherattribute) {
        this.id = id;
        this.code = code;
        this.desc = desc;
        this.type = type;
        this.othertable = othertable;
        this.otherattribute = otherattribute;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
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
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOthertable() {
        return othertable;
    }

    public void setOthertable(String othertable) {
        this.othertable = othertable;
    }

    public String getOtherattribute() {
        return otherattribute;
    }

    public void setOtherattribute(String otherattribute) {
        this.otherattribute = otherattribute;
    }

    @Override
    public String toString() {
        return "RuleType{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", othertable='" + othertable + '\'' +
                ", otherattribute='" + otherattribute + '\'' +
                '}';
    }
}


