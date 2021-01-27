package ru.ketbiev.bot.zoom.zoomapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Handler {

    final static Logger logger = Logger.getLogger(Handler.class);

    public String getRequest (String url, Map<String, String> headerParams) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet get = new HttpGet(url);
            for(String s : headerParams.keySet()) {
                get.setHeader(s, headerParams.get(s));
            }
            CompletableFuture<HttpResponse> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return client.execute(get);
                } catch (IOException e) {
                    return null;
                }
            });
            HttpResponse response = future.get();
            //HttpResponse response = client.execute(get);
            return getResponse(response);
        } catch (IOException | InterruptedException | ExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    public String postRequest (String url, Map<String, String> headerParams, JSONObject bodyParams) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPost post = new HttpPost(url);
            for(String s : headerParams.keySet()) {
                post.setHeader(s, headerParams.get(s));
            }
            post.setEntity(new StringEntity(bodyParams.toString()));
            CompletableFuture<HttpResponse> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return client.execute(post);
                } catch (IOException e) {
                    return null;
                }
            });
            HttpResponse response = future.get();
            //HttpResponse response = client.execute(post);
            return getResponse(response);
        } catch (IOException | InterruptedException | ExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    public String patchRequest(String url, Map<String, String> headerParams, JSONObject bodyParams) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPatch patch = new HttpPatch(url);
            for(String s : headerParams.keySet()) {
                patch.setHeader(s, headerParams.get(s));
            }
            patch.setEntity(new StringEntity(bodyParams.toString()));
            CompletableFuture<HttpResponse> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return client.execute(patch);
                } catch (IOException e) {
                    return null;
                }
            });
            HttpResponse response = future.get();
            //HttpResponse response = client.execute(patch);
            return getSuccess(response);
        } catch (IOException | InterruptedException | ExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    public String deleteRequest (String url, Map<String, String> headerParams) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpDelete delete = new HttpDelete(url);
            for(String s : headerParams.keySet()) {
                delete.setHeader(s, headerParams.get(s));
            }
            CompletableFuture<HttpResponse> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return client.execute(delete);
                } catch (IOException e) {
                    return null;
                }
            });
            HttpResponse response = future.get();
            //HttpResponse response = client.execute(delete);
            return getSuccess(response);
        } catch (IOException | InterruptedException | ExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    public String getResponse(HttpResponse response) throws IOException {
        String resp = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        if (response.getStatusLine().getStatusCode()>=300) {
            String[] message = resp.split("\"message\":\"");
            return message[1].replace(".\"}", "");
        }
        return resp;
    }

    public String getSuccess(HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode()>=300) {
            String resp = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            String[] message = resp.split("\"message\":\"");
            return message[1].replace(".\"}", "");
        }
        return "Ok";
    }

}
