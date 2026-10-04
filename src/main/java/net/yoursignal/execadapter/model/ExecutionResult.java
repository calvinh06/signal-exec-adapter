package net.yoursignal.execadapter.model;
public record ExecutionResult<O>(String executionId, ExecutionStatus status, O output, ExecutionDiagnostics diagnostics, ObservabilityCoordinates observability) {}
