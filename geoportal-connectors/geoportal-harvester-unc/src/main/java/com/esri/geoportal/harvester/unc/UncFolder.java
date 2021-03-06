/*
 * Copyright 2016 Esri, Inc.
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
package com.esri.geoportal.harvester.unc;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * UNC folder.
 */
/*package*/ class UncFolder {
  private static final FileSystem fileSystem = FileSystems.getDefault();
  private static final String DEFAULT_MATCH_PATTERN = "**.xml";
  
  private final UncBroker broker;
  private final File folder;
  private final String matchPattern;
  private final Date since;

  /**
   * Creates instance of the UNC folder.
   * @param broker broker
   * @param folder folder
   * @param matchPattern match pattern
   * @param since since date
   */
  public UncFolder(UncBroker broker, File folder, String matchPattern, Date since) {
    this.broker = broker;
    this.folder = folder;
    this.matchPattern = StringUtils.defaultIfBlank(matchPattern, DEFAULT_MATCH_PATTERN);
    this.since = since;
  }

  /**
   * Reads content of the folder.
   * @return content
   * @throws IOException if error reading content
   * @throws URISyntaxException if invalid URL
   */
  public UncFolderContent readContent() throws IOException, URISyntaxException {
    List<UncFile> files = new ArrayList<>();
    List<UncFolder> subFolders = new ArrayList<>();

    Arrays.asList(folder.listFiles()).forEach(f->{
      if (f.isDirectory()) {
        subFolders.add(new UncFolder(broker, f, matchPattern, since));
      } else if (f.isFile() && matchFileName(f, matchPattern) && (since==null || f.lastModified()>=since.getTime())) {
        files.add(new UncFile(broker, f));
      }
    });

    return new UncFolderContent(this, subFolders, files);
  }

  @Override
  public String toString() {
    return folder.toString();
  }
  
  /**
   * Matches file
   * @param file file
   * @param pattern match patter (glob)
   * @return <code>true</code> if URL matches the pattern
   */
  private boolean matchFileName(File file, String pattern) {
    Path path = file.toPath();
    PathMatcher pathMatcher = fileSystem.getPathMatcher("glob:"+pattern);
    return pathMatcher.matches(path);
  }
  
}
