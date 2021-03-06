/*
 * Copyright 2014 the original author or authors.
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

package org.gradle.api.reporting.components.internal;

import org.apache.commons.lang.StringUtils;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.diagnostics.internal.text.TextReportBuilder;
import org.gradle.language.base.LanguageSourceSet;
import org.gradle.logging.StyledTextOutput;
import org.gradle.reporting.ReportRenderer;
import org.gradle.util.CollectionUtils;

import java.io.File;
import java.util.Comparator;
import java.util.Set;

class SourceSetRenderer extends ReportRenderer<LanguageSourceSet, TextReportBuilder> {
    static final Comparator<LanguageSourceSet> SORT_ORDER = new Comparator<LanguageSourceSet>() {
        @Override
        public int compare(LanguageSourceSet o1, LanguageSourceSet o2) {
            return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
        }
    };

    @Override
    public void render(LanguageSourceSet sourceSet, TextReportBuilder builder) {
        StyledTextOutput textOutput = builder.getOutput();
        textOutput.println(StringUtils.capitalize(sourceSet.getDisplayName()));
        Set<File> srcDirs = sourceSet.getSource().getSrcDirs();
        if (srcDirs.isEmpty()) {
            textOutput.println("    No source directories");
        } else {
            for (File file : srcDirs) {
                builder.item("srcDir", file);
            }
            SourceDirectorySet source = sourceSet.getSource();
            Set<String> includes = source.getIncludes();
            if(!includes.isEmpty()) {
                builder.item("includes", CollectionUtils.join(", ", includes));
            }
            Set<String> excludes = source.getExcludes();
            if(!excludes.isEmpty()) {
                builder.item("excludes", CollectionUtils.join(", ", excludes));
            }
        }
    }
}
