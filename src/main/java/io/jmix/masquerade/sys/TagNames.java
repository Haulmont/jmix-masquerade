/*
 * Copyright (c) 2008-2020 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jmix.masquerade.sys;

import org.openqa.selenium.By;

public final class TagNames {
    public static final By SPAN = By.tagName("span");
    public static final By DIV = By.tagName("div");
    public static final By TD = By.tagName("td");
    public static final By TR = By.tagName("tr");
    public static final By LABEL = By.tagName("label");
    public static final By INPUT = By.tagName("input");
    public static final By TEXTAREA = By.tagName("textarea");

    private TagNames() {
    }
}