/*
 * Copyright 2016 Esri, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.esri.geoportal.harvester.waf;

import com.esri.geoportal.commons.http.BotsHttpClient;
import static com.esri.geoportal.commons.utils.Constants.DEFAULT_REQUEST_CONFIG;
import static com.esri.geoportal.commons.utils.HttpClientContextBuilder.createHttpClientContext;
import com.esri.geoportal.commons.utils.SimpleCredentials;
import com.esri.geoportal.harvester.api.base.SimpleDataReference;
import com.esri.geoportal.harvester.api.mime.MimeType;
import com.esri.geoportal.harvester.api.mime.MimeTypeUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;

/**
 * WAF file.
 */
/*package*/ class WafFile {

  private final WafBroker broker;
  private final URL fileUrl;
  private final SimpleCredentials creds;

  /**
   * Creates instance of the WAF file.
   *
   * @param broker broker
   * @param fileUrl file URL
   * @param creds credentials
   */
  public WafFile(WafBroker broker, URL fileUrl, SimpleCredentials creds) {
    this.broker = broker;
    this.fileUrl = fileUrl;
    this.creds = creds;
  }

  /**
   * Reads content.
   * @param httpClient HTTP client
   * @return content reference
   * @throws IOException if reading content fails
   * @throws URISyntaxException if file url is an invalid URI
   */
  public SimpleDataReference readContent(BotsHttpClient httpClient) throws IOException, URISyntaxException {
    HttpGet method = new HttpGet(fileUrl.toExternalForm());
    method.setConfig(DEFAULT_REQUEST_CONFIG);
    HttpClientContext context = creds!=null && !creds.isEmpty()? createHttpClientContext(fileUrl, creds): null;
    HttpResponse response = httpClient.execute(method,context);
    Date lastModifiedDate = readLastModifiedDate(response);
    MimeType contentType = readContentType(response);
    try (InputStream input = response.getEntity().getContent()) {
      return new SimpleDataReference(broker.getBrokerUri(), fileUrl.toExternalForm(), lastModifiedDate, fileUrl.toURI(), IOUtils.toByteArray(input), contentType);
    }
  }

  /**
   * Reads last modified date.
   * @param response HTTP response
   * @return last modified date or <code>null</code> if unavailable
   */
  private Date readLastModifiedDate(HttpResponse response) {
    try {
      Header lastModifedHeader = response.getFirstHeader("Last-Modified");
      return lastModifedHeader != null
              ? Date.from(ZonedDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(lastModifedHeader.getValue())).toInstant())
              : null;
    } catch (Exception ex) {
      return null;
    }
  }
  
  /**
   * Reads content type.
   * @param response HTTP response
   * @return content type or <code>null</code> if unable to read content type
   */
  private MimeType readContentType(HttpResponse response) {
    try {
      Header contentTypeHeader = response.getFirstHeader("Content-Type");
      MimeType contentType = null;
      if (contentTypeHeader!=null) {
        contentType = MimeType.parse(contentTypeHeader.getValue());
      }
      if (contentType==null) {
        String strFileUrl = fileUrl.toExternalForm();
        int lastDotIndex = strFileUrl.lastIndexOf(".");
        String ext = lastDotIndex>=0? strFileUrl.substring(lastDotIndex+1): "";
        contentType = MimeTypeUtils.mapExtension(ext);
      }
      return contentType;
    } catch (Exception ex) {
      return null;
    }
  }

  /**
   * Gets file URL.
   * @return file URL
   */
  public URL getFileUrl() {
    return fileUrl;
  }

  @Override
  public String toString() {
    return fileUrl.toString();
  }
}
