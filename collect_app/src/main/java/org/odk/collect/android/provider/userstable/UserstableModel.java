package org.odk.collect.android.provider.userstable;

import androidx.annotation.Nullable;

import org.odk.collect.android.provider.base.BaseModel;

import java.util.Date;


/**
 * Data model for the {@code userstable} table.
 */
public interface UserstableModel extends BaseModel {

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
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getCreatedAt();

    /**
     * Get the {@code number_plate} value.
     * Can be {@code null}.
     */
    @Nullable
    String getNumberPlate();

    /**
     * Get the {@code role} value.
     * Can be {@code null}.
     */
    @Nullable
    String getRole();

    /**
     * Get the {@code midwifeid} value.
     * Can be {@code null}.
     */
    @Nullable
    String getMidwifeid();

    /**
     * Get the {@code village} value.
     * Can be {@code null}.
     */
    @Nullable
    String getVillage();
}
