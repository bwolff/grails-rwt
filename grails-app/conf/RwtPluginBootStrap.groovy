import org.eclipse.rap.rwt.application.ApplicationConfiguration
import org.eclipse.rap.rwt.application.ApplicationRunner

/**
 * This bootstrap class is executed when the application, and therefore this plugin, is started. It
 * fires up the RWT application using the {@link ApplicationRunner} and cleans up when the
 * application is stopped.
 * <p>
 * The {@link ApplicationConfiguration} is configured and initialised as a Spring bean and
 * automatically injected on startup.
 *
 * @author Benjamin Wolff
 */
class RwtPluginBootStrap {

    ApplicationConfiguration rwtApplicationConfiguration
    ApplicationRunner applicationRunner

    def init = { servletContext ->
        if (rwtApplicationConfiguration) {
            log.info "Starting RWT application ..."
            applicationRunner = new ApplicationRunner(rwtApplicationConfiguration, servletContext)
            applicationRunner.start()
            log.info "... RWT application started."
        }
    }

    def destroy = {
        if (applicationRunner) {
            log.info "Stopping RWT application ..."
            applicationRunner.stop()
            applicationRunner = null
            log.info "... RWT application stopped."
        }
    }
}
