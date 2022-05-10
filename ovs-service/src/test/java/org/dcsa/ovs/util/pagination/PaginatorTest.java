package org.dcsa.ovs.util.pagination;

import org.dcsa.ovs.configuration.JacksonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaginatorTest {
  private HttpServletRequest request = mock(HttpServletRequest.class);
  private HttpServletResponse response = mock(HttpServletResponse.class);
  private CursorDefaults cursorDefaults = new CursorDefaults(10, Sort.Direction.ASC, "sort_field");

  private Paginator paginator = new Paginator(new JacksonConfiguration().defaultObjectMapper());

  @BeforeEach
  public void resetMocks() {
    reset(request, response);
  }

  @Test
  public void testSerialization() {
    Cursor original = new Cursor(2, 20, Sort.Direction.ASC, "created_timestamp");

    String serialized = paginator.cursorToString(original);
    Cursor deserialized = paginator.cursorFromString(serialized);

    assertEquals(original, deserialized);
  }

  @Test
  public void testParseRequest_WithCursor() {
    // Setup
    Cursor original = new Cursor(2, 20, Sort.Direction.ASC, "created_timestamp");
    when(request.getParameter("cursor")).thenReturn(paginator.cursorToString(original));

    // Execute
    Cursor cursor = paginator.parseRequest(request, cursorDefaults);

    // Verify
    assertEquals(original, cursor);
  }

  @Test
  public void testParseRequest_WithLimit() {
    // Setup
    when(request.getParameter("limit")).thenReturn("33");

    // Execute
    Cursor cursor = paginator.parseRequest(request, cursorDefaults);

    // Verify
    assertEquals(0, cursor.getPage());
    assertEquals(33, cursor.getPageSize());
    assertEquals(cursorDefaults.getDirection(), cursor.getDirection());
    assertEquals(cursorDefaults.getSortFields(), cursor.getSortFields());
  }

  @Test
  public void testParseRequest_NoArgs() {
    // Execute
    Cursor cursor = paginator.parseRequest(request, cursorDefaults);

    // Verify
    assertEquals(0, cursor.getPage());
    assertEquals(cursorDefaults.getPageSize(), cursor.getPageSize());
    assertEquals(cursorDefaults.getDirection(), cursor.getDirection());
    assertEquals(cursorDefaults.getSortFields(), cursor.getSortFields());
  }

  @Test
  public void testSetPageHeaders_Page0Of1() {
    // Setup
    Cursor cursor = new Cursor(0, 20, Sort.Direction.ASC, "created_timestamp");
    when(request.getScheme()).thenReturn("http");
    when(request.getServerName()).thenReturn("localhost");
    when(request.getServerPort()).thenReturn(9090);
    when(request.getRequestURI()).thenReturn("/vx/myService");

    // Execute
    paginator.setPageHeaders(request, response, cursor, 1);

    // Verify
    verify(response).setHeader("Current-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor));
    verify(response).setHeader("First-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor));
    verify(response, never()).setHeader(eq("Next-Page"), any());
    verify(response, never()).setHeader(eq("Previous-Page"), any());
  }

  /* */
  @Test
  public void testSetPageHeaders_Page0Of2() {
    // Setup
    Cursor cursor = new Cursor(0, 20, Sort.Direction.ASC, "created_timestamp");
    when(request.getScheme()).thenReturn("http");
    when(request.getServerName()).thenReturn("localhost");
    when(request.getServerPort()).thenReturn(9090);
    when(request.getRequestURI()).thenReturn("/vx/myService");

    // Execute
    paginator.setPageHeaders(request, response, cursor, 2);

    // Verify
    verify(response).setHeader("Current-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor));
    verify(response).setHeader("First-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor));
    verify(response).setHeader("Next-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor.withPage(1)));
    verify(response, never()).setHeader(eq("Previous-Page"), any());
  }

  @Test
  public void testSetPageHeaders_Page1Of2() {
    // Setup
    Cursor cursor = new Cursor(1, 20, Sort.Direction.ASC, "created_timestamp");
    when(request.getScheme()).thenReturn("http");
    when(request.getServerName()).thenReturn("localhost");
    when(request.getServerPort()).thenReturn(9090);
    when(request.getRequestURI()).thenReturn("/vx/myService");

    // Execute
    paginator.setPageHeaders(request, response, cursor, 2);

    // Verify
    verify(response).setHeader("Current-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor.withPage(1)));
    verify(response).setHeader("First-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor.withPage(0)));
    verify(response, never()).setHeader(eq("Next-Page"), any());
    verify(response).setHeader("Previous-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor.withPage(0)));
  }

  @Test
  public void testSetPageHeaders_Page1Of3() {
    // Setup
    Cursor cursor = new Cursor(1, 20, Sort.Direction.ASC, "created_timestamp");
    when(request.getScheme()).thenReturn("http");
    when(request.getServerName()).thenReturn("localhost");
    when(request.getServerPort()).thenReturn(9090);
    when(request.getRequestURI()).thenReturn("/vx/myService");

    // Execute
    paginator.setPageHeaders(request, response, cursor, 3);

    // Verify
    verify(response).setHeader("Current-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor.withPage(1)));
    verify(response).setHeader("First-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor.withPage(0)));
    verify(response).setHeader("Next-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor.withPage(2)));
    verify(response).setHeader("Previous-Page", "http://localhost:9090/vx/myService?cursor=" + paginator.cursorToString(cursor.withPage(0)));
  }
}
