package net.yoursignal.execadapter.model;
import java.time.Instant;
import java.util.Map;
public record ObservabilityCoordinates(String executionId, String correlationId, String traceId, String spanId, String service, String environment, Instant startedAt, Instant completedAt, Map<String,String> labels) {
  public ObservabilityCoordinates { labels = labels == null ? Map.of() : Map.copyOf(labels); }
}
