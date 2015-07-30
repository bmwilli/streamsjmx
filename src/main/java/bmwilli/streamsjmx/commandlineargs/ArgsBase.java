package bmwilli.streamsjmx.commandlineargs;

import com.beust.jcommander.Parameter;

public class ArgsBase {
  @Parameter(names = "-url", description = "JMX Connection URL (e.g. service:jmx:jmxmp://server:9975)", required=true)
  public String jmxUrl;

  @Parameter(names = "-u", description = "Streams user name", required=true)
  public String user;

  @Parameter(names = "-p", description = "Streams user password", password=true)
  public String password;
}
