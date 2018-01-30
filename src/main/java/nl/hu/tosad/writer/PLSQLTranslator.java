package nl.hu.tosad.writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.hu.tosad.domain.CompareOperator;
import nl.hu.tosad.domain.ListOperator;
import nl.hu.tosad.domain.Other;
import nl.hu.tosad.domain.RangeOperator;
import nl.hu.tosad.domain.Rule;

public class PLSQLTranslator implements Translator{

	public String generateCode(Rule rule, String name, String type){
		if (type.equals("Trigger")){
			return generateTrigger(rule, name);
		}
		else if (type.equals("Constraint")){
			return generateConstraint(rule,name);
		}
		return "Geen geldig type gegeven";
	}
	
	
    private String generateConstraint(Rule rule, String name) {
		StringBuilder result = new StringBuilder("ALTER TABLE ");
		result.append("\n\t" + rule.getTable());
		result.append("\nADD CONSTRAINT");
		result.append("\n\t" + name);
        result.append("\n\tCHECK");

		String ruleCode = rule.getType().getCode() + rule.getFunction().getCode();

		switch (ruleCode) {
            case "ARNG" :
                RangeOperator attributeRangeFunction = ((RangeOperator) rule.getFunction());

                result.append("\n\t(" + rule.getAttribute() + " " + translateOperator(attributeRangeFunction.getType()));
                result.append(" " + attributeRangeFunction.getMin() + " AND " + attributeRangeFunction.getMax() + ")");
                result.append("\n;");
                break;

            case "ACMP" :
                CompareOperator attributeCompareFunction = ((CompareOperator) rule.getFunction());

                result.append("\n\t(" + rule.getAttribute() + " ");
                result.append(translateOperator(attributeCompareFunction.getType()) + " ");
                if (isNumeric(attributeCompareFunction.getValue())) {
                    result.append(attributeCompareFunction.getValue() + ")");
                }
                else {
                    result.append("\'" + attributeCompareFunction.getValue() + "\')");
                }
                result.append("\n;");
                break;

            case "ALIS" :
                ListOperator attributeListFunction = ((ListOperator) rule.getFunction());

                result.append("\n\t(" + rule.getAttribute() + " " + translateOperator(attributeListFunction.getType()));
                result.append("\n\t\t(");
                int count = 0;
                List<String> list = new ArrayList<String>(Arrays.asList(attributeListFunction.getList().split(",")));
                for (String s : list) {
                    count++;
                    if (isNumeric(s)) {
                        result.append("\n\t\t " + s);
                    }
                    else {
                        result.append("\n\t\t \'" + s + "\'");
                    }
                    if (count < list.size()) {
                        result.append(",");
                    }
                }
                result.append("\n\t\t)");
                result.append("\n\t)");
                result.append("\n;");
                break;

            case "TCMP" :
                CompareOperator tupleCompareFunction = ((CompareOperator) rule.getFunction());

                result.append("\n\t(" + rule.getAttribute() + " ");
                result.append(translateOperator(tupleCompareFunction.getType()) + " ");
                result.append(rule.getType().getOtherattribute() + ")");
                result.append("\n;");
                break;

            default :
                result = new StringBuilder("This constraint can currently not be translated to PL SQL");
                break;
        }
		return result.toString();
	}


	public String generateTrigger(Rule rule, String name) {
        StringBuilder result = new StringBuilder("CREATE OR REPLACE TRIGGER " + name);
        result.append("\nBEFORE UPDATE OR INSERT ");
        result.append("\nON " + rule.getTable());
        result.append("\nFOR EACH ROW");

        String ruleCode = rule.getType().getCode() + rule.getFunction().getCode();

        switch (ruleCode) {
            case "ARNG" :
                RangeOperator attributeRangeFunction = ((RangeOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new." + rule.getAttribute());
                result.append(" " + translateOperator(attributeRangeFunction.getType()) + " ");
                result.append(attributeRangeFunction.getMin() + " AND " + attributeRangeFunction.getMax() + "))");
                result.append("\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'" + name + " was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND " + name + ";");
                break;

            case "ACMP" :
                CompareOperator attributeCompareFunction = ((CompareOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new." + rule.getAttribute());
                result.append(" " + translateOperator(attributeCompareFunction.getType()) + " ");
                if (isNumeric(attributeCompareFunction.getValue())) {
                    result.append(attributeCompareFunction.getValue() + "))");
                }
                else {
                    result.append("\'" + attributeCompareFunction.getValue() + "\'))");
                }
                result.append("\n\tTHEN");
                result.append("\n\tRAISE_APPLICATION_ERROR (-20000,\'" + name + " was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND " + name + ";");

                break;

            case "ALIS" :
            	ListOperator attributeListFunction = ((ListOperator) rule.getFunction());

            	List<String> list = new ArrayList<String>(Arrays.asList(attributeListFunction.getList().split(",")));
                int count = 0;
                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new." + rule.getAttribute());
                result.append(" " + translateOperator(attributeListFunction.getType()) + " (");
                for (String s : list) {
                    count++;
                    if (isNumeric(s)) {
                    result.append(s);
                    }
                    else {
                    result.append("\'" + s + "\'");
                    }
                    if (count < list.size()) {
                        result.append(",");
                    }
                }
                result.append("))) \n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'" + name + " was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND " + name + ";");

                break;

            case "AOTH" :
                Other attributeOtherFunction = ((Other) rule.getFunction());

                result.append(attributeOtherFunction.getBody());
                result.append(name + ";");
                break;

            case "TCMP" :
                CompareOperator tupleCompareFunction = ((CompareOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new." + rule.getAttribute() + " ");
                result.append(translateOperator(tupleCompareFunction.getType()) + " :new.");
                result.append(rule.getType().getOtherattribute() + "))");
                result.append("\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'" + name + " was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND " + name + ";");
                break;

            case "TOTH" :
                Other tupleOtherFunction = ((Other) rule.getFunction());

                result.append(tupleOtherFunction.getBody());
                result.append(name + ";");
                break;

            case "ICMP" :
                CompareOperator entityCompareFunction = ((CompareOperator) rule.getFunction());

                result.append("\n\tCURSOR lc IS");
                result.append("\n\tSELECT TOP 1" + rule.getType().getOtherattribute());
                result.append("\n\tFROM " + rule.getType().getOthertable());
                result.append("\n\tl_" + rule.getType().getOtherattribute() + " " + rule.getType().getOthertable());
                result.append("." + rule.getType().getOtherattribute() + "%type");

                result.append("\nBEGIN");
                result.append("\n\tOPEN lc;");
                result.append("\n\tFETCH lc INTO l_" + rule.getType().getOtherattribute() + ";");
                result.append("\n\tCLOSE lc;");
                result.append("\n\tIF (NOT (:new." + rule.getTable() + "." + rule.getAttribute());
                result.append(" " + translateOperator(entityCompareFunction.getType()));
                result.append("l_" + rule.getType().getOtherattribute());
                result.append("))");
                result.append("\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'" + name + " was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND " + name + ";");
                break;

            case "EOTH" :
                Other entityOtherFunction = ((Other) rule.getFunction());

                result.append(entityOtherFunction.getBody());
                result.append(name + ";");
                break;

            default :
                result = new StringBuilder("This business rule type can currently not be translated to PL SQL");
                break;
        }
        return result.toString();
    }

    public String translateOperator(String code) {
        String result = "";

        switch (code){
	        case "I" :
	            result = "IN";
	            break;
	        case "NI" :
	            result = "NOT IN";
	            break;
            case "B" :
                result = "BETWEEN";
                break;
            case "NB" :
                result = "NOT BETWEEN";
                break;
            case "NE" :
                result = "!=";
                break;
            case "E" :
                result = "=";
                break;
            case "LT" :
                result = "<";
                break;
            case "GT" :
                result = ">";
                break;
            case "LOET" :
                result = "<=";
                break;
            case "GOET" :
                result = ">=";
                break;
        }

        return result;
    }

    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}
