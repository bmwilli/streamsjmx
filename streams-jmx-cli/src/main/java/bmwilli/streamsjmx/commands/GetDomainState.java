package bmwilli.streamsjmx.commands;

import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import com.ibm.streams.management.ObjectNameBuilder;

import com.ibm.streams.management.domain.DomainMXBean;
import com.ibm.streams.management.resource.ResourceMXBean;

// First command
// Goal is to separate execution from output
// Idea: execution captures information required and stores in JSON, returning it
// Provide a toString() method to get console readable output of JSON

@Parameters(commandDescription = "Returns status of domain and its resources")
public class GetDomainState extends BaseDomainCmd {

  private JSONObject jsonOut;
  private String consoleOut;

  public GetDomainState() {
    super();
  }

  @Override
  public void execute() {
    try {
 
      //Start the execution on the super
      super.execute();

      DomainMXBean domain = getDomainMXBean();

      // Create the result object
      jsonOut = new JSONObject();
      StringBuilder sb = new StringBuilder();

      // Populate the result object
      jsonOut.put("domain",domain.getName());
      jsonOut.put("state",domain.getStatus());
      sb.append(String.format("domain: %s State: %s\n",domain.getName(),domain.getStatus()));


      JSONArray resourceArray = new JSONArray();
      sb.append(String.format("%-11s %-7s\n","Resource","Status"));
      for (String s : domain.getResources()) {
        //get resource
        ObjectName objName = ObjectNameBuilder.resource(domain.getName(),s);
        ResourceMXBean resourceBean = JMX.newMXBeanProxy(getMBeanServerConnection(), objName, ResourceMXBean.class, true);

        //json
        JSONObject resourceObject = new JSONObject();
        resourceObject.put("resource",s);
        resourceObject.put("status",resourceBean.getStatus());
        resourceArray.add(resourceObject);
        //string
        sb.append(String.format("%-11s %-7s\n", s, resourceBean.getStatus()));
      }
      jsonOut.put("resources",resourceArray);

      consoleOut = sb.toString();

      // Print the json
      System.out.println("json: " + System.lineSeparator() + jsonOut.toString());
      System.out.println("console:" + System.lineSeparator() + consoleOut);

      // Execute and output Results (should be separated)
      //System.out.println("Status: " + getDomainMXBean().getStatus());
     // System.out.println("Instances: " + getDomainMXBean().getInstances());

    } catch (Exception e) {
     e.printStackTrace();     
    }   
  }

}
