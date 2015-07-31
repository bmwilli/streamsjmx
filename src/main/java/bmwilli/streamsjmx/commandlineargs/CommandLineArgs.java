package bmwilli.streamsjmx.commandlineargs;

import com.beust.jcommander.Parameter;

// These are arguments/options that can appear before or without a command
public class CommandLineArgs {
  @Parameter(names = {"-h","--help"}, help = true)
  public boolean help;
}
