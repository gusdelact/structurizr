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
public class Microservices {

    private static final WorkspaceOne WORKSPACE = new WorkspaceOne();

    private static final long WORKSPACE_ID = WORKSPACE.id();
    private static final String API_KEY = WORKSPACE.key();
    private static final String API_SECRET = WORKSPACE.secret();

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
        SoftwareSystem softwareSystem = model.addSoftwareSystem("Customer information System", "Stores information");

	Person user = model.addPerson("Customer", "A customer.");

	Container customerApplication = softwareSystem.addContainer("Customer Application", "Allows customers to manage their profile.", "Angular");
        Container customerService = softwareSystem.addContainer("Customer Service", "The point of access for customer information.", "Java and Spring Boot");
        Container customerDatabase = softwareSystem.addContainer("Customer Database", "Stores customer information.", "Oracle 12c");
        Container reportingService = softwareSystem.addContainer("Reporting Service", "Creates normalised data for reporting purposes.", "Ruby");
        Container reportingDatabase = softwareSystem.addContainer("Reporting Database", "Stores a normalised version of all business data for ad hoc reporting purposes.", "MySQL");
        Container auditService = softwareSystem.addContainer("Audit Service", "Provides organisation-wide auditing facilities.", "C# .NET");
        Container auditStore = softwareSystem.addContainer("Audit Store", "Stores information about events that have happened.", "Event Store");
        Container messageBus = softwareSystem.addContainer("Message Bus", "Transport for business events.", "RabbitMQ");

	//Elements Tags
	
	customerService.addTags(MICROSERVICE_TAG);
        customerDatabase.addTags(DATABASE_TAG);
        reportingService.addTags(MICROSERVICE_TAG);
        reportingDatabase.addTags(DATABASE_TAG);
        auditService.addTags(MICROSERVICE_TAG);
        auditStore.addTags(DATABASE_TAG);
        messageBus.addTags(MESSAGE_BUS_TAG);
        messageBus.addTags(MESSAGE_BUS_TAG);


	// Connections

	user.uses(softwareSystem, "Uses");
	user.uses(customerApplication, "Uses");
	customerApplication.uses(customerService, "Updates customer information using", "JSON/HTTPS");
	customerService.uses(messageBus, "Sends customer update events to", "");
	customerService.uses(customerDatabase, "Stores data in", "JDBC");
	customerService.uses(customerApplication, "Sends events to", "WebSocket");
	messageBus.uses(reportingService, "Sends customer update events to", "");
	messageBus.uses(auditService, "Sends customer update events to", "");
	reportingService.uses(reportingDatabase, "Stores data in", "");
	auditService.uses(auditStore, "Stores events in", "");

	//Landscape view
	
        SystemContextView contextView = views.createSystemContextView(softwareSystem, "SystemContext", "An example of a System Context diagram.");
	
	contextView.addAllElements();


	//Container view

        ContainerView containerView = views.createContainerView(softwareSystem, "Containers", null);
	containerView.addAllElements();

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
