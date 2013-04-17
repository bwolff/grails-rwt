/*******************************************************************************
 * Copyright 2012-2013 Benjamin Wolff
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Benjamin Wolff - initial API and implementation
 ******************************************************************************/
package grails.plugins.rwt

import org.eclipse.rap.rwt.application.EntryPoint
import org.eclipse.rap.rwt.application.EntryPointFactory

/**
 * Simple implementation of an {@link EntryPointFactory}. A concrete {@link EntryPoint}
 * implementing object can be set, which is then returned by the {@link #create()} method.
 * <p>
 * This factory implementation is used to be passed to the entry point configuration in the
 * {@link RwtApplicationConfiguration}.
 *
 * @author Benjamin Wolff
 */
class RwtEntryPointFactory implements EntryPointFactory {

    EntryPoint entryPoint

    EntryPoint create() {
        return entryPoint
    }
}
