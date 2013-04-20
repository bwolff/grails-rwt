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
 * This class helps to extract the RWT theme configurations from the Grails config. It also
 * provides utility methods to work with these configurations.
 *
 * @author Benjamin Wolff
 */
@Commons
class ThemeConfigurationHelper {

    final List<ThemeConfigurationBean> themeConfigurations = []

    public ThemeConfigurationHelper(ConfigObject grailsConfig) {
        extractThemeConfigurations(grailsConfig)
    }

    Boolean hasThemeId(String themeId) {
        return themeConfigurations.find { it.themeId == themeId } != null
    }

    private void extractThemeConfigurations(ConfigObject grailsConfig) {
        grailsConfig.rwt.themes.each { String name, value ->
            def themeConfiguration = new ThemeConfigurationBean()
            themeConfiguration.themeId = name.toLowerCase() // Ignore capitalization.

            if (value instanceof List) {
                themeConfiguration.themeFilePaths = value
            } else if (value instanceof String) {
                themeConfiguration.themeFilePaths = [value]
            } else {
                throw new GrailsConfigurationException("[RWT] Invalid value for theme \"${name}\"." +
                    " The value needs to be a string denoting the CSS file in the classpath," +
                    " or a list of path strings.")
            }

            themeConfigurations << themeConfiguration
        }
    }
}
