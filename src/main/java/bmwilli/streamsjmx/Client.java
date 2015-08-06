package bmwilli.streamsjmx;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import bmwilli.streamsjmx.CommandLineArgs;
import bmwilli.streamsjmx.commands.Cmd;
import bmwilli.streamsjmx.commands.Help;
import bmwilli.streamsjmx.commands.GetDomainState;
import bmwilli.streamsjmx.commands.GetResourceState;


public class Client {
  public static void main(String [] args) {
    String programName = "streamsjmx";
    String parsedCommand;

    try { 

      CommandLineArgs cla = new CommandLineArgs();
      JCommander cmd = new JCommander(cla);

      cmd.setProgramName(programName);

      // Create and add the commands

      cmd.addCommand("help", new Help());
      cmd.addCommand("getdomainstate", new GetDomainState());
      cmd.addCommand("getresourcestate", new GetResourceState());

      // Parse command line arguments
      try {
        cmd.parse(args);
        parsedCommand = cmd.getParsedCommand();

        // Help option or a command
        if ((cla.help) || (parsedCommand == "help")) {
          cmd.usage();
          System.exit(0);
        }

        // Its a regular command, get a JCommander object specific to the command
        JCommander parsedJCommander = cmd.getCommands().get(parsedCommand);


        // Need to switch to factory pattern for commands and figure how to handle arguments

	// Handle Commands
        Cmd theCmd;
        switch(parsedCommand) {
          case "help":
            cmd.usage();
            break;
          case "getdomainstate":
          case "getresourcestate":
            theCmd = (Cmd)parsedJCommander.getObjects().get(0);
            theCmd.execute();
            break;
          default:
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
