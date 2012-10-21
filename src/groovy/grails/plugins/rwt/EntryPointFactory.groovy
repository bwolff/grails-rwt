package grails.plugins.rwt

import org.eclipse.rap.rwt.lifecycle.IEntryPoint
import org.eclipse.rap.rwt.lifecycle.IEntryPointFactory;

/**
 * TODO Document...
 *
 * @author Benjamin Wolff
 */
class EntryPointFactory implements IEntryPointFactory {

    IEntryPoint entryPoint

    @Override
    public IEntryPoint create() {
        return entryPoint
    }
}
