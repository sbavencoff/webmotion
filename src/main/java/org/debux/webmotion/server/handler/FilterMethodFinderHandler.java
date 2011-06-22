/*
 * #%L
 * Webmotion in action
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Debux
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.debux.webmotion.server.handler;

import java.util.List;
import org.debux.webmotion.server.call.Call;
import org.debux.webmotion.server.mapping.Config;
import org.debux.webmotion.server.mapping.FilterRule;
import org.debux.webmotion.server.mapping.Mapping;
import org.debux.webmotion.server.mapping.Action;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.debux.webmotion.server.WebMotionAction;
import org.debux.webmotion.server.WebMotionHandler;
import org.debux.webmotion.server.WebMotionUtils;
import org.debux.webmotion.server.WebMotionException;
import org.debux.webmotion.server.call.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Search in mapping the filters matched at the url.
 * 
 * @author julien
 */
public class FilterMethodFinderHandler implements WebMotionHandler {

    private static final Logger log = LoggerFactory.getLogger(FilterMethodFinderHandler.class);

    @Override
    public void handle(Mapping mapping, Call call) {
        List<FilterRule> filterRules = call.getFilterRules();
        List<Executor> filters = new ArrayList<Executor>(filterRules.size());
        call.setFilters(filters);

        for (FilterRule filterRule : filterRules) {
            Action action = filterRule.getAction();

            Config config = mapping.getConfig();
            String packageName = config.getPackageFilters();
            String className = action.getClassName();
            String fullQualifiedName = packageName + "." + className;

            try {
                Class<WebMotionAction> clazz = (Class<WebMotionAction>) Class.forName(fullQualifiedName);

                String methodName = action.getMethodName();
                Method method = WebMotionUtils.getMethod(clazz, methodName);

                Executor executor = new Executor();
                executor.setClazz(clazz);
                executor.setMethod(method);
                filters.add(executor);
                
            } catch (ClassNotFoundException clnfe) {
                throw new WebMotionException("Class not found with name " + fullQualifiedName, clnfe);
            }
        }
    }
}
