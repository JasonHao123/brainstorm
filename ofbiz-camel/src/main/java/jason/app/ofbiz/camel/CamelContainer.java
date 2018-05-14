/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package jason.app.ofbiz.camel;

import java.util.List;

import org.apache.camel.spring.javaconfig.Main;
import org.apache.ofbiz.base.container.Container;
import org.apache.ofbiz.base.container.ContainerException;
import org.apache.ofbiz.base.start.StartupCommand;
import org.apache.ofbiz.base.util.Debug;

import jason.app.ofbiz.camel.config.CamelConfig;

/**
 * A container for Apache Camel.
 */

public class CamelContainer implements Container {
	private static final String module = CamelContainer.class.getName();
	private String name;
	private Main main;

	@Override
	public void init(List<StartupCommand> ofbizCommands, String name, String configFile) throws ContainerException {
		this.name = name;
		main = new Main();
		main.setConfigClass(CamelConfig.class);
	}

	@Override
	public boolean start() throws ContainerException {
		Debug.logInfo("Starting camel container", module);

		try {
			main.run();
		} catch (Exception e) {
			throw new ContainerException(e);
		}
		return true;
	}

	@Override
	public void stop() throws ContainerException {
		Debug.logInfo("Stopping camel container", module);

		try {
			// context.stop();
			main.stop();
		} catch (Exception e) {
			throw new ContainerException(e);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	public static void main(String[] args) {
		CamelContainer container = new CamelContainer();
		try {
			container.init(null, "ofbiz-camel", null);
			container.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
