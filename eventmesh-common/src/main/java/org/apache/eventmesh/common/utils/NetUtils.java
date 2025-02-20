/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.eventmesh.common.utils;

import static org.apache.eventmesh.common.Constants.SUCCESS_CODE;

import org.apache.eventmesh.common.Constants;
import org.apache.eventmesh.common.enums.HttpMethod;

import org.apache.http.Consts;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpExchange;

/**
 * NetUtils
 */
public class NetUtils {

    private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);

    /**
     * Transform the url form string to Map
     *
     * @param formData
     * @return
     */
    public static Map<String, String> formData2Dic(String formData) {
        Map<String, String> result = new HashMap<>();
        if (formData == null || formData.trim().length() == 0) {
            return result;
        }
        final String[] items = formData.split(Constants.AND);
        Arrays.stream(items).forEach(item -> {
            final String[] keyAndVal = item.split(Constants.EQ);
            if (keyAndVal.length == 2) {
                try {
                    final String key = URLDecoder.decode(keyAndVal[0], StandardCharsets.UTF_8.name());
                    final String val = URLDecoder.decode(keyAndVal[1], StandardCharsets.UTF_8.name());
                    result.put(key, val);
                } catch (UnsupportedEncodingException e) {
                    logger.warn("formData2Dic:param decode failed...", e);
                }
            }
        });
        return result;
    }

    public static String addressToString(List<InetSocketAddress> clients) {
        if (clients.isEmpty()) {
            return "no session had been closed";
        }
        StringBuilder sb = new StringBuilder();
        for (InetSocketAddress addr : clients) {
            sb.append(addr).append(Constants.VERTICAL_LINE);
        }
        return sb.toString();
    }

    public static String parsePostBody(HttpExchange exchange)
        throws IOException {
        StringBuilder body = new StringBuilder();
        if (HttpMethod.POST.name().equalsIgnoreCase(exchange.getRequestMethod())
            || HttpMethod.PUT.name().equalsIgnoreCase(exchange.getRequestMethod())) {
            try (InputStreamReader reader =
                     new InputStreamReader(exchange.getRequestBody(), Consts.UTF_8)) {
                char[] buffer = new char[256];
                int read;
                while ((read = reader.read(buffer)) != -1) {
                    body.append(buffer, 0, read);
                }
            }
        }
        return body.toString();
    }

    public static void sendSuccessResponseHeaders(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(SUCCESS_CODE, 0);
    }
}
