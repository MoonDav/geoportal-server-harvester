/*
 * Copyright 2016 Esri, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.esri.geoportal.harvester.api.defs;

/**
 * Publishing status.
 */
public class PublishingStatus {
  public static final PublishingStatus EMPTY   = new ImmutablePublishingStatus(0, 0, 0);
  public static final PublishingStatus SKIPPED = new ImmutablePublishingStatus(1, 0, 0);
  public static final PublishingStatus CREATED = new ImmutablePublishingStatus(0, 1, 0);
  public static final PublishingStatus UPDATED = new ImmutablePublishingStatus(0, 0, 1);
  
  private final long skipped;
  private final long created;
  private final long updated;
  
  /**
   * Creates instance of the status.
   * @param skipped number of records skipped
   * @param created number of created record
   * @param updated number of updated records
   */
  public PublishingStatus(long skipped, long created, long updated) {
    this.skipped = skipped;
    this.created = created;
    this.updated = updated;
  }
  
  /**
   * Creates empty status.
   * @return empty status
   */
  public static PublishingStatus emptyStatus() {
    return new PublishingStatus(0,0,0);
  }
  
  /**
   * Accumulates status.
   * @param ps publishing status
   * @return outcome of collecting
   */
  public PublishingStatus collect(PublishingStatus ps) {
    return new PublishingStatus(skipped+ps.getSkipped(), created+ps.getCreated(), updated+ps.getUpdated());
  }
  
  /**
   * Gets number of skipped records.
   * @return number of skipped records
   */
  public long getSkipped() {
    return skipped;
  }

  /**
   * Gets number of created records.
   * @return number of created records
   */
  public long getCreated() {
    return created;
  }

  /**
   * Gets number of updated records.
   * @return number of updated records
   */
  public long getUpdated() {
    return updated;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PublishingStatus) {
      PublishingStatus ps = (PublishingStatus)obj;
      return getSkipped()==ps.getSkipped() && getCreated()==ps.getCreated() && getUpdated()==ps.getUpdated();
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 73 * hash + (int) (this.skipped ^ (this.skipped >>> 32));
    hash = 73 * hash + (int) (this.created ^ (this.created >>> 32));
    hash = 73 * hash + (int) (this.updated ^ (this.updated >>> 32));
    return hash;
  }
  
  @Override
  public String toString() {
    if (equals(SKIPPED)) {
      return String.format("STATUS::SKIPPED");
    }
    if (equals(CREATED)) {
      return String.format("STATUS::CREATED");
    }
    if (equals(UPDATED)) {
      return String.format("STATUS::UPDATED");
    }
    if (equals(EMPTY)) {
      return String.format("STATUS::EMPTY");
    }
    return String.format("STATUS::skipped:%d,created:%d,updated:%s", skipped,created,updated);
  }
  
  /**
   * Publishing status which would disallow to mo
   */
  private static final class ImmutablePublishingStatus extends PublishingStatus {

    /**
     * Creates instance of the status.
     * @param skipped number of records skipped
     * @param created number of created record
     * @param updated number of updated records
     */
    public ImmutablePublishingStatus(long skipped, long created, long updated) {
      super(skipped, created, updated);
    }

    @Override
    public PublishingStatus collect(PublishingStatus ps) {
      throw new IllegalStateException("Object is immutable.");
    }
    
  }
}
