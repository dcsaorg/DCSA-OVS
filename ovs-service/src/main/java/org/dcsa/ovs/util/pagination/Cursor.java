package org.dcsa.ovs.util.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * A cursor implementation that maps to how spring data does paging.
 */
@Value
public class Cursor {
  private final int page;
  private final int pageSize;
  private final Sort.Direction direction;
  private final String[] sortFields;

  public Cursor(int page, int pageSize, Sort.Direction direction, String... sortFields) {
    this.page = page;
    this.pageSize = pageSize;
    this.direction = direction;
    this.sortFields = sortFields;
  }

  /**
   * Transform the cursor into a PageRequest that can be used together with JPA repositories.
   */
  @JsonIgnore
  public PageRequest toPageRequest() {
    return PageRequest.of(page, pageSize, Sort.by(direction, sortFields));
  }

  /**
   * Returns a cursor for a specific page.
   */
  @JsonIgnore
  public Cursor withPage(int page) {
    return page == this.page ? this : new Cursor(page, pageSize, direction, sortFields);
  }

  /**
   * Returns a cursor relative to the page pointed to by this cursor.
   */
  @JsonIgnore
  public Cursor withRelativePage(int offset) {
    return new Cursor(page + offset, pageSize, direction, sortFields);
  }
}
