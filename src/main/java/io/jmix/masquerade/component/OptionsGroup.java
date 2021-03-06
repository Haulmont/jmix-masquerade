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

package io.jmix.masquerade.component;

import io.jmix.masquerade.Conditions;
import io.jmix.masquerade.util.Log;

import java.util.List;

/**
 * OptionsGroup component with single select.
 * <br>
 * Supported conditions:
 * <ul>
 *     <li>{@link Conditions#VISIBLE}</li>
 *     <li>{@link Conditions#HIDDEN}</li>
 *     <li>{@link Conditions#ENABLED}</li>
 *     <li>{@link Conditions#DISABLED}</li>
 *     <li>{@link Conditions#value(String)}</li>
 *     <li>{@link Conditions#options(String...)}</li>
 *     <li>{@link Conditions#optionsCount(int)}</li>
 * </ul>
 */
public interface OptionsGroup extends Field<OptionsGroup> {
    String getSelectedValue();
    int getSelectedIndex();

    @Log
    OptionsGroup select(String option);

    List<String> getOptions();
}