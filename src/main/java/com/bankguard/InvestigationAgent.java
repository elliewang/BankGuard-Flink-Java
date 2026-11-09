package com.bankguard;
import java.util.*;
/** Local, explainable retrieval agent; not an LLM and makes no autonomous blocking decisions. */
public final class InvestigationAgent {
 private static final Map<String,String> KNOWLEDGE=new LinkedHashMap<>();
 static {
  KNOWLEDGE.put("HIGH_VALUE_TRANSACTION","Rulebook F-101: inspect merchant history and confirm unusually large transaction with customer before release.");
  KNOWLEDGE.put("VELOCITY_5M","Rulebook F-112: repeated transactions in five minutes merit device verification and beneficiary review.");
  KNOWLEDGE.put("SPEND_SPIKE_5M","Rulebook F-120: check recent spend and account baseline; route large spikes for investigation.");
  KNOWLEDGE.put("NEW_DEVICE","Rulebook F-131: evaluate device binding, MFA and recent account recovery events.");
  KNOWLEDGE.put("COUNTRY_CHANGE","Rulebook F-142: compare travel history and verified location changes; avoid using geography alone.");
  KNOWLEDGE.put("HIGH_RISK_CHANNEL","Rulebook F-156: apply heightened review to riskier transaction channels with additional independent signals.");
 }
 public Map<String,Object> investigate(FraudDecision d){
  List<String> evidence=new ArrayList<>();for(String reason:d.reasons)if(KNOWLEDGE.containsKey(reason))evidence.add(KNOWLEDGE.get(reason));
  String next="ALLOW".equals(d.action)?"Continue monitoring; retain audit trail.":"BLOCK".equals(d.action)?"Escalate to a human fraud analyst before any irreversible action; verify customer and transaction details.":"Queue for analyst review and request corroborating device or behavioral signals.";
  Map<String,Object> response=new LinkedHashMap<>();response.put("transactionId",d.transactionId);response.put("riskScore",d.score);response.put("ruleReferences",evidence);response.put("nextAction",next);response.put("automatedDecision",false);return response;
 }
 public static void main(String[] args){Transaction t=new Transaction("TX-AGENT","ACC-1001",6500,"CRYPTO","GB","new-device","WIRE",System.currentTimeMillis());FraudDecision d=new FraudRules().score(t,5,10000,true,true);System.out.println(TransactionParser.json(new InvestigationAgent().investigate(d)));}
}
