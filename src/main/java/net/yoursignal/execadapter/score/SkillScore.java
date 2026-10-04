package net.yoursignal.execadapter.score;
import java.util.Map;
public record SkillScore(double overall, Map<ScoreDimension,Double> dimensions, String maturityLevel, int agentRatingCount) {}
