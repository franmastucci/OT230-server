package com.alkemy.ong.controller;

public interface ApiConstants {

   String ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
   String ROLE_USER = "hasRole('ROLE_USER')";
   String BOTH = "hasAnyRole('ROLE_ADMIN','ROLE_USER')";
}
