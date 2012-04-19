/*
 * #%L
 * WebMotion extras
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
package org.debux.webmotion.jpa;

import java.lang.reflect.Type;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import org.debux.webmotion.server.WebMotionMainHandler;
import org.debux.webmotion.server.call.Call;
import org.debux.webmotion.server.call.HttpContext;
import org.debux.webmotion.server.call.ServerContext;
import org.debux.webmotion.server.handler.ExecutorParametersInjectorHandler.Injector;
import org.debux.webmotion.server.mapping.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Add configuration to use Jpa :
 * <ul>
 * <li>Add Jpa as global controller</li>
 * <li>Add GenericDaoInjector as injector to get the DAO in Jpa controller</li>
 * <li>Add EntityManagerInjector as injector to get EntityManager</li>
 * <li>Add EntityTransactionManager as injector to get EntityTransaction</li>
 * </ul>
 * 
 * @author julien
 */
public class JpaMainHandler extends WebMotionMainHandler {

    private static final Logger log = LoggerFactory.getLogger(JpaMainHandler.class);

    @Override
    protected void initHandlers(Mapping mapping, ServerContext context) {
        super.initHandlers(mapping, context);
        
        context.addGlobalController(Jpa.class);
        
        context.addInjector(new GenericDaoInjector());
        context.addInjector(new EntityManagerInjector());
        context.addInjector(new EntityTransactionManager());
    }

    /**
     * Inject EntityTransaction
     */
    public static class EntityTransactionManager implements Injector {
        @Override
        public Object getValue(Mapping mapping, Call call, Class<?> type, Type generic) {
            if (EntityTransaction.class.isAssignableFrom(type)) {
                HttpContext httpContext = call.getContext();
                HttpServletRequest request = httpContext.getRequest();
                return request.getAttribute(Transactional.CURRENT_ENTITY_TRANSACTION);
            }
            return null;
        }
    }

    /**
     * Inject EntityManager
     */
    public static class EntityManagerInjector implements Injector {
        @Override
        public Object getValue(Mapping mapping, Call call, Class<?> type, Type generic) {
            if (EntityManager.class.isAssignableFrom(type)) {
                HttpContext httpContext = call.getContext();
                HttpServletRequest request = httpContext.getRequest();
                return request.getAttribute(Transactional.CURRENT_ENTITY_MANAGER);
            }
            return null;
        }
    }

    /**
     * Inject GenericDAO
     */
    public static class GenericDaoInjector implements Injector {
        @Override
        public Object getValue(Mapping mapping, Call call, Class<?> type, Type generic) {
            if (GenericDAO.class.isAssignableFrom(type)) {
                HttpContext httpContext = call.getContext();
                HttpServletRequest request = httpContext.getRequest();
                return request.getAttribute(Transactional.CURRENT_GENERIC_DAO);
            }
            return null;
        }
    }

}
