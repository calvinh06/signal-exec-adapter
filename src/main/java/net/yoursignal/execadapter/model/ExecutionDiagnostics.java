package net.yoursignal.execadapter.model;
import java.util.List;
import java.util.Map;
public record ExecutionDiagnostics(String phase, String errorCode, String message, boolean retryable, Map<String,Object> safeDetails, List<String> correctionHints) {
  public ExecutionDiagnostics { safeDetails = safeDetails == null ? Map.of() : Map.copyOf(safeDetails); correctionHints = correctionHints == null ? List.of() : List.copyOf(correctionHints); }
}
