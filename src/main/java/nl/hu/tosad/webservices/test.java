package nl.hu.tosad.webservices;

import nl.hu.tosad.domain.RangeOperator;
import nl.hu.tosad.domain.Rule;

public class test {
	public static void main(String[] args){
		RuleDAO dao=new RuleDAO();
		Rule r=dao.getRulebyID(1);
		System.out.println(r.getType().getDesc());
		System.out.println(r.getTable());
		if (r.getFunction().equals(RangeOperator.class))
		System.out.println(r.getFunction().getCode());
		}
	}
