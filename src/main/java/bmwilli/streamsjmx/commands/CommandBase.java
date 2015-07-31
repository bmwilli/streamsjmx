package bmwilli.streamsjmx.commands;

import java.util.HashMap;

//import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import bmwilli.streamsjmx.commandlineargs.ArgsBase;

// Most commands are going to require making the jmx connection so define this once
public abstract class CommandBase {

  private ArgsBase args;
  private JMXConnector jmxc;
  private MBeanServerConnection mbsc;


  public CommandBase(ArgsBase commandArgs) {
    args = commandArgs;
  }

  public void connect() {
    try {
      HashMap<String,Object> env = new HashMap<String,Object>();
      String [] creds = {args.user, args.password};
      env.put("jmx.remote.credentials", creds);
      env.put("jmx.remote.protocol.provider.pkgs", "com.ibm.streams.management");

      jmxc = JMXConnectorFactory.connect(new JMXServiceURL(args.jmxUrl), env);
      mbsc = jmxc.getMBeanServerConnection();
    } catch (Exception e) {
     e.printStackTrace();     
    }   

  }

  public MBeanServerConnection getMBeanServerConnection() {
    return mbsc;
  }

}
