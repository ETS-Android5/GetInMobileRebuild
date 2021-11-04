package org.odk.getin.android.provider.healthfacilitytable;

import androidx.annotation.Nullable;

import org.odk.getin.android.provider.base.BaseModel;

import java.util.Date;


/**
 * Data model for the {@code healthfacilitytable} table.
 */
public interface HealthfacilitytableModel extends BaseModel {

    /**
     * Get the {@code serverid} value.
     * Can be {@code null}.
     */
    @Nullable
    String getServerid();

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getName();

    /**
     * Get the {@code facilitylevel} value.
     * Can be {@code null}.
     */
    @Nullable
    String getFacilitylevel();

    /**
     * Get the {@code district} value.
     * Can be {@code null}.
     */
    @Nullable
    String getDistrict();

    /**
     * Get the {@code subcounty} value.
     * Can be {@code null}.
     */
    @Nullable
    String getSubcounty();

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getCreatedAt();
}
