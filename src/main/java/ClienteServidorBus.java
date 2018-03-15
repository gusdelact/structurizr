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
public class ClienteServidorBus {

    private static final long WORKSPACE_ID = 38549;
    private static final String API_KEY = "fa603ed4-775b-44e6-9d69-5ee8518d881b";
    private static final String API_SECRET = "44e200f6-b14f-420a-a1c3-715c2c70bf1b";

    private static final String MICROSERVICE_TAG = "Microservice";
    private static final String MESSAGE_BUS_TAG = "Message Bus";
    private static final String EXISTING_SYSTEM_TAG = "Existing System";
    private static final String STAFF_TAG = "Staff";
    private static final String DATABASE_TAG = "Database";
    private static SoftwareSystem bus;
    private static SoftwareSystem sistema;
    private static Container cliente;
    private static Container servidor;
    private static Component interfaz;
    private static Component logica;
    private static Component persistencia;



    ClienteServidorBus (Workspace workspace) { 
        // Workspace
        Model model = workspace.getModel();
	ViewSet views = workspace.getViews();


	// Elements
	
        bus = model.getSoftwareSystemWithName("Bus de Servicios");

        sistema = model.addSoftwareSystem("Sistema Cliente Servidor", "Desc...");

        cliente = sistema.addContainer("Cliente", "Desc...","");
        servidor = sistema.addContainer("Servidor", "Desc...","");
        
       
	interfaz = cliente.addComponent("Interfaz de usuario", "", "");
        logica = cliente.addComponent("Logica", "", "");
	persistencia = servidor.addComponent("Persistencia", "", "");
	//Elements Tags


	// Connections
  
	sistema.uses(bus, "");
        cliente.uses(bus, "");
        interfaz.uses(bus, "");
	cliente.uses(servidor, "");
	cliente.uses(persistencia, "");
	logica.uses(servidor, "");
    }
	//Landscape view

    public static void loadViews (Workspace workspace) { 
	
    	Model model = workspace.getModel();
	ViewSet views = workspace.getViews();

	    
	SystemContextView contextView = views.createSystemContextView(sistema, "ClienteServidorR Context", "");

	contextView.addNearestNeighbours(sistema);

	ContainerView clienteContainerView = views.createContainerView(sistema, "Cliente Container", "");
	clienteContainerView.addAllElements();
	
	ComponentView clienteComponentView = views.createComponentView(cliente, "Cliente Component", "");
	clienteComponentView.addAllElements();
	
	ComponentView servidorComponentView = views.createComponentView(servidor, "Servidor Component", "");
	servidorComponentView.addAllElements();
	


        // add some styling to the diagram elements

	addStylingToDiagrams(views);

        // upload to structurizr.com (you'll need your own workspace ID, API key and API secret)
	

    }







    private static void addStylingToDiagrams(ViewSet views) {
    
        Styles styles = views.getConfiguration().getStyles();

	styles.addElementStyle(Tags.ELEMENT).color("#ffffff");
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd");
        styles.addElementStyle(Tags.CONTAINER).background("#438dd5");
        styles.addElementStyle(Tags.COMPONENT).background("#85bbf0").color("#000000");
        styles.addElementStyle(Tags.PERSON).background("#08427b").shape(Shape.Person).fontSize(22);
        styles.addElementStyle(EXISTING_SYSTEM_TAG).background("#999999");
        styles.addElementStyle(STAFF_TAG).background("#999999");
        styles.addElementStyle(DATABASE_TAG).shape(Shape.Cylinder);
        styles.addElementStyle("Failover").opacity(25);
        styles.addRelationshipStyle("Failover").opacity(25).position(70);

	styles.addElementStyle(MESSAGE_BUS_TAG).width(1600).shape(Shape.Pipe);
	styles.addElementStyle(MICROSERVICE_TAG).shape(Shape.Hexagon);
	styles.addRelationshipStyle(Tags.RELATIONSHIP).routing(Routing.Orthogonal);


    }

    private static void uploadWorkspaceToStructurizr(Workspace workspace) throws Exception {
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }


    /*public static void main(String[] args) throws Exception {

        Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");
        Model model = workspace.getModel();
	ViewSet views = workspace.getViews();

	uploadWorkspaceToStructurizr(create(workspace, model, views));
    }*/
}
