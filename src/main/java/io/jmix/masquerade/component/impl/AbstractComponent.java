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

import com.codeborne.selenide.SelenideElement;
import io.jmix.masquerade.component.Component;
import io.jmix.masquerade.condition.SpecificCondition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static io.jmix.masquerade.sys.matcher.ConditionCases.componentApply;
import static com.leacox.motif.Motif.match;

@SuppressWarnings("unchecked")
public abstract class AbstractComponent<T extends Component>
        extends AbstractSpecificConditionHandler<T>
        implements Component<T> {

    protected final By by;
    protected final SelenideElement impl;

    protected AbstractComponent(By by) {
        this.by = by;
        this.impl = $(by);
    }

    @Override
    public By getBy() {
        return by;
    }

    @Override
    public SelenideElement getDelegate() {
        return impl;
    }

    @Override
    public boolean apply(SpecificCondition condition) {
        return componentApply(match(condition), getDelegate())
                .getMatch();
    }
}