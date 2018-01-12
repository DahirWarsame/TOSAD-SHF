package nl.hu.tosad.webservices;

public class FactoryProducer {
	public static AbstractFactory getFactory(String choice){
		   
	      if(choice.equalsIgnoreCase("Attribute")){
	         return new RangeFactory();
	         
	      }else if(choice.equalsIgnoreCase("Tuple")){
	         return new ListFactory();
	      }
	      
	      return null;
	   }
	}
}