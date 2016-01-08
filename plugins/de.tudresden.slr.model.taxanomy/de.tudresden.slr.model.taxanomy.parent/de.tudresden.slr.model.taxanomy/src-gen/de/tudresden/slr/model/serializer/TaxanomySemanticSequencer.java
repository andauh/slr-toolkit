/*
 * generated by Xtext 2.9.1
 */
package de.tudresden.slr.model.serializer;

import com.google.inject.Inject;
import de.tudresden.slr.model.services.TaxanomyGrammarAccess;
import de.tudresden.slr.model.taxonomy.Model;
import de.tudresden.slr.model.taxonomy.Term;
import de.tudresden.slr.model.taxonomy.taxonomyPackage;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;

@SuppressWarnings("all")
public class TaxanomySemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private TaxanomyGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == taxonomyPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case taxonomyPackage.MODEL:
				sequence_Model(context, (Model) semanticObject); 
				return; 
			case taxonomyPackage.TERM:
				sequence_Term(context, (Term) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     Model returns Model
	 *
	 * Constraint:
	 *     dimensions+=Term+
	 */
	protected void sequence_Model(ISerializationContext context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Term returns Term
	 *
	 * Constraint:
	 *     (name=ID subclasses+=Term*)
	 */
	protected void sequence_Term(ISerializationContext context, Term semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}