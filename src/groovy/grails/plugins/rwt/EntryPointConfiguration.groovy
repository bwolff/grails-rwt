package grails.plugins.rwt

import java.util.Map;

import org.eclipse.rap.rwt.lifecycle.IEntryPointFactory;

/**
 * TODO Document...
 *
 * @author Benjamin Wolff
 */
class EntryPointConfiguration {

    String path
    String entryPointBeanName
    Map<String, String> props
}
