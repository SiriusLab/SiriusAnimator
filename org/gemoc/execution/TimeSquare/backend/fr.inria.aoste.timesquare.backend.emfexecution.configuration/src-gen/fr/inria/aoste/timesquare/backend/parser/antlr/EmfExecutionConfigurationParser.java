/*
* generated by Xtext
*/
package fr.inria.aoste.timesquare.backend.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import fr.inria.aoste.timesquare.backend.services.EmfExecutionConfigurationGrammarAccess;

public class EmfExecutionConfigurationParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private EmfExecutionConfigurationGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected fr.inria.aoste.timesquare.backend.parser.antlr.internal.InternalEmfExecutionConfigurationParser createParser(XtextTokenStream stream) {
		return new fr.inria.aoste.timesquare.backend.parser.antlr.internal.InternalEmfExecutionConfigurationParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "EMFExecutionConfiguration";
	}
	
	public EmfExecutionConfigurationGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(EmfExecutionConfigurationGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}