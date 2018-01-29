package nl.hu.tosad.writer;

import nl.hu.tosad.domain.Rule;

public interface Translator {

	String generateCode(Rule rule, String name);

}
