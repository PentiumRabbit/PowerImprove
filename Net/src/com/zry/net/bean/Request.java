package com.zry.net.bean;

import android.net.Uri;

import com.zry.net.database.NetSaveModel;
import com.zry.net.engine.ConnectMode;
import com.zry.net.engine.NetWork.RequestMethod;
import com.zry.net.http.DataParser;
import com.zry.net.http.StringParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ----zhaoruyang----
 * @data: 2014/12/25
 */
public final class Request {
    private final String key;
    private final NetSaveModel saveModel;
    private final RequestMethod method;
    private final String urlType;
    private final int threadPriority;
    private final Map<String, String> params;
    private final boolean isSync;
    private final ConnectMode connectMode;
    private final DataParser parser;


    private Request(Builder builder) {
        key = builder.key;
        saveModel = builder.saveModel;
        method = builder.method;
        urlType = builder.urlType;
        threadPriority = builder.threadPriority;
        params = builder.params;
        isSync = builder.isSync;
        connectMode = builder.connectMode;
        parser = builder.parser;
    }


    public static Request createSimple() {
        return new Builder().build();
    }

    public ConnectMode getConnectMode() {
        return connectMode;
    }

    public DataParser parser() {
        return parser;
    }

    public boolean isSync() {
        return isSync;
    }

    public String getKey() {
        return key;
    }

    public String url() {
        return urlType;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public boolean saveCache() {
        return saveModel != NetSaveModel.no_cache;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public int getThreadPriority() {
        return threadPriority;
    }

    public boolean readCache() {
        return saveModel != NetSaveModel.no_cache;
    }

    public NetSaveModel getSaveModel() {
        return saveModel;
    }

    public static class Builder {
        public String urlType;
        public Map<String, String> params = new HashMap<>();
        public boolean isSync = false;
        /*默认HttpClient*/
        public ConnectMode connectMode = ConnectMode.connect_ok;
        private String key;
        private NetSaveModel saveModel = NetSaveModel.no_cache;
        private RequestMethod method = RequestMethod.POST;
        private int threadPriority = android.os.Process.THREAD_PRIORITY_BACKGROUND;
        private DataParser parser = new StringParser();

        public Builder() {

        }

        public Builder connectMode(ConnectMode connectMode) {
            this.connectMode = connectMode;
            return this;
        }


        public Builder saveModel(NetSaveModel saveModel) {
            this.saveModel = saveModel;
            return this;
        }

        public Builder method(RequestMethod method) {
            this.method = method;
            return this;
        }

        public Builder url(String url) {
            this.urlType = url;
            return this;
        }

        public Builder threadPriority(int threadPriority) {
            this.threadPriority = threadPriority;
            return this;
        }

        public Builder addParams(Map<String, String> params) {
            this.params.putAll(params);
            return this;
        }

        public Builder addParams(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        public Builder sync(boolean isSync) {
            this.isSync = isSync;
            return this;
        }

        public Builder parser(DataParser parser) {
            this.parser = parser;
            return this;
        }


        /**
         * Sets all options equal to incoming options
         */
        public Builder cloneFrom(Request options) {
            key = options.key;
            saveModel = options.saveModel;
            method = options.method;
            urlType = options.urlType;
            threadPriority = options.threadPriority;
            params = options.params;
            isSync = options.isSync;
            parser = options.parser;
            connectMode = options.connectMode;

            return this;
        }


        public Request build() {
            key = createKey();
            return new Request(this);
        }

        protected String createKey() {
            if (urlType == null || params == null || params.isEmpty()) {
                return urlType;
            }
            Uri.Builder builder = Uri.parse(urlType).buildUpon();
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.appendQueryParameter(key, params.get(key));
            }
            return builder.build().toString();
        }


    }
}
