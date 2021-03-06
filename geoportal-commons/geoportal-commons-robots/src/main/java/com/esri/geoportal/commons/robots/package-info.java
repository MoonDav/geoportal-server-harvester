/* See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * Esri Inc. licenses this file to You under the Apache License, Version 2.0
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

/**
 * Provides support for reading and parsing robots.txt
 * <p>
 * Standard for Robots Exclusion is a mechanism allowing servers to communicate
 * with web crawlers about it's access policy. This implementation follows
 * recommendations found in the following sources:
 * <p>
 * <a href="http://www.robotstxt.org/orig.html">http://www.robotstxt.org/orig.html</a><br>
 * <a href="http://www.robotstxt.org/norobots-rfc.txt">http://www.robotstxt.org/norobots-rfc.txt</a><br>
 * <a href="https://en.wikipedia.org/wiki/Robots_exclusion_standard">https://en.wikipedia.org/wiki/Robots_exclusion_standard</a><br>
 * <a href="https://developers.google.com/webmasters/control-crawl-index/docs/robots_txt">https://developers.google.com/webmasters/control-crawl-index/docs/robots_txt</a><br>
 * <p>
 * The easiest way to take advantage of this package is to request robots.txt
 * structure using {@link com.esri.geoportal.commons.robots.BotsUtils#readBots(com.esri.geoportal.commons.robots.BotsMode, java.lang.String)}
 * method. It will return an instance of the {@link com.esri.geoportal.commons.robots.Bots} or <code>null</code>
 * if no robots.txt available for the requested URL. If Bots are available, then the
 * next step is to request access to the resource through {@link com.esri.geoportal.commons.robots.BotsUtils#requestAccess(com.esri.geoportal.commons.robots.Bots, java.lang.String)}.
 * 
 * @see com.esri.geoportal.commons.robots.Bots
 * @see com.esri.geoportal.commons.robots.BotsParser
 * @see com.esri.geoportal.commons.robots.BotsUtils
 */
package com.esri.geoportal.commons.robots;
