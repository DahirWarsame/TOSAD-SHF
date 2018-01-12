package nl.hu.tosad.webservices;

import java.util.ArrayList;

import nl.hu.tosad.domain.RuleType;

public class test {
	public static void main(String[] args){
		FactoryProducer af=new FactoryProducer();
		AbstractFactory fac=null;
		RuleDAO r=new RuleDAO();
		ArrayList<RuleType> rul=r.getRules();
		for (RuleType ru:rul){
			System.out.println(ru.getName());
			System.out.println(ru.getType());
			fac=af.getFactory(ru.getName());
			fac.getCompareOperator(value, type)
			
		}
	}

}
