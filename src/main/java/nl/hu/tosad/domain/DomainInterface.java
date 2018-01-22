package nl.hu.tosad.domain;

public class DomainInterface {

public void printRule(Rule r, String language) throws Exception{
	Translator trans=null;
	if (language.equals("PLSQL")){
		trans=new PLSQLTranslator();
	}
	else{
		throw new Exception("No translator found for the input language");
	}
	trans.generateDemo(r);
}
}
