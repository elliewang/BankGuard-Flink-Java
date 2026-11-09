package com.bankguard;
import java.io.Serializable;
public class Transaction implements Serializable {
 public String id,accountId,merchant,country,deviceId,channel,currency; public double amount; public long timestamp;
 public Transaction() {} // Jackson / Flink
 public Transaction(String id,String accountId,double amount,String merchant,String country,String deviceId,String channel,long timestamp) {
  this.id=id;this.accountId=accountId;this.amount=amount;this.merchant=merchant;this.country=country;this.deviceId=deviceId;this.channel=channel;this.timestamp=timestamp;this.currency="USD";
 }
}
