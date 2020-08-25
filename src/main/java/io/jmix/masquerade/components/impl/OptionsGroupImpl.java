/*
 * Copyright (c) 2008-2017 Haulmont.
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

package io.jmix.masquerade.components.impl;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.jmix.masquerade.Selectors;
import io.jmix.masquerade.components.OptionsGroup;
import io.jmix.masquerade.conditions.Options;
import io.jmix.masquerade.conditions.OptionsCount;
import io.jmix.masquerade.conditions.SpecificCondition;
import io.jmix.masquerade.conditions.Value;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.jmix.masquerade.Selectors.byChain;
import static io.jmix.masquerade.sys.TagNames.SPAN;
import static io.jmix.masquerade.sys.matchers.ConditionCases.componentApply;
import static io.jmix.masquerade.sys.matchers.InstanceOfCases.hasType;
import static com.leacox.motif.Motif.match;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;

public class OptionsGroupImpl extends AbstractComponent<OptionsGroup> implements OptionsGroup {

    public static final By SELECTED_OPTION = xpath(".//input[@checked]/following-sibling::label");
    public static final By OPTION_LABEL = tagName("label");

    public OptionsGroupImpl(By by) {
        super(by);
    }

    @Override
    public boolean apply(SpecificCondition condition) {
        return componentApply(match(condition), getDelegate())
                .when(hasType(Value.class)).get(v -> {
                    String value = $(byChain(by, SELECTED_OPTION)).getText();

                    return Objects.equals(value, v.getExpectedValue());
                })
                .when(hasType(Options.class)).get(opts -> {
                    List<String> texts = $$(byChain(by, OPTION_LABEL)).texts();
                    return Objects.equals(texts, opts.getOptions());
                })
                .when(hasType(OptionsCount.class)).get(optsCount -> {
                    ElementsCollection opts = $$(byChain(by, OPTION_LABEL));
                    return opts.size() == optsCount.getCount();
                })
                .getMatch();
    }

    @Override
    public String getSelectedValue() {
        impl.shouldBe(visible);

        SelenideElement selectedOpt = $(byChain(by, SELECTED_OPTION));
        if (selectedOpt.is(visible)) {
            return selectedOpt.getText();
        } else {
            return null;
        }
    }

    @Override
    public int getSelectedIndex() {
        impl.shouldBe(visible);

        SelenideElement selectedOpt = $(byChain(by, SELECTED_OPTION));
        if (!selectedOpt.is(visible)) {
            return -1;
        }

        List<String> texts = $$(byChain(by, OPTION_LABEL)).texts();

        return texts.indexOf(selectedOpt.getText());
    }

    @Override
    public OptionsGroup select(String option) {
        $(byChain(by, SPAN, Selectors.byText(option)))
                .shouldBe(visible)
                .click();
        return this;
    }

    @Override
    public List<String> getOptions() {
        impl.shouldBe(visible);

        return $$(byChain(by, OPTION_LABEL)).texts();
    }
}