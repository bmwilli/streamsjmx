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
import streams.jmx.client.Constants;
import streams.jmx.client.ExitStatus;

import com.beust.jcommander.Parameters;

import javax.management.ObjectName;
import com.ibm.streams.management.ObjectNameBuilder;
import com.ibm.streams.management.domain.DomainMXBean;
import com.ibm.streams.management.resource.ResourceMXBean;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

@Parameters(commandDescription = Constants.DESC_GETDOMAINSTATE)
public class GetDomainState extends AbstractInstanceCommand {

    public GetDomainState() {
    }

    @Override
    public String getName() {
        return (Constants.CMD_GETDOMAINSTATE);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CommandResult doExecute() {
        try {
            JSONObject jsonOut = new JSONObject();

            StringBuilder sb = new StringBuilder();

            DomainMXBean domain = getDomainMXBean();

            // Populate the result object
            jsonOut.put("domain",domain.getName());
            jsonOut.put("state",domain.getStatus());
            sb.append(String.format("domain: %s State: %s\n",domain.getName(),domain.getStatus()));
    
    
            JSONArray resourceArray = new JSONArray();
            sb.append(String.format("%-11s %-7s\n","Resource","Status"));
            for (String resourceId : domain.getResources()) {
            //get resource
            //ObjectName objName = ObjectNameBuilder.resource(domain.getName(),s);
            //ResourceMXBean resourceBean = JMX.newMXBeanProxy(getMBeanServerConnection(), objName, ResourceMXBean.class, true);
                ResourceMXBean resourceBean = getBeanSource().getResourceBean(domain.getName(), resourceId);
            //json
            JSONObject resourceObject = new JSONObject();
            resourceObject.put("resource",resourceId);
            resourceObject.put("status",resourceBean.getStatus());
            resourceArray.add(resourceObject);
            //string
            sb.append(String.format("%-11s %-7s\n", resourceId, resourceBean.getStatus()));
            }
            jsonOut.put("resources",resourceArray);

            return new CommandResult(jsonOut.toString());
        } catch (Exception e) {
            System.out.println("GetDomainState caught Exception: " + e.toString());
            e.printStackTrace();
            return new CommandResult(ExitStatus.FAILED_COMMAND, null, e.getLocalizedMessage());
        }
    }
}
