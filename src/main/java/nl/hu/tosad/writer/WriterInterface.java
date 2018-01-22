package nl.hu.tosad.writer;

import java.io.IOException;

import nl.hu.tosad.domain.Rule;

public class WriterInterface {
	FilePrinter printer=new FilePrinter();

public String generateCode(Rule rule, String name, String language) throws Exception{
	Translator trans=null;
	if (language.equals("PLSQL")){
		trans=new PLSQLTranslator();
	}
	else{
		throw new Exception("No translator found for the input language");
	}
	return trans.generateCode(rule, name);
}

public void print(String name, String output) throws IOException {
	printer.print(name, output);
	
}
}
