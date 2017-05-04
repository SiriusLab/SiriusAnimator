package fr.inria.diverse.event.commons.interpreter.property

import fr.inria.diverse.event.commons.model.property.BinaryProperty
import fr.inria.diverse.event.commons.model.property.BooleanAttributeProperty
import fr.inria.diverse.event.commons.model.property.ContainerReferenceProperty
import fr.inria.diverse.event.commons.model.property.IntegerAttributeProperty
import fr.inria.diverse.event.commons.model.property.ManyBooleanAttributeProperty
import fr.inria.diverse.event.commons.model.property.ManyIntegerAttributeProperty
import fr.inria.diverse.event.commons.model.property.ManyReferenceProperty
import fr.inria.diverse.event.commons.model.property.ManyStringAttributeProperty
import fr.inria.diverse.event.commons.model.property.SingleReferenceProperty
import fr.inria.diverse.event.commons.model.property.StringAttributeProperty
import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import java.util.Collections
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import java.util.HashSet
import fr.inria.diverse.event.commons.model.property.StateProperty

@Aspect(className=StateProperty)
class ClassPropertyAspect {
	public def boolean evaluate(EObject o) {
		false
	}
	
	public def Set<EObject> extractDynamicTerms() {
		return Collections.emptySet
	}
}

@Aspect(className=SingleReferenceProperty)
class SingleReferencePropertyAspect extends ClassPropertyAspect {
	@OverrideAspectMethod
	public def boolean evaluate(EObject o) {
		val eObject = o.eGet(_self.feature) as EObject
		return _self.property.evaluate(eObject)
	}
	
	@OverrideAspectMethod
	public def Set<EObject> extractDynamicTerms() {
		return Collections.emptySet
	}
}

@Aspect(className=ContainerReferenceProperty)
class ContainerReferencePropertyAspect extends ClassPropertyAspect {
	@OverrideAspectMethod
	public def boolean evaluate(EObject o) {
		val eObject = o.eContainer
		return _self.property.evaluate(eObject)
	}
	
	@OverrideAspectMethod
	public def Set<EObject> extractDynamicTerms() {
		return Collections.emptySet
	}
}

@Aspect(className=BinaryProperty)
class BinaryPropertyAspect extends ClassPropertyAspect {
	@OverrideAspectMethod
	public def boolean evaluate(EObject o) {
		var result = false
		switch (_self.operator) {
			case AND: {
				result = _self.left.evaluate(o) && _self.right.evaluate(o)
			}
			case OR: {
				result = _self.left.evaluate(o) || _self.right.evaluate(o)
			}
			case IMPLIES: {
				result = !_self.left.evaluate(o) || _self.right.evaluate(o)
			}
		}
		return result
	}
	
	@OverrideAspectMethod
	public def Set<EObject> extractDynamicTerms() {
		return Collections.emptySet
	}
}

@Aspect(className=ManyReferenceProperty)
class ManyPropertyAspect extends ClassPropertyAspect {
	@OverrideAspectMethod
	public def boolean evaluate(EObject o) {
		val l = o.eGet(_self.feature) as List
		switch (_self.quantifier) {
			case EXISTS: {
				if (_self.property != null) {
					return l.exists[e|_self.property.evaluate(e)]
				} else {
					return !l.empty
				}
			}
			case FORALL: {
				return l.forall[e|_self.property.evaluate(e)]
			}
		}
	}
	
	@OverrideAspectMethod
	public def Set<EObject> extractDynamicTerms() {
		return Collections.emptySet
	}
}

@Aspect(className=BooleanAttributeProperty)
class BooleanAttributePropertyAspect extends ClassPropertyAspect {
	@OverrideAspectMethod
	public def boolean evaluate(EObject o) {
		val i = o.eGet(_self.feature) as Integer
		var result = false
		switch (_self.operator) {
			case EQUAL: {
				result = i == _self.value
			}
		}
		return result
	}
	
	@OverrideAspectMethod
	public def Set<EObject> extractDynamicTerms() {
		return if (_self.feature.EAnnotations.exists[a|a.source == "aspect"]) {
			new HashSet => [add(_self)]
		} else {
			Collections.emptySet
		}
	}
}

@Aspect(className=IntegerAttributeProperty)
class IntegerAttributePropertyAspect extends ClassPropertyAspect {
	@OverrideAspectMethod
	public def boolean evaluate(EObject o) {
		val i = o.eGet(_self.feature) as Integer
		var result = false
		switch (_self.operator) {
			case EQUAL: {
				result = i == _self.value
			}
		}
		return result
	}
	
	@OverrideAspectMethod
	public def Set<EObject> extractDynamicTerms() {
		return if (_self.feature.EAnnotations.exists[a|a.source == "aspect"]) {
			new HashSet => [add(_self)]
		} else {
			Collections.emptySet
		}
	}
}

@Aspect(className=StringAttributeProperty)
class StringAttributePropertyAspect extends ClassPropertyAspect {
	@OverrideAspectMethod
	public def boolean evaluate(EObject o) {
		val i = o.eGet(_self.feature) as String
		var result = false
		switch (_self.operator) {
			case EQUAL: {
				result = i == _self.value
			}
		}
		return result
	}
	
	@OverrideAspectMethod
	public def Set<EObject> extractDynamicTerms() {
		return if (_self.feature.EAnnotations.exists[a|a.source == "aspect"]) {
			new HashSet => [add(_self)]
		} else {
			Collections.emptySet
		}
	}
}

@Aspect(className=ManyBooleanAttributeProperty)
class ManyBooleanAttributePropertyAspect extends ClassPropertyAspect {
	@OverrideAspectMethod
	public def boolean evaluate(EObject o) {
		val l = o.eGet(_self.feature) as List<Boolean>
		var result = false
		switch (_self.operator) {
			case EQUAL: {
				switch (_self.quantifier) {
					case EXISTS: {
						result = l.exists[i|i == _self.value]
					}
					case FORALL: {
						result = l.forall[i|i == _self.value]
					}
				}
			}
		}
		return result
	}
	
	@OverrideAspectMethod
	public def Set<EObject> extractDynamicTerms() {
		return if (_self.feature.EAnnotations.exists[a|a.source == "aspect"]) {
			new HashSet => [add(_self)]
		} else {
			Collections.emptySet
		}
	}
}

@Aspect(className=ManyIntegerAttributeProperty)
class ManyIntegerAttributePropertyAspect extends ClassPropertyAspect {
	@OverrideAspectMethod
	public def boolean evaluate(EObject o) {
		val l = o.eGet(_self.feature) as List<Integer>
		var result = false
		switch (_self.operator) {
			case EQUAL: {
				switch (_self.quantifier) {
					case EXISTS: {
						result = l.exists[i|i == _self.value]
					}
					case FORALL: {
						result = l.forall[i|i == _self.value]
					}
				}
			}
		}
		return result
	}
	
	@OverrideAspectMethod
	public def Set<EObject> extractDynamicTerms() {
		return if (_self.feature.EAnnotations.exists[a|a.source == "aspect"]) {
			new HashSet => [add(_self)]
		} else {
			Collections.emptySet
		}
	}
}

@Aspect(className=ManyStringAttributeProperty)
class ManyStringAttributePropertyAspect extends ClassPropertyAspect {
	@OverrideAspectMethod
	public def boolean evaluate(EObject o) {
		val l = o.eGet(_self.feature) as List<String>
		var result = false
		switch (_self.operator) {
			case EQUAL: {
				switch (_self.quantifier) {
					case EXISTS: {
						result = l.exists[i|i == _self.value]
					}
					case FORALL: {
						result = l.forall[i|i == _self.value]
					}
				}
			}
		}
		return result
	}
	
	@OverrideAspectMethod
	public def Set<EObject> extractDynamicTerms() {
		return if (_self.feature.EAnnotations.exists[a|a.source == "aspect"]) {
			new HashSet => [add(_self)]
		} else {
			Collections.emptySet
		}
	}
}

