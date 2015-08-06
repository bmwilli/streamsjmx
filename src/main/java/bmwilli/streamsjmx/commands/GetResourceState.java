package bmwilli.streamsjmx.commands;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

import com.ibm.streams.management.domain.DomainMXBean;
import com.ibm.streams.management.ObjectNameBuilder;

import bmwilli.streamsjmx.commandlineargs.GetDomainStateArgs;

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
