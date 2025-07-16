package com.google.legal_sales_sidekick.app_filters.annotations;


import com.google.legal_sales_sidekick.constants.enums.AccessType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface APIAccess {
    AccessType[] accessTypes();
}