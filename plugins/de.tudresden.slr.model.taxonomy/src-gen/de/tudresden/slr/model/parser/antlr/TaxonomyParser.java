/*
* generated by Xtext
*/
package de.tudresden.slr.model.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import de.tudresden.slr.model.services.TaxonomyGrammarAccess;

public class TaxonomyParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private TaxonomyGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_ML_COMMENT", "RULE_WS", "RULE_NEWLINE");
	}
	
	@Override
	protected de.tudresden.slr.model.parser.antlr.internal.InternalTaxonomyParser createParser(XtextTokenStream stream) {
		return new de.tudresden.slr.model.parser.antlr.internal.InternalTaxonomyParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "Model";
	}
	
	public TaxonomyGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(TaxonomyGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
