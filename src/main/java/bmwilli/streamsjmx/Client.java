package bmwilli.streamsjmx;

import java.util.HashMap;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.ibm.streams.management.domain.DomainMXBean;
import com.ibm.streams.management.ObjectNameBuilder;

// javac -cp com.ibm.streams.management.jmxmp.jar:com.ibm.streams.management.mx.jar Client.java
// java -cp .;com.ibm.streams.management.jmxmp.jar:com.ibm.streams.management.mx.jar:jmxremote_optional.jar 
//   Client service:jmx:jmxmp://server:9975 domainA user password
// Note: It is important to include com.ibm.streams.management.jmxmp.jar in the class path before jmxremote_optional.jar
public class Client {
  public static void main(String [] args) {
    try { 
      String jmxUrl = args[0];  // use streamtool getjmxconnect to find
      String domainName = args[1];
      String user = args[2];    // use streamtool setacl or streamtool setdomainacl to assign required permissions
      String password = args[3];
      //System.out.println("jmxURL: " + jmxUrl + ", domainName: " + domainName + ", user: " + user + ", password: " + password);
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
