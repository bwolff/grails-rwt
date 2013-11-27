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

import groovy.util.logging.Commons;

import org.codehaus.groovy.grails.exceptions.GrailsConfigurationException

/**
 * This class helps to extract the RWT entry point configurations from the Grails config. It also
 * provides utility methods to work with these configurations.
 *
 * @author Benjamin Wolff
 */
@Commons
class EntryPointConfigurationHelper {

    final List<EntryPointConfigurationBean> entryPointConfigurations = []

    public EntryPointConfigurationHelper(ConfigObject grailsConfig) {
        extractEntryPointConfigurations(grailsConfig)
    }

    Boolean hasEntryPoints() {
        return !entryPointConfigurations.isEmpty()
    }

    List<String> getEntryPointPaths() {
        return entryPointConfigurations.collect { it.path }
    }

    private void extractEntryPointConfigurations(ConfigObject grailsConfig) {
        grailsConfig.rwt.entrypoints.each { String name, values ->
            String entryPointBeanName = values.bean ?: null

            if (!entryPointBeanName) {
                throw new GrailsConfigurationException("[RWT] Missing \"bean\" value for" +
                        " entry point configuration: ${name}")
            }

            String themeId = values.themeId ?: null
            if (themeId) {
                themeId = themeId.toLowerCase() // Ignore capitalization.
            }

            def entryPointConfiguration = new EntryPointConfigurationBean()
            entryPointConfiguration.name = name
            entryPointConfiguration.path = '/' + name // The name of the entry point determines the URL path.
            entryPointConfiguration.entryPointBeanName = entryPointBeanName
            entryPointConfiguration.pageTitle = values.pageTitle ?: null
            entryPointConfiguration.favicon = values.favicon ?: null
            entryPointConfiguration.themeId = themeId
            entryPointConfiguration.headHtml = values.headHtml ?: null
            entryPointConfiguration.bodyHtml = values.bodyHtml ?: null
            entryPointConfigurations << entryPointConfiguration
        }
    }
}
