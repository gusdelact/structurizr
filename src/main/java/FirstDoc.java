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
public class FirstDoc {

    private static final long WORKSPACE_ID = 38550;
    private static final String API_KEY = "134d5f9f-5a5d-4913-9944-bdb3bfd7a8f3";
    private static final String API_SECRET = "f9f881a3-6d26-41fe-b310-c9b0f46d2e1c";

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

	// Elements
	
        //model.setEnterprise(new Enterprise("Big Bank plc"));


        SoftwareSystem requerimientos = model.addSoftwareSystem("Requerimientos", "Desc ...");

        SoftwareSystem funcionales = model.addSoftwareSystem("Funcionales", "Desc ...");

        SoftwareSystem tecnologicos = model.addSoftwareSystem("Tecnologicos", "Desc ...");


        SoftwareSystem capacidad = model.addSoftwareSystem("Capacidad", "Desc ...");

        SoftwareSystem disponibilidad = model.addSoftwareSystem("Disponibilidad", "Desc ...");

        SoftwareSystem seguridad = model.addSoftwareSystem("Seguridad", "Desc ...");

        SoftwareSystem continuidad = model.addSoftwareSystem("Continuidad", "Desc ...");

        SoftwareSystem mantenibilidad = model.addSoftwareSystem("Mantenibilidad", "Desc ...");

        SoftwareSystem operacion = model.addSoftwareSystem("Operacion", "Desc ...");

        SoftwareSystem usabilidad = model.addSoftwareSystem("Usabilidad", "La solucion debe ser facil de utilizar por el usuario");

        SoftwareSystem internacional = model.addSoftwareSystem("Internacional", "Desc ...");

	
        SoftwareSystem diseno = model.addSoftwareSystem("Diseno de la aplicacion", "Desc ...");

        SoftwareSystem administracion = model.addSoftwareSystem("Administracion del proyecto", "Desc ...");


        SoftwareSystem alcance = model.addSoftwareSystem("Alcance", "Desc ..."); 
        SoftwareSystem tiempo = model.addSoftwareSystem("Tiempo", "Desc ...");
        SoftwareSystem costo = model.addSoftwareSystem("Costo", "Desc ...");

        SoftwareSystem calidad = model.addSoftwareSystem("Calidad del proyecto", "Desc ..."); 
        SoftwareSystem riesgos = model.addSoftwareSystem("Riesgos", "Desc ...");	
        SoftwareSystem rec_humanos = model.addSoftwareSystem("Recursos humanos", "Desc ...");
        SoftwareSystem proveedores = model.addSoftwareSystem("proveedores", "Desc ...");
        SoftwareSystem involucrados = model.addSoftwareSystem("Involucrados", "Desc ...");
        SoftwareSystem comunicacion = model.addSoftwareSystem("Comunicacion", "Desc ...");

        SoftwareSystem especificacion = model.addSoftwareSystem("Espicificacion Logica = Arquitectura de Software", "Desc ...");
	



	// Connections


	requerimientos.uses(funcionales, "Se divide en");
	requerimientos.uses(tecnologicos, "Se divde en");

	tecnologicos.uses(capacidad, "Toma en cuenta");	
	tecnologicos.uses(disponibilidad, "Toma en cuenta");
	tecnologicos.uses(seguridad, "Toma en cuenta");	
	tecnologicos.uses(continuidad, "Toma en cuenta");
	tecnologicos.uses(mantenibilidad, "Toma en cuenta");	
	tecnologicos.uses(operacion, "Toma en cuenta");
	tecnologicos.uses(usabilidad, "Toma en cuenta");	
	tecnologicos.uses(internacional, "Toma en cuenta");

	administracion.uses(alcance, "Toma en cuenta");
	administracion.uses(tiempo, "Toma en cuenta");
	administracion.uses(costo, "Toma en cuenta");
	administracion.uses(calidad, "Toma en cuenta");
	administracion.uses(riesgos, "Toma en cuenta");
	administracion.uses(rec_humanos, "Toma en cuenta");

	administracion.uses(proveedores, "Toma en cuenta");
	administracion.uses(involucrados, "Toma en cuenta");
	administracion.uses(comunicacion, "Toma en cuenta");
	
	requerimientos.uses(diseno, "Se apoya");
	administracion.uses(diseno, "Se apoya");

	diseno.uses(especificacion, "");


	//Landscape view
	

	SystemLandscapeView systemLandscapeView = views.createSystemLandscapeView("SystemLandscape", "");
        systemLandscapeView.addAllElements();


	SystemContextView requerimientosContext = views.createSystemContextView(requerimientos, "Requerimienntos", "");
	requerimientosContext.addNearestNeighbours(requerimientos);

	SystemContextView tecnologicosContext = views.createSystemContextView(tecnologicos, "Requerimienntos Tecnologicos", "");
	tecnologicosContext.addNearestNeighbours(tecnologicos);

	SystemContextView disenoContext = views.createSystemContextView(diseno, "Diseno", "");
	disenoContext.addNearestNeighbours(diseno);

	SystemContextView administracionContext = views.createSystemContextView(administracion, "Administracion", "");
	administracionContext.addNearestNeighbours(administracion);



        // add some styling to the diagram elements

	addStylingToDiagrams(views);

        // upload to structurizr.com (you'll need your own workspace ID, API key and API secret)

	uploadWorkspaceToStructurizr(workspace);
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

}
