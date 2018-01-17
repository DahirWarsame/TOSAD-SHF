package nl.hu.tosad.data;

import nl.hu.tosad.domain.Rule;

public class test {
	public static void main(String[] args){
	RuleDAO dao=new RuleDAO();
	Rule r=dao.getRuleByID("BRG_VMBG_KLANTEN_ALIS_2");
	System.out.println(r.getCode());
}
}