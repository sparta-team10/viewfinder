package com.sparta.viewfinder.entity;

import com.sparta.viewfinder.exception.LikeErrorCode;
import com.sparta.viewfinder.exception.MismatchException;
import java.util.Objects;

public enum ContentEnumType {
  POST("post"), COMMENT("comment");

  private final String type;

  ContentEnumType(String type) {
    this.type = type;
  }

  public static ContentEnumType getByType(String type) {
    if (Objects.equals(type, POST.type)) {
      return POST;
    } else if (Objects.equals(type, COMMENT.type)) {
      return COMMENT;
    } else
      throw new MismatchException(LikeErrorCode.CONTENT_TYPE_MISMATCH);
  }
}