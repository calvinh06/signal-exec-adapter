package net.yoursignal.execadapter.compare;
import net.yoursignal.execadapter.api.ExecutionAdapter;
import net.yoursignal.execadapter.score.*;
public class SkillComparator {
  private final SkillScorer scorer;
  public SkillComparator(SkillScorer scorer) { this.scorer = scorer; }
  public SkillComparison compare(ExecutionAdapter<?,?> a, ExecutionAdapter<?,?> b) {
    var as = scorer.score(a); var bs = scorer.score(b);
    var ad = a.descriptor(); var bd = b.descriptor();
    double delta = bs.overall()-as.overall();
    LifecycleRecommendation r; String reason;
    if (ad.id().equals(bd.id()) && !ad.version().equals(bd.version()) && delta >= 8) { r=LifecycleRecommendation.PREFER_VERSION; reason="newer candidate materially out-scores current version"; }
    else if (Math.abs(delta) < 5 && ad.capability().equals(bd.capability())) { r=LifecycleRecommendation.MERGE; reason="similar value for the same capability; consider consolidation"; }
    else if (as.overall() < 40 && bs.overall() >= 65) { r=LifecycleRecommendation.ELIMINATE; reason="lower-scoring implementation is substantially dominated"; }
    else if (as.overall() < 60) { r=LifecycleRecommendation.UPGRADE; reason="current skill is below managed-quality target"; }
    else { r=LifecycleRecommendation.KEEP; reason="no material replacement advantage"; }
    return new SkillComparison(ad.id(), ad.version(), as, bd.id(), bd.version(), bs, r, reason);
  }
}
