package com.bankguard;
import com.fasterxml.jackson.databind.ObjectMapper;
public final class TransactionParser {
 private static final ObjectMapper MAPPER=new ObjectMapper();
 private TransactionParser() {}
 public static Transaction parse(String line) throws Exception {
  Transaction t=MAPPER.readValue(line,Transaction.class);
  if(t.id==null||t.id.isBlank()||t.accountId==null||t.accountId.isBlank()||t.timestamp<=0||!Double.isFinite(t.amount)||t.amount<=0||t.country==null||t.deviceId==null) throw new IllegalArgumentException("Invalid transaction");
  return t;
 }
 public static String json(Object x) {try{return MAPPER.writeValueAsString(x);}catch(Exception e){throw new IllegalStateException(e);}}
}
