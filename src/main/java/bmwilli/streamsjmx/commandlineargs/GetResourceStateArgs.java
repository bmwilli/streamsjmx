package bmwilli.streamsjmx.commandlineargs;

import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

@Parameters(commandDescription = "Returns the status of the instance and its resources")
public class GetResourceStateArgs extends ArgsBase {
  @Parameter(names = "-d", description = "Streams domain name", required=true)
  public String domainName;

  @Parameter(names = "-i", description = "Streams instance name", required=true)
  public String instanceName;
}
