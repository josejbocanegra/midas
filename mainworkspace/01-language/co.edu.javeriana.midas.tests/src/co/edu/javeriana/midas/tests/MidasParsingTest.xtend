/*
 * generated by Xtext 2.9.1
 */
package co.edu.javeriana.midas.tests

import co.edu.javeriana.midas.midas.Package
import com.google.inject.Inject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
//@InjectWith(MidasInjectorProvider)
class MidasParsingTest{

	@Inject
	ParseHelper<Package> parseHelper;

	@Test 
	def void loadModel() {
		val result = parseHelper.parse('''
			Hello Xtext!
		''')
		Assert.assertNotNull(result)
	}

}
