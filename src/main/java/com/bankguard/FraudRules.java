package com.bankguard;
import java.util.*;
/** Interpretable scoring; illustrative thresholds, NOT calibrated or production-approved. */
public final class FraudRules {
 public FraudDecision score(Transaction tx,int attemptsFiveMinutes,double spentFiveMinutes,boolean newDevice,boolean crossBorder) {
  FraudDecision d=new FraudDecision(tx);int score=0;
  if(tx.amount>=5000){score+=35;d.reasons.add("HIGH_VALUE_TRANSACTION");}
  else if(tx.amount>=2000){score+=18;d.reasons.add("ELEVATED_AMOUNT");}
  if(attemptsFiveMinutes>=4){score+=30;d.reasons.add("VELOCITY_5M");}
  if(spentFiveMinutes>=8000){score+=25;d.reasons.add("SPEND_SPIKE_5M");}
  if(newDevice){score+=20;d.reasons.add("NEW_DEVICE");}
  if(crossBorder){score+=18;d.reasons.add("COUNTRY_CHANGE");}
  if("CRYPTO".equalsIgnoreCase(tx.merchant)||"WIRE".equalsIgnoreCase(tx.channel)){score+=15;d.reasons.add("HIGH_RISK_CHANNEL");}
  d.score=Math.min(100,score);d.action=score>=75?"BLOCK":score>=45?"REVIEW":"ALLOW";
  d.severity=score>=75?"CRITICAL":score>=45?"HIGH":score>=20?"MEDIUM":"LOW";
  return d;
 }
}
