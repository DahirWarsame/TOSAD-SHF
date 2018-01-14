package nl.hu.tosad.webservices;

import nl.hu.tosad.domain.Other;
import nl.hu.tosad.domain.RangeOperator;
import nl.hu.tosad.domain.Rule;

import java.util.List;

public class Controller {
    public static void main(String[] args) {

        RuleDAO dao = new RuleDAO();

        //Get all rules from Tool database
        List<Rule> rules = dao.getAllRules();
        generatePLSQL(rules);





    }

    private static void generatePLSQL(List<Rule> rules) {

        //TODO call SQL generator

        for (Rule rule : rules) {
            String type = rule.getFunction().getCode();
            if (type.equals("RNG")) {
                RangeOperator ro = (RangeOperator) rule.getFunction();
                //TODO send data to SQL generator
                System.out.println(rule.getType().getDesc());
                System.out.println(rule.getTable());
                System.out.println(type);
                System.out.println(ro.getMax());
                System.out.println(ro.getMin());
                System.out.println(ro.getType());
                //TODO return generated sql
            }
            if (type.equals("OTH")) {
                Other o = (Other) rule.getFunction();

                //TODO send function SQL generator
                System.out.println(rule.getType().getDesc());
                System.out.println(rule.getTable());
                System.out.println(type);
                System.out.println(o.getBody());
                //TODO return generated sql
            }
        }
    }
}
