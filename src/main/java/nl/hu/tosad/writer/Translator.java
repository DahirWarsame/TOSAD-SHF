package nl.hu.tosad.writer;

import nl.hu.tosad.domain.Rule;

public abstract class Translator {

	public abstract String generateCode(Rule rule, String name);

}
