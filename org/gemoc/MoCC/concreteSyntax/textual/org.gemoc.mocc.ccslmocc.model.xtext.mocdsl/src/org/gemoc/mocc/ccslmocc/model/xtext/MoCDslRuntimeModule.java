/*
 * generated by Xtext NOT
 */
package org.gemoc.mocc.ccslmocc.model.xtext;


import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import com.google.inject.Binder;

import fr.inria.aoste.timesquare.ccslkernel.library.xtext.CCSLLibraryRuntimeModule;
import fr.inria.aoste.timesquare.ccslkernel.xtext.util.CCSLLinkingDiagnosticMessageProvider;
import fr.inria.aoste.timesquare.ccslkernel.xtext.util.CCSLTerminalConverters;
import fr.inria.aoste.timesquare.ccslkernel.xtext.util.SimpleNamedElementProvider;




/**
 * @see CCSLLibraryRuntimeModule methods
 * @author Stfun
 * @generated NOT
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class MoCDslRuntimeModule extends org.gemoc.mocc.ccslmocc.model.xtext.AbstractMoCDslRuntimeModule {
	
	
	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
	
		return SimpleNamedElementProvider.class;
	}

	@Override
	public Class<? extends org.eclipse.xtext.conversion.IValueConverterService> bindIValueConverterService() {
		return CCSLTerminalConverters.class;
	}
	
	
	@Override
	public void configure(Binder binder) {

		super.configure(binder);		
		binder.bind(ILinkingDiagnosticMessageProvider.class).to(CCSLLinkingDiagnosticMessageProvider.class);
	}
	

}
