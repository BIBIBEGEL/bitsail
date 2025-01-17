/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytedance.bitsail.flink.core.typeinfo;

import com.bytedance.bitsail.common.column.Column;
import com.bytedance.bitsail.common.column.MapColumn;
import com.bytedance.bitsail.flink.core.typeutils.base.MapColumnSerializer;

import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.util.Preconditions;

import java.util.Map;

import static org.apache.flink.util.Preconditions.checkNotNull;

/**
 * Special {@code TypeInformation} used by {@link org.apache.flink.api.common.state.MapStateDescriptor}.
 *
 * @param <K> The type of the keys in the map.
 * @param <V> The type of the values in the map.
 */
@PublicEvolving
@Deprecated
public class MapColumnTypeInfo<K extends Column, V extends Column> extends TypeInformation<MapColumn<K, V>> {

  /* The type information for the keys in the map*/
  private final TypeInformation<K> keyTypeInfo;

  /* The type information for the values in the map */
  private final TypeInformation<V> valueTypeInfo;

  public MapColumnTypeInfo(TypeInformation<K> keyTypeInfo, TypeInformation<V> valueTypeInfo) {
    this.keyTypeInfo = Preconditions.checkNotNull(keyTypeInfo, "The key type information cannot be null.");
    this.valueTypeInfo = Preconditions.checkNotNull(valueTypeInfo, "The value type information cannot be null.");
  }

  public MapColumnTypeInfo(Class<K> keyClass, Class<V> valueClass) {
    this.keyTypeInfo = of(checkNotNull(keyClass, "The key class cannot be null."));
    this.valueTypeInfo = of(checkNotNull(valueClass, "The value class cannot be null."));
  }

  // ------------------------------------------------------------------------
  //  MapTypeInfo specific properties
  // ------------------------------------------------------------------------

  /**
   * Gets the type information for the keys in the map
   */
  public TypeInformation<K> getKeyTypeInfo() {
    return keyTypeInfo;
  }

  /**
   * Gets the type information for the values in the map
   */
  public TypeInformation<V> getValueTypeInfo() {
    return valueTypeInfo;
  }

  // ------------------------------------------------------------------------
  //  TypeInformation implementation
  // ------------------------------------------------------------------------

  @Override
  public boolean isBasicType() {
    return false;
  }

  @Override
  public boolean isTupleType() {
    return false;
  }

  @Override
  public int getArity() {
    return 0;
  }

  @Override
  public int getTotalFields() {
    return 1;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<MapColumn<K, V>> getTypeClass() {
    return (Class<MapColumn<K, V>>) (Class<?>) Map.class;
  }

  @Override
  public boolean isKeyType() {
    return false;
  }

  @Override
  public TypeSerializer<MapColumn<K, V>> createSerializer(ExecutionConfig config) {
    TypeSerializer<K> keyTypeSerializer = keyTypeInfo.createSerializer(config);
    TypeSerializer<V> valueTypeSerializer = valueTypeInfo.createSerializer(config);

    return new MapColumnSerializer<K, V>(keyTypeSerializer, valueTypeSerializer, keyTypeInfo.getTypeClass(), valueTypeInfo.getTypeClass());
  }

  @Override
  public String toString() {
    return "Map<" + keyTypeInfo + ", " + valueTypeInfo + ">";
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    } else if (obj instanceof MapColumnTypeInfo) {
      @SuppressWarnings("unchecked")
      MapColumnTypeInfo<K, V> other = (MapColumnTypeInfo<K, V>) obj;

      return (other.canEqual(this) &&
          keyTypeInfo.equals(other.keyTypeInfo) && valueTypeInfo.equals(other.valueTypeInfo));
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return 31 * keyTypeInfo.hashCode() + valueTypeInfo.hashCode();
  }

  @Override
  public boolean canEqual(Object obj) {
    return (obj != null && obj.getClass() == getClass());
  }
}
