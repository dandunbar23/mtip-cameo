package org.aero.huddle.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.sysml.util.MDCustomizationForSysMLProfile;
import com.nomagic.magicdraw.sysml.util.SysMLProfile;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.activities.mdfundamentalactivities.Activity;
import com.nomagic.uml2.ext.magicdraw.classes.mdassociationclasses.AssociationClass;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Pseudostate;
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKind;
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum;
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Region;
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.StateMachine;
import com.nomagic.uml2.impl.ElementsFactory;

public class CameoUtils {
	public static void logGUI(String text) {
		Application.getInstance().getGUILog().log(text);
	}
	
	public static Element findNearestPackage(Project project, Element element) {
		Element topPackage = project.getPrimaryModel();
		Element owner = element.getOwner();
		if(owner.equals(topPackage)) {
			return topPackage;
		}
		if(owner instanceof com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package || owner instanceof Profile) {
			return owner;
		} else {
			return findNearestPackage(project, owner);
		}
		
	}
	
	public static Element findNearestRegion(Project project, Element owner) {
		Region region = null;
		Collection<Region> regions = null;
		CameoUtils.logGUI("Searching for state machine with current element " + owner.getHumanType() + " and id " + owner.getLocalID());
		if(owner instanceof StateMachine) {
			StateMachine sm = (StateMachine)owner;
			regions = sm.getRegion();
			if(regions != null) {
				region = regions.iterator().next();
				return region;
			} else {
				ElementsFactory f = project.getElementsFactory();
				region = f.createRegionInstance();
				region.setOwner(sm);
				return region;
			}
		} else {
			Element nextOwner = owner.getOwner();
			return findNearestRegion(project, nextOwner);
		}
	}
	public static Element findNearestBlock(Project project, Element owner) {
		CameoUtils.logGUI("Searching for activity with current element " + owner.getHumanType() + " and id " + owner.getLocalID());
		if(SysMLProfile.isBlock(owner)) {
			return owner;
		} else {
			Element nextOwner = owner.getOwner();
			return findNearestBlock(project, nextOwner);
		}
	}
	
	public static Element findNearestActivity(Project project, Element owner) {
		CameoUtils.logGUI("Searching for activity with current element " + owner.getHumanType() + " and id " + owner.getLocalID());
		if(owner instanceof Activity) {
			return owner;
		} else {
			Element nextOwner = owner.getOwner();
			return findNearestActivity(project, nextOwner);
		}
	}
	public static Stereotype getStereotype(String stereotypeName) {
		Project project = Application.getInstance().getProject();
		switch(stereotypeName) {
		case "auxiliary":
			Profile umlProfile = StereotypesHelper.getProfile(project, "UML Standard Profile"); 
			Stereotype auxiliaryStereotype = StereotypesHelper.getStereotype(project, "auxiliaryResource", umlProfile);
			return auxiliaryStereotype;
		}
		return null;
	}
	
	public static boolean isCustomization(Project project, Element element) {
		Profile umlStandardprofile = StereotypesHelper.getProfile(project,  "MagicDraw Profile");
		Stereotype customizationStereotype = StereotypesHelper.getStereotype(project, "Customization", umlStandardprofile);
		
		List<Stereotype> stereotypes = StereotypesHelper.getStereotypes(element);
		if(stereotypes.contains(customizationStereotype)) {
			return true;
		}
		return false;
	}
	
	public static Stereotype getModelLibraryStereotype() {
		
		return null;
	}
	
	public static boolean isModel(Element element, Project project) {
		if(element.equals(project.getPrimaryModel())) {
			return true;
		}
		return false;
	}
	
	public static boolean isProfile(Element element, Project project) {
		if(element instanceof Profile) {
			return true;
		}
		return false;
	}
	
	public static boolean isProperty(Element element, Project project) {
		if(element instanceof Property) {
			if(MDCustomizationForSysMLProfile.isPartProperty(element) || MDCustomizationForSysMLProfile.isReferenceProperty(element) ||MDCustomizationForSysMLProfile.isValueProperty(element)) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public static String getPseudoState(Element element) {
		try {
			Pseudostate pseudoState = (Pseudostate)element;
			PseudostateKind psKind = pseudoState.getKind();
			if(psKind.equals(PseudostateKindEnum.CHOICE)) {
				return SysmlConstants.CHOICEPSEUDOSTATE;
			} else if(psKind.equals(PseudostateKindEnum.DEEPHISTORY)) {
				return SysmlConstants.DEEPHISTORY;
			} else if(psKind.equals(PseudostateKindEnum.ENTRYPOINT)) {
				return SysmlConstants.ENTRYPOINT;
			} else if(psKind.equals(PseudostateKindEnum.EXITPOINT)) {
				return SysmlConstants.EXITPOINT;
			} else if(psKind.equals(PseudostateKindEnum.FORK)) {
				return SysmlConstants.FORK;
			} else if(psKind.equals(PseudostateKindEnum.INITIAL)) {
				return SysmlConstants.INITIALPSEUDOSTATE;
			} else if(psKind.equals(PseudostateKindEnum.JOIN)) {
				return SysmlConstants.JOIN;
			} else if(psKind.equals(PseudostateKindEnum.SHALLOWHISTORY)) {
				return SysmlConstants.SHALLOWHISTORY;
			} else if(psKind.equals(PseudostateKindEnum.TERMINATE)) {
				return SysmlConstants.TERMINATE;
			} else {
				return null;
			}
		} catch(ClassCastException cce) {
			return null;
		}
	}
	
	public static boolean isChoicePseudoState(Element element, Project project) {
		try {
			Pseudostate pseudoState = (Pseudostate)element;
			PseudostateKind psKind = pseudoState.getKind();
			if(psKind.equals(PseudostateKindEnum.CHOICE)) {
				return true;
			} else {
				return false;
			}
		} catch(ClassCastException cce) {
			return false;
		}
	}
//	
//	@SuppressWarnings("unlikely-arg-type")
//	public static boolean isQuantityKind(Element element, Project project) {
//		Profile mdCustomization = StereotypesHelper.getProfile(project,  "MD Customization for SysML");
//		Stereotype quantityKindStereotype = StereotypesHelper.getStereotype(project, "quantityKind", mdCustomization);
//		Collection<Stereotype> stereotypes = StereotypesHelper.getStereotypes(element);
//		if(Arrays.asList(stereotypes).contains(quantityKindStereotype)) {
//			CameoUtils.logGUI("Found Quantity Kind");
//			return true;
//		}
//		return false;
//	}
//	
//	@SuppressWarnings("unlikely-arg-type")
//	public static boolean isUnit(Element element, Project project) {
//		Profile mdCustomization = StereotypesHelper.getProfile(project, "additional_stereotypes");
//		Stereotype unitStereotype = StereotypesHelper.getStereotype(project, "unit", mdCustomization);
//		Collection<Stereotype> stereotypes = StereotypesHelper.getStereotypes(element);
//		if(Arrays.asList(stereotypes).contains(unitStereotype)) {
//			CameoUtils.logGUI("Found Unit");
//			return true;
//		}
//		return false;
//	}
	
	public static boolean isAssociationBlock(Element element, Project project) {
		//Add additional check for block stereotype
		if(element instanceof AssociationClass && isBlock(element, project)) {
			return true;
		}
		return false;
	}
	
	public static boolean isSysmlStereotypedElement(Element element, Project project, String stereotype) {
		Profile sysml = StereotypesHelper.getProfile(project,  "SysML");
		Collection<Stereotype> stereotypes = StereotypesHelper.getStereotypes(element);
		Stereotype blockStereotype = StereotypesHelper.getStereotype(project, stereotype, sysml);
		if(stereotypes.contains(blockStereotype)) { 
			return true;
		}
		return false;
	}
	
	public static boolean isCopy(Element element, Project project) {
		String stereotype = SysMLProfile.COPY_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isDeriveRequirement(Element element, Project project) {
		String stereotype = SysMLProfile.DERIVEREQT_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isRefine(Element element, Project project) {
		String stereotype = SysMLProfile.REFINE_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isSatisfy(Element element, Project project) {
		String stereotype = SysMLProfile.SATISFY_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isTrace(Element element, Project project) {
		String stereotype = SysMLProfile.TRACE_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isVerify(Element element, Project project) {
		String stereotype = SysMLProfile.VERIFY_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isBlock(Element element, Project project) {
		String stereotype = SysMLProfile.BLOCK_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isDesignConstraint(Element element, Project project) {
		String stereotype = SysMLProfile.DESIGNCONSTRAINT_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isExtendedRequirement(Element element, Project project) {
		String stereotype = SysMLProfile.EXTENDEDREQUIREMENT_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isFunctionalRequirement(Element element, Project project) {
		String stereotype = SysMLProfile.FUNCTIONALREQUIREMENT_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isInterfaceBlock(Element element, Project project) {
		String stereotype = SysMLProfile.INTERFACEBLOCK_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isInterfaceRequirement(Element element, Project project) {
		String stereotype = SysMLProfile.INTERFACEREQUIREMENT_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isPerformanceRequirement(Element element, Project project) {
		String stereotype = SysMLProfile.PERFORMANCEREQUIREMENT_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isPhysicalRequirement(Element element, Project project) {
		String stereotype = SysMLProfile.PHYSICALREQUIREMENT_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isRequirement(Element element, Project project) {
		String stereotype = SysMLProfile.REQUIREMENT_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isPartProperty(Element element, Project project) {
		String stereotype = MDCustomizationForSysMLProfile.PARTPROPERTY_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isValueProperty(Element element, Project project) {
		String stereotype = MDCustomizationForSysMLProfile.VALUEPROPERTY_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
	
	public static boolean isValueType(Element element, Project project) {
		String stereotype = SysMLProfile.VALUETYPE_STEREOTYPE;
		return isSysmlStereotypedElement(element, project, stereotype);
	}
}
