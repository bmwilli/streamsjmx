package bmwilli.streamsjmx.commands;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

import com.ibm.streams.management.instance.InstanceMXBean;
import com.ibm.streams.management.ObjectNameBuilder;

public class BaseInstanceCmd extends BaseDomainCmd {

  @Parameter(names = "-i", description = "Streams instance name", required=true)
  public String instanceName;

  private InstanceMXBean instanceBean;
  InstanceMXBean getInstanceMXBean() {
    return instanceBean;
  }


  public BaseInstanceCmd() {
    super();
  }

  @Override
  public void execute() {
    try {
      // Call parent to connect to JMX server
      super.execute();

      ObjectName objName = ObjectNameBuilder.instance(getDomainName(),instanceName);

      instanceBean = JMX.newMXBeanProxy(getMBeanServerConnection(), objName, InstanceMXBean.class, true);

    } catch (Exception e) {
     e.printStackTrace();     
    }   

  }
}
