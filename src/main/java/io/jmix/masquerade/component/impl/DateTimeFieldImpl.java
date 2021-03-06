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
import io.jmix.masquerade.component.DateTimeField;
import io.jmix.masquerade.condition.DateValue;
import io.jmix.masquerade.condition.SpecificCondition;
import io.jmix.masquerade.condition.TimeValue;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static io.jmix.masquerade.Conditions.*;
import static io.jmix.masquerade.Selectors.byChain;
import static io.jmix.masquerade.sys.VaadinClassNames.readonlyClass;
import static io.jmix.masquerade.sys.VaadinClassNames.requiredClass;
import static io.jmix.masquerade.sys.matcher.ConditionCases.componentApply;
import static io.jmix.masquerade.sys.matcher.InstanceOfCases.hasType;
import static com.leacox.motif.MatchesExact.eq;
import static com.leacox.motif.Motif.match;
import static org.openqa.selenium.By.cssSelector;

public class DateTimeFieldImpl extends AbstractComponent<DateTimeField> implements DateTimeField {

    public static final By DATEPART = cssSelector("div[class*='popupcalendar'] > input");
    public static final By TIMEPART = cssSelector("input[class*='timefield']");

    public DateTimeFieldImpl(By by) {
        super(by);
    }

    @SuppressWarnings("CodeBlock2Expr")
    @Override
    public boolean apply(SpecificCondition condition) {
        return componentApply(match(condition), impl)
                .when(eq(REQUIRED)).get(() -> {
                    return impl.has(requiredClass);
                })
                .when(eq(READONLY)).get(() -> {
                    return impl.has(readonlyClass);
                })
                .when(eq(EDITABLE)).get(() -> {
                    return !impl.has(readonlyClass);
                })
                .when(hasType(DateValue.class)).get(dv -> {
                    SelenideElement dateFieldImpl = $(byChain(by, DATEPART));
                    return dateFieldImpl.has(exactValue(dv.getExpectedValue()));
                })
                .when(hasType(TimeValue.class)).get(tv -> {
                    SelenideElement timeFieldImpl = $(byChain(by, TIMEPART));
                    return timeFieldImpl.has(exactValue(tv.getExpectedValue()));
                })
                .getMatch();
    }

    @Override
    public String getDateValue() {
        return $(byChain(by, DATEPART))
                .shouldBe(visible)
                .shouldBe(enabled)
                .getValue();
    }

    @Override
    public DateTimeField setDateValue(String value) {
        SelenideElement dateFieldImpl = $(byChain(by, DATEPART));
        dateFieldImpl
                .shouldBe(visible)
                .shouldBe(enabled)
                .shouldNotBe(readonly)
                .click();

        dateFieldImpl.sendKeys(Keys.HOME, value);
        return this;
    }

    @Override
    public String getTimeValue() {
        return $(byChain(by, TIMEPART))
                .shouldBe(visible)
                .shouldBe(enabled)
                .getValue();
    }

    @Override
    public DateTimeField setTimeValue(String value) {
        SelenideElement timeFieldImpl = $(byChain(by, TIMEPART));

        timeFieldImpl
                .shouldBe(visible)
                .shouldBe(enabled)
                .shouldNotBe(readonly)
                .click();

        timeFieldImpl.sendKeys(Keys.HOME, value);
        return this;
    }
}