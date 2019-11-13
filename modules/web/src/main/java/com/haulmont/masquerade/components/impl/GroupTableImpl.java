package com.haulmont.masquerade.components.impl;

import com.codeborne.selenide.SelenideElement;
import com.haulmont.masquerade.Conditions;
import com.haulmont.masquerade.components.GroupTable;
import com.haulmont.masquerade.components.Table;
import com.haulmont.masquerade.conditions.SpecificCondition;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.haulmont.masquerade.Selectors.$c;
import static com.haulmont.masquerade.Selectors.byChain;
import static com.haulmont.masquerade.sys.matchers.ConditionCases.componentApply;
import static com.leacox.motif.MatchesExact.eq;
import static com.leacox.motif.Motif.match;
import static org.openqa.selenium.By.cssSelector;

public class GroupTableImpl extends AbstractComponent<GroupTable> implements GroupTable {

    public static final By CELL_EXPANDER = cssSelector("div[class*='c-grouptable-group-cell-expander']");
    public static final By GROUP_ROW = cssSelector("tr.c-group-row");
    public static final String EXPANDED_ROW_STYLE = "v-expanded";

    public GroupTableImpl(By by) {
        super(by);
    }

    @Override
    public boolean apply(SpecificCondition condition) {
        return componentApply(match(condition), getDelegate())
                // CAUTION copied from TableImpl
                .when(eq(Conditions.LOADED)).get(() -> {
                    // we have to wait for minimal lazy-loading time
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        return false;
                    }
                    return !$(byClassName("v-loading-indicator")).is(visible);
                })
                .when(eq(Conditions.EXPANDED)).get(() -> getCollapsedRows().isEmpty())
                .when(eq(Conditions.COLLAPSED)).get(() -> getExpandedRows().isEmpty())
                .getMatch();
    }

    @Override
    public GroupTable expandAll() {
        List<SelenideElement> collapsedRows = getGroupRows(this::isRowCollapsed);
        while (!collapsedRows.isEmpty()) {
            collapsedRows.forEach(this::expandRow);
            collapsedRows = getGroupRows(this::isRowCollapsed);
        }
        return this;
    }

    @Override
    public GroupTable collapseAll() {
        List<SelenideElement> expandedRows = getGroupRows(this::isRowExpanded);
        while (!expandedRows.isEmpty()) {
            expandedRows.forEach(this::collapseRow);
            expandedRows = getGroupRows(this::isRowExpanded);
        }
        return this;
    }

    @Override
    public Table asTable() {
        return $c(Table.class, by);
    }

    protected List<SelenideElement> getExpandedRows() {
        return getGroupRows()
                .stream()
                .filter(this::isRowExpanded)
                .collect(Collectors.toList());
    }

    protected List<SelenideElement> getCollapsedRows() {
        return getGroupRows()
                .stream()
                .filter(this::isRowCollapsed)
                .collect(Collectors.toList());
    }

    protected List<SelenideElement> getGroupRows() {
        return Arrays.asList($$(byChain(by, GROUP_ROW))
                .toArray(new SelenideElement[]{}));
    }

    protected List<SelenideElement> getGroupRows(Predicate<SelenideElement> predicate) {
        return getGroupRows()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    protected boolean isRowExpanded(SelenideElement groupRow) {
        return groupRow.has(cssClass(EXPANDED_ROW_STYLE));
    }

    protected boolean isRowCollapsed(SelenideElement groupRow) {
        return !isRowExpanded(groupRow);
    }

    protected void expandRow(SelenideElement groupRow) {
        if (isRowCollapsed(groupRow)) {
            toggleExpanded(groupRow);
        }
    }

    protected void collapseRow(SelenideElement groupRow) {
        if (isRowExpanded(groupRow)) {
            toggleExpanded(groupRow);
        }
    }

    protected void toggleExpanded(SelenideElement groupRow) {
        $(groupRow).$(CELL_EXPANDER)
                .shouldBe(visible)
                .click();
    }
}