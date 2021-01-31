package ru.ketbiev.bot.zoom.zoomapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ru.ketbiev.bot.zoom.config.Config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class Handler {

    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    private final CloseableHttpClient client = context.getBean("closeableHttpClient", CloseableHttpClient.class);

    final static Logger logger = Logger.getLogger(Handler.class);

    public String getRequest(String url, Map<String, String> headerParams) {
        HttpGet get = new HttpGet(url);
        for (String s : headerParams.keySet()) {
            get.setHeader(s, headerParams.get(s));
        }
        HttpResponse response = queryExecution(get);
        return getResponse(response);
    }

    public String postRequest(String url, Map<String, String> headerParams, JSONObject bodyParams) {
        try {
            HttpPost post = new HttpPost(url);
            for (String s : headerParams.keySet()) {
                post.setHeader(s, headerParams.get(s));
            }
            post.setEntity(new StringEntity(bodyParams.toString()));
            HttpResponse response = queryExecution(post);
            return getResponse(response);
        } catch (IOException e) {
            logger.error(e);
            return null;
        }
    }

    public String patchRequest(String url, Map<String, String> headerParams, JSONObject bodyParams) {
        try {
            HttpPatch patch = new HttpPatch(url);
            for (String s : headerParams.keySet()) {
                patch.setHeader(s, headerParams.get(s));
            }
            patch.setEntity(new StringEntity(bodyParams.toString()));
            HttpResponse response = queryExecution(patch);
            return getSuccess(response);
        } catch (IOException e) {
            logger.error(e);
            return null;
        }
    }

    public String deleteRequest(String url, Map<String, String> headerParams) {
        HttpDelete delete = new HttpDelete(url);
        for (String s : headerParams.keySet()) {
            delete.setHeader(s, headerParams.get(s));
        }
        HttpResponse response = queryExecution(delete);
        return getSuccess(response);
    }

    public HttpResponse queryExecution(HttpRequestBase get) {
        try {
            CompletableFuture<HttpResponse> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return client.execute(get);
                } catch (IOException e) {
                    return null;
                }
            });
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    public String getResponse(HttpResponse response) {
        try {
            String resp = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 300) {
                return "Error " + new JSONObject(resp).get("message");
            }
            return resp;
        } catch (IOException e) {
            return null;
        }
    }

    public String getSuccess(HttpResponse response) {
        try {
            if (response.getStatusLine().getStatusCode() >= 300) {
                String resp = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                return "Error " + new JSONObject(resp).get("message");
            }
            return "Done!";
        } catch (IOException e) {
            return null;
        }
    }
}
