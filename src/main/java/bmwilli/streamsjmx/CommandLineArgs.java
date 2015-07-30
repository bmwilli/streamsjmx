package bmwilli.streamsjmx;

import com.beust.jcommander.Parameter;

public class CommandLineArgs {
  @Parameter(names = "-url", description = "JMX Connection URL (e.g. service.jmx.jmxmp://server:9957)", required=true)
  public String jmxUrl;

  @Parameter(names = "-d", description = "Streams domain name", required=true)
  public String domainName;

  @Parameter(names = "-u", description = "Streams user name", required=true)
  public String user;

  @Parameter(names = "-p", description = "Streams user password", password=true, required=true)
  public String password;
}
