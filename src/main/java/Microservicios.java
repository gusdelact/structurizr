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
public class Microservicios {


    private static final long WORKSPACE_ID = 38565;
    private static final String API_KEY = "9a853f81-19ed-475d-a218-40022362c8b2";
    private static final String API_SECRET = "23bf7a7e-2c0b-4e57-9143-71c386922ee8";

    private static final String MICROSERVICE_TAG = "Microservice";
    private static final String MESSAGE_BUS_TAG = "Message Bus";
    private static final String EXISTING_SYSTEM_TAG = "Existing System";
    private static final String STAFF_TAG = "Staff";
    private static final String DATABASE_TAG = "Database";
    private static final String SERVICES_TAG = "services";



    public static void main(String[] args) throws Exception {
        // Workspace
        Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");
        Model model = workspace.getModel();
	ViewSet views = workspace.getViews();

	// Elements
        
        Person user = model.addPerson("Usuario", "");
       
	SoftwareSystem multicanal = model.addSoftwareSystem("Multicanal", "Web, Moviles, Smart Devices");

	Container google = multicanal.addContainer("API Web Google", "Desc...", "");
	Container empresarial = multicanal.addContainer("API Web Empresarial", "Desc...", "");
        Container twitter = multicanal.addContainer("API Web Twitter", "Desc...", "");

	Component serviciosGoogleFirst = google.addComponent("Servicio 1", "");
	Component serviciosGoogleSecond = google.addComponent("Servicio 2", "");
	Component serviciosGoogleThird = google.addComponent("Servicio 3", "");
	Component serviciosEmpresarialFirst = empresarial.addComponent("Servicio 1", "");
	Component serviciosEmpresarialSecond = empresarial.addComponent("Servicio 2", "");
	Component serviciosEmpresarialThird = empresarial.addComponent("Servicio 3", "");
	Component dataBaseEmpresarialFirst = empresarial.addComponent("BD 1", "");
	Component dataBaseEmpresarialSecond = empresarial.addComponent("BD 2", "");
	Component dataBaseEmpresarialThird = empresarial.addComponent("BD 3", "");
        Component serviciosTwitterFirst = twitter.addComponent("Servicio 1", "");
        Component serviciosTwitterSecond = twitter.addComponent("Servicio 2", "");
        Component serviciosTwitterThird = twitter.addComponent("Servicio 3", "");
	

	//Elements Tags

	// Connections
  
	user.uses(multicanal, "Usa");
        user.uses(google, "Usa");
	user.uses(empresarial, "Usa");
	user.uses(twitter, "Usa");	
	user.uses(serviciosGoogleFirst, "Usa");
	user.uses(serviciosGoogleSecond, "Usa");
	user.uses(serviciosGoogleThird, "Usa");
	user.uses(serviciosEmpresarialFirst, "Usa");
	user.uses(serviciosEmpresarialSecond, "Usa");
	user.uses(serviciosEmpresarialThird, "Usa");
	user.uses(serviciosTwitterFirst, "Usa");
	user.uses(serviciosTwitterSecond, "Usa");
	user.uses(serviciosTwitterThird, "Usa");
	serviciosEmpresarialFirst.uses(dataBaseEmpresarialFirst, "");
	serviciosEmpresarialSecond.uses(dataBaseEmpresarialSecond, "");
	serviciosEmpresarialThird.uses(dataBaseEmpresarialThird, "");

	//Landscape view
	
	SystemLandscapeView systemView = views.createSystemLandscapeView("Sistema", "");
        systemView.addAllElements();

	ContainerView containerView = views.createContainerView(multicanal, "Contenedores", "");
	containerView.addAllElements();
	
	ComponentView googleComponentView = views.createComponentView(google, "Servicios Google", "");
	googleComponentView.addAllElements();

	ComponentView empresarialComponentView = views.createComponentView(empresarial, "Servicios Empresariales", "");
	empresarialComponentView.addAllElements();

	ComponentView twitterComponentView = views.createComponentView(twitter, "Servicios Twitter", "");
	twitterComponentView.addAllElements();
	

	
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

	styles.addElementStyle(SERVICES_TAG).shape(Shape.Folder);

    }

    private static void uploadWorkspaceToStructurizr(Workspace workspace) throws Exception {
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }

}
