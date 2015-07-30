package bmwilli.streamsjmx;

import java.util.HashMap;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.ibm.streams.management.domain.DomainMXBean;
import com.ibm.streams.management.ObjectNameBuilder;

import bmwilli.streamsjmx.commandlineargs.HelpArgs;
import bmwilli.streamsjmx.commandlineargs.GetDomainStateArgs;
import bmwilli.streamsjmx.commandlineargs.GetResourceStateArgs;

public class Client {
  public static void main(String [] args) {
    try { 

      HelpArgs helpArgs = new HelpArgs();
      GetDomainStateArgs getdomainstateArgs = new GetDomainStateArgs();
      GetResourceStateArgs getresourcestateArgs = new GetResourceStateArgs();

      //CommandLineArgs cla = new CommandLineArgs();
      //String[] argv = {};
      JCommander cmd = new JCommander();
      cmd.addCommand("help", helpArgs);
      cmd.addCommand("getdomainstate", getdomainstateArgs);
      cmd.addCommand("getresourcestate", getresourcestateArgs);

      // Parse command line arguments
      try {
        cmd.parse(args);

        if ("help".equals(cmd.getParsedCommand())) {
          cmd.usage();
        } else if ("getdomainstate".equals(cmd.getParsedCommand())) {
          System.out.println("getdomainstate: jmxURL: " + getdomainstateArgs.jmxUrl + ", domainName: " + getdomainstateArgs.domainName + ", user: " + getdomainstateArgs.user + ", password: " + getdomainstateArgs.password);
        } else if ("getresourcestate".equals(cmd.getParsedCommand())) {
          System.out.println("getresourcestate: jmxURL: " + getresourcestateArgs.jmxUrl + ", domainName: " + getresourcestateArgs.domainName + ", user: " + getresourcestateArgs.user + ", password: " + getresourcestateArgs.password);
        } else {
          System.out.println("Unrecognized command.");
          System.out.println("Use the 'help' command to dipslay usage");
          System.exit(1);
        }
      } catch (ParameterException ex) {
        System.out.println("command exception: " + ex.getMessage());
        System.out.println("Use the 'help' command to dipslay usage");
        System.exit(1);
      }

      System.exit(0);

      String jmxUrl = getdomainstateArgs.jmxUrl;
      String domainName = getdomainstateArgs.domainName;
      String user = getdomainstateArgs.user;
      String password = getdomainstateArgs.password;

      HashMap<String,Object> env = new HashMap<String,Object>();
      String [] creds = {user, password};
      env.put("jmx.remote.credentials", creds);
      env.put("jmx.remote.protocol.provider.pkgs", "com.ibm.streams.management");

      JMXConnector jmxc = JMXConnectorFactory.connect(new JMXServiceURL(jmxUrl), env);
      MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

      ObjectName objName = ObjectNameBuilder.domain(domainName);
      DomainMXBean domain = JMX.newMXBeanProxy(mbsc, objName, DomainMXBean.class, true);

      System.out.println("Status: " + domain.getStatus());
      System.out.println("Instances: " + domain.getInstances());
    }
    catch (Exception e) {
     e.printStackTrace();     
    }   
  }
}
