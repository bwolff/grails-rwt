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

import groovy.util.ConfigObject

import org.eclipse.rap.rwt.application.Application.OperationMode

/**
 * Simple pogo that encapsulates the configuration for the RWT application. There is only one
 * application configuration that can be configured in the Config.groovy file.
 *
 * @author Benjamin Wolff
 */
class ApplicationConfigurationBean {

    /**
     * The RWT operation mode. If not provided, the operation mode defaults to JEE_COMPATIBILITY,
     * which is the recommended mode for new standalone RWT applications.
     *
     * See: http://eclipse.org/rap/developers-guide/devguide.php?topic=advanced/application-setup.html#compat
     */
    OperationMode operationMode

    public ApplicationConfigurationBean(ConfigObject grailsConfig) {
        extractApplicationConfiguration(grailsConfig)
    }

    private void extractApplicationConfiguration(ConfigObject grailsConfig) {
        operationMode = grailsConfig.rwt.operationMode ?: null
    }
}
