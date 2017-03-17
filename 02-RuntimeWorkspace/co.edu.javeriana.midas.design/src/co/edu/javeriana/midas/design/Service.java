package co.edu.javeriana.midas.design;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.query.EObjectQuery;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.ui.business.api.dialect.DialectUIManager;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.ViewpointPackage;

import co.edu.javeriana.midas.midas.FunctionCall;
import co.edu.javeriana.midas.midas.FunctionDefinition;
import co.edu.javeriana.midas.midas.MidasFactory;
import co.edu.javeriana.midas.midas.TypeSpecification;
import co.edu.javeriana.midas.midas.VarReference;
import co.edu.javeriana.midas.midas.Variable;


public class Service {
	
	public static Stack<DRepresentation> representationsStack= new Stack<DRepresentation>();
	
	public EList<EObject> getInputVarReferences(EObject self){
		((FunctionCall) self).getInputVarReferences().clear();
		
		EList<EObject> newList = new BasicEList<EObject>();
		
		EList<Variable> varList= ((FunctionCall) self).getCallee().getInputVars();
		for(int i=0;i<varList.size();i++){
			VarReference var= MidasFactory.eINSTANCE.createVarReference();
			var.setName(varList.get(i).getName());
			newList.add(var);
		}
		return newList;
	}
	
	public EList<EObject> getOutputVarReferences(EObject self){
		((FunctionCall) self).getOutputVarReferences().clear();
		
		EList<EObject> newList = new BasicEList<EObject>();
		
		EList<Variable> varList= ((FunctionCall) self).getCallee().getOutputVars();
		for(int i=0;i<varList.size();i++){
			VarReference var= MidasFactory.eINSTANCE.createVarReference();
			var.setName(varList.get(i).getName());
			newList.add(var);
		}
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
			if(next instanceof VarReference){
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
				if(next instanceof FunctionDefinition) {
					String attribute = getAttributeName(next);
					if (typeTrimed.equals("FunctionDefinition")){
						if(attribute.equals(nameTrimed)){
							return (FunctionDefinition) next;
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
	
	public EList<EObject> getAvalilableFunctions(EObject self) {
		EList<EObject> newList = new BasicEList<EObject>();
		EList<Resource> resourceList = self.eResource().getResourceSet().getResources();
		Iterator<Resource> itResourceList = resourceList.iterator();
		while (itResourceList.hasNext()) {
			Resource resource = (Resource) itResourceList.next();
			TreeIterator<EObject> eAllContents= resource.getAllContents();
			while (eAllContents.hasNext()) {
				EObject next= eAllContents.next();
				if(next instanceof FunctionDefinition) {
					String a = getAttributeName(next);
					if (!a.equals("Main")) {
						newList.add(next);
					}
				}
			}
		}
		return newList;
	}
}