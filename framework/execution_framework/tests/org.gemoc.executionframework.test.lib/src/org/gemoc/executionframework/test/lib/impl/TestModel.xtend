package org.gemoc.executionframework.test.lib.impl

import org.eclipse.xtend.lib.annotations.Accessors
import org.gemoc.executionframework.test.lib.IExecutableModel

class TestModel implements IExecutableModel {

	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	val String fileName
	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	val String pluginName
	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	val String folderPath
	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	val String initArgument
	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	val String melangeQuery
	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	val int shouldStopAfter

	new(String pluginName, String folderPath, String fileName, String initArgument, String melangeQuery,
		int shouldStopAfter) {
		this.pluginName = pluginName
		this.folderPath = folderPath
		this.fileName = fileName
		this.initArgument = initArgument
		this.melangeQuery = melangeQuery
		this.shouldStopAfter = shouldStopAfter
	}

	new(String pluginName, String folderPath, String fileName) {
		this(pluginName, folderPath, fileName, "", "", -1)
	}
	
	new(String pluginName, String folderPath, String fileName, String initArgument, String melangeQuery) {
		this(pluginName, folderPath, fileName, initArgument, melangeQuery, -1)
	}
	

}
