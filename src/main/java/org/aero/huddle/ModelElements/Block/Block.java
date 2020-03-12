package org.aero.huddle.ModelElements.Block;

import java.util.HashMap;

import org.aero.huddle.ModelElements.CommonElement;
import org.aero.huddle.util.XMLItem;
import org.aero.huddle.util.XmlTagConstants;
import org.w3c.dom.Document;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.sysml.util.SysMLProfile;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

public class Block extends CommonElement {
	public Block(String name, String EAID) {
		super(name, EAID);
	}
	
	@Override
	public Element createElement(Project project, Element owner, XMLItem xmlElement) {
		Profile sysmlProfile = StereotypesHelper.getProfile(project, "SysML"); 
		Stereotype blockStereotype = StereotypesHelper.getStereotype(project, SysMLProfile.BLOCK_STEREOTYPE, sysmlProfile);
		// find other stereotypes - Domain, External, etc.
//		if(xmlElement.hasStereotypes()) {
//			HashMap<String, String> stereotypes = xmlElement.getStereotypes();
//			for()
//			Profile profile = 
//		}
		
		if (blockStereotype != null) {
			return createClassWithStereotype(project, name, blockStereotype, owner);
		}
		return null;
	}

	@Override
	public void writeToXML(Element element, Project project, Document xmlDoc) {
		org.w3c.dom.Element data = createBaseXML(element, xmlDoc);
		
//		org.w3c.dom.Element attributes = getAttributes(data.getChildNodes());
		
		// Create type field for Sysml model element types
		org.w3c.dom.Element type = xmlDoc.createElement("type");
		type.appendChild(xmlDoc.createTextNode(XmlTagConstants.BLOCK));
		data.appendChild(type);
		
		org.w3c.dom.Element root = (org.w3c.dom.Element) xmlDoc.getFirstChild();
		root.appendChild(data);
	}
}
