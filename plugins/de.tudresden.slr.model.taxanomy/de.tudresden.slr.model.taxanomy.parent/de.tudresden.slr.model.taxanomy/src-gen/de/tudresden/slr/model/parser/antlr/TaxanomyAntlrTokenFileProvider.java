/*
 * generated by Xtext 2.9.1
 */
package de.tudresden.slr.model.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class TaxanomyAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("de/tudresden/slr/model/parser/antlr/internal/InternalTaxanomy.tokens");
	}
}