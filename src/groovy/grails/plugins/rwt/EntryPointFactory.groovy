package grails.plugins.rwt

import org.eclipse.rap.rwt.lifecycle.IEntryPoint
import org.eclipse.rap.rwt.lifecycle.IEntryPointFactory

/**
 * Simple implementation of an {@link IEntryPointFactory}. A concrete {@link IEntryPoint}
 * implementing object can be set, which is then returned by the {@link #create()} method.
 * <p>
 * This factory implementation is used to be passed to the entry point configuration in the
 * {@link ApplicationConfigurationBean}.
 *
 * @author Benjamin Wolff
 */
class EntryPointFactory implements IEntryPointFactory {

    IEntryPoint entryPoint

    IEntryPoint create() {
        return entryPoint
    }
}
