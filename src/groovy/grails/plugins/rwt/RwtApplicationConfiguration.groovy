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

import groovy.util.logging.Commons

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.exceptions.GrailsConfigurationException
import org.eclipse.rap.rwt.RWT
import org.eclipse.rap.rwt.application.Application
import org.eclipse.rap.rwt.application.ApplicationConfiguration
import org.eclipse.rap.rwt.application.Application.OperationMode
import org.eclipse.rap.rwt.application.EntryPoint
import org.eclipse.rap.rwt.application.EntryPointFactory

/**
 * Configures the RWT application. An instance of this class will be registered as a Spring bean
 * and can then be injected where needed.
 *
 * @author Benjamin Wolff
 */
@Commons
class RwtApplicationConfiguration implements ApplicationConfiguration {

    private static final String FAVICON_PATH = '/favicon.ico'

    GrailsApplication grailsApplication
    Application rwtApplication
    ApplicationConfigurationBean applicationConfigurationBean
    EntryPointConfigurationHelper entryPointConfigurationHelper
    ThemeConfigurationHelper themeConfigurationHelper

    @Override
    void configure(Application application) {
        this.rwtApplication = application

        // Set the RWT operation mode.
        if (applicationConfigurationBean.operationMode) {
            application.setOperationMode(applicationConfigurationBean.operationMode)
            log.debug "RWT operation mode set to: ${applicationConfigurationBean.operationMode}"
        }

        // First we register the themes, they need to be present before we register the entry points.
        if (themeConfigurationHelper.hasThemes()) {
            themeConfigurationHelper.themeConfigurations.each { ThemeConfigurationBean conf ->
                conf.validateThemeFilesExists()

                // Themes configured under the default theme are contributions to the RWT default
                // theme.
                if (conf.isDefaultTheme()) {
                    registerTheme(application, RWT.DEFAULT_THEME_ID, conf.themeFilePaths)
                    log.debug "Adding CSS files as contribution to RWT default theme: ${conf.themeFilePaths}"
                } else {
                    registerTheme(application, conf.themeId, conf.themeFilePaths)
                    log.debug "Adding CSS files to custom RWT theme \"${conf.themeId}\": ${conf.themeFilePaths}"
                }
            }
        } else {
            log.debug "No RWT themes configured, skipping registration."
        }

        // Register the entry points.
        entryPointConfigurationHelper.entryPointConfigurations.each { EntryPointConfigurationBean conf ->
            validateReferencedThemeIdExists(conf)

            if (conf.favicon) {
                application.addResource(FAVICON_PATH, new RwtClasspathResourceLoader(conf.favicon))
                log.debug "Registered resource for favicon. Classpath resource: ${conf.favicon}"
                conf.favicon = FAVICON_PATH
            }

            EntryPointFactory entryPointFactory = getEntryPointFactoryForBean(conf.entryPointBeanName)
            application.addEntryPoint(conf.path, entryPointFactory, conf.entryPointProperties)

            log.debug "Registered RWT entry point." +
                    " Path: ${conf.path}," +
                    " EntryPoint bean: ${conf.entryPointBeanName}," +
                    " Properties: ${conf.entryPointProperties}"
        }
    }

    private void validateReferencedThemeIdExists(EntryPointConfigurationBean conf) {
        if (conf.themeId && !themeConfigurationHelper.hasThemeId(conf.themeId)) {
            throw new GrailsConfigurationException("[RWT] The entry point configuration" +
                " \"${conf.name}\" references a custom theme that was not configured: ${conf.themeId}")
        }
    }

    private EntryPointFactory getEntryPointFactoryForBean(String entryPointBeanName) {
        EntryPoint entryPointBean = grailsApplication.mainContext.getBean(entryPointBeanName, EntryPoint)
        return new RwtEntryPointFactory(entryPoint: entryPointBean)
    }

    private registerTheme(Application application, String themeId, List<String> themeFilePaths) {
        themeFilePaths.each { String path ->
            application.addStyleSheet(themeId, path)
        }
    }
}
