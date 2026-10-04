package net.yoursignal.execadapter.score;
import net.yoursignal.execadapter.api.ExecutionAdapter;
import net.yoursignal.execadapter.feature.*;
import net.yoursignal.execadapter.model.*;
import net.yoursignal.execadapter.rating.*;
import java.util.*;
public class DefaultSkillScorer implements SkillScorer {
  private final AgentRatingRepository ratings;
  public DefaultSkillScorer(AgentRatingRepository ratings) { this.ratings = Objects.requireNonNull(ratings); }
  public SkillScore score(ExecutionAdapter<?,?> adapter) {
    var d = adapter.descriptor();
    double capability = capability(adapter, d);
    double reliability = reliability(adapter);
    double observability = observability(adapter);
    var rs = ratings.find(d.id(), d.version());
    double agentValue = rs.isEmpty() ? 50.0 : weightedAgentValue(rs);
    double overall = capability*.30 + reliability*.30 + observability*.20 + agentValue*.20;
    var dims = new EnumMap<ScoreDimension,Double>(ScoreDimension.class);
    dims.put(ScoreDimension.CAPABILITY, round(capability));
    dims.put(ScoreDimension.RELIABILITY, round(reliability));
    dims.put(ScoreDimension.OBSERVABILITY, round(observability));
    dims.put(ScoreDimension.AGENT_VALUE, round(agentValue));
    return new SkillScore(round(overall), Map.copyOf(dims), maturity(adapter), rs.size());
  }
  private double capability(ExecutionAdapter<?,?> a, SkillDescriptor d) {
    double s = 40; // descriptor + execute are mandatory
    if (!d.requiredAuthorities().isEmpty()) s += 15;
    if (!d.requiredCapabilities().isEmpty()) s += 15;
    if (d.sideEffect()!=null) s += 10;
    if (a instanceof StatusAwareExecution) s += 10;
    if (a instanceof CancellableExecution) s += 10;
    return Math.min(100,s);
  }
  private double reliability(ExecutionAdapter<?,?> a) {
    double s = 25;
    if (a instanceof IdempotentExecution) s += 25;
    if (a instanceof RetryAwareExecution) s += 20;
    if (a instanceof HealthAwareExecution) s += 15;
    if (a instanceof StatusAwareExecution) s += 10;
    if (a instanceof CompensatableExecution) s += 5;
    return Math.min(100,s);
  }
  private double observability(ExecutionAdapter<?,?> a) {
    double s = 20;
    if (a instanceof ObservableExecution) s += 35;
    if (a instanceof DiagnosableExecution) s += 35;
    if (a instanceof StatusAwareExecution) s += 10;
    return Math.min(100,s);
  }
  private double weightedAgentValue(List<AgentSkillRating> rs) {
    double num=0, den=0;
    for (var r: rs) { double w = Math.max(.05, r.confidence()); num += r.value()*w; den += w; }
    return den == 0 ? 50 : num/den;
  }
  private String maturity(ExecutionAdapter<?,?> a) {
    if (a instanceof CompensatableExecution && a instanceof ObservableExecution && a instanceof DiagnosableExecution && a instanceof CancellableExecution) return "LEVEL_5_RECOVERABLE";
    if (a instanceof ObservableExecution && a instanceof DiagnosableExecution && a instanceof CancellableExecution) return "LEVEL_4_AUTONOMOUS";
    if (a instanceof IdempotentExecution && a instanceof RetryAwareExecution && a instanceof HealthAwareExecution) return "LEVEL_3_RESILIENT";
    if (a instanceof StatusAwareExecution) return "LEVEL_2_MANAGED";
    return "LEVEL_1_CALLABLE";
  }
  private double round(double d) { return Math.round(d*10.0)/10.0; }
}
