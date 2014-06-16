package org.mailfilter.service.model;

public class SpammerMongoEntity {

  public static final PropertyLiteralExpression<String> id = new PropertyLiteralExpression<String>(String.class, "_id");
  public static final PropertyLiteralExpression<String> sender = new PropertyLiteralExpression<String>(String.class, "sender");
  public static final PropertyLiteralExpression<String> email = new PropertyLiteralExpression<String>(String.class, "email");
  public static final PropertyLiteralExpression<String> status = new PropertyLiteralExpression<String>(String.class, "status");
  public static final PropertyLiteralExpression<String> description = new PropertyLiteralExpression<String>(String.class, "description");
  
}
