/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.advancement.criterion;


import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.advancement.criteria.AdvancementCriterion;
import org.spongepowered.api.advancement.criteria.ScoreAdvancementCriterion;
import org.spongepowered.api.advancement.criteria.trigger.FilteredTrigger;
import org.spongepowered.api.advancement.criteria.trigger.FilteredTriggerConfiguration;
import org.spongepowered.api.advancement.criteria.trigger.Trigger;
import org.spongepowered.common.util.Preconditions;

import java.util.Objects;


@SuppressWarnings("unchecked")
public abstract class AbstractCriterionBuilder<T extends AdvancementCriterion, B extends AdvancementCriterion.BaseBuilder<T, B>>
        implements ScoreAdvancementCriterion.BaseBuilder<T, B> {

    protected @Nullable FilteredTrigger<?> trigger;
    protected @Nullable String name;
    protected @Nullable Trigger<?> type;


    @Override
    public <C extends FilteredTriggerConfiguration> B trigger(Trigger<C> type, FilteredTrigger<C> trigger) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(trigger, "trigger");
        this.trigger = trigger;
        this.type = type;
        return (B) this;
    }

    @Override
    public B name(String name) {
        Objects.requireNonNull(name, "name");
        this.name = name;
        return (B) this;
    }

    @Override
    public T build() {
        Preconditions.checkState(this.name != null, "The name must be set");
        return this.build0();
    }

    abstract T build0();

    @Override
    public B from(T value) {
        this.trigger = value.trigger().orElse(null);
        this.name = value.name();
        return (B) this;
    }

    @Override
    public B reset() {
        this.trigger = null;
        this.name = null;
        return (B) this;
    }
}
