package org.dcsa.ovs.util.pagination;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

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

    int pageSize = cursorDefaults.getPageSize();
    String limit = request.getParameter("limit");
    if (limit != null) {
      pageSize = Integer.parseInt(limit);
    }

    return new Cursor(0, pageSize, cursorDefaults.getSortBy());
  }

  /**
   * Sets DCSA pagination headers.
   *
   * @param totalPages total pages in a result (see also org.springframework.data.domain.Page)
   */
  public void setPageHeaders(
    HttpServletRequest request,
    HttpServletResponse response,
    Cursor currentCursor,
    int totalPages,
    String... allowedParameters
  ) {
    Set<String> allowedParametersSet = allowedParameters.length == 0 ? null : Arrays.stream(allowedParameters).collect(Collectors.toSet());
    setPageHeaders(request, response, currentCursor, totalPages, allowedParametersSet);
  }

  /**
   * Sets DCSA pagination headers.
   *
   * @param totalPages total pages in a result (see also org.springframework.data.domain.Page)
   */
  public void setPageHeaders(
    HttpServletRequest request,
    HttpServletResponse response,
    Cursor currentCursor,
    int totalPages,
    Set<String> allowedParameters
  ) {
    String basePath = request.getScheme() + "://" +
      request.getServerName() + ":" + request.getServerPort() +
      request.getRequestURI() + "?cursor=";
    String parameters = getRequestParameters(request, allowedParameters);

    response.setHeader("Current-Page", basePath + cursorToString(currentCursor) + parameters);
    response.setHeader("First-Page", basePath + cursorToString(currentCursor.withPage(0)) + parameters);

    if (currentCursor.getPage() > 0) {
      response.setHeader("Previous-Page", basePath + cursorToString(currentCursor.withRelativePage(-1)) + parameters);
    }
    if (totalPages > currentCursor.getPage() + 1) {
      response.setHeader("Next-Page", basePath + cursorToString(currentCursor.withRelativePage(1)) + parameters);
    }
  }

  @SneakyThrows
  String cursorToString(Cursor cursor) {
    return Base64.getUrlEncoder().encodeToString(objectMapper.writeValueAsBytes(cursor));
  }

  @SneakyThrows
  Cursor cursorFromString(String cursor) {
    return objectMapper.readValue(Base64.getUrlDecoder().decode(cursor), Cursor.class);
  }

  private String getRequestParameters(HttpServletRequest request, Set<String> allowedParameters) {
    StringBuilder parameters = new StringBuilder();

    request.getParameterMap().forEach((key, values) -> {
      if (!"limit".equals(key) && (allowedParameters == null || allowedParameters.contains(key))) {
        String firstValue = (values != null && values.length > 0) ? values[0] : "";
        parameters.append("&").append(urlEncode(key)).append("=").append(urlEncode(firstValue));
      } else {
        if (!"limit".equals(key)) {
          log.warn("Ignored request parameter '{}'", key);
        }
      }
    });

    return parameters.toString();
  }

  private String urlEncode(String src) {
    return URLEncoder.encode(src, Charset.defaultCharset());
  }
}
