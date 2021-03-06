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
import io.jmix.masquerade.component.TextField;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;

public class TextFieldImpl extends AbstractInputComponent<TextField> implements TextField {

    public TextFieldImpl(By by) {
        super(by);
    }

    @Override
    protected SelenideElement getInputDelegate() {
        return impl;
    }

    @Override
    public TextField setValue(String value) {
        impl.shouldBe(visible)
                .shouldBe(enabled)
                .shouldNotBe(readonly)
                .setValue(value);
        return this;
    }

    @Override
    public String getValue() {
        return impl.shouldBe(visible)
                .getValue();
    }
}