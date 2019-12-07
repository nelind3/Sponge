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

import com.google.common.collect.Maps;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.BodyPart;
import org.spongepowered.api.data.type.BodyParts;
import org.spongepowered.api.data.value.MapValue.Mutable;
import org.spongepowered.api.data.value.Value.Immutable;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.common.data.processor.common.AbstractSpongeValueProcessor;
import org.spongepowered.common.data.value.mutable.SpongeMapValue;
import org.spongepowered.common.mixin.accessor.entity.item.ArmorStandEntityAccessor;
import org.spongepowered.common.util.VecHelper;
import org.spongepowered.math.vector.Vector3d;
import java.util.Map;
import java.util.Optional;
import net.minecraft.entity.item.ArmorStandEntity;

public class BodyRotationsValueProcessor extends AbstractSpongeValueProcessor<ArmorStandEntity, Map<BodyPart, Vector3d>, Mutable<BodyPart, Vector3d>> {

    public BodyRotationsValueProcessor() {
        super(ArmorStandEntity.class, Keys.BODY_ROTATIONS);
    }

    @Override
    public DataTransactionResult removeFrom(final ValueContainer<?> container) {
        return DataTransactionResult.failNoData();
    }

    @Override
    protected Mutable<BodyPart, Vector3d> constructValue(final Map<BodyPart, Vector3d> actualValue) {
        return new SpongeMapValue<>(Keys.BODY_ROTATIONS, actualValue);
    }

    @Override
    protected boolean set(final ArmorStandEntity container, final Map<BodyPart, Vector3d> value) {
        container.setHeadRotation(VecHelper.toRotation(value.get(BodyParts.HEAD)));
        container.setBodyRotation(VecHelper.toRotation(value.get(BodyParts.CHEST)));
        container.setLeftArmRotation(VecHelper.toRotation(value.get(BodyParts.LEFT_ARM)));
        container.setRightArmRotation(VecHelper.toRotation(value.get(BodyParts.RIGHT_ARM)));
        container.setLeftLegRotation(VecHelper.toRotation(value.get(BodyParts.LEFT_LEG)));
        container.setRightLegRotation(VecHelper.toRotation(value.get(BodyParts.RIGHT_LEG)));
        return true;
    }

    @Override
    protected Optional<Map<BodyPart, Vector3d>> getVal(final ArmorStandEntity container) {
        final Map<BodyPart, Vector3d> values = Maps.newHashMap();
        
        values.put(BodyParts.HEAD, VecHelper.toVector3d(container.getHeadRotation()));
        values.put(BodyParts.CHEST, VecHelper.toVector3d(container.getBodyRotation()));
        values.put(BodyParts.LEFT_ARM, VecHelper.toVector3d(((ArmorStandEntityAccessor) container).accessor$getLeftArmRotation()));
        values.put(BodyParts.RIGHT_ARM, VecHelper.toVector3d(((ArmorStandEntityAccessor) container).accessor$getRightArmRotation()));
        values.put(BodyParts.LEFT_LEG, VecHelper.toVector3d(((ArmorStandEntityAccessor) container).accessor$getLeftLegRotation()));
        values.put(BodyParts.RIGHT_LEG, VecHelper.toVector3d(((ArmorStandEntityAccessor) container).accessor$getRightLegRotation()));
        return Optional.of(values);
    }

    @Override
    protected Immutable<Map<BodyPart, Vector3d>> constructImmutableValue(final Map<BodyPart, Vector3d> value) {
        return this.constructValue(value).asImmutable();
    }

}
