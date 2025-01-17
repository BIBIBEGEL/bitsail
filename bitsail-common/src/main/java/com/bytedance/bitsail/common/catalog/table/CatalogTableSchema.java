/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.bytedance.bitsail.common.catalog.table;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class CatalogTableSchema implements Serializable {

  private final List<CatalogTableColumn> columns;

  private final List<CatalogTableColumn> primaryKeys;

  public CatalogTableSchema(List<CatalogTableColumn> columns) {
    this(columns, Collections.emptyList());
  }

  public CatalogTableSchema(List<CatalogTableColumn> columns,
                            List<CatalogTableColumn> primaryKeys) {
    this.columns = columns;
    this.primaryKeys = primaryKeys;
  }

  @Override
  public String toString() {
    return "CatalogTableSchema{" +
        "columns=" + columns +
        ", primaryKeys=" + primaryKeys +
        '}';
  }
}
