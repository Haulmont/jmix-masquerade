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

package com.haulmont.masquerade.conditions;

import java.util.function.Supplier;

public class SpecificConditionContext {

    private static final ThreadLocal<SpecificConditionHandler> holder = new ThreadLocal<>();

    public static void with(SpecificConditionHandler handler, Runnable r) {
        holder.set(handler);
        try {
            r.run();
        } finally {
            holder.set(null);
        }
    }

    public static <T> T get(SpecificConditionHandler handler, Supplier<T> supplier) {
        holder.set(handler);
        try {
            return supplier.get();
        } finally {
            holder.set(null);
        }
    }

    public static SpecificConditionHandler getHandler() {
        return holder.get();
    }
}