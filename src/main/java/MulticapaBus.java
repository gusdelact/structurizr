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
public class MulticapaBus {

    private static final long WORKSPACE_ID = 38550;
    private static final String API_KEY = "134d5f9f-5a5d-4913-9944-bdb3bfd7a8f3";
    private static final String API_SECRET = "f9f881a3-6d26-41fe-b310-c9b0f46d2e1c";

    private static final String MICROSERVICE_TAG = "Microservice";
    private static final String MESSAGE_BUS_TAG = "Message Bus";
    private static final String EXISTING_SYSTEM_TAG = "Existing System";
    private static final String STAFF_TAG = "Staff";
    private static final String DATABASE_TAG = "Database";

    private static SoftwareSystem bus;   
    private static SoftwareSystem sistema;
    private static Container interfazContenedor;
    private static Container presentacionContenedor;
    private static Container logicaContenedor;
    private static Container persistenciaContenedor;
    private static Component interfaz;
    private static Component presentacion;
    private static Component logica;
    private static Component persistencia;



    MulticapaBus(Workspace workspace) { 
        // Workspace
        Model model = workspace.getModel();
	ViewSet views = workspace.getViews();

	// Elements
        
        bus = model.getSoftwareSystemWithName("Bus de Servicios");
       
	sistema = model.addSoftwareSystem("Sistema Multicapa", "Desc...");

	interfazContenedor = sistema.addContainer("Contenedor Interfaz de usuario", "Desc...", "");
	presentacionContenedor = sistema.addContainer("Contenedor Presentacion", "Desc...", "");
        logicaContenedor = sistema.addContainer("Contenedor Logica", "Desc...", "");
	persistenciaContenedor = sistema.addContainer("Contenedor Persistencia", "Desc...", "...");

	interfaz = interfazContenedor.addComponent("Interfaz de usuario", "");
	presentacion = presentacionContenedor.addComponent("Presentacion", "");
        logica =logicaContenedor.addComponent("Logica", "");
	persistencia = persistenciaContenedor.addComponent("Persistencia", "");

	//Elements Tags


	// Connections
  
	sistema.uses(bus, "");
        interfazContenedor.uses(bus, "");
	interfaz.uses(bus, "");
	interfazContenedor.uses(presentacionContenedor, "Usa");
	interfazContenedor.uses(presentacion, "Usa");
	interfaz.uses(presentacionContenedor, "Usa");
	presentacionContenedor.uses(logicaContenedor, "Usa");
	presentacionContenedor.uses(logica, "Usa");
	presentacion.uses(logicaContenedor, "Usa");
	logicaContenedor.uses(persistenciaContenedor, "Usa");
	logicaContenedor.uses(persistencia, "Usa");
	logica.uses(persistenciaContenedor, "Usa");
    }


    public static void loadViews (Workspace workspace) { 
	//Landscape view
	
	Model model = workspace.getModel();
	ViewSet views = workspace.getViews();


	SystemContextView contextView = views.createSystemContextView(sistema, "Sistema", "");
	contextView.addNearestNeighbours(sistema);

	ContainerView containerView = views.createContainerView(sistema, "Contenedores", "");
	containerView.addAllElements();
	
	ComponentView interfazComponentView = views.createComponentView(interfazContenedor, "Interfaz Componentes", "");
	interfazComponentView.addAllElements();

	ComponentView presentacionComponentView = views.createComponentView(presentacionContenedor, "Presentacion Componentes", "");
	presentacionComponentView.addAllElements();

	ComponentView logicaComponentView = views.createComponentView(logicaContenedor, "Logica Componentes", "");
	logicaComponentView.addAllElements();
	
	ComponentView persistenciaComponentView = views.createComponentView(persistenciaContenedor, "Persistencia Componentes", "");
	persistenciaComponentView.addAllElements();
	
	
	
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


/*    public static void main(String[] args) throws Exception {
	    
	uploadWorkspaceToStructurizr(create());
    }*/
}
