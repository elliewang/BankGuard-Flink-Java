package com.bankguard;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
class FraudRulesTest {
 private final FraudRules rules=new FraudRules();
 private Transaction tx(double amount){return new Transaction("T-1","A-1",amount,"RETAIL","US","D-1","CARD",1700000000000L);}
 @Test void normalTransactionAllowed(){FraudDecision d=rules.score(tx(80),1,80,false,false);assertEquals("ALLOW",d.action);assertEquals(0,d.score);}
 @Test void suspiciousVelocityTriggersReview(){FraudDecision d=rules.score(tx(2500),5,5000,false,false);assertEquals("REVIEW",d.action);assertEquals(48,d.score);}
 @Test void multipleRedFlagsBlock(){FraudDecision d=rules.score(tx(6000),6,12000,true,true);assertEquals("BLOCK",d.action);assertEquals(100,d.score);}
 @Test void invalidJsonRejected(){assertThrows(Exception.class,()->TransactionParser.parse("{\"id\":\"x\",\"amount\":-4}"));}
 @Test void agentReturnsCitedPolicies(){FraudDecision d=rules.score(tx(6000),6,12000,true,false);assertFalse(new InvestigationAgent().investigate(d).get("ruleReferences").toString().isEmpty());}
}
