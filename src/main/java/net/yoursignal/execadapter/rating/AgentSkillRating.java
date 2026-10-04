package net.yoursignal.execadapter.rating;
import java.time.Instant;
public record AgentSkillRating(String skillId, String version, String agentId, double value, double confidence, String reason, Instant createdAt) {
  public AgentSkillRating {
    if (value < 0 || value > 100) throw new IllegalArgumentException("value must be 0..100");
    if (confidence < 0 || confidence > 1) throw new IllegalArgumentException("confidence must be 0..1");
    createdAt = createdAt == null ? Instant.now() : createdAt;
  }
}
