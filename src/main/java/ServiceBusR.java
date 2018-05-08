import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.documentation.*;
import com.structurizr.model.*;
import com.structurizr.view.*;
import java.util.*;

/**
 * A "getting started" example that illustrates how to
 * create a software architecture diagram using code.
 *
 * The live workspace is available to view at https://structurizr.com/share/25441
 */
public class ServiceBusR {

    private static final WorkspaceThree WORKSPACE = new WorkspaceThree();

    private static final long WORKSPACE_ID = WORKSPACE.id();
    private static final String API_KEY = WORKSPACE.key();
    private static final String API_SECRET = WORKSPACE.secret();

    private static final String MICROSERVICE_TAG = "Microservice";
    private static final String MESSAGE_BUS_TAG = "Message Bus";
    private static final String EXISTING_SYSTEM_TAG = "Existing System";
    private static final String STAFF_TAG = "Staff";
    private static final String DATABASE_TAG = "Database";
    public Model model;
 


    public static void main(String[] args) throws Exception {
        // Workspace
	
	    Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");
       Model model = workspace.getModel();
	ViewSet views = workspace.getViews();



        SoftwareSystem bus = model.addSoftwareSystem("Bus de Servicios", "");

        bus.addTags(MESSAGE_BUS_TAG);
        
	MonoliticoBus monolitico = new MonoliticoBus(workspace);
        ClienteServidorBus clienteServidor = new ClienteServidorBus(workspace);
        MulticapaBus multicapa = new MulticapaBus(workspace);

	monolitico.loadViews(workspace);
	clienteServidor.loadViews(workspace);
	multicapa.loadViews(workspace);

	SystemLandscapeView landscapeView = views.createSystemLandscapeView("Landscape", "");
	landscapeView.addAllElements();


	// Elements

      	uploadWorkspaceToStructurizr(workspace);
    }



    private static void uploadWorkspaceToStructurizr(Workspace workspace) throws Exception {
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }

 

  
}
