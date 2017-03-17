package co.edu.javeriana.midas.misc;

import java.io.File;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent;
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;

public class CreateFolders implements IWorkflowComponent {
	public void invoke(IWorkflowContext arg0) {		
		String folder[]={"../co.edu.javeriana.midas/src-gen",
	 		"../co.edu.javeriana.midas.ide/src",
	 		"../co.edu.javeriana.midas.ide/xtend-gen",
	 		"../co.edu.javeriana.midas/xtend-gen",
			"../co.edu.javeriana.midas.tests/src",
			"../co.edu.javeriana.midas.tests/src-gen",
			"../co.edu.javeriana.midas.tests/xtend-gen",
			"../co.edu.javeriana.midas.ui/src-gen",
			"../co.edu.javeriana.midas.ui/xtend-gen",
			"../co.edu.javeriana.midas.ui.tests/src",
			"../co.edu.javeriana.midas.ui.tests/xtend-gen",
	  	};
	 	for (int i = 0 ; i < folder.length; i++) {
	 		File dir = new File(folder[i]);
			dir.mkdir();
			System.out.println("Folder " + folder[i] + " generated");
        }
	}

	@Override
	public void postInvoke() {}

	@Override
	public void preInvoke() {}

}
