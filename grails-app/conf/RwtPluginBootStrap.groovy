import org.eclipse.rap.rwt.application.ApplicationConfiguration
import org.eclipse.rap.rwt.application.ApplicationRunner

/**
 *
 * @author Benjamin Wolff
 */
class RwtPluginBootStrap {

    ApplicationConfiguration rwtApplicationConfiguration
    ApplicationRunner applicationRunner

    def init = { servletContext ->
        if (rwtApplicationConfiguration) {
            log.info "Starting RWT application ..."
            applicationRunner = new ApplicationRunner(rwtApplicationConfiguration, servletContext);
            applicationRunner.start();
            log.info "... RWT application started."
        }
    }

    def destroy = {
        if (applicationRunner) {
            applicationRunner.stop();
            applicationRunner = null;
            log.info "RWT application destroyed."
        }
    }
}
