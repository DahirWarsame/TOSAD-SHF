package nl.hu.tosad.writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.hu.tosad.domain.CompareOperator;
import nl.hu.tosad.domain.ListOperator;
import nl.hu.tosad.domain.Other;
import nl.hu.tosad.domain.RangeOperator;
import nl.hu.tosad.domain.Rule;

public class PLSQLTranslator extends Translator{

    public PLSQLTranslator() {

    }

    public String generateCode(Rule rule, String name) {
        StringBuilder result = new StringBuilder("CREATE OR REPLACE TRIGGER " + name);
        result.append("\nBEFORE UPDATE OR INSERT ");
        result.append("\nON ").append(rule.getTable());
        result.append("\nFOR EACH ROW");

        String ruleCode = rule.getType().getCode() + rule.getFunction().getCode();

        switch (ruleCode) {
            case "ARNG" :
                RangeOperator rangeFunction = ((RangeOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new."+rule.getAttribute());
                result.append(" ").append(translateOperator(rangeFunction.getType())).append(" ");
                result.append(rangeFunction.getMin()+" AND "+rangeFunction.getMax() + "))");
                result.append("\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'" + name + " was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND ").append(name).append(";");
                break;

            case "ACMP" :
                CompareOperator compareFunction = ((CompareOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new.").append(rule.getAttribute());
                result.append(" ").append(translateOperator(compareFunction.getType())).append(" ");
                if (isNumeric(compareFunction.getValue())){
                    result.append(compareFunction.getValue() + "))");
                }
                else{
                    result.append("\'"+compareFunction.getValue() + "\'))");
                }
                result.append("\n\tTHEN");
                result.append("\n\tRAISE_APPLICATION_ERROR (-20001,\'").append(name).append(" was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND ").append(name).append(";");

                break;

            case "ALIS" :
            	ListOperator listFunction = ((ListOperator) rule.getFunction());

            	List<String> list = new ArrayList<String>(Arrays.asList(listFunction.getList().split(",")));
                int count = 0;
                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new."+rule.getAttribute());
                result.append(" "+translateOperator(listFunction.getType())+" (");
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
                Other otherFunction = ((Other) rule.getFunction());

                result.append("\nBEGIN\n\t");
                result.append(otherFunction.getBody());
                result.append("\nEND ").append(name).append(";");
                break;

            case "TCMP" :
                CompareOperator tupleCompareFunction = ((CompareOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new.").append(rule.getAttribute()).append(" ");
                result.append(translateOperator(tupleCompareFunction.getType())).append(" ");
                result.append(tupleCompareFunction.getValue()).append("))");
                result.append("\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'").append(name).append(" was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND ").append(name).append(";");
                break;

            case "TOTH" :
                Other tupleOtherFunction = ((Other) rule.getFunction());

                result.append("\nBEGIN\n\t");
                result.append(tupleOtherFunction.getBody());
                result.append("\nEND ").append(name).append(";");
                break;

            case "ICMP" :

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
            case "B" :
                result = "BETWEEN";
                break;
            case "NB" :
                result = "NOT BETWEEN";
                break;
            case "NE" :
                result =  "=!";
                break;
            case "E" :
                result = "=!";
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
