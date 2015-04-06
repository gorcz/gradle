/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.api.publish.maven.internal.publisher;

import org.apache.maven.artifact.ant.Authentication;
import org.apache.maven.artifact.ant.RemoteRepository;
import org.gradle.api.artifacts.repositories.PasswordCredentials;
import org.gradle.api.credentials.Credentials;
import org.gradle.internal.Factory;
import org.gradle.internal.artifacts.repositories.MavenArtifactRepositoryInternal;

class MavenRemoteRepositoryFactory implements Factory<RemoteRepository> {

    private final MavenArtifactRepositoryInternal artifactRepository;

    public MavenRemoteRepositoryFactory(MavenArtifactRepositoryInternal artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    public RemoteRepository create() {
        RemoteRepository remoteRepository = new RemoteRepository();
        remoteRepository.setUrl(artifactRepository.getUrl().toString());

        Credentials alternativeCredentials = artifactRepository.getAlternativeCredentials();
        if(alternativeCredentials instanceof PasswordCredentials){
            PasswordCredentials credentials = (PasswordCredentials) alternativeCredentials;
            if(credentials != null) {
                String username = credentials.getUsername();
                String password = credentials.getPassword();
                if (username != null || password != null) {
                    Authentication authentication = new Authentication();
                    authentication.setUserName(username);
                    authentication.setPassword(password);
                    remoteRepository.addAuthentication(authentication);
                }
            }
        }
        return remoteRepository;

    }
}