import grails.plugins.rwt.EntryPointConfigurationHelper
import grails.plugins.rwt.ThemeConfigurationHelper

class RwtGrailsPlugin {
    def version = "0.1"
    def grailsVersion = "2.0 > *"
    def title = "The RAP Widget Toolkit (RWT) for Grails"
    def author = "Benjamin Wolff"
    def authorEmail = "benjamin.wolff@cern.ch"
    def description = '''\
This plugin integrates RWT - the web-ported Standard Widget Toolkit (SWT) - into your Grails
application. It enables you to build your RWT and JFace powered user interfaces using Groovy and the
complete Grails framework stack with all its powerful features and syntactic DSL sugar.
'''

    def documentation = "http://bwolff.github.com/grails-rwt/"
    def license = "APACHE"
    def issueManagement = [ system: "github", url: "https://github.com/bwolff/grails-rwt/issues" ]
    def scm = [ url: "https://github.com/bwolff/grails-rwt/" ]

    def doWithWebDescriptor = { xml ->
        // If there is no RWT entry point configured, don't register the RWT servlet.
        final entryPointHelper = new EntryPointConfigurationHelper(application.config)
        if (!entryPointHelper.hasEntryPoints()) {
            log.warn "No RWT entry point configured. Skipping RWT servlet registration ..."
            return
        }

        // Register the RWT servlet as the last servlet.
        def servletElement = xml.'servlet'
        def lastMapping = servletElement[servletElement.size() - 1]
        lastMapping + {
            'servlet' {
                'servlet-name'("rwtServlet")
                'servlet-class'("org.eclipse.rap.rwt.engine.RWTServlet")
            }
        }

        // Add an RWT servlet mapping for each configured entry point path.
        entryPointHelper.entryPointPaths.each { path ->
            def mappingElement = xml.'servlet-mapping'
            lastMapping = mappingElement[mappingElement.size() - 1]
            lastMapping + {
                'servlet-mapping' {
                    'servlet-name'("rwtServlet")
                    'url-pattern'(path)
                }
            }

            log.info "Registered RWT servlet mapping for entry point path: ${path}"
        }
    }

    def doWithSpring = {
        // If there is no RWT entry points configured, don't create the RWT application
        // configuration bean.
        final entryPointHelper = new EntryPointConfigurationHelper(application.config)
        if (!entryPointHelper.hasEntryPoints()) {
            log.warn "No RWT entry points configured. Skipping RWT application configuration."
            return
        }
        
        final themeHelper = new ThemeConfigurationHelper(application.config)

        // Extract the RWT operation mode. If not provided, the operation mode defaults to
        // JEE_COMPATIBILITY, which is the recommended mode for new standalone RWT applications.
        // See: http://eclipse.org/rap/developers-guide/devguide.php?topic=advanced/application-setup.html#compat
        final opMode = application.config.rwt.operationmode ?: null

        // Create the RWT ApplicationConfiguration bean.
        rwtApplicationConfiguration(grails.plugins.rwt.ApplicationConfigurationBean) {
            grailsApplication = ref('grailsApplication')
            operationMode = opMode
            entryPointConfigurationHelper = entryPointHelper
            themeConfigurationHelper = themeHelper
        }
    }
}
