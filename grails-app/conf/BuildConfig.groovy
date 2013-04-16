grails.project.work.dir = 'target'
grails.project.source.level = 1.6

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
	}

    dependencies {
        compile('org.eclipse.rap:org.eclipse.rap.rwt:2.0.0')
        compile('org.eclipse.rap:org.eclipse.rap.jface:2.0.0')
        compile('org.eclipse.rap:org.eclipse.rap.jface.databinding:2.0.0')
    }

	plugins {
		build(':release:2.2.1', ':rest-client-builder:1.0.3') {
			export = false
		}
	}
}
