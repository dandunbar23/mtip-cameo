package org.aero.huddle.util;

public class SysmlConstants {
	//Categorization constants
	public static final String ELEMENT = "Element";
	public static final String RELATIONSHIP = "Relationship";
	public static final String DIAGRAM = "Diagram";
	
	//Sysml Element constants
	public static final String ACTIVITY = "Activity";
	public static final String ACTIVITYPARAMETERNODE = "ActivityParameter"; // Sysml specifies as ActivityParameterNode
	public static final String BLOCK = "Block";
	public static final String CLASS = "Class";
	public static final String FINALSTATE = "FinalState";
	public static final String INITIALPSEUDOSTATE = "InitialPseudoState";
	public static final String OPERATION = "Operation";
	public static final String PACKAGE = "Package";
	public static final String PORT = "Port";
	public static final String STATE = "State";
	public static final String STATEMACHINE = "StateMachine";
	public static final String VALUEPROPERTY = "ValueProperty";
	public static final String PROPERTY = "Property";
	public static final String INTERFACEBLOCK = "InterfaceBlock";
	public static final String VALUETYPE = "ValueType";
	public static final String STEREOTYPE = "Stereotype";
	
	public static final String MODEL = "Model";
	public static final String PartProperty = "PartProperty";
//	public static final String TRIGGER = "Trigger";
	public static final String SIGNAL = "Signal";
//	public static final String NOTE = "Note";
	
//	public static final String PROFILE = "Profile";
//	public static final String PARAMETER = "Parameter";
	
	//Sysml Relationship Constants
	public static final String ABSTRACTION = "Abstraction";
	public static final String AGGREGATION = "Aggregation";
	public static final String ALLOCATE = "Allocate";
	public static final String ASSOCIATION = "Association";
	public static final String CONNECTOR = "Connector";
	public static final String GENERALIZATION = "Generalization";
	public static final String TRANSITION = "Transition";
	
	
	//Sysml Diagram constants
	public static final String BDD = "BlockDefinitionDiagram";
//	public static final String IBD = "InternalBlockDiagram";
//	public static final String PKG = "PackageDiagram";
//	public static final String REQ = "RequirementsDiagram";
//	public static final String PAR = "ParametricDiagram";
	public static final String STM = "StateMachineDiagram";
	public static final String ACT = "ActivityDiagram";
//	public static final String SEQ = "SequenceDiagram";
//	public static final String UC = "UseCaseDiagram";
	
	public static final String[] SYSMLELEMENTS = {
			ACTIVITY,
			ACTIVITYPARAMETERNODE,
			BLOCK,
			CLASS,
			INITIALPSEUDOSTATE,
			INTERFACEBLOCK,
			FINALSTATE,
			OPERATION,
			PACKAGE,
			PORT,
			PROPERTY,
			SIGNAL,
			STATE,
			STATEMACHINE,
			STEREOTYPE,
			VALUEPROPERTY,
			VALUETYPE,			
	};
	
	public static final String[] SYSMLRELATIONSHIPS = {
			ABSTRACTION,
			AGGREGATION,
			ALLOCATE,
			ASSOCIATION,
//			CONNECTOR,
			GENERALIZATION,
			TRANSITION	
	};

	public static final String[] SYSMLDIAGRAMS = {
			BDD,
			STM,
			ACT,
	};
}
