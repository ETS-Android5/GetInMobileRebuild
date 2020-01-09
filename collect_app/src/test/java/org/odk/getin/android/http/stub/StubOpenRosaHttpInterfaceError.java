package org.odk.getin.android.http.stub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.odk.getin.android.http.HttpCredentialsInterface;
import org.odk.getin.android.http.HttpGetResult;

import java.net.URI;

public class StubOpenRosaHttpInterfaceError extends StubOpenRosaHttpInterface {

    @Override
    @NonNull
    public HttpGetResult executeGetRequest(@NonNull URI uri, @Nullable String contentType, @Nullable HttpCredentialsInterface credentials) throws Exception {
        return null;
    }
}
