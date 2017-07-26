package bmwilli.streamsjmx.commands;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import com.beust.jcommander.Parameter;

import com.ibm.streams.management.domain.DomainMXBean;
import com.ibm.streams.management.ObjectNameBuilder;

public abstract class BaseDomainCmd extends BaseJMXCmd {

  @Parameter(names = "-d", description = "Streams domain name", required=true)
  private String domainName;
  public String getDomainName() {
    return domainName;
  }

  private DomainMXBean domainBean;
  public DomainMXBean getDomainMXBean() {
    return domainBean;
  }


  public BaseDomainCmd() {
    super();
  }

  @Override
  public void execute() {
    try {
      // Call parent to connect to JMX server
      super.execute();

      ObjectName objName = ObjectNameBuilder.domain(domainName);

      domainBean = JMX.newMXBeanProxy(getMBeanServerConnection(), objName, DomainMXBean.class, true);

    } catch (Exception e) {
     e.printStackTrace();     
    }   

  }
}
