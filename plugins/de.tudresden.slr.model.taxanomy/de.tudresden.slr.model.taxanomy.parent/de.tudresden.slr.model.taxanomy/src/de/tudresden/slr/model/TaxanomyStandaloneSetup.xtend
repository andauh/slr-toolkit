/*
 * generated by Xtext 2.9.1
 */
package de.tudresden.slr.model


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class TaxanomyStandaloneSetup extends TaxanomyStandaloneSetupGenerated {

	def static void doSetup() {
		new TaxanomyStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}