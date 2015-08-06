package bmwilli.streamsjmx.commands;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

import com.ibm.streams.management.domain.DomainMXBean;
import com.ibm.streams.management.ObjectNameBuilder;

import bmwilli.streamsjmx.commandlineargs.GetDomainStateArgs;

@Parameters(commandDescription = "Returns status of domain and its resources")
public class GetDomainState extends BaseDomainCmd {

  public GetDomainState() {
    super();
    //cmdArgs = commandArgs;
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
