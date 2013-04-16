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

import org.eclipse.rap.rwt.client.WebClient

/**
 * Simple pogo that encapsulates the configuration for an RWT entry point. A list of these object is
 * created according to the Config.groovy configuration and passed to the
 * {@link ApplicationConfigurationBean}, which uses it to register the entry point.
 *
 * @author Benjamin Wolff
 */
class EntryPointConfigurationBean {

    String name
    String path
    String entryPointBeanName
    String pageTitle
    String favicon
    String themeId
    String headHtml
    String bodyHtml

    /**
     * Returns a map of the configured entry point properties according to the {@link WebClient}
     * constants.
     *
     * @return A map of the configured entry point properties. Never null.
     */
    Map<String, String> getEntryPointProperties() {
        Map<String, String> properties = [:]

        if (pageTitle) {
            properties[WebClient.PAGE_TITLE] = pageTitle
        }

        if (favicon) {
            properties[WebClient.FAVICON] = favicon
        }

        if (themeId) {
            properties[WebClient.THEME_ID] = themeId
        }

        if (headHtml) {
            properties[WebClient.HEAD_HTML] = headHtml
        }

        if (bodyHtml) {
            properties[WebClient.BODY_HTML] = bodyHtml
        }

        return properties
    }
}
