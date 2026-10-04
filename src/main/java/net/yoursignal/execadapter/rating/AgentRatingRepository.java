package net.yoursignal.execadapter.rating;
import java.util.List;
public interface AgentRatingRepository {
  void save(AgentSkillRating rating);
  List<AgentSkillRating> find(String skillId, String version);
}
