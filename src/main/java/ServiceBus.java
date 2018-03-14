import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.documentation.*;
import com.structurizr.model.*;
import com.structurizr.view.*;

/**
 * A "getting started" example that illustrates how to
 * create a software architecture diagram using code.
 *
 * The live workspace is available to view at https://structurizr.com/share/25441
 */
public class ServiceBus {

    private static final long WORKSPACE_ID = 38549;
    private static final String API_KEY = "fa603ed4-775b-44e6-9d69-5ee8518d881b";
    private static final String API_SECRET = "44e200f6-b14f-420a-a1c3-715c2c70bf1b";

    private static final String MICROSERVICE_TAG = "Microservice";
    private static final String MESSAGE_BUS_TAG = "Message Bus";
    private static final String EXISTING_SYSTEM_TAG = "Existing System";
    private static final String STAFF_TAG = "Staff";
    private static final String DATABASE_TAG = "Database";



    public static void main(String[] args) throws Exception {
        // Workspace
	
	    Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");
        Model model = workspace.getModel();
	ViewSet views = workspace.getViews();



        Workspace workspaceMonolitico = new Monolitico().create();
        Workspace workspaceClienteServidor = new ClienteServidor().create();
        Workspace workspaceMulticapa = new Multicapa().create();
        Workspace workspaceMicroservicios = new Microservicios().create();
        	
	
	workspaceMonolitico.getViews().getSystemLandscapeViews().stream().forEach(c -> views.createSystemLandscapeView(c.getKey(), c.getDescription()));

	workspaceMonolitico.getViews().getSystemContextViews().stream().forEach(c -> views.createSystemContextView(c.getKey(), c.getDescription()));
	workspaceMonolitico.getViews().getComponentViews().stream().forEach(c -> views.createComponentView(c.getKey(), c.getDescription()));
	
	workspaceMonolitico.getViews().getContainerViews().stream().forEach(c -> views.createContainerView(c.getKey(), c.getDescription()));

	//SystemLandscapeView systemView = views.createSystemLandscapeView("Sistema", "");
//        systemView.addAllElements();


	// Elements
	
       	uploadWorkspaceToStructurizr(workspace);
    }

 

    private static void uploadWorkspaceToStructurizr(Workspace workspace) throws Exception {
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }

}
