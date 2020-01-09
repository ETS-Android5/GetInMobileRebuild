package org.odk.getin.android.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import org.odk.getin.android.BuildConfig;
import org.odk.getin.android.database.helpers.MappedGirlsDatabaseHelper;
import org.odk.getin.android.provider.base.BaseContentProvider;
import org.odk.getin.android.provider.appointmentstable.AppointmentstableColumns;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableColumns;
import org.odk.getin.android.provider.userstable.UserstableColumns;

public class MappedGirlsProvider extends BaseContentProvider {
    private static final String TAG = MappedGirlsProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "org.odk.getin.android.provider.odk.mappedgirls";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_APPOINTMENTSTABLE = 0;
    private static final int URI_TYPE_APPOINTMENTSTABLE_ID = 1;

    private static final int URI_TYPE_MAPPEDGIRLTABLE = 2;
    private static final int URI_TYPE_MAPPEDGIRLTABLE_ID = 3;

    private static final int URI_TYPE_USERSTABLE = 4;
    private static final int URI_TYPE_USERSTABLE_ID = 5;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, AppointmentstableColumns.TABLE_NAME, URI_TYPE_APPOINTMENTSTABLE);
        URI_MATCHER.addURI(AUTHORITY, AppointmentstableColumns.TABLE_NAME + "/#", URI_TYPE_APPOINTMENTSTABLE_ID);
        URI_MATCHER.addURI(AUTHORITY, MappedgirltableColumns.TABLE_NAME, URI_TYPE_MAPPEDGIRLTABLE);
        URI_MATCHER.addURI(AUTHORITY, MappedgirltableColumns.TABLE_NAME + "/#", URI_TYPE_MAPPEDGIRLTABLE_ID);
        URI_MATCHER.addURI(AUTHORITY, UserstableColumns.TABLE_NAME, URI_TYPE_USERSTABLE);
        URI_MATCHER.addURI(AUTHORITY, UserstableColumns.TABLE_NAME + "/#", URI_TYPE_USERSTABLE_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return MappedGirlsDatabaseHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_APPOINTMENTSTABLE:
                return TYPE_CURSOR_DIR + AppointmentstableColumns.TABLE_NAME;
            case URI_TYPE_APPOINTMENTSTABLE_ID:
                return TYPE_CURSOR_ITEM + AppointmentstableColumns.TABLE_NAME;

            case URI_TYPE_MAPPEDGIRLTABLE:
                return TYPE_CURSOR_DIR + MappedgirltableColumns.TABLE_NAME;
            case URI_TYPE_MAPPEDGIRLTABLE_ID:
                return TYPE_CURSOR_ITEM + MappedgirltableColumns.TABLE_NAME;

            case URI_TYPE_USERSTABLE:
                return TYPE_CURSOR_DIR + UserstableColumns.TABLE_NAME;
            case URI_TYPE_USERSTABLE_ID:
                return TYPE_CURSOR_ITEM + UserstableColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_APPOINTMENTSTABLE:
            case URI_TYPE_APPOINTMENTSTABLE_ID:
                res.table = AppointmentstableColumns.TABLE_NAME;
                res.idColumn = AppointmentstableColumns._ID;
                res.tablesWithJoins = AppointmentstableColumns.TABLE_NAME;
                res.orderBy = AppointmentstableColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_MAPPEDGIRLTABLE:
            case URI_TYPE_MAPPEDGIRLTABLE_ID:
                res.table = MappedgirltableColumns.TABLE_NAME;
                res.idColumn = MappedgirltableColumns._ID;
                res.tablesWithJoins = MappedgirltableColumns.TABLE_NAME;
                res.orderBy = MappedgirltableColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_USERSTABLE:
            case URI_TYPE_USERSTABLE_ID:
                res.table = UserstableColumns.TABLE_NAME;
                res.idColumn = UserstableColumns._ID;
                res.tablesWithJoins = UserstableColumns.TABLE_NAME;
                res.orderBy = UserstableColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_APPOINTMENTSTABLE_ID:
            case URI_TYPE_MAPPEDGIRLTABLE_ID:
            case URI_TYPE_USERSTABLE_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
