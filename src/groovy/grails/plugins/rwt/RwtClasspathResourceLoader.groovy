/*******************************************************************************
 * Copyright 2012-2013 Benjamin Wolff
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Benjamin Wolff - initial API and implementation
 ******************************************************************************/
package grails.plugins.rwt

import java.io.IOException
import java.io.InputStream;

import org.eclipse.rap.rwt.service.ResourceLoader;

class RwtClasspathResourceLoader implements ResourceLoader {

    final String resourcePath

    public RwtClasspathResourceLoader() {
        this(null)
    }

    public RwtClasspathResourceLoader(String resourcePath) {
        this.resourcePath = resourcePath
    }

    @Override
    public InputStream getResourceAsStream(String resourceName) throws IOException {
        if (resourcePath) {
            resourceName = resourcePath
        }

        return getClass().getClassLoader().getResourceAsStream(resourceName)
    }
}
