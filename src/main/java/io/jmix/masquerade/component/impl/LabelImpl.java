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

package io.jmix.masquerade.component.impl;

import io.jmix.masquerade.component.Label;
import io.jmix.masquerade.condition.SpecificCondition;
import io.jmix.masquerade.condition.Value;
import io.jmix.masquerade.condition.ValueContains;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exactTextCaseSensitive;
import static com.codeborne.selenide.Condition.text;
import static io.jmix.masquerade.sys.matcher.ConditionCases.componentApply;
import static io.jmix.masquerade.sys.matcher.InstanceOfCases.hasType;
import static com.leacox.motif.Motif.match;

public class LabelImpl extends AbstractComponent<Label> implements Label {
    public LabelImpl(By by) {
        super(by);
    }

    @Override
    public String getValue() {
        return impl.getText();
    }

    @Override
    public boolean apply(SpecificCondition condition) {
        return componentApply(match(condition), getDelegate())
                .when(hasType(Value.class)).get(v ->
                        impl.has(exactTextCaseSensitive(v.getExpectedValue()))
                )
                .when(hasType(ValueContains.class)).get(v ->
                        impl.has(text(v.getExpectedValueSubstring()))
                )
                .getMatch();
    }
}