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

/**
 * Simple pogo that encapsulates the configuration for an RWT theme. A list of these object is
 * created according to the Config.groovy configuration and passed to the
 * {@link ApplicationConfigurationBean}, which uses it to register the theme.
 *
 * @author Benjamin Wolff
 */
class ThemeConfigurationBean {

    public static final String DEFAULT_THEME_ID = 'rwtdefault'

    String themeId
    List<String> themeFilePaths

    Boolean isDefaultTheme() {
        return DEFAULT_THEME_ID == themeId
    }
}
