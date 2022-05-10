package org.dcsa.ovs.util.pagination;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Helper class for doing "cursor" based pagination using spring-data.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Paginator {
  private final ObjectMapper objectMapper;

  /**
   * Parses the incoming request and returns a Cursor for querying jpa repositories.
   */
  public Cursor parseRequest(HttpServletRequest request, CursorDefaults cursorDefaults) {
    String cursor = request.getParameter("cursor");
    if (cursor != null) {
      return cursorFromString(cursor);
    }

    String limit = request.getParameter("limit");
    int pageSize = cursorDefaults.getPageSize();
    if (limit != null) {
      pageSize = Integer.parseInt(limit);
    }

    return new Cursor(0, pageSize, cursorDefaults.getDirection(), cursorDefaults.getSortFields());
  }

  /**
   * Sets DCSA pagination headers.
   *
   * @param totalPages total pages in a result (see also org.springframework.data.domain.Page)
   */
  public void setPageHeaders(HttpServletRequest request, HttpServletResponse response, Cursor currentCursor, int totalPages) {
    String basePath = new StringBuilder()
      .append(request.getScheme()).append("://")
      .append(request.getServerName()).append(":").append(request.getServerPort())
      .append(request.getRequestURI()).append("?cursor=")
      .toString();

    response.setHeader("Current-Page", basePath + cursorToString(currentCursor));
    response.setHeader("First-Page", basePath + cursorToString(currentCursor.withPage(0)));

    if (currentCursor.getPage() > 0) {
      response.setHeader("Previous-Page", basePath + cursorToString(currentCursor.withRelativePage(-1)));
    }
    if (totalPages > currentCursor.getPage() + 1) {
      response.setHeader("Next-Page", basePath + cursorToString(currentCursor.withRelativePage(1)));
    }
  }

  @SneakyThrows
  @VisibleForTesting
  String cursorToString(Cursor cursor) {
    return new String(Base64.getEncoder().encode(objectMapper.writeValueAsBytes(cursor)), StandardCharsets.ISO_8859_1);
  }

  @SneakyThrows
  @VisibleForTesting
  Cursor cursorFromString(String cursor) {
    return objectMapper.readValue(Base64.getDecoder().decode(cursor.getBytes(StandardCharsets.ISO_8859_1)), Cursor.class);
  }
}
