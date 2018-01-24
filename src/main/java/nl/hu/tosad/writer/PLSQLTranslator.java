package nl.hu.tosad.writer;

import nl.hu.tosad.domain.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PLSQLTranslator extends Translator{

    public PLSQLTranslator() {

    }

    public String generateCode(Rule rule, String name) {
        StringBuilder result = new StringBuilder("CREATE OR REPLACE TRIGGER " + name);
        result.append("\nBEFORE ").append(rule.getTrigger());
        result.append("\nON ").append(rule.getTable());
        result.append("\nFOR EACH ROW");

        String ruleCode = rule.getType().getCode() + rule.getFunction().getCode();

        switch (ruleCode) {
            case "ARNG" :
                RangeOperator rangeFunction = ((RangeOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new.").append(rule.getAttribute());
                result.append(rangeFunction.getType().equals("NB") ? " NOT BETWEEN " : " BETWEEN ");
                result.append(rangeFunction.getMin()).append(" AND ").append(rangeFunction.getMax()).append("))");
                result.append("\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'").append(name).append(" was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND ").append(name).append(";");
                break;

            case "ACMP" :
                CompareOperator compareOperator = ((CompareOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new.").append(rule.getAttribute());
                result.append(compareOperator.getType()).append(compareOperator.getValue()).append("))");
                result.append("\n\tTHEN");
                result.append("\n\tRAISE_APPLICATION_ERROR (-20001,\'").append(name).append(" was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND");

                break;

            case "ALIS" :
                ListOperator listFunction = ((ListOperator) rule.getFunction());
                listFunction.getList();
                result.append("\nBEGIN");
                result.append("\nIF (NOT (:new.").append(rule.getAttribute()).append(" IN (");
                int count = 0;
                for (String s : listFunction.getList())
                {
                    count+=1;
                    result.append(s);
                    if (count < listFunction.getList().size()){
                        result.append(",");
                    }
                }
                result.append("\n\t)");
                result.append("\n\tTHEN");
                result.append("\n\tRAISE_APPLICATION_ERROR (-20001,\'").append(name).append(" was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND");

                break;

            case "AOTH" :
                Other otherFunction = ((Other) rule.getFunction());

                result.append("\nBEGIN\n\t");
                result.append(otherFunction.getBody());
                result.append("\nEND ").append(name);
                break;

            case "TCMP" :

                break;

            case "TOTH" :

                break;

            case "ICMP" :

                break;

            default :
                result = new StringBuilder("This business rule type is currently not available in PL SQL");
                break;
        }
        return result.toString();
    }

    public String generateDemo(Rule rule) {
        String result = "";

        result += "\nHERE BE CONSTRAINT OF TYPE " + rule.getType().getDesc() + "\n";
        result += "\n\tAFTER " + rule.getTrigger();
        result += "\n\tON " + rule.getTable();
        result += "\n\tCONCERNING " + rule.getAttribute() + "\n";
        result += "\nCORRECT AND COMPLETE PL SQL STATEMENT WILL FOLLOW SOON(TM)";

        return result;
    }

}