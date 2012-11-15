import org.eclipse.rap.rwt.application.Application.OperationMode;

import grails.plugins.rwt.EntryPointConfiguration;

class RwtGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/**",
        "web-app/css/**",
        "web-app/images/**",
        "web-app/js/**"
    ]

    def title = "The RAP Widget Toolkit (RWT) for Grails"
    def author = "Benjamin Wolff"
    def authorEmail = "benjamin.wolff@cern.ch"
    def description = '''\
This plugin integrates RWT - the web-ported Standard Widget Toolkit (SWT) - into your Grails
application. It enables you to build your RWT and JFace powered user interfaces using Groovy and the
complete Grails framework stack with all its powerful features and syntactic DSL sugar.
'''

    // URL to the plugin's documentation
    def documentation = "http://bwolff.github.com/grails-rwt/"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "github", url: "https://github.com/bwolff/grails-rwt/issues" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/bwolff/grails-rwt/" ]

    def doWithWebDescriptor = { xml ->
        // If there is no RWT entry points configured, don't register the RWT servlet.
        def paths = getEntryPointPaths(application)
        if (!paths) {
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
        paths.each { path ->
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
        def configurations = getEntryPointConfigurations(application)
        if (!configurations) {
            log.warn "No RWT entry points configured. Skipping RWT application configuration."
            return
        }

        // Extract the RWT operation mode. The operation mode defaults to JEE_COMPATIBILITY, which
        // is the recommended mode for new standalone RWT applications.
        // See: http://eclipse.org/rap/developers-guide/devguide.php?topic=advanced/application-setup.html#compat
        def opMode = application.config.rwt.operationmode ?: OperationMode.JEE_COMPATIBILITY
        
        // Create the RWT ApplicationConfiguration bean.
        rwtApplicationConfiguration(grails.plugins.rwt.ApplicationConfigurationBean) {
            grailsApplication = ref('grailsApplication')
            operationMode = opMode
            entryPointConfigurations = configurations
        }
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
    
    private getEntryPointPaths(def application) {
        return application.config.rwt.entrypoints.collect { name, values -> '/' + name }
    }
    
    private getEntryPointConfigurations(def application) {
        application.config.rwt.entrypoints.collect { name, values ->
            def path = '/' + name
            def entryPointBeanName = values.bean ?: null // TODO This would be an error!
            def entryPointConfiguration =  new EntryPointConfiguration()
            entryPointConfiguration.path = path
            entryPointConfiguration.entryPointBeanName = entryPointBeanName
            entryPointConfiguration.pageTitle = values.pageTitle ?: null
            entryPointConfiguration.favicon = values.favicon ?: null
            entryPointConfiguration.themeId = values.themeId ?: null
            entryPointConfiguration.headHtml = values.headHtml ?: null
            entryPointConfiguration.bodyHtml = values.bodyHtml ?: null
            return entryPointConfiguration
        }
    }
}
