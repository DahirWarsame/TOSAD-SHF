package nl.hu.tosad.writer;

import nl.hu.tosad.domain.Other;
import nl.hu.tosad.domain.RangeOperator;
import nl.hu.tosad.domain.Rule;

public class PLSQLTranslator extends Translator{

    public PLSQLTranslator() {

    }

    public String generateCode(Rule rule, String name) {
        String result = "CREATE OR REPLACE TRIGGER " + name;
        result += "\nBEFORE " + rule.getTrigger();
        result += "\nON " + rule.getTable();
        result += "\nFOR EACH ROW";

        String ruleCode = rule.getType().getCode() + rule.getFunction().getCode();

        switch (ruleCode) {
            case "ARNG" :
                RangeOperator rangeFunction = ((RangeOperator) rule.getFunction());

                result += "\nBEGIN";
                result += "\n\tIF (NOT (:new."+rule.getAttribute();
                result += rangeFunction.getType().equals("NB") ? " NOT BETWEEN " : " BETWEEN ";
                result += rangeFunction.getMin()+" AND "+rangeFunction.getMax() + "))";
                result += "\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'" + name + " was triggered\');";
                result += "\n\tEND IF;";
                result += "\nEND " + name+";";
                break;

            case "ACMP" :
                
                break;

            case "ALIS" :

                break;

            case "AOTH" :
                Other otherFunction = ((Other) rule.getFunction());

                result += "\nBEGIN\n\t";
                result += otherFunction.getBody();
                result += "\nEND " + name;
                break;

            case "TCMP" :

                break;

            case "TOTH" :

                break;

            case "ICMP" :

                break;

            default :
                result = "This business rule type is currently not available in PL SQL";
                break;
        }
        return result;
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