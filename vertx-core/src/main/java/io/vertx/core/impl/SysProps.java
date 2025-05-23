/*
 * Copyright (c) 2011-2024 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package io.vertx.core.impl;

import io.vertx.codegen.annotations.Unstable;
import io.vertx.core.internal.http.HttpHeadersInternal;

import java.io.File;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * Vert.x system properties, most of them are internal and not supported.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public enum SysProps {

  /**
   * Duplicate of {@link HttpHeadersInternal#DISABLE_HTTP_HEADERS_VALIDATION}
   */
  DISABLE_HTTP_HEADERS_VALIDATION("vertx.disableHttpHeadersValidation"),

  /**
   * Internal property that disables websockets benchmarking purpose.
   */
  DISABLE_WEBSOCKETS("vertx.disableWebsockets"),

  /**
   * Internal property that disables metrics for benchmarking purpose.
   */
  DISABLE_METRICS("vertx.disableMetrics"),

  /**
   * Internal property that disables the context task execution measures for benchmarking purpose.
   */
  DISABLE_CONTEXT_TIMINGS("vertx.disableContextTimings"),

  /**
   * Disable Netty DNS resolver usage.
   *
   * Documented and (not much) tested.
   */
  DISABLE_DNS_RESOLVER("vertx.disableDnsResolver"),

  /**
   * Default value of {@link io.vertx.core.file.FileSystemOptions#DEFAULT_FILE_CACHING_ENABLED}
   *
   */
  DISABLE_FILE_CACHING("vertx.disableFileCaching"),

  /**
   * Default value of {@link io.vertx.core.file.FileSystemOptions#DEFAULT_CLASS_PATH_RESOLVING_ENABLED}
   *
   */
  DISABLE_FILE_CP_RESOLVING("vertx.disableFileCPResolving"),

  /**
   * Default value of {@link io.vertx.core.file.FileSystemOptions#DEFAULT_FILE_CACHING_DIR}
   */
  FILE_CACHE_DIR("vertx.cacheDirBase") {
    @Override
    public String get() {
      String val = super.get();
      if (val == null) {
        // get the system default temp dir location (can be overriden by using the standard java system property)
        // if not present default to the process start CWD
        String tmpDir = System.getProperty("java.io.tmpdir", ".");
        String cacheDirBase = "vertx-cache";
        val = tmpDir + File.separator + cacheDirBase;
      }
      return val;
    }
  },

  /**
   * Enable bytes caching of HTTP/1.x immutable response headers.
   */
  @Unstable
  CACHE_IMMUTABLE_HTTP_RESPONSE_HEADERS("vertx.cacheImmutableHttpResponseHeaders"),

  /**
   * Enable common HTTP/1.x request headers to their lower case version
   *
   * <ul>
   *   <li>host/Host: {@link io.vertx.core.http.HttpHeaders#HOST}</li>
   *   <li>accept/Accept: {@link io.vertx.core.http.HttpHeaders#ACCEPT}</li>
   *   <li>content-type/Content-Type: {@link io.vertx.core.http.HttpHeaders#CONTENT_TYPE}</li>
   *   <li>content-length/Content-Length: {@link io.vertx.core.http.HttpHeaders#CONTENT_LENGTH}</li>
   *   <li>connection/Connection: {@link io.vertx.core.http.HttpHeaders#CONNECTION}</li>
   * </ul>
   *
   */
  @Unstable
  INTERN_COMMON_HTTP_REQUEST_HEADERS_TO_LOWER_CASE("vertx.internCommonHttpRequestHeadersToLowerCase"),

  /**
   * Configure the Vert.x logger.
   *
   * Documented and tested.
   */
  LOGGER_DELEGATE_FACTORY_CLASS_NAME("vertx.logger-delegate-factory-class-name"),

  JACKSON_DEFAULT_READ_MAX_NESTING_DEPTH("vertx.jackson.defaultReadMaxNestingDepth"),
  JACKSON_DEFAULT_READ_MAX_DOC_LEN("vertx.jackson.defaultReadMaxDocumentLength"),
  JACKSON_DEFAULT_READ_MAX_NUM_LEN("vertx.jackson.defaultReadMaxNumberLength"),
  JACKSON_DEFAULT_READ_MAX_STRING_LEN("vertx.jackson.defaultReadMaxStringLength"),
  JACKSON_DEFAULT_READ_MAX_NAME_LEN("vertx.jackson.defaultReadMaxNameLength"),
  JACKSON_DEFAULT_READ_MAX_TOKEN_COUNT("vertx.jackson.defaultMaxTokenCount"),

  ;

  public final String name;

  SysProps(String name) {
    this.name = name;
  }

  public String get() {
    return System.getProperty(name);
  }

  public OptionalLong getAsLong() throws NumberFormatException {
    String s = get();
    if (s != null) {
      return OptionalLong.of(Long.parseLong(s));
    }
    return OptionalLong.empty();
  }

  public OptionalInt getAsInt() throws NumberFormatException {
    String s = get();
    if (s != null) {
      return OptionalInt.of(Integer.parseInt(s));
    }
    return OptionalInt.empty();
  }

  public boolean getBoolean() {
    return Boolean.getBoolean(name);
  }

}
