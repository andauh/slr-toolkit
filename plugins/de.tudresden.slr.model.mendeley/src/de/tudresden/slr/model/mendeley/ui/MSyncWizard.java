package de.tudresden.slr.model.mendeley.ui;


import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.jbibtex.BibTeXEntry;
import de.tudresden.slr.model.bibtex.util.BibtexResourceImpl;
import de.tudresden.slr.model.mendeley.api.authentication.MendeleyClient;
import de.tudresden.slr.model.mendeley.api.model.*;
import de.tudresden.slr.model.mendeley.synchronisation.WorkspaceManager;
import de.tudresden.slr.model.mendeley.util.*;
import de.tudresden.slr.model.modelregistry.ModelRegistryPlugin;

public class MSyncWizard extends Wizard {

	protected MSyncWizardPage zero;
	protected MSyncWizardPageOne one;
    protected MSyncWizardPageTwo two;
    protected MSyncWizardPageThree three;
    protected AdapterFactoryEditingDomain editingDomain;
    private MendeleyClient mc;
    private WorkspaceManager wm;

    public MSyncWizard() {
        super();
        setNeedsProgressMonitor(true);
        mc = MendeleyClient.getInstance();
        wm.getInstance();
    }

    @Override
    public String getWindowTitle() {
        return "Export My Data";
    }

    @Override
    public void addPages() {
    	zero = new MSyncWizardPage();
    	one = new MSyncWizardPageOne();
        two = new MSyncWizardPageTwo();
        three = new MSyncWizardPageThree();
        addPage(zero);
        addPage(one);
        addPage(two);
        addPage(three);
    }

    @Override
    public boolean performFinish() {
    	List<SyncItem> syncItems = three.getSyncItems();
    	
		ModelRegistryPlugin.getModelRegistry().getEditingDomain().ifPresent((domain) -> editingDomain = domain);
		Resource resource  = editingDomain.getResourceSet().getResources().get(0);
		BibtexResourceImpl bibResource = (BibtexResourceImpl)resource;
		URI uri = bibResource.getURI();
    	
		
		for(SyncItem si : syncItems){
    		MendeleyDocument document = one.getFolder_selected().getDocumentById(si.getId());
    		document.updateFields(si.getSelectedFields());
    		mc.updateDocument(document);
    	}
    	
    	List<BibTeXEntry> entries = two.getMissingInMendeley();
    	
    	for(BibTeXEntry entry : entries){
    		MendeleyDocument document = mc.addDocument(entry);
    		mc.addDocumentToFolder(document, one.getFolder_selected().getId());
    	}
    	
    	
		this.zero.getResourceSelected().setMendeleyFolder(one.getFolder_selected());
    	wm.addWorkspaceBibtexEntry(this.zero.getResourceSelected());
    	wm.updateWorkspaceBibTexEntry(this.zero.getResourceSelected());
   
    	
        return true;
    }
    
    @Override
    public IWizardPage getNextPage(IWizardPage page) {
    	return super.getNextPage(page);
    }  
    
}