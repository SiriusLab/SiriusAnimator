package org.gemoc.executionframework.test.lib

import java.util.Set
import org.eclipse.emf.common.util.URI
import org.gemoc.xdsmlframework.api.core.IExecutionEngine

interface IEngineWrapper {
	
	def void prepare(ILanguageWrapper wrapper,IExecutableModel model, Set<String> addons, URI uri) 
	
	def void run() 
	
	def IExecutionEngine getRealEngine()
	
}