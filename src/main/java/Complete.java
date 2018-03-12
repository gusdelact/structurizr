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
public class Complete {

    private static final long WORKSPACE_ID = 38505;
    private static final String API_KEY = "2a7ff768-c92e-4ccf-9d71-ec19484864eb";
    private static final String API_SECRET = "7e1779dc-9a08-4246-9a0a-a11ce2a5a981";

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
	
        model.setEnterprise(new Enterprise("Big Bank plc"));


        SoftwareSystem internetBankingSystem = model.addSoftwareSystem(Location.Internal, "Internet Banking System", "Allows customers to view information about their bank accounts, and make payments.");
        SoftwareSystem mainframeBankingSystem = model.addSoftwareSystem(Location.Internal, "Mainframe Banking System", "Stores all of the core banking information about customers, accounts, transactions, etc.");
        SoftwareSystem emailSystem = model.addSoftwareSystem(Location.Internal, "E-mail System", "The internal Microsoft Exchange e-mail system.");
        SoftwareSystem atm = model.addSoftwareSystem(Location.Internal, "ATM", "Allows customers to withdraw cash.");


        Person customer = model.addPerson(Location.External, "Personal Banking Customer", "A customer of the bank, with personal bank accounts.");
        Person customerServiceStaff = model.addPerson(Location.Internal, "Customer Service Staff", "Customer service staff within the bank.");
        Person backOfficeStaff = model.addPerson(Location.Internal, "Back Office Staff", "Administration and support staff within the bank.");

	Container singlePageApplication = internetBankingSystem.addContainer("Single-Page Application", "Provides all of the Internet banking functionality to customers via their web browser.", "JavaScript and Angular");
        Container mobileApp = internetBankingSystem.addContainer("Mobile App", "Provides a limited subset of the Internet banking functionality to customers via their mobile device.", "Xamarin");
	Container webApplication = internetBankingSystem.addContainer("Web Application", "Delivers the static content and the Internet banking single page application.", "Java and Spring MVC");
	Container apiApplication = internetBankingSystem.addContainer("API Application", "Provides Internet banking functionality via a JSON/HTTPS API.", "Java and Spring MVC");
	Container database = internetBankingSystem.addContainer("Database", "Stores user registration information, hashed authentication credentials, access logs, etc.", "Relational Database Schema");

	Component signinController = apiApplication.addComponent("Sign In Controller", "Allows users to sign in to the Internet Banking System.", "Spring MVC Rest Controller");
        Component accountsSummaryController = apiApplication.addComponent("Accounts Summary Controller", "Provides customers with a summary of their bank accounts.", "Spring MVC Rest Controller");
        Component securityComponent = apiApplication.addComponent("Security Component", "Provides functionality related to signing in, changing passwords, etc.", "Spring Bean");
        Component mainframeBankingSystemFacade = apiApplication.addComponent("Mainframe Banking System Facade", "A facade onto the mainframe banking system.", "Spring Bean");
	//Elements Tags
	
        mainframeBankingSystem.addTags(EXISTING_SYSTEM_TAG);
        emailSystem.addTags(EXISTING_SYSTEM_TAG);
        atm.addTags(EXISTING_SYSTEM_TAG);
        customerServiceStaff.addTags(STAFF_TAG);
        database.addTags(DATABASE_TAG);
        backOfficeStaff.addTags(STAFF_TAG);


	// Connections

        customer.uses(internetBankingSystem, "Uses");
        customer.uses(atm, "Withdraws cash using");
        customer.interactsWith(customerServiceStaff, "Asks questions to", "Telephone");
	customerServiceStaff.uses(mainframeBankingSystem, "Uses");
	internetBankingSystem.uses(mainframeBankingSystem, "Uses");
	emailSystem.delivers(customer, "Sends e-mails to");
        atm.uses(mainframeBankingSystem, "Uses");
        backOfficeStaff.uses(mainframeBankingSystem, "Uses");
	customer.uses(webApplication, "Uses", "HTTPS");
        customer.uses(singlePageApplication, "Uses", "");
        customer.uses(mobileApp, "Uses", "");
        webApplication.uses(singlePageApplication, "Delivers", "");
        apiApplication.uses(database, "Reads from and writes to", "JDBC");
        apiApplication.uses(mainframeBankingSystem, "Uses", "XML/HTTPS");
        apiApplication.uses(emailSystem, "Sends e-mail using", "SMTP");

	apiApplication.getComponents().stream().filter(c -> "Spring MVC Rest Controller".equals(c.getTechnology())).forEach(c -> singlePageApplication.uses(c, "Uses", "JSON/HTTPS"));
        apiApplication.getComponents().stream().filter(c -> "Spring MVC Rest Controller".equals(c.getTechnology())).forEach(c -> mobileApp.uses(c, "Uses", "JSON/HTTPS"));

	signinController.uses(securityComponent, "Uses");
	accountsSummaryController.uses(mainframeBankingSystemFacade, "Uses");
        securityComponent.uses(database, "Reads from and writes to", "JDBC");
        mainframeBankingSystemFacade.uses(mainframeBankingSystem, "Uses", "XML/HTTPS");

        model.addImplicitRelationships();

	//Landscape view
	

	SystemLandscapeView systemLandscapeView = views.createSystemLandscapeView("SystemLandscape", "The system landscape diagram for Big Bank plc.");
        systemLandscapeView.addAllElements();
	systemLandscapeView.setPaperSize(PaperSize.A5_Landscape);

	SystemContextView systemContextView = views.createSystemContextView(internetBankingSystem, "SystemContext", "The system context diagram for the Internet Banking System.");
	systemContextView.addNearestNeighbours(internetBankingSystem);
	systemContextView.setPaperSize(PaperSize.A5_Landscape);

	ContainerView containerView = views.createContainerView(internetBankingSystem, "Containers", "The container diagram for the Internet Banking System.");
	containerView.add(customer);
	containerView.addAllContainers();
	containerView.add(mainframeBankingSystem);
	containerView.add(emailSystem);
	containerView.setPaperSize(PaperSize.A5_Landscape);
	
	ComponentView componentView = views.createComponentView(apiApplication, "Components", "The component diagram for the API Application.");
	componentView.add(mobileApp);
	componentView.add(singlePageApplication);
	componentView.add(database);
	componentView.addAllComponents();
	componentView.add(mainframeBankingSystem);
	componentView.setPaperSize(PaperSize.A5_Landscape);


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
