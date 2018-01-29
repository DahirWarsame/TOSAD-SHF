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
		StringBuilder result = new StringBuilder("ALTER TABLE " + rule.getTable());

		String ruleCode = rule.getType().getCode() + rule.getFunction().getCode();

		switch (ruleCode) {
            default :
                result = new StringBuilder("This constraint can currently not be translated to PL SQL");
                break;
        }
		return result.toString();
	}


	public String generateTrigger(Rule rule, String name) {
        StringBuilder result = new StringBuilder("CREATE OR REPLACE TRIGGER " + name);
        result.append("\nBEFORE UPDATE OR INSERT ");
        result.append("\nON ").append(rule.getTable());
        result.append("\nFOR EACH ROW");

        String ruleCode = rule.getType().getCode() + rule.getFunction().getCode();

        switch (ruleCode) {
            case "ARNG" :
                RangeOperator attributeRangeFunction = ((RangeOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new."+rule.getAttribute());
                result.append(" ").append(translateOperator(attributeRangeFunction.getType())).append(" ");
                result.append(attributeRangeFunction.getMin()+" AND "+attributeRangeFunction.getMax() + "))");
                result.append("\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'" + name + " was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND ").append(name).append(";");
                break;

            case "ACMP" :
                CompareOperator attributeCompareFunction = ((CompareOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new.").append(rule.getAttribute());
                result.append(" ").append(translateOperator(attributeCompareFunction.getType())).append(" ");
                if (isNumeric(attributeCompareFunction.getValue())){
                    result.append(attributeCompareFunction.getValue() + "))");
                }
                else{
                    result.append("\'"+attributeCompareFunction.getValue() + "\'))");
                }
                result.append("\n\tTHEN");
                result.append("\n\tRAISE_APPLICATION_ERROR (-20001,\'").append(name).append(" was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND ").append(name).append(";");

                break;

            case "ALIS" :
            	ListOperator attributeListFunction = ((ListOperator) rule.getFunction());

            	List<String> list = new ArrayList<String>(Arrays.asList(attributeListFunction.getList().split(",")));
                int count = 0;
                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new."+rule.getAttribute());
                result.append(" "+translateOperator(attributeListFunction.getType())+" (");
                for (String s : list){
                    count+=1;
                    if (isNumeric(s)==true){
                    result.append(s);
                    }
                    else{
                    result.append("'"+s+"'");
                    	}
                    if (count < list.size()){
                        result.append(",");
                    }
                }
                result.append("))) \n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'" + name + " was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND ").append(name).append(";");

                break;

            case "AOTH" :
                Other attributeOtherFunction = ((Other) rule.getFunction());

                result.append(attributeOtherFunction.getBody());
                result.append(name).append(";");
                break;

            case "TCMP" :
                CompareOperator tupleCompareFunction = ((CompareOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new.").append(rule.getAttribute()).append(" ");
                result.append(translateOperator(tupleCompareFunction.getType())).append(" :new.");
                result.append(rule.getType().getOtherattribute()).append("))");
                result.append("\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'").append(name).append(" was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND ").append(name).append(";");
                break;

            case "TOTH" :
                Other tupleOtherFunction = ((Other) rule.getFunction());

                result.append(tupleOtherFunction.getBody());
                result.append(name).append(";");
                break;

            case "ICMP" :
                CompareOperator entityCompareFunction = ((CompareOperator) rule.getFunction());

                result.append("\n\tCURSOR lc IS");
                result.append("\n\tSELECT TOP 1" + rule.getType().getOtherattribute());
                result.append("\n\tFROM " + rule.getType().getOthertable());
                result.append("\n\tl_" + rule.getType().getOtherattribute() + " " + rule.getType().getOthertable() + "." + rule.getType().getOtherattribute() + "%type");

                result.append("\nBEGIN");
                result.append("\n\tOPEN lc;");
                result.append("\n\tFETCH lc INTO l_" + rule.getType().getOtherattribute() + ";");
                result.append("\n\tCLOSE lc;");
                result.append("\n\tIF (NOT (:new.").append(rule.getTable()).append(".").append(rule.getAttribute());
                result.append(" ").append(translateOperator(entityCompareFunction.getType()));
                result.append("l_" + rule.getType().getOtherattribute());
                result.append("))");
                result.append("\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'").append(name).append(" was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND ").append(name).append(";");
                break;

            case "EOTH" :
                Other entityOtherFunction = ((Other) rule.getFunction());

                result.append(entityOtherFunction.getBody());
                result.append(name).append(";");
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

    public String generateDemo(Rule rule) {
        String result = "";

        result += "\nHERE BE CONSTRAINT OF TYPE " + rule.getType().getDesc() + "\n";
        result += "\n\tAFTER UPDATE OR INSERT OR DELETE";
        result += "\n\tON " + rule.getTable();
        result += "\n\tCONCERNING " + rule.getAttribute() + "\n";
        result += "\nCORRECT AND COMPLETE PL SQL STATEMENT WILL FOLLOW SOON(TM)";

        return result;
    }

    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}
