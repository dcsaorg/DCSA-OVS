package org.dcsa.ovs.util.pagination;

import lombok.NonNull;
import lombok.Value;
import org.springframework.data.domain.Sort;

/**
 * Defaults if no other options are given.
 */
@Value
public class CursorDefaults {
  private final int pageSize;

  @NonNull
  private final Sort.Direction direction;

  @NonNull
  private final String[] sortFields;

  public CursorDefaults(int pageSize, Sort.Direction direction, String... sortFields) {
    this.pageSize = pageSize;
    this.direction = direction;
    this.sortFields = sortFields;
  }
}
