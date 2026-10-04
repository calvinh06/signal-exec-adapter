package net.yoursignal.execadapter.model;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
public record ExecutionContext(String agentId, String tenantId, String actingUserId, Set<String> authorities, String requestId, String correlationId, String idempotencyKey, Instant deadline, Map<String,Object> attributes) {
  public ExecutionContext { authorities = authorities == null ? Set.of() : Set.copyOf(authorities); attributes = attributes == null ? Map.of() : Map.copyOf(attributes); }
}
