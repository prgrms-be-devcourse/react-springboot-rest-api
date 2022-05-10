package com.example.myfirstfullstackproject.controller.message;

public enum BusinessStatus {

    // for errors in CloudRestController
    ALL_CLOUDS_RETURNED(0, "카테고리 정보가 없으므로 저장된 모든 클라우드 상품을 반환합니다."),
    CLOUDS_BY_CATEGORY_RETURNED(1, "요청된 카테고리에 해당하는 클라우드 상품을 반환합니다."),
    ILLEGAL_CATEGORY_REQUEST(2, "존재하지 않는 카테고리에 대한 요청입니다."),

    // for errors in CloudRestController
    CONTRACT_CREATED(3, "계약 정보가 성공적으로 저장되었습니다."),
    NOT_ENOUGH_CONTRACT_FIELDS(4, "필요한 정보가 누락되었습니다. 확인해 주세요.");

    private final int statusCode;
    private final String description;

    BusinessStatus(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }

}
