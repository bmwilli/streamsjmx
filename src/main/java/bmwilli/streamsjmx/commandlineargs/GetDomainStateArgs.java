package bmwilli.streamsjmx.commandlineargs;

import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

@Parameters(commandDescription = "Returns status of domain and its resources")
public class GetDomainStateArgs extends ArgsBase {
  @Parameter(names = "-d", description = "Streams domain name", required=true)
  public String domainName;

}
