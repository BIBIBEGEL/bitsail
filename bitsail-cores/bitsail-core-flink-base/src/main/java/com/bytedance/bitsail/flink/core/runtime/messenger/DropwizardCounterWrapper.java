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

package com.bytedance.bitsail.flink.core.runtime.messenger;

import org.apache.flink.metrics.Counter;

public class DropwizardCounterWrapper implements Counter {

  private final com.codahale.metrics.Counter counter;

  public DropwizardCounterWrapper(com.codahale.metrics.Counter counter) {
    this.counter = counter;
  }

  @Override
  public void inc() {
    counter.inc();
  }

  @Override
  public void inc(long n) {
    counter.inc(n);
  }

  @Override
  public void dec() {
    counter.dec();
  }

  @Override
  public void dec(long n) {
    counter.dec(n);
  }

  @Override
  public long getCount() {
    return counter.getCount();
  }
}
