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
public class Naranja {

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
	
//        model.setEnterprise(new Enterprise("Yuawi As a Service"));


        SoftwareSystem yuawiAsaService = model.addSoftwareSystem("Yuawi As a Service", "");
        Container aplicacionMovil = yuawiAsaService.addContainer("Aplicacion Movil", "", "Ionic");
        Container portal = yuawiAsaService.addContainer("Portal", "", "Java Spring MVC");
        SoftwareSystem payPal = model.addSoftwareSystem("Paypal", "");
        SoftwareSystem awsS3 = model.addSoftwareSystem("AWS S3", "");
        SoftwareSystem awsCognito = model.addSoftwareSystem("AWS Cognito", "");
	awsS3.setUrl("https://s3.amazonaws.com/yuawi.gusdelact.com/Movimiento-Naranja-Yuawi.mp3");


        Person customer = model.addPerson(Location.External, "Usuario", "");

	Component interfazAplicacionMovil = aplicacionMovil.addComponent("Interfaz de usuario", "", "Ionic");
	Component restClientAplicacionMovil = aplicacionMovil.addComponent("Controlador Cliente Rest", "", "Ionic");
        Component interfazPortal = portal.addComponent("Interfaz de usuario", "", "Java Spring MVC");
        Component restClientPortal = portal.addComponent("Controlador Cliente Rest", "", "Java Spring MVC");
	//Elements Tags
	
        
	// Connections

        customer.uses(yuawiAsaService, "Usa");
        customer.uses(aplicacionMovil, "Usa");
        customer.uses(portal, "Usa");
        customer.uses(interfazAplicacionMovil, "Accede");
        customer.uses(interfazPortal, "Accede");
	yuawiAsaService.uses(payPal, "Paga");
	yuawiAsaService.uses(awsCognito, "Autentica");
	yuawiAsaService.uses(awsS3, "Reproduce");
	aplicacionMovil.uses(awsS3, "Reproduce");
	aplicacionMovil.uses(awsCognito, "Autentica");
	awsCognito.uses(aplicacionMovil, "Confirma Creedenciales");
	portal.uses(payPal, "Paga");	
	portal.uses(awsCognito, "Guarda Creedenciales");
	payPal.uses(portal, "Confirma pago");
	interfazAplicacionMovil.uses(restClientAplicacionMovil, "Usa");
	restClientAplicacionMovil.uses(awsS3, "Reproduce");
	restClientAplicacionMovil.uses(awsCognito, "Autentica");
	awsCognito.uses(restClientAplicacionMovil, "Confirma Creedenciales");
	interfazPortal.uses(restClientPortal, "Usa");
	restClientPortal.uses(payPal, "Paga");
	restClientPortal.uses(awsCognito, "Guarda Creedenciales");
	payPal.uses(restClientPortal, "Confirma pago");

	//Landscape view
	

	SystemContextView systemContextView = views.createSystemContextView(yuawiAsaService, "Contexto", "");
	systemContextView.addAllElements();

	ContainerView containerView = views.createContainerView(yuawiAsaService, "Contenedores", "");
	containerView.addAllElements();
	
	ComponentView dispositivoView = views.createComponentView(aplicacionMovil, "Aplicacion Movil", "");
	dispositivoView.addAllElements();

	ComponentView servidorView = views.createComponentView(portal, "Portal Web", "");
	servidorView.addAllElements();

 
	DynamicView servidorDynamicView = views.createDynamicView(yuawiAsaService, "Pago del servicio", "Diagrama que muestra el pago del servicio"); 
	servidorDynamicView.add(customer, "Accede al portal para pagar el servicio", portal);         
	servidorDynamicView.add(portal, "El usuario es dirigido a PayPal para completar su pago", payPal);         
	servidorDynamicView.add(payPal, "Se confirma el pago", portal);         
	servidorDynamicView.add(portal, "Se guardan las creedencuales del usuario", awsCognito);               


	DynamicView dispositivoDynamicView = views.createDynamicView(yuawiAsaService, "Reproduccion de musica", "Diagrama que muestra el acceso al servicio"); 
	dispositivoDynamicView.add(customer, "Accede a la aplicacion movil", aplicacionMovil);         
	dispositivoDynamicView.add(aplicacionMovil, "Valida las creedenciales del usuario", awsCognito);         
	dispositivoDynamicView.add(awsCognito, "Confirma las creedenciales", aplicacionMovil);         
	dispositivoDynamicView.add(aplicacionMovil, "Reproduce el archivo", awsS3);               




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
