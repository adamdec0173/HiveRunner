/*
 * Copyright (C) 2013-2021 Klarna AB
 * Copyright (C) 2021-2024 The HiveRunner Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.klarna.hiverunner;

import com.klarna.hiverunner.annotations.HiveSQL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(HiveRunnerExtension.class)
public class SetPropertyTest {

    @HiveSQL(files = {}, autoStart = false)
    private HiveShell shell;

    @Test
    public void propertyShouldNotBeSetIfShellIsAlreadyStarted() {
        shell.start();
        Assertions.assertThrows(IllegalStateException.class, () -> shell.setHiveConfValue("foo", "bar"));
    }

    @Test
    public void propertyShouldBeSetInHiveConfiguration() {
        shell.setHiveConfValue("foo", "bar");
        shell.start();
        Assertions.assertEquals("bar", shell.getHiveConf().get("foo"));
    }



}
