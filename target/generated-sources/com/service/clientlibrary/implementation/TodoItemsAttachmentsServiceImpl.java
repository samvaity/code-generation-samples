package com.service.clientlibrary.implementation;

import io.clientcore.core.http.models.HttpHeaderName;
import io.clientcore.core.http.models.HttpHeaders;
import io.clientcore.core.http.models.HttpMethod;
import io.clientcore.core.http.models.HttpRequest;
import io.clientcore.core.http.models.RequestOptions;
import io.clientcore.core.http.models.Response;
import io.clientcore.core.http.models.ResponseBodyMode;
import io.clientcore.core.http.pipeline.HttpPipeline;
import io.clientcore.core.implementation.http.rest.RestProxyUtils;
import io.clientcore.core.util.ClientLogger;
import io.clientcore.core.util.binarydata.BinaryData;
import io.clientcore.core.util.serializer.ObjectSerializer;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.lang.Void;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TodoItemsAttachmentsServiceImpl implements TodoItemsAttachmentsService {
    private static final ClientLogger LOGGER = new ClientLogger(TodoItemsAttachmentsServiceImpl.class);

    private static Map<HttpPipeline, TodoItemsAttachmentsService> INSTANCE_MAP = new HashMap<>();

    private final HttpPipeline defaultPipeline;

    private final ObjectSerializer serializer = RestProxyUtils.createDefaultSerializer();

    private TodoItemsAttachmentsServiceImpl(HttpPipeline defaultPipeline) {
        this.defaultPipeline = defaultPipeline;
    }

    public static TodoItemsAttachmentsService getInstance(HttpPipeline defaultPipeline) {
        return INSTANCE_MAP.computeIfAbsent(defaultPipeline, pipeline -> new TodoItemsAttachmentsServiceImpl(defaultPipeline));
    }

    private HttpRequest configRequest(HttpRequest request, ObjectSerializer serializer) {
        return request;
    }

    @Override
    public Response<BinaryData> listSync(String endpoint, long itemId, String accept,
            RequestOptions requestOptions) {
        return listSync(defaultPipeline, endpoint, itemId, accept, requestOptions);
    }

    private Response<BinaryData> listSync(HttpPipeline pipeline, String endpoint, long itemId,
            String accept, RequestOptions requestOptions) {
        String host = endpoint + "/items/" + itemId + "/attachments";

        // create the request;
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, host);

        // set the headers;
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaderName.fromString("accept"), "accept");
        httpRequest.setHeaders(headers);

        // send the request through the pipeline;
        Response<?> response = pipeline.send(httpRequest);

        final int responseCode = response.getStatusCode();
        boolean expectedResponse = Arrays.binarySearch(new int[] {200, 404}, responseCode) > -1;
        if (!expectedResponse) {
            throw new RuntimeException("Unexpected response code: " + responseCode);
        }
        ResponseBodyMode responseBodyMode = null;
        if (requestOptions != null) {
            responseBodyMode = requestOptions.getResponseBodyMode();
        }
        return (Response<BinaryData>) handleResponseMode(response, responseBodyMode);
    }

    @Override
    public Response<Void> createAttachmentSync(String endpoint, long itemId, String accept,
            BinaryData contents, RequestOptions requestOptions) {
        return createAttachmentSync(defaultPipeline, endpoint, itemId, accept, contents, requestOptions);
    }

    private Response<Void> createAttachmentSync(HttpPipeline pipeline, String endpoint, long itemId,
            String accept, BinaryData contents, RequestOptions requestOptions) {
        String host = endpoint + "/items/" + itemId + "/attachments";

        // create the request;
        HttpRequest httpRequest = new HttpRequest(HttpMethod.POST, host);

        // set the headers;
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaderName.fromString("accept"), "accept");
        httpRequest.setHeaders(headers);

        // set the body;
        httpRequest = configRequest(httpRequest, serializer);

        // send the request through the pipeline;
        Response<?> response = pipeline.send(httpRequest);

        final int responseCode = response.getStatusCode();
        boolean expectedResponse = Arrays.binarySearch(new int[] {204, 404}, responseCode) > -1;
        if (!expectedResponse) {
            throw new RuntimeException("Unexpected response code: " + responseCode);
        }
        try {
            response.close();
        } catch (IOException e) {
            throw LOGGER.logThrowableAsError(new UncheckedIOException(e));
        }
        return (Response<Void>) response;
    }

    private Response<?> handleResponseMode(Response<?> response,
            ResponseBodyMode responseBodyMode) {
        return response;
    }
}
