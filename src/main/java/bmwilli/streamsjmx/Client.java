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

// java -jar xxx service:jmx:jmxmp://server:9975 domainA user password



public class Client {
  public static void main(String [] args) {
    try { 

      //String jmxUrl = args[0];  // use streamtool getjmxconnect to find
      //String domainName = args[1];
      //String user = args[2];    // use streamtool setacl or streamtool setdomainacl to assign required permissions
      //String password = args[3];


      CommandLineArgs cla = new CommandLineArgs();
      //String[] argv = {};
      JCommander cmd = new JCommander(cla);

      // Parse command line arguments
      try {
        cmd.parse(args);
        System.out.println("jmxURL: " + cla.jmxUrl + ", domainName: " + cla.domainName + ", user: " + cla.user + ", password: " + cla.password);
      } catch (ParameterException ex) {
        System.out.println(ex.getMessage());
        cmd.usage();
        System.exit(1);
      }



      String jmxUrl = cla.jmxUrl;
      String domainName = cla.domainName;
      String user = cla.user;
      String password = cla.password;

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
