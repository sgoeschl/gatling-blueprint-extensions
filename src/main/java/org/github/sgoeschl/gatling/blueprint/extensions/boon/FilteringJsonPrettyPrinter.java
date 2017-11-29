/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.github.sgoeschl.gatling.blueprint.extensions.boon;

import io.advantageous.boon.core.Lists;
import io.advantageous.boon.core.Predicate;
import io.advantageous.boon.json.JsonFactory;
import io.advantageous.boon.json.ObjectMapper;
import io.advantageous.boon.primitive.CharBuf;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;

/**
 * Pretty-print a Boon JSON document while skipping user-provided
 * keys and their content. This is handy when you want to print a
 * JSON document while skipping changing data such as the timestamps,
 * or generatedUUIDs.
 */
public final class FilteringJsonPrettyPrinter {

    private static final ObjectMapper OBJECT_MAPPER = JsonFactory.create();
    private static final String EMPTY_JSON_STRING = "[]";
    private static final Object EMPTY_JSON = OBJECT_MAPPER.fromJson(EMPTY_JSON_STRING);

    public static String prettyPrint(Object object) {
        return print(object);
    }

    public static String print(Object object, String... skippedKeys) {
        return print(object, asList(skippedKeys));
    }

    public static String print(Object object, Iterable<String> skippedKeys) {
        return print(object, false, new AcceptKeyPredicate(skippedKeys));
    }

    /**
     * Pretty-print the JSON object.
     *
     * @param object The JSON object to print
     * @param sort   Sort the JSON elements alphabetically or keep their natural order
     * @param filter Accept or skip JSON elements based on the key
     * @return the JSON string
     */
    private static String print(Object object, boolean sort, Predicate<String> filter) {
        final ModifyingCharBuf modifyingCharBuf = new ModifyingCharBuf(sort, filter);
        return modifyingCharBuf.prettyPrintObject(convertToJson(object), false, 0).toString();
    }

    private static Object convertToJson(Object object) {
        if (object == null) {
            return EMPTY_JSON;
        } else if (object instanceof byte[]) {
            return convertToJson((byte[]) object);
        } else if (object instanceof String) {
            return convertToJson((String) object);
        } else {
            return object;
        }
    }

    private static Object convertToJson(byte[] bytes) {
        return convertToJson(new String(bytes, Charset.forName("UTF-8")));
    }

    private static Object convertToJson(String value) {
        return value.trim().isEmpty() ? OBJECT_MAPPER.fromJson(EMPTY_JSON_STRING) : OBJECT_MAPPER.fromJson(value);
    }

    /**
     * Some ugly and intrusive hack using a lot of
     * internal Boon knowledge to get it working.
     */
    private static final class ModifyingCharBuf extends CharBuf {

        private final boolean sort;
        private final Predicate filter;

        private ModifyingCharBuf(boolean sort, Predicate filter) {
            this.sort = sort;
            this.filter = filter;
        }

        @Override
        public CharBuf prettyPrintMap(Map map) {
            return this.prettyPrintMap(map, 0);
        }

        @Override
        @SuppressWarnings("unchecked")
        public CharBuf prettyPrintMap(Map source, int indent) {

            final int indentChars = 1;

            if (source == null) {
                return null;
            }

            final Map map = createMap(source, sort, filter);

            final Set set = map.entrySet();
            final Iterator iterator = set.iterator();

            add("\n");
            indent(indent * indentChars).add("{\n");

            while (iterator.hasNext()) {
                final Map.Entry entry = (Map.Entry) iterator.next();
                indent((indent + 1) * indentChars);
                addJsonEscapedString(entry.getKey().toString().toCharArray());
                add(": ");
                final Object value = entry.getValue();
                prettyPrintObject(value, true, indent);
                add(",\n");
            }

            if (!map.isEmpty()) {
                removeLastChar();
                removeLastChar();
                add("\n");
            }

            indent(indent * indentChars).add('}');

            return this;
        }

        private Map createMap(Map<String, Object> map, boolean sort, Predicate<String> predicate) {

            final List<String> keys = Lists.filterBy(map.keySet(), predicate);
            final Map<String, Object> result = (sort ? new TreeMap<>() : new LinkedHashMap<>());

            for (String key : keys) {
                result.put(key, map.get(key));
            }

            return result;
        }
    }

    private static final class AcceptKeyPredicate implements Predicate<String> {

        private final Set<String> skippedKeys;

        AcceptKeyPredicate(Iterable<String> skippedKeys) {
            this.skippedKeys = new HashSet<>(toList(skippedKeys));
        }

        @Override
        public boolean test(String input) {
            return input != null && !skippedKeys.contains(input);
        }

        private static <T> List<T> toList(final Iterable<T> iterable) {
            return StreamSupport.stream(iterable.spliterator(), false)
                    .collect(Collectors.toList());
        }
    }
}
