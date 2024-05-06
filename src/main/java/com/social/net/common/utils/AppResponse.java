package com.social.net.common.utils;

public abstract class AppResponse<T, U extends App> {
    public abstract T fromEntity(U entity);

    public abstract U toUpdatedEntity(U entity);

    public abstract U toEntity();
}
