package bmwilli.streamsjmx.commands;

import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

@Parameters(commandDescription = "Returns status of an instance and its resources")
public class GetResourceState extends BaseInstanceCmd {

  public GetResourceState() {
    super();
  }

  @Override
  public void execute() {
    try {
 
      //Start the execution on the super
      super.execute();

      // Execute and output Results (should be separated)
      System.out.println("Status: " + getInstanceMXBean().getStatus());
      System.out.println("Resources: " + getInstanceMXBean().getResources());

    } catch (Exception e) {
     e.printStackTrace();     
    }   

  }
}
