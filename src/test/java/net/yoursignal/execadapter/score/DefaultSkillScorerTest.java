package net.yoursignal.execadapter.score;
import net.yoursignal.execadapter.api.ExecutionAdapter;
import net.yoursignal.execadapter.feature.*;
import net.yoursignal.execadapter.model.*;
import net.yoursignal.execadapter.rating.*;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class DefaultSkillScorerTest {
  static class RichAdapter implements ExecutionAdapter<Map<String,Object>,Map<String,Object>>, StatusAwareExecution, IdempotentExecution, RetryAwareExecution, ObservableExecution, DiagnosableExecution, HealthAwareExecution {
    public SkillDescriptor descriptor(){ return new SkillDescriptor("erp.lookup","1.0.0","erp.inventory.lookup","lookup",Set.of("erp.inventory.read"),Set.of("erp.inventory.lookup"),SideEffect.READ_ONLY,Set.of()); }
    public ExecutionResult<Map<String,Object>> execute(Map<String,Object> i, ExecutionContext c){ return new ExecutionResult<>("1",ExecutionStatus.SUCCEEDED,i,null,null); }
    public ExecutionStatus status(String id){return ExecutionStatus.SUCCEEDED;} public String idempotencyStrategy(){return "request-key";} public int maxRetries(){return 3;} public boolean healthy(){return true;}
    public ObservabilityCoordinates observability(String id){return new ObservabilityCoordinates(id,"c","t","s","svc","test",Instant.now(),Instant.now(),Map.of());}
    public ExecutionDiagnostics diagnose(String id){return new ExecutionDiagnostics("INVOCATION",null,"ok",false,Map.of(),List.of());}
  }
  @Test void scoresAndAcceptsAgentValue(){
    var repo=new InMemoryAgentRatingRepository();
    repo.save(new AgentSkillRating("erp.lookup","1.0.0","agent-a",90,.9,"useful",Instant.now()));
    var score=new DefaultSkillScorer(repo).score(new RichAdapter());
    assertTrue(score.overall()>75); assertEquals(1,score.agentRatingCount()); assertEquals("LEVEL_3_RESILIENT",score.maturityLevel());
  }
}
