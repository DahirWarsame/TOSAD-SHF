package nl.hu.tosad.domain;

public class Other extends RuleFunction{
public String body;

public Other(String id,String body) {
	super(id);
	this.body = body;
}

public String getBody() {
	return body;
}

public void setBody(String body) {
	this.body = body;
}


}
