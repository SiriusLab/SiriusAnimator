/**
 */
package fr.obeo.dsl.sirius.animationtemplate.impl;

import fr.obeo.dsl.sirius.animationtemplate.AnimationTemplate;
import fr.obeo.dsl.sirius.animationtemplate.AnimationtemplatePackage;

import fr.obeo.dsl.sirius.animationtemplate.TTransformer;
import fr.obeo.dsl.viewpoint.description.RepresentationDescription;

import fr.obeo.dsl.viewpoint.description.impl.RepresentationTemplateImpl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Animation Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.obeo.dsl.sirius.animationtemplate.impl.AnimationTemplateImpl#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link fr.obeo.dsl.sirius.animationtemplate.impl.AnimationTemplateImpl#getAnimate <em>Animate</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AnimationTemplateImpl extends RepresentationTemplateImpl implements AnimationTemplate {
	/**
	 * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> outputs;
	/**
	 * The cached value of the '{@link #getAnimate() <em>Animate</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnimate()
	 * @generated
	 * @ordered
	 */
	protected EList<RepresentationDescription> animate;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnimationTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnimationtemplatePackage.Literals.ANIMATION_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getOutputs() {
		if (outputs == null) {
			outputs = new EObjectResolvingEList<EObject>(EObject.class, this, AnimationtemplatePackage.ANIMATION_TEMPLATE__OUTPUTS);
		}
		return outputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RepresentationDescription> getAnimate() {
		if (animate == null) {
			animate = new EObjectResolvingEList<RepresentationDescription>(RepresentationDescription.class, this, AnimationtemplatePackage.ANIMATION_TEMPLATE__ANIMATE);
		}
		return animate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AnimationtemplatePackage.ANIMATION_TEMPLATE__OUTPUTS:
				return getOutputs();
			case AnimationtemplatePackage.ANIMATION_TEMPLATE__ANIMATE:
				return getAnimate();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AnimationtemplatePackage.ANIMATION_TEMPLATE__OUTPUTS:
				getOutputs().clear();
				getOutputs().addAll((Collection<? extends EObject>)newValue);
				return;
			case AnimationtemplatePackage.ANIMATION_TEMPLATE__ANIMATE:
				getAnimate().clear();
				getAnimate().addAll((Collection<? extends RepresentationDescription>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AnimationtemplatePackage.ANIMATION_TEMPLATE__OUTPUTS:
				getOutputs().clear();
				return;
			case AnimationtemplatePackage.ANIMATION_TEMPLATE__ANIMATE:
				getAnimate().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AnimationtemplatePackage.ANIMATION_TEMPLATE__OUTPUTS:
				return outputs != null && !outputs.isEmpty();
			case AnimationtemplatePackage.ANIMATION_TEMPLATE__ANIMATE:
				return animate != null && !animate.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == TTransformer.class) {
			switch (derivedFeatureID) {
				case AnimationtemplatePackage.ANIMATION_TEMPLATE__OUTPUTS: return AnimationtemplatePackage.TTRANSFORMER__OUTPUTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == TTransformer.class) {
			switch (baseFeatureID) {
				case AnimationtemplatePackage.TTRANSFORMER__OUTPUTS: return AnimationtemplatePackage.ANIMATION_TEMPLATE__OUTPUTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //AnimationTemplateImpl