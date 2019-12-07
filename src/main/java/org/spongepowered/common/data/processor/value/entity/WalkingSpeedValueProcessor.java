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
package org.spongepowered.common.data.processor.value.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.Value.Immutable;
import org.spongepowered.api.data.value.Value.Mutable;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.common.data.processor.common.AbstractSpongeValueProcessor;
import org.spongepowered.common.data.value.mutable.SpongeValue;
import org.spongepowered.common.mixin.accessor.entity.player.PlayerCapabilitiesAccessor;

import java.util.Optional;

public class WalkingSpeedValueProcessor extends AbstractSpongeValueProcessor<PlayerEntity, Double, Mutable<Double>> {

    public WalkingSpeedValueProcessor() {
        super(PlayerEntity.class, Keys.WALKING_SPEED);
    }

    @Override
    protected Mutable<Double> constructValue(final Double defaultValue) {
        return new SpongeValue<>(Keys.WALKING_SPEED, 0.7D);
    }

    @Override
    protected Immutable<Double> constructImmutableValue(final Double value) {
        return this.constructValue(value).asImmutable();
    }

    @Override
    protected boolean set(final PlayerEntity container, final Double value) {
        setWalkSpeed(container, value);
        container.sendPlayerAbilities();
        return true;
    }

    @Override
    protected Optional<Double> getVal(final PlayerEntity container) {
        return Optional.of(((double) container.abilities.getWalkSpeed()));
    }

    @Override
    public DataTransactionResult removeFrom(final ValueContainer<?> container) {
        return DataTransactionResult.failNoData();
    }

    public static void setWalkSpeed(final PlayerEntity container, final double value) {
        ((PlayerCapabilitiesAccessor) container.abilities).accessor$setWalkSpeed((float) value);
        final IAttributeInstance attribute = container.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        attribute.setBaseValue(value);
    }
}
