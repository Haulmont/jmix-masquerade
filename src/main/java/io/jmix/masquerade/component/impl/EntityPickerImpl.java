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

import io.jmix.masquerade.Components;
import io.jmix.masquerade.component.EntityPicker;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static io.jmix.masquerade.Selectors.byChain;
import static io.jmix.masquerade.Selectors.byJTestId;
import static io.jmix.masquerade.sys.TagNames.DIV;
import static io.jmix.masquerade.sys.VaadinClassNames.disabledClass;

public class EntityPickerImpl extends AbstractInputComponent<EntityPicker> implements EntityPicker {
    public EntityPickerImpl(By by) {
        super(by);
    }

    @Override
    public void triggerAction(Action action) {
        $(byChain(by, DIV, byJTestId(action.getId())))
                .shouldBe(visible)
                .shouldNotHave(disabledClass)
                .click();
    }

    @Override
    public <T> T triggerAction(Class<T> clazz, Action action) {
        triggerAction(action);
        return Components.wire(clazz);
    }

    @Override
    public String getValue() {
        return getInputDelegate()
                .shouldBe(visible)
                .getValue();
    }
}