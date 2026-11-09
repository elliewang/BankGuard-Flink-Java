package com.bankguard;
import java.io.Serializable;import java.util.ArrayList;import java.util.List;
public class FraudDecision implements Serializable {
 public String transactionId, accountId, merchant, country, action, severity, modelVersion="rules-v1", currency="USD";
 public long timestamp; public double amount; public int score; public List<String> reasons=new ArrayList<>();
 public FraudDecision() {}
 public FraudDecision(Transaction tx) {transactionId=tx.id;accountId=tx.accountId;merchant=tx.merchant;country=tx.country;timestamp=tx.timestamp;amount=tx.amount;currency=tx.currency;}
}
