/**
 * Copyright (C) 2013-2021 Klarna AB
 * Copyright (C) 2021 The HiveRunner Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.klarna.hiverunner.builder;

import com.klarna.hiverunner.HiveServerContainer;
import com.klarna.hiverunner.HiveShellContainer;
import com.klarna.hiverunner.sql.cli.CommandShellEmulator;

import java.util.List;
import java.util.Map;

/**
 * HiveShellContainer implementation that will do a full tear down of the hive server after test method is executed.
 */
class HiveShellTearable extends HiveShellBase implements HiveShellContainer {

    HiveShellTearable(HiveServerContainer hiveServerContainer, Map<String, String> hiveConf,
                      List<String> setupScripts, List<HiveResource> resources,
                      List<Script> scriptsUnderTest, CommandShellEmulator commandShellEmulator) {
        super(hiveServerContainer, hiveConf, setupScripts, resources, scriptsUnderTest, commandShellEmulator);
    }

    @Override
    public void tearDown() {
        hiveServerContainer.tearDown();
    }
}
