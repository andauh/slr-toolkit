/*
* generated by Xtext
*/
package de.tudresden.slr.model;

import org.eclipse.xtext.junit4.IInjectorProvider;

import com.google.inject.Injector;

public class TaxonomyUiInjectorProvider implements IInjectorProvider {
	
	public Injector getInjector() {
		return de.tudresden.slr.model.ui.internal.TaxonomyActivator.getInstance().getInjector("de.tudresden.slr.model.Taxonomy");
	}
	
}
