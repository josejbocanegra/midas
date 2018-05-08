package co.edu.javeriana.midas.formatting

import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter
import co.edu.javeriana.midas.services.MidasGrammarAccess
import org.eclipse.xtext.formatting.impl.FormattingConfig


class Formating extends AbstractDeclarativeFormatter {
	override protected void configureFormatting(FormattingConfig c){
		var grammar = getGrammarAccess() as MidasGrammarAccess
		c.autoLinewrap = 120
		println("Formating .... cccc")
		for (pair : grammar.findKeywordPairs("{", "}")) {
			config.setIndentation(pair.getFirst(), pair.getSecond());
			config.setLinewrap(1).after(pair.getFirst());
			config.setLinewrap(1).before(pair.getSecond());
		}
	}
}