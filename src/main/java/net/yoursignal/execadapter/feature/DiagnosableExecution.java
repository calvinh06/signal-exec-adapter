package net.yoursignal.execadapter.feature;
import net.yoursignal.execadapter.model.ExecutionDiagnostics;
public interface DiagnosableExecution { ExecutionDiagnostics diagnose(String executionId); }
