package net.yoursignal.execadapter.score;
import net.yoursignal.execadapter.api.ExecutionAdapter;
public interface SkillScorer { SkillScore score(ExecutionAdapter<?,?> adapter); }
