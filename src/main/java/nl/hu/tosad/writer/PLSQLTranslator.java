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
        result.append("\nBEFORE ").append(rule.getTrigger());
        result.append("\nON ").append(rule.getTable());
        result.append("\nFOR EACH ROW");

        String ruleCode = rule.getType().getCode() + rule.getFunction().getCode();

        switch (ruleCode) {
            case "ARNG" :
                RangeOperator rangeFunction = ((RangeOperator) rule.getFunction());

                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new."+rule.getAttribute());
                result.append(rangeFunction.getType().equals("NB") ? " NOT BETWEEN " : " BETWEEN ");
                result.append(rangeFunction.getMin()+" AND "+rangeFunction.getMax() + "))");
                result.append("\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'" + name + " was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND " + name+";");
                break;
            
            case "ACMP" :
                CompareOperator compareFunction = ((CompareOperator) rule.getFunction());
                
                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new."+rule.getAttribute());
                result.append(getSymbol(compareFunction.getType()));
                if (isNumeric(compareFunction.getValue1())==true){
                	result.append(compareFunction.getValue1() + "))");
                }
                else{
                	result.append("'"+compareFunction.getValue1() + "'))");
                }
                result.append("\n\tTHEN RAISE_APPLICATION_ERROR(-20000, \'" + name + " was triggered\');");
                result.append("\n\tEND IF;");
                result.append("\nEND " + name+";");
                break;
            
            case "ALIS" :
            	ListOperator listFunction = ((ListOperator) rule.getFunction());
            	List<String> list = new ArrayList<String>(Arrays.asList(listFunction.getList().split(",")));
                int count = 0;
                result.append("\nBEGIN");
                result.append("\n\tIF (NOT (:new."+rule.getAttribute());
                result.append(" "+getSymbol(listFunction.getType())+" ("); 
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
                result.append("\nEND " + name+";");
                break;

            case "AOTH" :
                Other otherFunction = ((Other) rule.getFunction()); 
                result.append(otherFunction.getBody());

                break;

            default :
            	 result = new StringBuilder("This business rule type is currently not available in PL SQL");
                 break;
        }
        return result.toString();
    }
    public String getSymbol(String input){
    	 String symbol="";
         if (input.equals("E")){
         	symbol="=";
         }
         else if (input.equals("NE")){
         	symbol="!=";
         }
         else if (input.equals("GT")){
         	symbol=">";
         }
         else if (input.equals("LT")){
         	symbol="<";
         }
         else if (input.equals("LOET")){
         	symbol="<=";
         }
         else if (input.equals("GOET")){
         	symbol=">=";
         }
         else if (input.equals("I")){
          	symbol="in";
          }
         else if (input.equals("NI")){
           	symbol="not in";
           }
         return symbol;
    }
    
    public boolean isNumeric(String s) {  
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
    }  
}
