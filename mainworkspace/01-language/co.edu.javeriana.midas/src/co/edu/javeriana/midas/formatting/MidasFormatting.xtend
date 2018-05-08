package co.edu.javeriana.midas.formatting

import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter
import org.eclipse.xtext.formatting.impl.FormattingConfig
import javax.inject.Inject
import co.edu.javeriana.midas.services.MidasGrammarAccess

class MidasFormatting extends AbstractDeclarativeFormatter {
	@Inject extension MidasGrammarAccess
	
	
	override protected configureFormatting(FormattingConfig c) {
		println("Formating...")
		c.setLinewrap(1).after(contextEntityRule)
	}
}