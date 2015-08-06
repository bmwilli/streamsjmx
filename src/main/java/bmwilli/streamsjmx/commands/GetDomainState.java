package bmwilli.streamsjmx.commands;

import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

@Parameters(commandDescription = "Returns status of domain and its resources")
public class GetDomainState extends BaseDomainCmd {

  public GetDomainState() {
    super();
  }

  @Override
  public void execute() {
    try {
 
      //Start the execution on the super
      super.execute();

      // Execute and output Results (should be separated)
      System.out.println("Status: " + getDomainMXBean().getStatus());
      System.out.println("Instances: " + getDomainMXBean().getInstances());

    } catch (Exception e) {
     e.printStackTrace();     
    }   

  }
}
