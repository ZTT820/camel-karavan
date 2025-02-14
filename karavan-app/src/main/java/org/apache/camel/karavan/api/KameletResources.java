/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.karavan.api;

import org.apache.camel.karavan.service.CodeService;
import org.apache.camel.karavan.service.InfinispanService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/kamelet")
public class KameletResources {

    @Inject
    InfinispanService infinispanService;

    @Inject
    CodeService codeService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getCustomYamls() {
        StringBuilder kamelets = new StringBuilder(codeService.getResourceFile("/kamelets/kamelets.yaml"));
        List<String> customKameletNames = infinispanService.getKameletNames();
        if (customKameletNames.size() > 0) {
            kamelets.append("\n---\n");
            kamelets.append(infinispanService.getKameletNames().stream()
                    .map(name -> infinispanService.getKameletYaml(name))
                    .collect(Collectors.joining("\n---\n")));
        }
        return kamelets.toString();
    }
}
