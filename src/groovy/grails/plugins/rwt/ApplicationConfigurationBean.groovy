package grails.plugins.rwt

import groovy.util.logging.Commons;

import java.util.List;

import javax.security.auth.login.ConfigurationSpi;

import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.eclipse.rap.rwt.application.Application
import org.eclipse.rap.rwt.application.Application.OperationMode;
import org.eclipse.rap.rwt.application.ApplicationConfiguration
import org.eclipse.rap.rwt.lifecycle.IEntryPoint
import org.eclipse.rap.rwt.lifecycle.IEntryPointFactory

/**
 * TODO Document...
 *
 * @author Benjamin Wolff
 */
@Commons
class ApplicationConfigurationBean implements ApplicationConfiguration {

    GrailsApplication grailsApplication
    OperationMode operationMode
    List<EntryPointConfiguration> entryPointConfigurations

    @Override
    public void configure(Application application) {
        application.setOperationMode(operationMode)
        log.info "RWT operation mode set to: ${operationMode}"

        entryPointConfigurations.each { EntryPointConfiguration conf ->
            IEntryPointFactory entryPointFactory = getEntryPointFactoryForBean(conf.entryPointBeanName)
            application.addEntryPoint(conf.path, entryPointFactory, conf.props)

            log.info "Registered RWT entry point -" +
                    " Path: ${conf.path}," +
                    " EntryPoint bean: ${conf.entryPointBeanName}," +
                    " Properties: ${conf.props}"
        }
    }

    IEntryPointFactory getEntryPointFactoryForBean(String entryPointBeanName) {
        IEntryPoint entryPointBean = grailsApplication.mainContext.getBean(entryPointBeanName, IEntryPoint)
        return new EntryPointFactory(entryPoint: entryPointBean)
    }
}
