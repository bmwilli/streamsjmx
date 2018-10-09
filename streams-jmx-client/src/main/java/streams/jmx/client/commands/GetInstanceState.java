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

import java.util.List;
import java.util.Set;

import javax.management.ObjectName;
import com.ibm.streams.management.ObjectNameBuilder;
import com.ibm.streams.management.domain.DomainMXBean;
import com.ibm.streams.management.instance.InstanceServiceMXBean;
import com.ibm.streams.management.resource.ResourceMXBean;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

@Parameters(commandDescription = Constants.DESC_GETDOMAINSTATE)
public class GetInstanceState extends AbstractInstanceCommand {

    public GetInstanceState() {
    }

    @Override
    public String getName() {
        return (Constants.CMD_GETINSTANCESTATE);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CommandResult doExecute() {
        try {
            JSONObject jsonOut = new JSONObject();

            StringBuilder sb = new StringBuilder();

            DomainMXBean instance = getDomainMXBean();

            // Populate the result object
            jsonOut.put("instance",instance.getName());
            jsonOut.put("state",instance.getStatus());
            sb.append(String.format("instance: %s State: %s\n",instance.getName(),instance.getStatus()));
    
    
            JSONArray resourceArray = new JSONArray();
            sb.append(String.format("%-11s %-7s\n","Resource","Status"));
            for (String resourceId : instance.getResources()) {
                //get resource
                //ObjectName objName = ObjectNameBuilder.resource(domain.getName(),s);
                //ResourceMXBean resourceBean = JMX.newMXBeanProxy(getMBeanServerConnection(), objName, ResourceMXBean.class, true);
                    ResourceMXBean resourceBean = getBeanSource().getResourceBean(instance.getName(), resourceId);
                //json
                JSONObject resourceObject = new JSONObject();
                resourceObject.put("resource",resourceId);
                resourceObject.put("status",resourceBean.getStatus());
                resourceObject.put("schedulerStatus",resourceBean.getSchedulerStatus(getInstanceName()).toString());

                // Instance Services
                List<InstanceServiceMXBean.Type> instanceServices = resourceBean.retrieveInstanceServices(getInstanceName());
                resourceObject.put("services",instanceServices);

                // Text Format
                StringBuilder serviceList = new StringBuilder();
                boolean needscomma = false;
                for (InstanceServiceMXBean.Type s : instanceServices) {
                    if (needscomma) {
                        serviceList.append(",");
                    } else {
                        needscomma = true;
                    }
                    serviceList.append(s.toString());
                }

                // Resource Tags
                Set<String> resourceTags = resourceBean.getTags();
                resourceObject.put("tags",resourceTags);

                // Text Format
                StringBuilder tagList = new StringBuilder();
                needscomma = false;
                for (String tag : resourceTags) {
                    if (needscomma) {
                        tagList.append(",");
                    } else {
                        needscomma = true;
                    }
                    tagList.append(tag);
                }

                resourceArray.add(resourceObject);
                //string
                sb.append(String.format("%-11s %-7s %-20s %-20s\n", resourceId, resourceBean.getStatus(), serviceList.toString(), tagList.toString()));
            }
            jsonOut.put("resources",resourceArray);
            //System.out.println(sb.toString());
            return new CommandResult(jsonOut.toString());
        } catch (Exception e) {
            System.out.println("GetInstanceState caught Exception: " + e.toString());
            e.printStackTrace();
            return new CommandResult(ExitStatus.FAILED_COMMAND, null, e.getLocalizedMessage());
        }
    }
}
