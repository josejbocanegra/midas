/*
 * generated by Xtext 2.9.1
 */
package co.edu.javeriana.midas


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class MidasStandaloneSetup extends MidasStandaloneSetupGenerated {

	def static void doSetup() {
		new MidasStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}
