package org.dcsa.ovs.util.pagination;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A cursor implementation that maps to how spring data does paging.
 */
@Value
@RequiredArgsConstructor(onConstructor_={@JsonCreator})
public class Cursor {
  public record SortBy(Sort.Direction direction, String field) {}

  private final int page;
  private final int pageSize;
  private final List<SortBy> sortBy;

  public Cursor(int page, int pageSize, SortBy... sortBy) {
    this(page, pageSize, Arrays.asList(sortBy));
  }

  /**
   * Constructor to use the same direction on all fields.
   */
  public Cursor(int page, int pageSize, Sort.Direction direction, String... fields) {
    this(page, pageSize, Arrays.stream(fields).map(field -> new SortBy(direction, field)).collect(Collectors.toList()));
  }

  /**
   * Transform the cursor into a PageRequest that can be used together with JPA repositories.
   */
  @JsonIgnore
  public PageRequest toPageRequest() {
    return PageRequest.of(
      page,
      pageSize,
      Sort.by(sortBy.stream().map(sb -> new Sort.Order(sb.direction, sb.field)).collect(Collectors.toList()))
    );
  }

  /**
   * Returns a cursor for a specific page.
   */
  @JsonIgnore
  public Cursor withPage(int page) {
    return page == this.page ? this : new Cursor(page, pageSize, sortBy);
  }

  /**
   * Returns a cursor relative to the page pointed to by this cursor.
   */
  @JsonIgnore
  public Cursor withRelativePage(int offset) {
    return new Cursor(page + offset, pageSize, sortBy);
  }
}
