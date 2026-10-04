package net.yoursignal.execadapter.model;
import java.util.Set;
public record SkillDescriptor(String id, String version, String capability, String description, Set<String> requiredAuthorities, Set<String> requiredCapabilities, SideEffect sideEffect, Set<ExecutionFeature> declaredFeatures) {
  public SkillDescriptor { requiredAuthorities = requiredAuthorities == null ? Set.of() : Set.copyOf(requiredAuthorities); requiredCapabilities = requiredCapabilities == null ? Set.of() : Set.copyOf(requiredCapabilities); declaredFeatures = declaredFeatures == null ? Set.of() : Set.copyOf(declaredFeatures); }
}
