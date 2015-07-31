package bmwilli.streamsjmx;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import bmwilli.streamsjmx.commandlineargs.CommandLineArgs;
import bmwilli.streamsjmx.commandlineargs.HelpArgs;
import bmwilli.streamsjmx.commandlineargs.GetDomainStateArgs;
import bmwilli.streamsjmx.commandlineargs.GetResourceStateArgs;

import bmwilli.streamsjmx.commands.GetDomainState;

public class Client {
  public static void main(String [] args) {
    String programName = "streamsjmx";

    try { 
      HelpArgs helpArgs = new HelpArgs();
      GetDomainStateArgs getdomainstateArgs = new GetDomainStateArgs();
      GetResourceStateArgs getresourcestateArgs = new GetResourceStateArgs();

      CommandLineArgs cla = new CommandLineArgs();

      JCommander cmd = new JCommander(cla);
      cmd.setProgramName(programName);
      cmd.addCommand("help", helpArgs);
      cmd.addCommand("getdomainstate", getdomainstateArgs);
      cmd.addCommand("getresourcestate", getresourcestateArgs);

      // Parse command line arguments
      try {
        cmd.parse(args);

        if (cla.help || "help".equals(cmd.getParsedCommand())) {
          cmd.usage();

        } else if ("getdomainstate".equals(cmd.getParsedCommand())) {
          GetDomainState gdsCmd = new GetDomainState(getdomainstateArgs);
          gdsCmd.run();

        } else if ("getresourcestate".equals(cmd.getParsedCommand())) {
          System.out.println("** Command Not Implemented Yet **");

        } else {
          System.out.println("Unrecognized command.");
          System.out.println("Use the 'help' command to dipslay usage");
          System.exit(1);
        }
      } catch (ParameterException ex) {
        String attemptedCommand = cmd.getParsedCommand();
        if (attemptedCommand != null) {
          System.out.println("Invalid options for command: " + attemptedCommand);
          cmd.usage(attemptedCommand);
          System.out.println(ex.getMessage());
        } else {
          System.out.println("Invalid command/options/parameters");
          System.out.println(ex.getMessage());
          System.out.println("Use the 'help' command to dipslay usage");
        }
        System.exit(1);
      }

      System.exit(0);

    }
    catch (Exception e) {
     e.printStackTrace();     
    }   
  }
}
