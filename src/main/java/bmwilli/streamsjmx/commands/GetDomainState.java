package bmwilli.streamsjmx.commands;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import com.ibm.streams.management.domain.DomainMXBean;
import com.ibm.streams.management.ObjectNameBuilder;

import bmwilli.streamsjmx.commandlineargs.GetDomainStateArgs;

public class GetDomainState extends CommandBase {

  private GetDomainStateArgs cmdArgs;

  public GetDomainState(GetDomainStateArgs commandArgs) {
    super(commandArgs);
    cmdArgs = commandArgs;
  }

  public void run() {
    DomainMXBean domain;
    try {
      // Connect to the JMX Server
      connect();

      // Get JMX Beans

      ObjectName objName = ObjectNameBuilder.domain(cmdArgs.domainName);

      domain = JMX.newMXBeanProxy(getMBeanServerConnection(), objName, DomainMXBean.class, true);

      // Execute and output Results (should be separated)
      System.out.println("Status: " + domain.getStatus());
      System.out.println("Instances: " + domain.getInstances());

    } catch (Exception e) {
     e.printStackTrace();     
    }   

  }
}
