package com.service.clientlibrary;

import com.service.clientlibrary.implementation.GeneratedServiceAPI;
import com.service.clientlibrary.implementation.TodoItemsAttachmentsService;
import io.clientcore.core.http.models.Response;
import io.clientcore.core.util.Context;
import io.clientcore.core.util.binarydata.BinaryData;

import java.util.List;

// This is the (commonly hand-written, but not always) convenience layer that the end user will interact with.
public class ClientLibrary {

    // NOTE: Normally these properties would be set by the user in a builder, but for now
    // they are simply hard coded here.
    // FIXME empty pipeline is not so useful here...
    private final GeneratedServiceAPI serviceAPI = GeneratedServiceAPI.getInstance(null);
    private final TodoItemsAttachmentsService todoItemsAttachmentsService = TodoItemsAttachmentsService.getInstance(null);

    private final String endpoint = "https://localhost:8080";

    public List<String> getKeys(String name) {
        return serviceAPI.getKeys(endpoint, name, "vals", "pathValueHere", "bar", Context.empty());
    }

    public void setKey() {
        serviceAPI.setKey(endpoint, "name", "bar", "key", Context.empty());
    }

    public Response<Void> testMethod() {
        return serviceAPI.testMethod(endpoint, "name", "bar", 0L);
    }

    public Response<BinaryData> listSync(long itemId, String accept) {
        return todoItemsAttachmentsService.listSync(endpoint, itemId, accept, null);
    }
}