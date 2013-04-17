package grails.plugins.rwt

import java.io.IOException
import java.io.InputStream;

import org.eclipse.rap.rwt.service.ResourceLoader;

class RwtClasspathResourceLoader implements ResourceLoader {

    final String resourcePath

    public RwtClasspathResourceLoader(String resourcePath) {
        this.resourcePath = resourcePath
    }
    @Override
    public InputStream getResourceAsStream(String resourceName) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)
        return is
    }
}
