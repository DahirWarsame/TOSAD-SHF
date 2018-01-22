package nl.hu.tosad.data;

import nl.hu.tosad.domain.Rule;

public class DataInterface {
private RuleDAO dao=new RuleDAO();

public Rule getRuleByID(String id){
	return dao.getRuleByID(id);
}
}
