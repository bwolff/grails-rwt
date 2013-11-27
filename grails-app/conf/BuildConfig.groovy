grails.project.work.dir = "target/${grailsVersion}"
grails.project.target.level = 1.7
grails.project.source.level = 1.7

grails.project.dependency.resolver = "maven"

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
        mavenLocal()
        mavenCentral() // Required for resolving the RWT bundles.
	}

    dependencies {
        compile('org.eclipse.rap:org.eclipse.rap.rwt:2.1.1')
        compile('org.eclipse.rap:org.eclipse.rap.jface:2.1.1')
        compile('org.eclipse.rap:org.eclipse.rap.jface.databinding:2.1.1')
    }

	plugins {
		build(':release:3.0.1', ':rest-client-builder:2.0.0') {
			export = false
		}
	}
}
