package net.yoursignal.execadapter.api;
import net.yoursignal.execadapter.model.*;
public interface ExecutionAdapter<I,O> {
  SkillDescriptor descriptor();
  ExecutionResult<O> execute(I input, ExecutionContext context);
}
