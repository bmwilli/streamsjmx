package bmwilli.streamsjmx.commands;

import java.util.HashMap;

import com.beust.jcommander.Parameter;

//import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

// Most commands are going to require making the jmx connection so define this once
public abstract class BaseJMXCmd implements Cmd {

  @Parameter(names = "-url", description = "JMX Connection URL (e.g. service:jmx:jmxmp://server:9975)", required=true)
  private String jmxUrl;
  public String getJmxUrl() {
    return jmxUrl;
  }

  @Parameter(names = "-u", description = "Streams user name", required=true)
  private String user;
  public String getUser() {
    return user;
  }

  //@Parameter(names = "-p", description = "Streams user password", password=true)  
  @Parameter(names = "-p", description = "Streams user password", required=true)
  private String password;

  private JMXConnector jmxc;
  private MBeanServerConnection mbsc;
  public MBeanServerConnection getMBeanServerConnection() {
    return mbsc;
  }


  public BaseJMXCmd() {
  }

  // execute makes the JMX Connection
  @Override
  public void execute() {
    try {
      HashMap<String,Object> env = new HashMap<String,Object>();
      String [] creds = {user, password};
      env.put("jmx.remote.credentials", creds);
      env.put("jmx.remote.protocol.provider.pkgs", "com.ibm.streams.management");

      jmxc = JMXConnectorFactory.connect(new JMXServiceURL(jmxUrl), env);
      mbsc = jmxc.getMBeanServerConnection();
    } catch (Exception e) {
     e.printStackTrace();     
    }   

  }

}
