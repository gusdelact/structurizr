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
public class ServiceBus {

    private static final long WORKSPACE_ID = 38549;
    private static final String API_KEY = "fa603ed4-775b-44e6-9d69-5ee8518d881b";
    private static final String API_SECRET = "44e200f6-b14f-420a-a1c3-715c2c70bf1b";

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



        Workspace workspaceMonolitico = new Monolitico().create();
        Workspace workspaceClienteServidor = new ClienteServidor().create();
        Workspace workspaceMulticapa = new Multicapa().create();
        Workspace workspaceMicroservicios = new Microservicios().create();
        	
	// Elements


        model = updateModel(model, workspaceMonolitico.getModel());
        model = updateModel(model, workspaceClienteServidor.getModel());
        updateRelationships(model, workspaceMonolitico.getModel());
        updateRelationships(model, workspaceClienteServidor.getModel());
        views = updateViews(model, workspaceMonolitico.getModel(), views, workspaceMonolitico.getViews());
        views = updateViews(model, workspaceClienteServidor.getModel(), views, workspaceClienteServidor.getViews());

     //  	uploadWorkspaceToStructurizr(workspace);
    }



    private static void uploadWorkspaceToStructurizr(Workspace workspace) throws Exception {
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }

    private static Model updateModel (Model oldModel, Model newModel){

      Collection<Person> people = new LinkedHashSet<>();
      Set<SoftwareSystem> softwareSystems = new LinkedHashSet<>();

      people = newModel.getPeople();
      softwareSystems = newModel.getSoftwareSystems();

      for (SoftwareSystem softwareSystem : softwareSystems) {
        SoftwareSystem tmpSoftwareSystem = oldModel.addSoftwareSystem(softwareSystem.getName(), softwareSystem.getDescription());
        for (Container container : softwareSystem.getContainers()) {

          Container tmpContainer = tmpSoftwareSystem.addContainer(container.getName(), container.getDescription(),"");

          for (Component component : container.getComponents()) {

            tmpContainer.addComponent(component.getName(), component.getDescription(), "");

          }
        }
      }
      return oldModel;
    }

    private static ViewSet updateViews (Model oldModel, Model newModel, ViewSet oldViews, ViewSet newViews) {


/*        for (SystemLandscapeView view : newViews.getSystemLandscapeViews()) {

            SystemLandscapeView systemView = oldViews.createSystemLandscapeView(view.getName(), view.getDescription());
            systemView.addAllElements();

        }*/

        for (SystemContextView view : newViews.getSystemContextViews()) {

          String name = newModel.getSoftwareSystemWithId(view.getSoftwareSystemId()).getName();


          SystemContextView contextView = oldViews.createSystemContextView(oldModel.getSoftwareSystemWithName(name), view.getName(), view.getDescription());
          contextView.addAllElements();
        }


        for (ContainerView view : newViews.getContainerViews()) {

          String name = newModel.getSoftwareSystemWithId(view.getSoftwareSystemId()).getName();

          ContainerView containerView = oldViews.createContainerView(oldModel.getSoftwareSystemWithName(name), view.getName(), view.getDescription());
          containerView.addAllElements();

        }


        for (ComponentView view : newViews.getComponentViews()) {

          String systemName = newModel.getSoftwareSystemWithId(view.getSoftwareSystemId()).getName();
          String  containerName = view.getSoftwareSystem().getContainerWithId(view.getContainerId()).getName();

          SoftwareSystem softwareSystem = oldModel.getSoftwareSystemWithName(systemName);
          Container container = softwareSystem.getContainerWithName(containerName);

          ComponentView componentView = oldViews.createComponentView(container, view.getName(), view.getDescription());
          componentView.addAllElements();

        }

        return oldViews;
    }

   private static void updateRelationships(Model oldModel, Model newModel) {
     for (Element element : newModel.getElements()) {

       for (Relationship relationship : element.getRelationships()) {

      // Element source = newModel.getElement(relationship.getSourceId());
       //Element destination = newModel.getElement(relationship.getDestinationId());

         String sourceName = relationship.getSource().getName();
         String destinationName = relationship.getDestination().getName();
         String description = relationship.getDescription();
         String technology = relationship.getTechnology();

         try{

           Element source = oldModel.getElements().stream().filter(e -> e.getName() == sourceName).findFirst().get();
           Element destination = oldModel.getElements().stream().filter(e -> e.getName() == destinationName).findFirst().get();

           System.out.println(source.getClass());
           System.out.println(source.getParent().getClass());
           System.out.println(destination.getClass());
           System.out.println(destination.getParent().getClass());


           Relationship Nrelationship = new Relationship();//source, destination, description, technology, InteractionStyle.Synchronous);

           //StaticStructureElement().uses(source, destination, description, technology, InteractionStyle.Synchronous);

           //oldModel.addRelationship(Nrelationship);

         }
         catch(Exception e){


         }

         //source.uses(destination, description);
       //relationship.setSource(getElement(relationship.getSourceId()));
       //relationship.setDestination(getElement(relationship.getDestinationId()));
//       addRelationshipToInternalStructures(relationship);
       }
     }
   }


}
