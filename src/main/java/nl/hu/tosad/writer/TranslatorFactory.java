package nl.hu.tosad.writer;

public class TranslatorFactory {
	
	   //use getShape method to get object of type shape 
	   public Translator getTranslator(String transType){
	      if(transType == null){
	         return null;
	      }		
	      if(transType.equalsIgnoreCase("PLSQL")){
	         return new PLSQLTranslator();
	      }
	      
	      return null;
	   }
	}
