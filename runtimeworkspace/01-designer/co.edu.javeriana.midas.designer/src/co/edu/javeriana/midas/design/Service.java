package co.edu.javeriana.midas.design;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.dialect.command.RefreshRepresentationsCommand;
import org.eclipse.sirius.business.api.query.EObjectQuery;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.ui.business.api.dialect.DialectUIManager;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.ViewpointPackage;
import co.edu.javeriana.midas.midas.ComputationNode;
import co.edu.javeriana.midas.midas.Port;
import co.edu.javeriana.midas.midas.PortDefinition;
import co.edu.javeriana.midas.midas.RequirementSpecification;
import co.edu.javeriana.midas.midas.ComputationNodeDefinition;
import co.edu.javeriana.midas.midas.MidasFactory;
import co.edu.javeriana.midas.midas.TypeSpecification;
import co.edu.javeriana.midas.midas.Variable;

public class Service {
	
	public static Stack<DRepresentation> representationsStack= new Stack<DRepresentation>();
	
	public void updateModel(EObject self) {
		Session session = SessionManager.INSTANCE.getSession(self); 
		TransactionalEditingDomain domain = session.getTransactionalEditingDomain();
		Collection<DRepresentation> allRepresentations = DialectManager.INSTANCE.getAllRepresentations(session);
		Command cmd = new RefreshRepresentationsCommand(domain, null, allRepresentations);
		domain.getCommandStack().execute(cmd);
	}
	
	public String setDependencyType(EObject self, String type) {
		updateModel(self);
		return type;
	}
	
	public EList<EObject> getInputPortReferences(EObject self){
		((ComputationNode) self).getInputPorts().clear();
		EList<EObject> newList = new BasicEList<EObject>();
		EList<PortDefinition> varList= ((ComputationNode) self).getCallee().getInputPortDefinitions();
		for(int i=0;i<varList.size();i++){
			Port var= MidasFactory.eINSTANCE.createPort();
			var.setName(varList.get(i).getName());
			newList.add(var);
		}
		return newList;
	}
	
	public EList<EObject> getOutputPortReferences(EObject self){
		((ComputationNode) self).getOutputPorts().clear();
		
		EList<EObject> newList = new BasicEList<EObject>();
		EList<PortDefinition> varList= ((ComputationNode) self).getCallee().getOutputPortDefinitions();
		for(int i=0;i<varList.size();i++){
			Port var= MidasFactory.eINSTANCE.createPort();
			var.setName(varList.get(i).getName());
			newList.add(var);
		}
		return newList;
	}
	
	public EList<Variable> getVariables(EObject self){
		EList<Variable> newList = new BasicEList<Variable>();
		
		//newList= ((RequirementSpecification)self.eContainer()).getVariables();
		return newList;
	}
	
	
	
	public String getName(EObject self, EObject i){
		String attribute=getAttributeName(i);
		System.out.println("getName: "+attribute);
		return attribute;
	}
	
	public boolean isValid(EObject self){
		EList<EObject> list= new BasicEList<EObject>();
		Resource e=self.eResource();
		TreeIterator<EObject> eAllContents= e.getAllContents();
		while (eAllContents.hasNext()) {
			EObject next = eAllContents.next();
			if(next instanceof Port){
				list.add(next);
			}
		}
		return isUnique(list);
	}
	
	public boolean isUnique(EList<EObject> list){
		for(int i=0;i<list.size();i++){
			for(int j=i+1;j<list.size();j++){
				String a1=getAttributeName(list.get(i));
				String b1=getAttributeName(list.get(j));
				if(a1.equals(b1)){
					return false;
				}
			}
		}
		return true;
	}
	
	public String getAttributeName(EObject object){
		EList<EAttribute> eAllAttributes=object.eClass().getEAllAttributes();
		for (EAttribute eAttribute : eAllAttributes) {
			if (eAttribute.getName().equals("name")) {
				return object.eGet(eAttribute).toString().trim();
			}
		}
		return "";
	}
	
	public EObject returnAncestor(EObject self){
		Session session = SessionManager.INSTANCE.getSession(self);
		if(!representationsStack.isEmpty()){
			DRepresentation dp=representationsStack.pop();
			DialectUIManager.INSTANCE.openEditor(session, dp, new NullProgressMonitor());
		}
		return self;
	}

	public EObject openRepresentation(EObject self) {
		//URI diagramURI = URI.createURI("/co.edu.javeriana.midas.model.ashyi/representations.aird");
		pushStack(self);
		Session session = SessionManager.INSTANCE.getSession(self);
		
		Collection<DRepresentation> dr = DialectManager.INSTANCE.getAllRepresentations(session);

		Iterator<DRepresentation> it = dr.iterator();
		while (it.hasNext()) {
			DRepresentation dp = it.next();
			
			String attribute= getAttributeName(dp);
			if(attribute.equals("createAdaptedPlanView")){
				DialectUIManager.INSTANCE.openEditor(session, dp, new NullProgressMonitor());
			}
		}
		return self;
	}
	
	public void pushStack(EObject self){
		Collection<EObject> result = new EObjectQuery(self).getInverseReferences(ViewpointPackage.Literals.DSEMANTIC_DECORATOR__TARGET);
		DRepresentation dp= (DRepresentation) result.iterator().next().eContainer();
		representationsStack.push(dp); 
	}
	
	public String trim(EObject self, String str) {
		return str.trim();
	}

	public EObject getModelElement(EObject self, String name, String type) {
		String nameTrimed = name.trim();
		String typeTrimed = type.trim(); 
		EList<Resource> resourceList = self.eResource().getResourceSet().getResources();
		Iterator<Resource> itResourceList = resourceList.iterator();
		while (itResourceList.hasNext()) {
			Resource resource = (Resource) itResourceList.next();
			TreeIterator<EObject> eAllContents= resource.getAllContents();
			while (eAllContents.hasNext()) {
				EObject next= eAllContents.next();
				if(next instanceof ComputationNodeDefinition) {
					String attribute = getAttributeName(next);
					if (typeTrimed.equals("ComputationNodeDefinition")){
						if(attribute.equals(nameTrimed)){
							return (ComputationNodeDefinition) next;
						}
					}
				}else if (next instanceof TypeSpecification){
					String attribute = getAttributeName(next);
					if (typeTrimed.equals("TypeSpecification")){
						if(attribute.equals(nameTrimed)){
							return (TypeSpecification) next;
						}
					}
				}
			}
		}
		return null;
	}
	
	public EObject getSource(EObject self, EObject source, EObject target) {
		System.out.println("------------------------------------");
		System.out.println("Source: "  + source);
		System.out.println("Target: "  + target);
		System.out.println("------------------------------------");
		return source;
	}
	
	public EObject getTarget(EObject self, EObject source, EObject target) {
		System.out.println("------------------------------------");
		System.out.println("Source: "  + source);
		System.out.println("Target: "  + target);
		System.out.println("------------------------------------");
		return target;
	}
	
	public EList<EObject> getAvalilableNodes(EObject self) {
		EList<EObject> newList = new BasicEList<EObject>();
		EList<Resource> resourceList = self.eResource().getResourceSet().getResources();
		Iterator<Resource> itResourceList = resourceList.iterator();
		while (itResourceList.hasNext()) {
			Resource resource = (Resource) itResourceList.next();
			TreeIterator<EObject> eAllContents= resource.getAllContents();
			while (eAllContents.hasNext()) {
				EObject next= eAllContents.next();
				if(next instanceof ComputationNodeDefinition) {
					String a = getAttributeName(next);
					if (!a.equals("Main")) {
						newList.add(next);
					}
				}
			}
		}
		return newList;
	}
	
	public EList<EObject> getTypeSpecification(EObject self) {
		EList<EObject> newList = new BasicEList<EObject>();
		EList<Resource> resourceList = self.eResource().getResourceSet().getResources();
		Iterator<Resource> itResourceList = resourceList.iterator();
		while (itResourceList.hasNext()) {
			Resource resource = (Resource) itResourceList.next();
			TreeIterator<EObject> eAllContents= resource.getAllContents();
			while (eAllContents.hasNext()) {
				EObject next= eAllContents.next();
				if(next instanceof TypeSpecification) {
					newList.add(next);
				}
			}
		}
		return newList;
	}
}