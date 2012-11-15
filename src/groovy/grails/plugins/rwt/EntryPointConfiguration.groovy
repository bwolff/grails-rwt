package grails.plugins.rwt

import org.eclipse.rap.rwt.client.WebClient

/**
 * Simple POGO that encapsulates the configuration for an RWT entry point. A list of these object is
 * created according to the Config.groovy configuration and passed to the
 * {@link ApplicationConfigurationBean} that uses it to configure the entry point.
 *
 * @author Benjamin Wolff
 */
class EntryPointConfiguration {

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
