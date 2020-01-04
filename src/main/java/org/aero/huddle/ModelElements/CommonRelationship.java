package org.aero.huddle.ModelElements;

import java.util.Collection;
import java.util.List;

import org.aero.huddle.util.CameoUtils;
import org.w3c.dom.Document;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.magicdraw.activities.mdbasicactivities.ActivityEdge;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.DirectedRelationship;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.compositestructures.mdinternalstructures.Connector;
import com.nomagic.uml2.ext.magicdraw.compositestructures.mdinternalstructures.ConnectorEnd;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Extension;
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Transition;

public abstract class CommonRelationship {
	protected String name;
	protected String EAID;
	
	public CommonRelationship(String name, String EAID) {
		this.EAID = EAID;
		this.name = name;
	}
	
	public abstract Element createElement(Project project, Element owner, Element client, Element supplier);

	public org.w3c.dom.Element createBaseXML(Element element, Project project, Document xmlDoc) {
		return null;
	}
	
	public abstract void writeToXML(Element element, Project project, Document xmlDoc);
	
	public org.w3c.dom.Element createBaseXML(Element element, Document xmlDoc) {
		org.w3c.dom.Element data = xmlDoc.createElement("data");
		
		//Add attributes
		org.w3c.dom.Element attributes = xmlDoc.createElement("attributes");
		
		//Add Name
		if(!name.equals("") && !name.equals(null)) {
			org.w3c.dom.Element name = xmlDoc.createElement("name");
			name.appendChild(xmlDoc.createTextNode(this.name));
			attributes.appendChild(name);
		} else {
			org.w3c.dom.Element name = xmlDoc.createElement("name");
			attributes.appendChild(name);
		}
		
		//Add ID
		org.w3c.dom.Element id = xmlDoc.createElement("id");
		org.w3c.dom.Element cameoID = xmlDoc.createElement("cameo");
		cameoID.appendChild(xmlDoc.createTextNode(element.getLocalID()));
		id.appendChild(cameoID);
		data.appendChild(id);
		
		Element supplier = null;
		Element client = null;
		
		if(element instanceof DirectedRelationship) {
			//Get Supplier Element
			DirectedRelationship cameoRelationship = (DirectedRelationship)element;
			Collection<Element> suppliers = cameoRelationship.getSource();
			if(suppliers.iterator().hasNext()) {
				 supplier = suppliers.iterator().next();
			}

			//Get Client element	
			Collection<Element> clients = cameoRelationship.getTarget();
			if(clients.iterator().hasNext()) {
				 client = clients.iterator().next();
			}
		} else if(element instanceof ActivityEdge) {
			ActivityEdge cameoRelationship = (ActivityEdge)element;
			supplier = cameoRelationship.getSource();
			client = cameoRelationship.getTarget();
		} else if(element instanceof Transition) {
			Transition cameoRelationship = (Transition)element;
			supplier = cameoRelationship.getSource();
			client = cameoRelationship.getTarget();
		} else if(element instanceof Connector) {
			List<ConnectorEnd> connectorEnds = ((Connector)element).getEnd();
			if(connectorEnds.size() > 1) {
				CameoUtils.logGUI("Checking connector ends for supplier and client.");
				supplier = connectorEnds.get(0).getPartWithPort();
				client = connectorEnds.get(1).getPartWithPort();
				if(supplier == null) {
					supplier = connectorEnds.get(0).getRole();
				}
				if(client == null) {
					client = connectorEnds.get(1).getRole();
				}
			}
		} else if(element instanceof com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Association) {
			// Cameo reads client and supplier reverse for extensions for some reason
			if(element instanceof Extension) {
				supplier = ModelHelper.getClientElement(element);
				client = ModelHelper.getSupplierElement(element);
			} else {
				supplier = ModelHelper.getSupplierElement(element);
				client = ModelHelper.getClientElement(element);
			}
			
		} else {
			CameoUtils.logGUI("Unable to cast relationship to DirectedRelationship or ActivityEdge to find client and supplier.");
		}
		
		if(supplier != null) {
			org.w3c.dom.Element supplierID = xmlDoc.createElement("supplier_id");
			supplierID.appendChild(xmlDoc.createTextNode(supplier.getLocalID()));
			attributes.appendChild(supplierID);	
		} else {
			CameoUtils.logGUI("No supplier element found.");
		}
		
		if(client != null) {
			org.w3c.dom.Element clientID = xmlDoc.createElement("client_id");
			clientID.appendChild(xmlDoc.createTextNode(client.getLocalID()));
			attributes.appendChild(clientID);
		} else {
			CameoUtils.logGUI("No client element found");
		}
		
		//Add parent relationship
		org.w3c.dom.Element relationship = xmlDoc.createElement("relationships");
		
		if(element.getOwner() != null) {
			org.w3c.dom.Element hasParent = xmlDoc.createElement("hasParent");
			Element parent = null;
			
			if(element instanceof Transition) {
				Element region = element.getOwner();
				parent = region.getOwner();
			} else {
				parent = element.getOwner();
			}
			
			hasParent.appendChild(xmlDoc.createTextNode(parent.getLocalID()));
			relationship.appendChild(hasParent);
		}
		
		data.appendChild(attributes);
		data.appendChild(relationship);
		
		return data;
	}
}
