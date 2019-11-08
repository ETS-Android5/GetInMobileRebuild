package org.odk.collect.android.provider.mappedgirltable;

import androidx.annotation.Nullable;

import org.odk.collect.android.provider.base.BaseModel;

import java.util.Date;


/**
 * Data model for the {@code mappedgirltable} table.
 */
public interface MappedgirltableModel extends BaseModel {

    /**
     * Get the {@code serverid} value.
     * Can be {@code null}.
     */
    @Nullable
    String getServerid();

    /**
     * Get the {@code firstname} value.
     * Can be {@code null}.
     */
    @Nullable
    String getFirstname();

    /**
     * Get the {@code lastname} value.
     * Can be {@code null}.
     */
    @Nullable
    String getLastname();

    /**
     * Get the {@code phonenumber} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPhonenumber();

    /**
     * Get the {@code nextofkinlastname} value.
     * Can be {@code null}.
     */
    @Nullable
    String getNextofkinlastname();

    /**
     * Get the {@code nextofkinfirstname} value.
     * Can be {@code null}.
     */
    @Nullable
    String getNextofkinfirstname();

    /**
     * Get the {@code nextofkinphonenumber} value.
     * Can be {@code null}.
     */
    @Nullable
    String getNextofkinphonenumber();

    /**
     * Get the {@code educationlevel} value.
     * Can be {@code null}.
     */
    @Nullable
    String getEducationlevel();

    /**
     * Get the {@code maritalstatus} value.
     * Can be {@code null}.
     */
    @Nullable
    String getMaritalstatus();

    /**
     * Get the {@code age} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getAge();

    /**
     * Get the {@code user} value.
     * Can be {@code null}.
     */
    @Nullable
    String getUser();

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getCreatedAt();

    /**
     * Get the {@code completed_all_visits} value.
     * Can be {@code null}.
     */
    @Nullable
    Boolean getCompletedAllVisits();

    /**
     * Get the {@code pending_visits} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getPendingVisits();

    /**
     * Get the {@code missed_visits} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getMissedVisits();

    /**
     * Get the {@code village} value.
     * Can be {@code null}.
     */
    @Nullable
    String getVillage();
}
