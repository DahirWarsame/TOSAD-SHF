package nl.hu.tosad.webservices;

import nl.hu.tosad.domain.Rule;

import java.util.List;

public class Controller {
    public static void main(String[] args) {

        RuleDAO dao = new RuleDAO();

        //Get all rules from Tool database
        List<Rule> rules = dao.getAllRules();

//      displays rules for testing
        for (Rule rule : rules) {
            System.out.println(rule.toString());
//            if (rule.getFunction().equals(RangeOperator.class))
//            System.out.println(rule.getFunction().getCode());
        }


    }
}
