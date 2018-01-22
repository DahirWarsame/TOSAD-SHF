package nl.hu.tosad.domain;

public class PLSQLTranslator extends Translator{

    public PLSQLTranslator() {

    }

//    public String generateConstraint(Rule rule, String name) {
//        String result = "CREATE OR REPLACE TRIGGER " + name;
//        result += "\nAFTER " + rule.getTrigger();
//        result += "\nON " + rule.getTable();
//        result += "\nFOR EACH ROW";
//
//        String ruleCode = rule.getType().getCode() + rule.getFunction().getCode();
//
//        switch (ruleCode) {
//            case "ARNG" :
//                result += "\nDECLARE";
//                result += "\nl_passed\tboolean := true;";
//                result += "\nBEGIN";
//                result += "\n";
//                break;
//        }
//
//        return result;
//    }
    @Override
    public void generateDemo(Rule rule) {
        String result = "";

        result += "\nHERE BE CONSTRAINT OF TYPE " + rule.getType().getDesc() + "\n";
        result += "\n\tAFTER " + rule.getTrigger();
        result += "\n\tON " + rule.getTable();
        result += "\n\tCONCERNING " + rule.getAttribute() + "\n";
        result += "\nCORRECT AND COMPLETE PL SQL STATEMENT WILL FOLLOW SOON(TM)";

        System.out.println(result);
    }

}
