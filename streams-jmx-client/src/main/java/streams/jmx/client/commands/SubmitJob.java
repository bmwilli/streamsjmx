// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package streams.jmx.client.commands;

import streams.jmx.client.jmx.JmxServiceContext;
import streams.jmx.client.ServiceConfig;
import streams.jmx.client.cli.FileExistsValidator;
import streams.jmx.client.Constants;
import streams.jmx.client.ExitStatus;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.management.ObjectName;
import com.ibm.streams.management.ObjectNameBuilder;
import com.ibm.streams.management.domain.DomainMXBean;
import com.ibm.streams.management.instance.InstanceMXBean;
import com.ibm.streams.management.instance.InstanceServiceMXBean;
import com.ibm.streams.management.job.DeployInformation;
import com.ibm.streams.management.resource.ResourceMXBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Parameters(commandDescription = Constants.DESC_SUBMITJOB)
public class SubmitJob extends AbstractInstanceCommand {

    @Parameter(names = {"-f","--file"}, description = "Path to the .sab file", required = true,
        converter=com.beust.jcommander.converters.FileConverter.class,
        validateWith = FileExistsValidator.class)
    private File sabFile;


    public SubmitJob() {
    }

    @Override
    public String getName() {
        return (Constants.CMD_SUBMITJOB);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CommandResult doExecute() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonOut = mapper.createObjectNode();
            
            InstanceMXBean instance = getInstanceMXBean();
    
            DeployInformation deployInformation = instance.deployApplication(sabFile.getName());

            getJmxServiceContext().getWebClient().putFile(deployInformation.getUri(),
                "application/x-jar", sabFile, getConfig().getJmxHttpHost(), getConfig().getJmxHttpPort());
            jsonOut.put("jobId", instance.submitJob(deployInformation.getApplicationId()).longValue());

            return new CommandResult(jsonOut.toString());
        } catch (Exception e) {
            //System.out.println("GetInstanceState caught Exception: " + e.toString());
            //e.printStackTrace();
            return new CommandResult(ExitStatus.FAILED_COMMAND, null, e.getLocalizedMessage());
        }
    }
}
