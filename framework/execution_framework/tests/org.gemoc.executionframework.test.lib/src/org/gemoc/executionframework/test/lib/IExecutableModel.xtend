package org.gemoc.executionframework.test.lib

interface IExecutableModel {

	def String getFileName()
	def String getPluginName()
	def String getFolderPath()
	def String getMelangeQuery()
	def String getInitArgument()
	def int getShouldStopAfter()
	
}
