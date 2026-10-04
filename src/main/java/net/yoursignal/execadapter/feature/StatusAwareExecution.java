package net.yoursignal.execadapter.feature;
import net.yoursignal.execadapter.model.ExecutionStatus;
public interface StatusAwareExecution { ExecutionStatus status(String executionId); }
