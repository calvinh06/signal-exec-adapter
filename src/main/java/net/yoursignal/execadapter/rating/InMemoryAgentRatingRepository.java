package net.yoursignal.execadapter.rating;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
public class InMemoryAgentRatingRepository implements AgentRatingRepository {
  private final List<AgentSkillRating> ratings = new CopyOnWriteArrayList<>();
  public void save(AgentSkillRating rating) { ratings.add(Objects.requireNonNull(rating)); }
  public List<AgentSkillRating> find(String skillId, String version) { return ratings.stream().filter(r -> r.skillId().equals(skillId) && r.version().equals(version)).toList(); }
}
