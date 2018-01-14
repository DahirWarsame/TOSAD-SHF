package nl.hu.tosad.domain;

public class RangeOperator extends RuleFunction {
    private int min;
    private int max;
    private String type;

    public RangeOperator(String id, int min, int max, String type) {
        super(id);
        this.min = min;
        this.max = max;
        this.type = type;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
