package net.yoursignal.execadapter.compare;
import net.yoursignal.execadapter.score.SkillScore;
public record SkillComparison(String leftId, String leftVersion, SkillScore leftScore, String rightId, String rightVersion, SkillScore rightScore, LifecycleRecommendation recommendation, String reason) {}
