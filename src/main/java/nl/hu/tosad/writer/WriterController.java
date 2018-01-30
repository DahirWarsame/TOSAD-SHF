package nl.hu.tosad.writer;

import java.io.IOException;

import nl.hu.tosad.domain.Rule;

public class WriterController {
	FilePrinter printer = new FilePrinter();

	public String generateCode(Rule rule, String name, String language, String type) {
		try {
			TranslatorFactory transFactory = new TranslatorFactory();

			Translator trans = transFactory.getTranslator(language);

			return trans.generateCode(rule, name, type);
		} catch (NullPointerException e) {
			return ("Translator niet beschikbaar");
		}
	}

	public void print(String name, String output) throws IOException {
		printer.print(name, output);

	}
}
