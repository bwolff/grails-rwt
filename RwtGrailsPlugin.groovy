import grails.plugins.rwt.EntryPointConfigurationHelper
import grails.plugins.rwt.ThemeConfigurationHelper
import grails.plugins.rwt.ApplicationConfigurationBean

class RwtGrailsPlugin {
    def version = "1.1"
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

    def pluginExcludes = [
        "web-app/"
    ]
    
    def doWithWebDescriptor = { xml ->
        // If there is no RWT entry point configured, don't register the RWT servlet.
        final entryPointHelper = new EntryPointConfigurationHelper(application.config)
        
        if (entryPointHelper.hasEntryPoints()) {
            // Register the RWT servlet as the last servlet.
            def servletElement = xml.'servlet'
            def lastMapping = servletElement[servletElement.size() - 1]
            lastMapping + {
                'servlet' {
                    'servlet-name'("rwtServlet")
                    'servlet-class'("org.eclipse.rap.rwt.engine.RWTServlet")
                }
            }
            
            log.info "Registered RWT servlet."
    
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
        } else {
            log.warn "No RWT entry point configured. Skipping RWT servlet registration ..."
        }
    }

    def doWithSpring = {
        // If there is no RWT entry points configured, don't create the RWT application
        // configuration bean.
        final entryPointHelper = new EntryPointConfigurationHelper(application.config)
        
        if (entryPointHelper.hasEntryPoints()) {
            final applicationConf = new ApplicationConfigurationBean(application.config)
            final themeHelper = new ThemeConfigurationHelper(application.config)
            
            // Create the RWT ApplicationConfiguration bean.
            rwtApplicationConfiguration(grails.plugins.rwt.RwtApplicationConfiguration) {
                grailsApplication = ref('grailsApplication')
                applicationConfigurationBean = applicationConf
                entryPointConfigurationHelper = entryPointHelper
                themeConfigurationHelper = themeHelper
            }
        } else {
            log.warn "No RWT entry points configured. Skipping RWT application configuration ..."
        }
    }
}
